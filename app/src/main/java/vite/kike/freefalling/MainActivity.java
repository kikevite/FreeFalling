package vite.kike.freefalling;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private boolean isStart;
    private long ini;
    private TextView distancia, tiempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        distancia = findViewById(R.id.dist);
        tiempo = findViewById(R.id.time);
        chronometer = findViewById(R.id.chronometer);
        Button btn = findViewById(R.id.btn);
        btn.setBackgroundColor(Color.GREEN);

        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                chronometer = chronometerChanged;
            }
        });
    }

    public void startStopChronometer(View view){
        if (isStart) {
            long fin = System.currentTimeMillis();
            chronometer.stop();
            isStart = false;
            ((Button)view).setText(getString(R.string.start));
            view.setBackgroundColor(Color.GREEN);
            long totalTime = fin - ini;
            double dist = 0.5 * 9.8 * (totalTime) * (totalTime) / 1000000.0;
            dist = Math.round(dist * 100.0) / 100.0;
            distancia.setText(String.format("%s m", dist));
            double time = totalTime / 1000.0;
            tiempo.setText(String.format("%s s", time));
        } else {
            ini = System.currentTimeMillis();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isStart = true;
            ((Button)view).setText(getString(R.string.stop));
            view.setBackgroundColor(Color.RED);
            distancia.setText("");
            tiempo.setText("");
        }
    }
}
