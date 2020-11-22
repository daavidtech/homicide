package com.example.homicide

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent

class GameControls {
    private var _arrowUpPressed = false;

    private var _arrowLeftPressed = false;
    public val arrowLeftPressed
        get() = _arrowLeftPressed;

    private var _arrowRightPressed = false;
    public val arrowRightPressed
        get() = _arrowRightPressed;

    private var _arrowDownPressed = false;

    private var _jumpPressed = false;
    public val jumpPressed
        get() = _jumpPressed;

    private var arrowLeftPressIndex = -1;
    private var arrowRightPressIndex = -1;
    private var jumpPressIndex = -1;

    private val leftButton = Rect();
    private val rightButton = Rect();
    private val jumpButton = Rect();

    private val buttonPaint = Paint();

    constructor() {
        this.buttonPaint.setARGB(128, 100, 100, 100);

    }

    public fun handleMotionEvent(event: MotionEvent?) {
        if (event == null) {
            return;
        }

        /*println("touchEvent " + event.toString());*/

        val action = event.actionMasked;

        val actionIndex = event.actionIndex;

        val x = event.getX(actionIndex).toInt();
        val y = event.getY(actionIndex).toInt();

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                println("action down " + event.actionIndex + " " + event.x + " " + event.y);

                if (this.leftButton.contains(x, y)) {
                    this._arrowLeftPressed = true;
                    this.arrowLeftPressIndex = actionIndex;
                }
                if (this.rightButton.contains(x, y)) {
                    this._arrowRightPressed = true;
                    this.arrowRightPressIndex = actionIndex;
                }

                if (this.jumpButton.contains(x, y)) {
                    this._jumpPressed = true;
                    this.jumpPressIndex = actionIndex;
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                println("action up " + event.actionIndex + " " + event.x + " " + event.y);

                if (this.arrowLeftPressIndex == actionIndex) {
                    this.arrowLeftPressIndex = -1;
                    this._arrowLeftPressed = false;
                }

                if (this.arrowRightPressIndex == actionIndex) {
                    this.arrowRightPressIndex = -1;
                    this._arrowRightPressed = false;
                }

                if (this.jumpPressIndex == actionIndex) {
                    this.jumpPressIndex = -1;
                    this._jumpPressed = false;
                }
            }
        }
    }

    public fun setScreenSize(w: Int, h: Int) {
        this.leftButton.set(50, h - 400, 300, h - 100);
        this.rightButton.set(350, h- 400, 600, h - 100);
        this.jumpButton.set(w - 350, h - 400, w - 50, h - 100);
    }

    public fun draw(canvas: Canvas) {
        canvas.drawRect(this.leftButton, this.buttonPaint)
        canvas.drawRect(this.rightButton, this.buttonPaint)
        canvas.drawRect(this.jumpButton, this.buttonPaint)
    }
}