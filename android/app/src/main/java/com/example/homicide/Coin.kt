package com.example.homicide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class CoinSpawner: Spawner {
    private lateinit var bitmap: Bitmap;

    constructor(context: Context) {
        val options = BitmapFactory.Options();
        options.inScaled = false;
        this.bitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.coin,
            options
        );
    }

    override fun spawn(position: Position): Entity {
        return Coin(
            hitbox = Rect(position.x - 50, position.y - 50, position.x + 50, position.y + 50),
            bitmap = this.bitmap,
        );
    }

    override fun spawn(rect: Rect): Entity {
        return Coin(
            hitbox = rect,
            bitmap = this.bitmap
        );
    }
}

class Coin: Entity {
    constructor(
        hitbox: Rect,
        bitmap: Bitmap,
    ): super(
        hitbox = hitbox,
        bitmap = bitmap
    ) {

    }

    public fun calculateValue(): Int {
        return 1;
    }
}
