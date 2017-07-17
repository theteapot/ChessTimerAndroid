package com.example.tkettle.chesstimer;

import android.widget.TextView;
import static android.os.SystemClock.elapsedRealtime;

/**
 * Created by tkettle on 11/07/17.
 */

public class CustomCounter implements Runnable {

    protected TextView counter;

    public CustomCounter(TextView textView) {
        counter = textView;
    }

    public void run() {
        while (true) {
            counter.setText(elapsedRealtime() + "");
        }
    }
}
