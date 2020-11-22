package com.example.homicide

import android.graphics.Bitmap
import android.graphics.Rect

enum class CharacterMoveDirection {
    LEFT,
    RIGHT,
    STATIONARY
}

open class Character: Entity {

    // Determines how fast the walk cycle animation is displayed.
    var animationSpeed = 1.0f;

    lateinit var animationBoxes: List<Rect>;

    lateinit var leftAnimationCycles: List<Int>;
    lateinit var rightAnimationCycles: List<Int>;

    constructor(
        bitmap: Bitmap,
        hitbox: Rect,
        animationBoxes: List<Rect>,
        leftAnimationCycles: List<Int>,
        rightAnimationCycles: List<Int>,
        xAcceleration: Int = 0,
        yAcceleration: Int = 50,
    ): super(
        bitmap = bitmap,
        hitbox = hitbox,
        xAcceleration = xAcceleration,
        yAcceleration = yAcceleration
    ) {
        this.animationBoxes = animationBoxes;
        this.leftAnimationCycles = leftAnimationCycles;
        this.rightAnimationCycles = rightAnimationCycles;

        this.xVelocity = 0;
        this.src = this.animationBoxes[1];
    }

    public fun jump() {
        this.yVelocity = -1200;
    }

    public fun move(m: CharacterMoveDirection) {
        when(m) {
            CharacterMoveDirection.RIGHT -> {
                this.xVelocity = 600;
                this.src = this.animationBoxes[1];
            }
            CharacterMoveDirection.LEFT -> {
                this.xVelocity = -600;
                this.src = this.animationBoxes[0];
            }
            CharacterMoveDirection.STATIONARY -> {
                this.xVelocity = 0;
                this.src = this.animationBoxes[1];
            }
        }
    }
}