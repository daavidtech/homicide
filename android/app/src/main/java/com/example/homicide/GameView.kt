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

    private var surfaceHolder: SurfaceHolder = super.getHolder();

    private val gameControls = GameControls();

    private lateinit var knifeMurderMission: KnifeMurderMission;

    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        this.knifeMurderMission = KnifeMurderMission(context, gameControls = this.gameControls);
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        this.gameControls.setScreenSize(w, h);

        println("onSizeChanged " + w.toString() + " " + h.toString());

        this.knifeMurderMission.setScreenSize(w, h);
    }

    override fun run() {
        var canvas: Canvas;

        while (running) {
            if (surfaceHolder.surface.isValid) {
                canvas = surfaceHolder.lockCanvas();

                canvas.save();
                canvas.drawColor(Color.WHITE);

                this.knifeMurderMission.draw(canvas);

                this.gameControls.draw(canvas);

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
        this.gameControls.handleMotionEvent(event);

        return true;
    }
}