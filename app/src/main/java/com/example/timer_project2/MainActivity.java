package com.example.timer_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnStart,btnPause,btnClear,btnStop;
    SeekBar seekBar;
    TextView plainText1,plainText2;
    CountDownTimer timer;
    int time = 0;
    int temp_time = 0;
    MediaPlayer mediaPlayer;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);
        btnClear = findViewById(R.id.btnClear);
        btnStop = findViewById(R.id.btnStop);

        btnPause.setEnabled(false);
        btnStop.setEnabled(false);

        seekBar = findViewById(R.id.seekBar);

        plainText1 = findViewById(R.id.plainText1);
        plainText2 = findViewById(R.id.plainText2);

        plainText1.setEnabled(false);
        plainText2.setEnabled(false);

        seekBar.setMax(3560);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.uu);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                time = i;
                String min = String.valueOf(i/60);
                String sec = String.valueOf(i%60);
                plainText1.setText(min);
                plainText2.setText(sec);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time>0) {
                    seekBar.setEnabled(false);
                    btnPause.setEnabled(true);
                    btnStart.setEnabled(false);
                    btnClear.setEnabled(false);
                    timer = new CountDownTimer(time * 1000, 1000) {
                        public void onTick(long sec) {
                            temp_time = (int) sec / 1000;
                            seekBar.setProgress((int) sec / 1000);
                            String min = String.valueOf(sec / 60000);
                            String sc = String.valueOf((sec / 1000) % 60);
                            plainText1.setText(min);
                            plainText2.setText(sc);
                        }

                        public void onFinish() {
                            Toast.makeText(getApplicationContext(), "Time Out", Toast.LENGTH_SHORT).show();
                            time = 0;
                            temp_time = 0;
                            seekBar.setProgress(0);
                            plainText1.setText("0");
                            plainText2.setText("0");
                            btnPause.setEnabled(false);
                            btnStart.setEnabled(true);
                            btnClear.setEnabled(true);
                            seekBar.setEnabled(true);
                            btnStop.setEnabled(true);
                            mediaPlayer.start();

                        }
                    }.start();
                }else{
                    Toast.makeText(getApplicationContext(), "Select A time Value", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = temp_time;
                timer.cancel();
                btnStart.setEnabled(true);
                btnPause.setEnabled(false);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(0);
                plainText1.setText("0");
                plainText2.setText("0");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
            }
        });

    }
}