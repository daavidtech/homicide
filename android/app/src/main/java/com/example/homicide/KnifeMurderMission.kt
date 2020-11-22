package com.example.homicide

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect

class KnifeMurderMission: Mission {
    private val engine = Engine();

    private lateinit var gameControls: GameControls;

    private lateinit var player: Terrorist;

    constructor(context: Context, gameControls: GameControls) {
        this.gameControls = gameControls;

        val options = BitmapFactory.Options();

        this.engine.implementSpawner("platform", PlatformSpawner(context));
        this.engine.implementSpawner("terrorist", TerroristSpawner(context));

       this.engine.spawnEntity("platform", Rect(
            50,
            800,
            500,
            900
        ))

        this.engine.spawnEntity("platform", Rect(
            600,
            600,
            800,
            700
        ))


        this.engine.spawnEntity("platform", Rect(
            900,
            500,
            1300,
            600
        ))

        this.engine.spawnEntity("platform", Rect(
            1500,
            400,
            1700,
            500
        ))

        this.engine.spawnEntity("platform", Rect(
            900,
            950,
            2100,
            1000
        ))

        this.engine.spawnEntity("platform", Rect(
            2000,
            800,
            2200,
            900
        ))

        this.engine.spawnEntity("platform", Rect(
            2050,
            700,
            2300,
            800
        ))

        this.engine.spawnEntity("platform", Rect(
            2100,
            600,
            2300,
            700
        ))

        this.engine.spawnEntity("platform", Rect(
            2150,
            500,
            2300,
            600
        ))

        this.player = this.engine.spawnEntity("terrorist", position = Position(
            200,
            200
        )) as Terrorist;

        this.engine.spawnEntity("terrorist", position = Position(
            600,
            100
        )) as Terrorist;
    }

    public fun draw(canvas: Canvas) {
        if (this.gameControls.arrowLeftPressed) {
            this.player.move(CharacterMoveDirection.LEFT)
        } else if (this.gameControls.arrowRightPressed) {
            this.player.move(CharacterMoveDirection.RIGHT);
        } else {
            this.player.move(CharacterMoveDirection.STATIONARY);
        }

        if (this.gameControls.jumpPressed && this.player.isCollidingBottom) {
            println("Jump pressed");
            this.player.jump();
        }

        this.engine.update();
        this.engine.draw(canvas, Rect(
            0,
            0,
            this.screenWidth,
            this.screenHeight
        ))
    }
}