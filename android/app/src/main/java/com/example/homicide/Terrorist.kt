package com.example.homicide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class TerroristSpawner: Spawner {
    private lateinit var bitmap: Bitmap;

    constructor(context: Context) {
        val options = BitmapFactory.Options();
        options.inScaled = false;
        this.bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.terrorist,
            options
        );
    }

    override fun spawn(position: Position): Entity {
        return Terrorist(
            this.bitmap,
            animationBoxes = listOf(Rect(0, 0, 238, 290), Rect(238, 0, 462, 290)),
            leftAnimationCycles = listOf(1),
            rightAnimationCycles = listOf(2),
            hitbox = Rect(position.x - 90, position.y - 150, position.x + 90, position.y + 150)
        )
    }

    override fun spawn(rect: Rect): Entity {
        return Terrorist(
            this.bitmap,
            animationBoxes = listOf(Rect(0, 0, 238, 290), Rect(238, 0, 462, 290)),
            leftAnimationCycles = listOf(1),
            rightAnimationCycles = listOf(2),
            hitbox = rect
        );
    }
}

class Terrorist: Character2 {
    constructor(
        bitmap: Bitmap,
        hitbox: Rect,
        animationBoxes: List<Rect>,
        leftAnimationCycles: List<Int>,
        rightAnimationCycles: List<Int>,
    ): super(
        bitmap = bitmap,
        hitbox = hitbox,
        animationBoxes = animationBoxes,
        leftAnimationCycles = leftAnimationCycles,
        rightAnimationCycles = rightAnimationCycles
    ) {

    }
}
