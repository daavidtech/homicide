package com.example.homicide

class EntitySpawner {
    private val spawns = HashMap<String, Spawner>();

    public fun addSpawn(entityType: String, spawn: Spawner) {
        this.spawns.put(entityType, spawn);
    }

    public fun spawnEntity(entityType: String, position: Position): Entity {
        val spawner = this.spawns[entityType];

        return spawner!!.spawn(position);
    }
}