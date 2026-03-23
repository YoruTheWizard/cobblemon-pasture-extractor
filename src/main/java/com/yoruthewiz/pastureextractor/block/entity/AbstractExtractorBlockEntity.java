package com.yoruthewiz.pastureextractor.block.entity;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.yoruthewiz.pastureextractor.Config;
import com.yoruthewiz.pastureextractor.PastureExtractor;
import com.yoruthewiz.pastureextractor.tier.ExtractorTier;
import com.yoruthewiz.pastureextractor.util.ExtractOnlyStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractExtractorBlockEntity extends BlockEntity {
    private static final Logger LOGGER = PastureExtractor.LOGGER;

    private final ExtractorTier tier;
    public final ExtractOnlyStackHandler inventory;

    public AbstractExtractorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, ExtractorTier tier) {
        super(type, pos, blockState);
        this.tier = tier;
        this.inventory = new ExtractOnlyStackHandler(tier.getInventorySize()) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (!level.isClientSide) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }
        };
    }

    private static Config getConfig() {
        return PastureExtractor.instance.getConfig();
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public IItemHandler getInventory(@Nullable Direction side) {
        return inventory;
    }

    public void clearContents() {
        for (int i = 0; i < inventory.getSlots(); i++)
            inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++)
            container.setItem(i, inventory.getStackInSlot(i));
        Containers.dropContents(this.level, this.worldPosition, container);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().blockChanged(worldPosition);
        }
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, AbstractExtractorBlockEntity extractorBlockEntity) {
        if (level.isClientSide) return;
        if (level.getGameTime() % getConfig().getCooldown() == 0) extractorBlockEntity.tryGenerateItem();
    }

    private void tryGenerateItem() {
        if (!(level instanceof ServerLevel serverLevel)) return;
        LOGGER.debug("Trying to generate item");
        if (isInventoryFull()) {
            LOGGER.warn("Inventory is full");
            return;
        }

        PokemonPastureBlockEntity pasture = findPastureBlock();
        if (pasture == null) {
            LOGGER.warn("There is no pasture block nearby.");
            return;
        }

        List<PokemonPastureBlockEntity.Tethering> tethering = pasture.getTetheredPokemon();
        if (tethering.isEmpty()) {
            LOGGER.warn("There is no pokemon in pasture.");
            return;
        }

        Pokemon pokemon = tethering.get(level.random.nextInt(tethering.size())).getPokemon();
        if (pokemon == null) return;
        LOGGER.debug("Chosen pokemon: {}.", pokemon.getSpecies().getName());

        float chance = tier.getBaseChance();
        if (!getConfig().ignoreFriendship())
            chance *= (0.5F + 0.5F * (pokemon.getFriendship() / 255F));
        LOGGER.debug("Chance: {}", chance);

        if (level.random.nextFloat() > chance) {
            LOGGER.debug("Drop roll failed.");
            return;
        }

        ItemStack result = rollLoot(serverLevel, pokemon);
        if (result.isEmpty()) {
            LOGGER.debug("Could not get loot item.");
            return;
        }

        insertItem(result);
        LOGGER.debug("Extracted loot item.");
    }

    private PokemonPastureBlockEntity findPastureBlock() {
        for (Direction dir : Direction.values()) {
            BlockEntity be = level.getBlockEntity(worldPosition.relative(dir));
            if (be instanceof PokemonPastureBlockEntity pasture) return pasture;
        }
        return null;
    }

    private ItemStack rollLoot(ServerLevel level, Pokemon pokemon) {
        try {
            FormData form = pokemon.getForm();
            DropTable dropTable = form.getDrops();
            List<DropEntry> drops = dropTable.getDrops(dropTable.getAmount(), pokemon); // Get pokemon's drop table
            if (drops.isEmpty()) return ItemStack.EMPTY;

            DropEntry drop = drops.get(level.random.nextInt(drops.size())); // Get random entry from drop table
            if (!(drop instanceof ItemDropEntry itemDropEntry)) return ItemStack.EMPTY;
            if (Arrays.asList(getConfig().getItemBlacklist()).contains(itemDropEntry.getItem().toString())) return ItemStack.EMPTY; // Skip blacklisted items

            Item item = level.registryAccess().registryOrThrow(Registries.ITEM).get(itemDropEntry.getItem());
            if (item == null) return ItemStack.EMPTY;

            if (!tier.isGetStack()) return new ItemStack(item); // Return only 1 item

            int quantity = itemDropEntry.getQuantity();
            if (itemDropEntry.getQuantityRange() != null)
                quantity = (int) Math.round(Math.random() * (itemDropEntry.getQuantityRange().getLast()));
            if (quantity < 1) return ItemStack.EMPTY;

            return new ItemStack(item, quantity);
        } catch (Exception e) {
            LOGGER.error("Error rolling loot.", e);
            return ItemStack.EMPTY;
        }
    }

    private void insertItem(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            stack = inventory.forceInsert(i, stack);
            setChanged();
            if (stack.isEmpty()) break;
        }
    }

    private boolean isInventoryFull() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }
}
