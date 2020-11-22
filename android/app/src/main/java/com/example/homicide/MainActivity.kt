package com.example.homicide

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.view.Window
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {
    lateinit var gameView: GameView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        supportActionBar!!.hide();

        gameView = GameView(this);

        gameView.systemUiVisibility = SYSTEM_UI_FLAG_FULLSCREEN;
        setContentView(gameView);
    }

    override fun onPause() {
        super.onPause();
        gameView.pause();
    }
    override fun onResume() {
        super.onResume();
        gameView.resume();
    }
}