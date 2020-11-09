package com.example.homicide

import android.graphics.Bitmap
import android.graphics.Rect
import android.view.Gravity

enum class CharacterMoveDirection {
    LEFT,
    RIGHT,
    STATIONARY
}

class Character {
    // Which direction character is indenting to move
    var moveDirection = CharacterMoveDirection.STATIONARY;

    var xVelocity = 0;

    // Characters velocity related to y axis gravity affects
    // how fast this value decreases.
    var yVelocity = 0;

    var gravity = 1;

    // Speed which character is able to move.
    var speed = 100;

    // Determines how fast the walk cycle animation is displayed.
    var animationSpeed = 1.0f;

    // Determines which animation is currently in use.
    var animationNumber = 0

    lateinit var animationBoxes: List<Rect>;

    lateinit var leftAnimationCycles: List<Int>;
    lateinit var rightAnimationCycles: List<Int>;

    lateinit var spriteBitmap: Bitmap;

    constructor(
        gravity: Int? = null,
        animationSpeed: Float = 1.0f,
        animationBoxes: List<Rect>,
        leftAnimationCycles: List<Int>,
        rightAnimationCycles: List<Int>,
        speed: Int? = null,
    ) {
        if (gravity != null) {
            this.gravity = gravity;
        }

        if (speed != null) {
            this.speed = speed;
        }

        this.animationSpeed = animationSpeed;
        this.animationBoxes = animationBoxes;
        this.leftAnimationCycles = leftAnimationCycles;
        this.rightAnimationCycles = rightAnimationCycles;
    }

    public fun move(m: CharacterMoveDirection) {
        this.moveDirection = m;
    }

    public fun jump() {
        this.yVelocity = 50;
    }

    public fun onDraw() {

    }
}