package com.example.homicide

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

class GameMap {
    val wallls: MutableList<Rect> = mutableListOf();

    constructor() {
        this.wallls.add(Rect(
            50,
            1200,
            1600,
            1300
        ))

        this.wallls.add(Rect(
            900,
            400,
            1700,
            500
        ))

/*        this.wallls.add(Rect(
            900,
            50,
            1050,
            1300
        ))*/
    }

    public fun findFirstCollision(r: Rect, dx: Int, dy: Int): Pair<XCollision?, YCollision?> {
        if (this.wallls.size == 0) {
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

        for (wall in this.wallls) {
            if (wall.top < r.bottom && wall.bottom > r.top) {
                if (wall.right <= r.left && (closest == null || wall.right > closest.right)) {
                    closest = wall;
                }
            }
        }

        return closest;
    }

    private fun findClosestRightSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.wallls) {
            if (wall.top < r.bottom && wall.bottom > r.top) {
                if (wall.left >= r.right && (closest == null || wall.left < closest.left)) {
                    closest = wall;
                }
            }
        }

        return closest;
    }

    private fun findClosestTopSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.wallls) {
            if (wall.left > r.right && wall.right < r.left) {
                if (wall.bottom <= r.top && (closest == null || wall.bottom > closest.bottom)) {
                    closest = wall;
                }
            }
        }

        return closest;
    }

    private fun findClosestBottomSide(r: Rect): Rect? {
        var closest: Rect? = null;

        for (wall in this.wallls) {
            if (wall.left < r.right && wall.right > r.left) {
                if (wall.top >= r.bottom && (closest == null || wall.top < closest.top)) {
                    closest = wall;
                }
            }
        }

        return closest;
    }
}