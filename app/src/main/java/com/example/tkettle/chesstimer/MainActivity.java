package com.example.tkettle.chesstimer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import static android.os.SystemClock.elapsedRealtime;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    TextView customCounter;
    public static final String START_TIME = "com.example.tkettle.chesstimer.START_TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.timer_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    protected void startTimer(View view) {
        EditText timeInput = (EditText) findViewById(R.id.timeInput);
        String timeString = timeInput.getText().toString();
        Log.d("timeInput", timeParser(timeString)+"");
        Intent intent = new Intent(this, ClassicChessTimer.class);
        intent.putExtra(START_TIME, timeParser(timeString));
        startActivity(intent);

    }

    private int timeParser(String timeString) {
        int seconds = 0;
        String[] timeArray = timeString.split(":");
        int maxPower = timeArray.length - 1;
        for (String segment : timeArray) {
            seconds += Integer.parseInt(segment) * Math.pow(60, maxPower);
            maxPower--;
        }

        return seconds;
    }


}
