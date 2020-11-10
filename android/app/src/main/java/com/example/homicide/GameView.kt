package com.example.homicide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView: SurfaceView, Runnable {
    private var running = false;
    private var gameThread: Thread? = null;
    private lateinit var bitmap: Bitmap;
    private var surfaceHolder: SurfaceHolder = super.getHolder();

    private lateinit var player: Character

    private val leftButton = Rect();
    private val rightButton = Rect();
    private val jumpButton = Rect();

    private val buttonPaint = Paint();

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        this.bitmap = BitmapFactory.decodeResource(
            this.context.resources,
            R.drawable.terrorist,
            options
        );

        this.buttonPaint.setARGB(128, 100, 100, 100)

        this.player = Character(
            spriteBitmap = this.bitmap,
            animationBoxes = listOf(Rect(0, 0, 238, 290), Rect(238, 0, 462, 290)),
            leftAnimationCycles = listOf(1),
            rightAnimationCycles = listOf(2),
            xPosition = 100,
            yPosition = 800,
            width = 400,
            height = 400
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        println("onSizeChanged " + w.toString() + " " + h.toString());

        this.leftButton.set(50, h - 400, 300, h - 100);
        this.rightButton.set(350, h- 400, 600, h - 100);
        this.jumpButton.set(w - 350, h - 400, w - 50, h - 100);
    }

    override fun run() {
        var canvas: Canvas;

        while (running) {
            if (surfaceHolder.surface.isValid) {
                canvas = surfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.WHITE);

                this.player.onDraw(canvas);

                canvas.drawRect(this.leftButton, this.buttonPaint)
                canvas.drawRect(this.rightButton, this.buttonPaint)
                canvas.drawRect(this.jumpButton, this.buttonPaint)

                surfaceHolder.unlockCanvasAndPost(canvas);
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        println("touchEvent " + event.toString());

        when (event!!.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN, MotionEvent.ACTION_MOVE -> {
                if (this.leftButton.contains(event.x.toInt(), event.y.toInt())) {
                    this.player.move(CharacterMoveDirection.LEFT);
                }
                if (this.rightButton.contains(event.x.toInt(), event.y.toInt())) {
                    this.player.move(CharacterMoveDirection.RIGHT);
                }

                if (this.jumpButton.contains(event.x.toInt(), event.y.toInt())) {
                    this.player.jump();
                }

                return true;
            }
            MotionEvent.ACTION_UP -> {
                this.player.move(CharacterMoveDirection.STATIONARY);
                return true;
            }
        }

        return false;
    }
}