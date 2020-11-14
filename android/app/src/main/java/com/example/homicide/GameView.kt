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
    private lateinit var wallBitmap: Bitmap;

    private var surfaceHolder: SurfaceHolder = super.getHolder();

    private lateinit var player: Character

    private val leftButton = Rect();
    private val rightButton = Rect();
    private val jumpButton = Rect();

    private val buttonPaint = Paint();

    private val view: Rect = Rect();

    private val gameMap = GameMap();

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val options = BitmapFactory.Options()
        options.inScaled = false
        this.bitmap = BitmapFactory.decodeResource(
            this.context.resources,
            R.drawable.terrorist,
            options
        );

        this.wallBitmap = BitmapFactory.decodeResource(
            this.context.resources,
            R.drawable.wall,
            options
        )

        this.buttonPaint.setARGB(128, 100, 100, 100)

        this.player = Character(
            gameMap = this.gameMap,
            spriteBitmap = this.bitmap,
            animationBoxes = listOf(Rect(0, 0, 238, 290), Rect(238, 0, 462, 290)),
            leftAnimationCycles = listOf(1),
            rightAnimationCycles = listOf(2),
            hitbox = Rect(100, 200, 350, 500)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        println("onSizeChanged " + w.toString() + " " + h.toString());

        this.leftButton.set(50, h - 400, 300, h - 100);
        this.rightButton.set(350, h- 400, 600, h - 100);
        this.jumpButton.set(w - 350, h - 400, w - 50, h - 100);

        this.view.set(0, 0, w, h);
    }

    override fun run() {
        var canvas: Canvas;

        while (running) {
            if (surfaceHolder.surface.isValid) {
                canvas = surfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.WHITE);

                this.player.draw(canvas);

                canvas.drawBitmap(this.wallBitmap, null, Rect(
                    50,
                    1200,
                    1600,
                    1300
                ), null);

                canvas.drawBitmap(this.wallBitmap, null, Rect(
                    900,
                    400,
                    1700,
                    500
                ), null);

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
        val action = event!!.actionMasked;

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                println("action down");
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                println("action pointer down");
            }
            MotionEvent.ACTION_POINTER_UP -> {
                println("action pointer up");
            }
            MotionEvent.ACTION_UP -> {
                println("action up");
            }
        }

        /*println(event!!.actionMasked);*/

        if (event!!.action != MotionEvent.ACTION_MOVE) {
            println("touchEvent " + event.toString());
        }

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