# Cobblemon Pasture Extractor
Collects items from Pokémon in nearby Pasture Blocks in Cobblemon.

## NOTE!
This mod was inspired by [Cobblemon Pasture Collector](https://github.com/timinc-cobble/cobblemon-pasturecollector-1.6-fabric/tree/main) Mod by TimInc.

## Features
There are currently three tiers of Pasture Extractors: **Iron**, **Gold** and **Diamond**, each with different inventory size and probability of extracting items (configurable).
The Extractors will *randomly* choose a Pokémon inside an adjacent Pasture Block and take a *random* item from its drop table (cooldown is configurable).
- The Extractor must be *directly adjacent* to a Pasture Block for it to work (top and bottom excluded);
- If more than one Pasture block is adjacent to an Extractor, it *randomly* chooses *one* of them;
- If more than one Extractor is adjacent to a single Pasture Block, the one with *higher tier* gets the items;
  - If Extractors have the same tier, the priority is chosen by their *relative position* from the Pasture block:
    **North** > **West** > **East** > **South**.

Possible drops are the same players would get if the Pokémon were defeated in battle, and probability scales with Pokémon's friendship level (less friendship, less chance) (also configurable).

Each cycle, the Extractors can emit particles:
- **Broken heart**: Higher priority Extractor at the same Pasture Block;
- **Smoke**: Item drop failed;
- **Green**: Successfully collected item;
- *No particle*: No Pasture Block nearby or no Pokémon inside adjacent Pasture Block.

Players can get items from Extractors by *right-clicking* them, by *opening their menus* (shift + right-click), or via *hoppers*.
- Extractors are **output-only**. Players can get items, but not insert items into them.

**Default config (`config/pastureextractor.json`):**
```jsonc
{
  "baseDropChances": {
    "iron": 0.15,
    "gold": 0.3,
    "diamond": 0.5
  },
  "extractorCooldown": 200, // 200 ticks -> 10 seconds
  "itemBlacklist": [],
  "ignoreFriendship": false
}
```

## Dependencies
- Cobblemon

## Credits
Huge shoutout to:
- Cobbled Studios Team - Cobblemon mod creators
- TimInc - Cobblemon Pasture Collector mod creator
- ZypherConspirator - Extractors models and textures
