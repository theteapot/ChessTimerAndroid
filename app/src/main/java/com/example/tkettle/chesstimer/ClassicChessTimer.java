package com.example.tkettle.chesstimer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import static android.os.SystemClock.elapsedRealtime;


public class ClassicChessTimer extends AppCompatActivity {

    int maxTime = 0;
    Long blackTime = 0L;
    Long whiteTime = 0L;
    TextView whiteTimer;
    TextView blackTimer;
    boolean whiteTurn = true;
    boolean blackTurn = false;
    long turnStart = System.currentTimeMillis();
    boolean isPaused = true;
    private Handler mHandler = new Handler();
    long elapsedTurnTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_chess_timer);

        Intent intent = getIntent();
        maxTime = intent.getIntExtra(MainActivity.START_TIME, 90);

        whiteTimer = (TextView) findViewById(R.id.whiteTimer);
        blackTimer = (TextView) findViewById(R.id.blackTimer);
        whiteTimer.setText(String.valueOf(maxTime));
        blackTimer.setText(String.valueOf(maxTime));
    }

    protected void changeTurn(View view) {
        if (whiteTurn) {
            whiteTime += System.currentTimeMillis() - turnStart;
        } else if (blackTurn) {
            blackTime += System.currentTimeMillis() - turnStart;
        }

        boolean temp = whiteTurn;
        whiteTurn = blackTurn;
        blackTurn = temp;
        turnStart = System.currentTimeMillis();

        Log.d("changeTurn(View view)", "White:" + whiteTurn + " Black: " + blackTurn);
    }

    protected void startTimers(View view) {
        mUpdateTimeTask updateTimeTask = new mUpdateTimeTask();
        if (isPaused) {
            isPaused = false;
            turnStart = System.currentTimeMillis() - elapsedTurnTime;
            TextView buttonText = (TextView) findViewById(R.id.button);
            buttonText.setText("PAUSE");
            mHandler.removeCallbacks(updateTimeTask);
            mHandler.postDelayed(updateTimeTask, 100);
        } else {
            isPaused = true;
            TextView buttonText = (TextView) findViewById(R.id.button);
            buttonText.setText("RESUME");
        }

    }

    public void outOfTime(TextView activeTimer) {
        activeTimer.setBackgroundColor(Color.RED);
        activeTimer.setTextColor(Color.WHITE);
        activeTimer.setText("RESTART");
    }

    private class mUpdateTimeTask implements Runnable {

        public void run() {
            long start = turnStart;
            long millis = System.currentTimeMillis() - start;
            TextView activeTimer = null;
            long activePlayerTime = 0L;
            if (whiteTurn) {
                activePlayerTime = whiteTime + millis;
                activeTimer = whiteTimer;
            } else if (blackTurn) {
                activePlayerTime = blackTime + millis;
                activeTimer = blackTimer;
            }
            int totalSeconds = maxTime - (int) (activePlayerTime / 1000);
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            if (seconds < 10) {
                activeTimer.setText("" + minutes + ":0" + seconds);
            } else {
                activeTimer.setText("" + minutes + ":" + seconds);
            }
            if (totalSeconds == 0) {
                outOfTime(activeTimer);
                return;
            }
            //mHandler.postAtTime(this, start + (((minutes * 60) + seconds + 1) * 1000));
            if (!isPaused) {
                mHandler.postDelayed(this, 200L);
            } else {
                elapsedTurnTime = millis;
            }
        }
    }




}
