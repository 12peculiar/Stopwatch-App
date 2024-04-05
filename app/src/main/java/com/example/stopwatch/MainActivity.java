package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.text.MessageFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    MaterialButton hold, start, stop;
    int seconds, minutes, milliSeconds;
    long milliSecond, startTime, timeBuff, updateTime = 0L;
    Handler handler;

    private final Runnable runnable = new Runnable() {


        public void run() {
            milliSecond = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milliSecond;
            seconds = (int) (updateTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliSeconds = (int) (updateTime % 1000);

            textView.setText(MessageFormat.format("{0}:{1}:{2}", minutes, String.format(Locale.getDefault(), "%02d", seconds), String.format(Locale.getDefault(), "%02d", milliSeconds)));
            handler.postDelayed((Runnable) this, 0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        hold = findViewById(R.id.hold);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);

        handler = new Handler(Looper.getMainLooper());
      start.setOnClickListener(new View.OnClickListener(){
        public void onClick (View view){
            startTime = SystemClock.uptimeMillis();
            handler.postDelayed(runnable, 0);
            hold.setEnabled(false);
            stop.setEnabled(true);
            start.setEnabled(false);
        }
         });
        stop.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                timeBuff += milliSecond;
                handler.removeCallbacks(runnable);
                hold.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });
        hold.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                milliSecond = 0L;
                startTime = 0L;
                timeBuff = 0L;
                updateTime = 0L;
                seconds = 0;
                minutes =0;
                milliSeconds =0;
                textView.setText("00:00:00");
                timeBuff += milliSecond;
                handler.removeCallbacks(runnable);
                hold.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });
        textView.setText("00:00:00");
    }
}