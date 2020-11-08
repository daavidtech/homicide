package com.example.homicide

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView: SurfaceView, Runnable {
    private var running = false;
    private var gameThread: Thread? = null;
  /*  private lateinit var context: Context;*/
    private var bitmap: Bitmap? = null;
    private lateinit var paint: Paint;
    private var surfaceHolder: SurfaceHolder = super.getHolder();

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        /*this.context = context;*/
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        this.paint = Paint();
        this.paint.color = Color.DKGRAY;
        bitmap = BitmapFactory.decodeResource(this.context.resources, R.drawable.ukko);
    }

    override fun run() {
        var canvas: Canvas;

        while (running) {
            if (surfaceHolder.surface.isValid) {
                canvas = surfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(this.bitmap!!, 10.0f, 10.0f, this.paint);

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


}