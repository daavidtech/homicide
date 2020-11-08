package com.example.homicide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

enum class MoveDirection {
    LEFT,
    RIGHT,
    STATIONARY
}

class GameView: SurfaceView, Runnable {
    private var running = false;
    private var gameThread: Thread? = null;
  /*  private lateinit var context: Context;*/
    private var bitmap: Bitmap? = null;
    private lateinit var paint: Paint;
    private var surfaceHolder: SurfaceHolder = super.getHolder();

    private var playerXPosition = 100;
    private var moveDirection: MoveDirection = MoveDirection.STATIONARY;

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        /*this.context = context;*/
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        println("onSizeChanged");

        this.paint = Paint();
        this.paint.color = Color.DKGRAY;
        this.bitmap = BitmapFactory.decodeResource(this.context.resources, R.drawable.ukko);
    }

    override fun run() {
        var canvas: Canvas;

        while (running) {
//            println("run");

            if (surfaceHolder.surface.isValid) {
                canvas = surfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.BLACK);
//                canvas.drawBitmap(this.bitmap!!, 10.0f, 10.0f, this.paint);

                val src = Rect(0,0, 400, 400);
                val dest = Rect(this.playerXPosition, 100, this.playerXPosition + 400, 400);

                canvas.drawBitmap(this.bitmap!!, null, dest, this.paint);

                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            when(this.moveDirection) {
                MoveDirection.LEFT -> {
                    this.playerXPosition -= 5;
                }
                MoveDirection.RIGHT -> {
                    this.playerXPosition += 5;
                }
            }
        }
    }

    public fun pause() {
        running = false;
        try {
            gameThread!!.join();
        } catch (e: InterruptedException) {

        }
    }

    public fun resume() {
        running  = true;
        gameThread = Thread(this);
        gameThread!!.start();
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        println("keycode: ");

/*        when (keyCode) {

        }*/

        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyUp(keyCode, event)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("touchEvent " + event.toString());

        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x < 700) {
                    this.moveDirection = com.example.homicide.MoveDirection.LEFT;
                } else if (event.x > 1500) {
                    this.moveDirection = com.example.homicide.MoveDirection.RIGHT;
                }

                return true;
            }
            MotionEvent.ACTION_UP -> {
                this.moveDirection = MoveDirection.STATIONARY;
                return true;
            }
            MotionEvent.ACTION_MOVE -> {

            }
        }

        return false;
    }

    override fun onGenericMotionEvent(event: MotionEvent?): Boolean {
        println("onGenericMotionEvent");

        return super.onGenericMotionEvent(event)
    }
}