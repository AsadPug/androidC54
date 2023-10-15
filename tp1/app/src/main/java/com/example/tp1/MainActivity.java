package com.example.tp1;


import android.os.Bundle;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.spotify.protocol.types.Track;


public class MainActivity extends AppCompatActivity {


    private SpotifyDiffuseur spotifyDiffuseur;
    private ConstraintLayout mainLayout;
    private ImageView previousSong, play, nextSong,cover;
    private TextView timeText, songTitle;
    private SeekBar songProgress;
    private Chronometer chronometer;
    private boolean paused;
    private int time, startingTime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        spotifyDiffuseur = SpotifyDiffuseur.getInstance(this);
        mainLayout = findViewById(R.id.mainLayout);
        play = findViewById(R.id.play);
        previousSong = findViewById(R.id.previousSong);
        nextSong = findViewById(R.id.nextSong);
        timeText = findViewById(R.id.timeText);
        songTitle = findViewById(R.id.songTitle);
        cover = findViewById(R.id.cover);
        songProgress = findViewById(R.id.songProgress);

        setupChronometer();
        setupSongControls();


    }
    private void setupSongControls(){

        paused = false;

        play.setOnClickListener((View v) -> {
            if (!paused) {
                spotifyDiffuseur.pause();
                play.setImageResource(R.drawable.play);
                paused = true;
            } else {
                spotifyDiffuseur.resume();
                play.setImageResource(R.drawable.pause);
                paused = false;
            }
        });
        nextSong.setOnClickListener((View v) -> {
            spotifyDiffuseur.nextSong();
            play.setImageResource(R.drawable.pause);
        });
        previousSong.setOnClickListener((View v) -> {
            spotifyDiffuseur.previousSong();
            play.setImageResource(R.drawable.pause);
        });
        songProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                if(fromUser){
                    spotifyDiffuseur.seekTo(seekBar.getProgress());
                }
            }
        });
    }
    private void setupChronometer(){
        time = spotifyDiffuseur.getSongProgress();

        chronometer = new Chronometer(this);
        mainLayout.addView(chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        chronometer.setOnChronometerTickListener((Chronometer c) ->{
            if(!paused){
                if (startingTime != spotifyDiffuseur.getSongProgress()) {
                    startingTime = spotifyDiffuseur.getSongProgress();
                    time = startingTime;
                }
                songTitle.setText(spotifyDiffuseur.getSongName() + " - " + spotifyDiffuseur.getArtistName());

                int duration = spotifyDiffuseur.getSongDuration();

                songProgress.setMax(duration);
                songProgress.setProgress(time);

                timeText.setText(
                        (int)(time/60) + ":" + ((time%60)>=10?(time%60):"0"+(time%60)) +"/"+
                        (int)(duration/60) + ":" + ((duration%60)>=10?(duration%60):"0"+(duration%60))
                );

                cover.setImageBitmap(spotifyDiffuseur.getCoverImage());
                time +=1;
            }
        });
    }
}