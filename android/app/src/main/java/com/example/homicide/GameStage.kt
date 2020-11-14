package com.example.homicide

import android.graphics.Canvas
import android.graphics.Rect

class GameStage {
    val objects = mutableListOf<MapObject>();

    public fun addMapObject(mapObject: MapObject) {
        this.objects.add(mapObject);
    }

/*    public fun getCollisions(r: Rect): List<MapObject> {
*//*        var collionObjects = mutableListOf<ObjectPhycis>();

        for (o in this.objects) {
            o.collidesWith(r);
        }

        return collionObjects;*//*
    }*/

    public fun draw(canvas: Canvas, view: Rect) {
        this.calculateChanges();

        for (o in this.objects) {
            if (view.contains(o.hitbox)) {
                o.draw(canvas);
            }
        }
    }

    private fun calculateChanges() {
        for (o in this.objects) {
            o.calculateChanges();
        }

        for (o in this.objects) {
            for (o in this.objects) {

            }
        }
    }
}