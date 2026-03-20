package com.yoruthewiz.pastureextractor.block.entity;

import com.cobblemon.mod.common.api.drop.DropEntry;
import com.cobblemon.mod.common.api.drop.DropTable;
import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.cobblemon.mod.common.block.entity.PokemonPastureBlockEntity;
import com.cobblemon.mod.common.pokemon.FormData;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.yoruthewiz.pastureextractor.Config;
import com.yoruthewiz.pastureextractor.PastureExtractor;
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
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

public class ExtractorBlockEntity extends BlockEntity {
    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };
    private static final Logger LOGGER = PastureExtractor.LOGGER;

    public ExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_BE.get(), pos, blockState);
    }

    private static Config getConfig() {
        return PastureExtractor.instance.getConfig();
    }

    public void clearContents() {
        inventory.setStackInSlot(0, ItemStack.EMPTY);
    }

    public void drops() {
        SimpleContainer container = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            container.setItem(i, inventory.getStackInSlot(i));
        }
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
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity extractorBlockEntity) {
        if (level.isClientSide) return;
        if (level.getGameTime() % getConfig().getCooldown() == 0) extractorBlockEntity.tryGenerateItem();
    }

    private void tryGenerateItem() {
        if (!(level instanceof ServerLevel serverLevel)) return;
        LOGGER.debug("Trying to generate item");
        if (!isInventoryEmpty()) {
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

        float happiness = pokemon.getFriendship() / 255.0F;
        float chance = getConfig().getBaseDropChance() * (0.5F + 0.5F * happiness);

        if (level.random.nextFloat() > chance) {
            LOGGER.debug("Drop roll failed. Chance: {}.", chance);
            return;
        }

        ItemStack result = rollLoot(serverLevel, pokemon);
        if (result.isEmpty()) return;

        result.setCount(1);
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
            List<DropEntry> drops = dropTable.getDrops(dropTable.getAmount(), pokemon);
            if (drops.isEmpty()) return ItemStack.EMPTY;

            DropEntry drop = drops.get(level.random.nextInt(drops.size()));
            if (!(drop instanceof ItemDropEntry itemDropEntry)) return ItemStack.EMPTY;
            if (Arrays.asList(getConfig().getItemBlacklist()).contains(itemDropEntry.getItem().toString())) return ItemStack.EMPTY;

            Item item = level.registryAccess().registryOrThrow(Registries.ITEM).get(itemDropEntry.getItem());
            if (item == null || itemDropEntry.getQuantity() < 1) return ItemStack.EMPTY;
            return new ItemStack(item, itemDropEntry.getQuantity());
        } catch (Exception e) {
            LOGGER.error("Error rolling loot.", e);
            return ItemStack.EMPTY;
        }
    }

    private void insertItem(ItemStack stack) {
        for (int i = 0; i < inventory.getSlots(); i++) {
            stack = inventory.insertItem(i, stack, false);
            if (stack.isEmpty()) break;
        }
    }

    private boolean isInventoryEmpty() {
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }
}
