# Cobblemon Pasture Extractor
Collects items from Pokémon in nearby Pasture Blocks in Cobblemon.

## NOTE!
This mod was inspired by [Cobblemon Pasture Collector](https://github.com/timinc-cobble/cobblemon-pasturecollector-1.6-fabric/tree/main) Mod by TimInc.

## Features
- Three tiers of Pasture Extractors: **Iron**, **Gold** and **Diamond**, each with different inventory size and probability of extracting items (configurable);
- The Extractors will *randomly* choose a Pokémon inside an adjacent Pasture Block and take a *random* item from its drop table (cooldown is configurable);
- Possible drops are the same players would get if the Pokémon were defeated in battle;
- Probability scales with Pokémon's friendship level (less friendship, less chance) (also configurable);
- Players can get items from Extractors by right-clicking them, by opening their menus (shift + right-click), or via hoppers.
  - Extractors are **output-only**. Players can get items, but not insert items into them.

Default config (`config/pastureextractor.json`):
```json
{
  "baseDropChances": {
    "iron": 0.15,
    "gold": 0.3,
    "diamond": 0.5
  },
  "extractorCooldown": 200,
  "itemBlacklist": [],
  "ignoreFriendship": false
}
```

## Dependencies
- Cobblemon
