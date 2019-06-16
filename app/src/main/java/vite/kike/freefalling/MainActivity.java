package vite.kike.freefalling;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Chronometer chronometer;
    private boolean isStart;
    private long ini;
    private TextView distanciaTV, tiempoTV;
    private Button btn, trigger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distanciaTV = findViewById(R.id.dist);
        tiempoTV = findViewById(R.id.time);
        chronometer = findViewById(R.id.chronometer);
        btn = findViewById(R.id.btn);
        trigger = findViewById(R.id.trigger);
        btn.setBackgroundColor(Color.GREEN);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;
            }
        });

        trigger.setOnTouchListener(this);
    }

    public void startStopChronometer(View view){
        if (isStart) {
            long fin = System.currentTimeMillis();
            chronometer.stop();
            isStart = false;
            btn.setText(getString(R.string.start));
            btn.setBackgroundColor(Color.GREEN);
            trigger.setVisibility(View.VISIBLE);
            long totalTime = fin - ini;
            double dist = 0.5 * 9.81 * (totalTime) * (totalTime) / 1000000.0;
            dist = Math.round(dist * 100.0) / 100.0;
            distanciaTV.setText(String.format("%s m", dist));
            double time = totalTime / 1000.0;
            tiempoTV.setText(String.format("%s s", time));
        } else {
            ini = System.currentTimeMillis();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isStart = true;
            trigger.setVisibility(View.INVISIBLE);
            btn.setText(getString(R.string.stop));
            btn.setBackgroundColor(Color.RED);
            distanciaTV.setText("");
            tiempoTV.setText("");
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ini = System.currentTimeMillis();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                isStart = true;
                trigger.setText(getString(R.string.release));
                btn.setVisibility(View.INVISIBLE);
                distanciaTV.setText("");
                tiempoTV.setText("");
                return true;
            case MotionEvent.ACTION_UP:
                long fin = System.currentTimeMillis();
                chronometer.stop();
                isStart = false;
                trigger.setText(getString(R.string.push));
                btn.setVisibility(View.VISIBLE);
                long totalTime = fin - ini;
                double dist = 0.5 * 9.81 * (totalTime) * (totalTime) / 1000000.0;
                dist = Math.round(dist * 100.0) / 100.0;
                distanciaTV.setText(String.format("%s m", dist));
                double time = totalTime / 1000.0;
                tiempoTV.setText(String.format("%s s", time));
                return true;
        }
        return true;
    }
}
