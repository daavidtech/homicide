package com.example.homicide

import android.graphics.Canvas
import android.graphics.Rect

enum class XCollisionType {
    Left,
    Right,
}

data class XCollision(val coordinate: Int, val collisionType: XCollisionType)

enum class YCollisionType {
    Top,
    Bottom
}

data class YCollision(val coordinate: Int, val collisionType: YCollisionType)

class Engine {
    private val entities = mutableSetOf<Entity>();
    private val spawners = mutableMapOf<String, Spawner>();

    public fun spawnEntity(entityType: String, position: Position): Entity {
        var spawner = this.spawners[entityType];

        val newEntity = spawner!!.spawn(position);

        this.entities.add(newEntity);

        return newEntity;
    }

    public fun spawnEntity(entityType: String, rect: Rect): Entity {
        var spawner = this.spawners[entityType];

        val newEntity = spawner!!.spawn(rect);

        this.entities.add(newEntity);

        return newEntity;
    }

    public fun addEntity(entity: Entity) {
        this.entities.add(entity);
    }

    public fun update() {
        for (entity in this.entities) {
            val positionChange = entity.calculateChanges();

            val (xCollision, yCollision) = this.findFirstCollision(entity.hitbox, positionChange.dx, positionChange.dy);

            this.handleCollisions(
                entity = entity,
                xCollision = xCollision,
                yCollision = yCollision,
                dx = positionChange.dx,
                dy = positionChange.dy,
                xVelocity = positionChange.xVelocity,
                yVelocity = positionChange.yVelocity
            )
        }
    }

    public fun draw(canvas: Canvas, area: Rect) {
        for (entity in entities) {
            if (entity.hitbox.intersects(area.left, area.top, area.right, area.bottom)) {
                entity.draw(canvas);
            }
        }
    }

    public fun implementSpawner(entityType: String, spawner: Spawner) {
        this.spawners[entityType] = spawner;
    }

    private fun handleCollisions(
        entity: Entity,
        xCollision: XCollision?,
        yCollision: YCollision?,
        dx: Int,
        dy: Int,
        xVelocity: Int,
        yVelocity: Int
    ) {
        entity.isCollidingBottom = false;

        if (xCollision == null && yCollision == null) {
            entity.xVelocity = xVelocity;
            entity.yVelocity = yVelocity;
            entity.move(dx, dy);
        } else if (xCollision != null && yCollision != null) {
            entity.xVelocity = 0;
            entity.yVelocity = 0;

            var newLeft = entity.hitbox.left;
            var newTop = entity.hitbox.top;
            var newRight = entity.hitbox.right;
            var newBottom = entity.hitbox.bottom;

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
                    entity.isCollidingBottom = true;
                }
                YCollisionType.Top -> {
                    newBottom = yCollision.coordinate + newBottom - newTop;
                    newTop = yCollision.coordinate;
                }
            }
        } else if (xCollision != null && yCollision == null) {
            entity.xVelocity = 0;
            entity.yVelocity = yVelocity;

            when (xCollision.collisionType) {
                XCollisionType.Right -> {
                    entity.moveWithCollidingRight(dy, xCollision.coordinate);
                }
                XCollisionType.Left -> {
                    entity.moveWithCollidingLeft(dy, xCollision.coordinate);
                }
            }
        } else if (xCollision == null && yCollision != null) {
            entity.yVelocity = 0;
            entity.xVelocity = xVelocity;

            when (yCollision.collisionType) {
                YCollisionType.Top -> {
                    entity.moveWithCollidingTop(dx, yCollision.coordinate);
                }
                YCollisionType.Bottom -> {
                    entity.isCollidingBottom = true;
                    entity.moveWithCollidingBottom(dx, yCollision.coordinate);
                }
            }
        }
    }

    private fun findFirstCollision(r: Rect, dx: Int, dy: Int): Pair<XCollision?, YCollision?> {
        if (this.entities.size == 0) {
            return Pair(null, null);
        }

        var xCollision: XCollision? = null;
        var yCollision: YCollision? = null;

        if (dx > 0) {
            var closest = this.findClosestRightSide(r);

            if (closest != null) {
                if (r.right + dx >= closest.left) {
                    xCollision = XCollision(closest.left, XCollisionType.Right);
                }
            }
        } else if (dx < 0) {
            var closest = this.findClosestLeftSide(r);

            if (closest != null) {
                if (r.left + dx <= closest.right) {
                    xCollision = XCollision(closest.right, XCollisionType.Left);
                }
            }
        }

        if (dy > 0) {
            var closest = this.findClosestBottomSide(r);

            if (closest != null) {
                if (r.bottom + dy >= closest.top) {
                    yCollision = YCollision(closest.top, YCollisionType.Bottom);
                }
            }
        } else if (dy < 0) {
            var closest = this.findClosestTopSide(r);

            if (closest != null) {
                if (r.top + dy <= closest.bottom) {
                    yCollision = YCollision(closest.bottom, YCollisionType.Top);
                }
            }
        }

        return Pair(xCollision, yCollision);
    }

    private fun findClosestLeftSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.entities.filter { it.isPlatform }) {
            if (wall.hitbox.top < r.bottom && wall.hitbox.bottom > r.top) {
                if (wall.hitbox.right <= r.left && (closest == null || wall.hitbox.right > closest.right)) {
                    closest = wall.hitbox;
                }
            }
        }

        return closest;
    }

    private fun findClosestRightSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.entities.filter { it.isPlatform }) {
            if (wall.hitbox.top < r.bottom && wall.hitbox.bottom > r.top) {
                if (wall.hitbox.left >= r.right && (closest == null || wall.hitbox.left < closest.left)) {
                    closest = wall.hitbox;
                }
            }
        }

        return closest;
    }

    private fun findClosestTopSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.entities.filter { it.isPlatform }) {
            if (wall.hitbox.left > r.right && wall.hitbox.right < r.left) {
                if (wall.hitbox.bottom <= r.top && (closest == null || wall.hitbox.bottom > closest.bottom)) {
                    closest = wall.hitbox;
                }
            }
        }

        return closest;
    }

    private fun findClosestBottomSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.entities.filter { it.isPlatform }) {
            if (wall.hitbox.left < r.right && wall.hitbox.right > r.left) {
                if (wall.hitbox.top >= r.bottom && (closest == null || wall.hitbox.top < closest.top)) {
                    closest = wall.hitbox;
                }
            }
        }

        return closest;
    }


}