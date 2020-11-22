package com.example.homicide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class PlatformSpawner: Spawner {
    private lateinit var bitmap: Bitmap;

    constructor(context: Context) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        this.bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.wall,
            options
        );
    }

    override fun spawn(rect: Rect): Entity {
        return Platform(
            hitbox = rect,
            bitmap = this.bitmap
        );
    }

    override fun spawn(position: Position): Entity {
        return Platform(
            hitbox = Rect(position.x - 100, position.y - 25, position.x + 100, position.y + 25),
            bitmap = this.bitmap
        )
    }
}

class Platform: Entity {
    constructor(
        bitmap: Bitmap,
        hitbox: Rect,
    ): super(
        bitmap = bitmap,
        hitbox = hitbox,
        isPlatform = true,
    ) {

    }
}