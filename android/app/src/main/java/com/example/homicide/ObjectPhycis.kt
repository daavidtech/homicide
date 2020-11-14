package com.example.homicide

import android.graphics.Rect
import android.os.SystemClock.elapsedRealtime

abstract class ObjectPhycis {
    private var gravity = 0;
    private var movable = false;

    public var xVelocity = 0;
    public var yVelocity = 0;

    protected var xAcceleration = 0;
    protected var yAcceleration = 60;

    private var lastdx = 0;
    private var lastdy = 0;

    var lastDrawTime = 0L;

    val _hitbox = Rect();
    public val hitbox: Rect
        get() {
            return _hitbox;
        }

    private lateinit var gameMap: GameMap;

    constructor(gameMap: GameMap) {
        this.gameMap = gameMap;
    }

    public fun collidesWith(r: Rect): Boolean {
        if (r == this.hitbox) {
            return false;
        }

        return this.hitbox.intersect(r);
    }

    public fun calculateChanges() {
        if (lastDrawTime == 0L) {
            this.lastDrawTime = elapsedRealtime();

            return;
        }

        val currentTime = elapsedRealtime();

        val timeFromLastCalculation = (currentTime - this.lastDrawTime).toDouble() / 1000;
        this.lastDrawTime = currentTime;

        if (this.xVelocity != 0) {
            println(this.xVelocity);
        }

        this.xVelocity += this.xAcceleration;
        this.yVelocity += this.yAcceleration;

        val dx = (timeFromLastCalculation * this.xVelocity).toInt();
        val dy = (timeFromLastCalculation * this.yVelocity).toInt();

        if (dx == 0 && dy == 0) {
            return;
        }

        if (dx != this.lastdx) {
            println("dx: " + dx);
        }

        if (dy != this.lastdy) {
            println("dy: " + dy);
        }

        this.lastdx = dx;
        this.lastdy = dy;

        val (xCollision, yCollision) = this.gameMap.findFirstCollision(this.hitbox, dx, dy);

        if (xCollision == null && yCollision == null) {
            this.move(dx, dy);
        } else if (xCollision != null && yCollision != null) {
            this.xVelocity = 0;
            this.yVelocity = 0;

            var newLeft = this.hitbox.left;
            var newTop = this.hitbox.top;
            var newRight = this.hitbox.right;
            var newBottom = this.hitbox.bottom;

            when (xCollision.collisionType) {
                XCollisionType.Left -> {
                    newRight = xCollision.coordinate + newRight - newLeft;
                    newLeft = xCollision.coordinate;
                }
                XCollisionType.Right -> {
                    newLeft = xCollision.coordinate - newRight - newLeft;
                    newRight = xCollision.coordinate;
                }
            }

            when (yCollision.collisionType) {
                YCollisionType.Bottom -> {
                    newTop = yCollision.coordinate - newBottom - newTop;
                    newBottom = yCollision.coordinate;
                }
                YCollisionType.Top -> {
                    newBottom = yCollision.coordinate + newBottom - newTop;
                    newTop = yCollision.coordinate;
                }
            }
        } else if (xCollision != null && yCollision == null) {
            this.xVelocity = 0;

            when (xCollision.collisionType) {
                XCollisionType.Right -> {
                    this.moveWithCollidingRight(dy, xCollision.coordinate);
                }
                XCollisionType.Left -> {
                    this.moveWithCollidingLeft(dy, xCollision.coordinate);
                }
            }
        } else if (xCollision == null && yCollision != null) {
            this.yVelocity = 0;

            when (yCollision.collisionType) {
                YCollisionType.Top -> {
                    this.moveWithCollidingTop(dx, yCollision.coordinate);
                }
                YCollisionType.Bottom -> {
                    this.moveWithCollidingBottom(dx, yCollision.coordinate);
                }
            }
        }

   /*     if (collision != null) {
            when (collision.collisionType) {
                CollisionType.Bottom -> {
                    println("Collision from bottom");
                    this.yVelocity = 0;
                    this.moveWithCollidingBottom(dx = dx, bottom = collision.coordinate);
                }
                CollisionType.Top -> {
                    this.yVelocity = 0;
                    this.move(dx = dx, dy = 0);
                }
                CollisionType.Left, CollisionType.Right -> {
                    this.xVelocity = 0;
                    this.move(dx = 0, dy = dy);
                }
            }
        } else {
            println("No collision");

        }*/

/*        if (dx != this.distanceMoved) {
            println("Time from last calculation " + timeFromLastCalculation.toString());
            println("Distance moving " + distance.toString());
        }


        this.move(distance.toInt(), 0);

        this.distanceMoved = distance;*/
        /*val collisions = this.gamStage.getCollisions(this.hitbox);

        if (collisions.size > 0) {
            println("New collisions");
        }*/
    }

    private fun moveWithCollidingTop(
        dx: Int,
        top: Int
    ) {
        val newLeft = this.hitbox.left + dx;
        val newBottom = top + (this.hitbox.bottom - this.hitbox.top);
        val newRight = this.hitbox.right + dx;

        this.hitbox.set(newLeft, top, newRight, newBottom);
    }

    private fun moveWithCollidingBottom(
        dx: Int,
        bottom: Int
    ) {
        val newLeft = this.hitbox.left + dx;
        val newTop = bottom - (this.hitbox.bottom - this.hitbox.top);
        val newRight = this.hitbox.right + dx;

        this.hitbox.set(newLeft, newTop, newRight, bottom);
    }

    private fun moveWithCollidingLeft(
        dy: Int,
        left: Int
    ) {
        val newRight = left + (this.hitbox.right - this.hitbox.left);
        val newTop = this.hitbox.top + dy;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(left, newTop, newRight, newBottom);
    }

    private fun moveWithCollidingRight(
        dy: Int,
        right: Int
    ) {
        val newLeft = right - (this.hitbox.right - this.hitbox.left);
        val newTop = this.hitbox.top + dy;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(newLeft, newTop, right, newBottom);
    }

    public fun move(
        dx: Int = 0,
        dy: Int = 0,
    ) {
        val newLeft = this.hitbox.left + dx;
        val newTop = this.hitbox.top + dy;
        val newRight = this.hitbox.right + dx;
        val newBottom = this.hitbox.bottom + dy;

        this.hitbox.set(newLeft, newTop, newRight, newBottom);
    }
}