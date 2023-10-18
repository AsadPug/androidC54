package com.example.tp1;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
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


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.spotify.protocol.types.Track;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {


    private SpotifyDiffuseur spotifyDiffuseur;
    private ConstraintLayout mainLayout;
    private LinearLayout drainGangPlaylistLayout, bladeePlaylistLayout, ecco2kPlaylistLayout,
            thaiboyDigitalPlaylistLayout, controlBar, mainSpace, mainVerticalLayout;
    private ImageView previousSong, play, nextSong, cover, changeTheme;
    private TextView timeText, songTitle, drainGang;
    private SeekBar songProgress;
    private Chronometer chronometer;
    private boolean paused;
    private int time, startingTime;
    private String drainGangPlaylist, bladeePlaylist, ecco2kPlaylist, thaiboyDigitalPlaylist;
    private ActivityResultLauncher<Intent> launcher;
    private HashMap<String,String> themeColors;

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
        drainGangPlaylistLayout = findViewById(R.id.drainGangPlaylist);
        bladeePlaylistLayout = findViewById(R.id.bladeePlaylist);
        ecco2kPlaylistLayout = findViewById(R.id.ecco2kPlaylist);
        thaiboyDigitalPlaylistLayout = findViewById(R.id.thaiboyDigitalPlaylist);
        mainSpace = findViewById(R.id.mainSpace);
        mainVerticalLayout = findViewById(R.id.mainVerticalLayout);
        changeTheme = findViewById(R.id.changeTheme);
        controlBar = findViewById(R.id.controlBar);
        drainGang = findViewById(R.id.drainGang);

        drainGangPlaylist = "37i9dQZF1DZ06evO1sizmS";
        bladeePlaylist =  "5EEtkGNTiuQm1ceVD2xzI4?si=dca4be69489a42fc";
        ecco2kPlaylist = "6Xgm1HUQ27y0ORuwlS9y3f?si=4fb9ce0cbe36424d";
        thaiboyDigitalPlaylist = "37i9dQZF1DZ06evO1R4sDg?si=d3c503c6cd984916";

        setupChronometer();
        setupSongControls();
        setupPlaylistChange();
        setupBoomerangTheme();
        setupThemeHashmap();
        setupDrainGangLienExterne();



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

        //met le texte du chronometre transparent pour le caché
        chronometer.setTextColor(Color.TRANSPARENT);
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

                //opération terniene assure que quand les secondes sont entre 0 et 10 on affiche deux caractère et pas seulement
                //pour afficher 0:09 plutot que 0:9
                timeText.setText(
                        (int)(time/60) + ":" + ((time%60)>=10?(time%60):"0"+(time%60)) +"/"+
                        (int)(duration/60) + ":" + ((duration%60)>=10?(duration%60):"0"+(duration%60))
                );

                cover.setImageBitmap(spotifyDiffuseur.getCoverImage());
                time +=1;
            }
        });
    }
    private void setupPlaylistChange(){
        drainGangPlaylistLayout.setOnClickListener((View v) ->{
            spotifyDiffuseur.play(drainGangPlaylist);
        });
        bladeePlaylistLayout.setOnClickListener((View v) ->{
            spotifyDiffuseur.play(bladeePlaylist);
        });
        ecco2kPlaylistLayout.setOnClickListener((View v) ->{
            spotifyDiffuseur.play(ecco2kPlaylist);
        });
        thaiboyDigitalPlaylistLayout.setOnClickListener((View v) ->{
            spotifyDiffuseur.play(thaiboyDigitalPlaylist);
        });

    }
    private void setupBoomerangTheme(){
        //boomerang pour les themes
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK){
                    HashMap<String, Object> themeChoisi = (HashMap<String, Object>)result.getData().getSerializableExtra("theme");
                    mainVerticalLayout.setBackgroundResource((int)themeChoisi.get("image"));
                    mainSpace.setBackgroundResource((int)themeChoisi.get("image"));
                    controlBar.setBackgroundColor(Color.parseColor(themeColors.get(themeChoisi.get("name"))));
                }
            }
        });
        changeTheme.setOnClickListener((View v) ->{
            launcher.launch(new Intent(MainActivity.this, ThemeSelect.class));

        });
    }
    private void setupThemeHashmap(){
        //le nom retourné par le boomerang sert de clé pour trouver la couleur associé au theme qui va s'appliquer au controlBar
        themeColors = new HashMap<String, String>();
        themeColors.put("Drain Gang Theme", "#B3C83782");
        themeColors.put("Bladee Theme", "#B2354661");
        themeColors.put("Ecco2k Theme", "#B2393939");
        themeColors.put("Thaiboy Digital Theme", "#B3E5C2FA");
    }
    private void setupDrainGangLienExterne(){
        //intent vers la chaine youtube de drain gang qd on clique sur le gros "drain gang" en haut de la page
        drainGang.setOnClickListener((View v) ->{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@gtbshieldgang"));
            startActivity(intent);
        });
    }
}