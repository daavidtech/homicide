package com.example.homicide

data class Collision(val entity: Entity, val position: Position);

class PhysicsSim: Subject<Collision> {
    constructor(): super() {

    }

    public fun update() {

    }

    public fun add(entity: Entity) {

    }
}