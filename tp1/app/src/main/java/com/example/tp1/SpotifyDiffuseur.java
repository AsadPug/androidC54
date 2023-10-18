package com.example.tp1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Image;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class SpotifyDiffuseur {
    private static SpotifyDiffuseur instance;
    private SpotifyAppRemote mSpotifyAppRemote;
    private PlayerApi playerApi;
    private static final String CLIENT_ID = "0a64ee2eb5fa42ea82f149a60cd17cff";
    private static final String REDIRECT_URI = "com.example.tp1://callback";
    private Context context;
    private String playlist, songName, artistName;
    private int songDuration, songProgress;
    private Bitmap coverImage;

    public static SpotifyDiffuseur getInstance(Context context) {
        if (instance == null){
            instance = new SpotifyDiffuseur(context);
        }
        return instance;
    }

    private SpotifyDiffuseur(Context context){
        this.context = context;
        this.connect();
    }

    private void connect(){
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this.context, connectionParams,
                new Connector.ConnectionListener() {

                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        playerApi = mSpotifyAppRemote.getPlayerApi();
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                        connected();

                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("MyActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    private void connected() {
        // Play a playlist

        play("37i9dQZF1DZ06evO1sizmS");
        // Subscribe to PlayerState
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        songName = track.name;
                        artistName = track.artist.name;
                    }
                    //transforme les valeurs ms en secondes parce que la precision est inutile dans mon contexte
                    songProgress = (int)(playerState.playbackPosition/1000);
                    songDuration = (int)(track.duration/1000);

                    //callback qd l'image va être loader
                    mSpotifyAppRemote.getImagesApi()
                    .getImage(playerState.track.imageUri).setResultCallback(bitmap -> {
                        coverImage = bitmap;
                    });

                });
    }
    public void play(String playlist){
        playerApi.play("spotify:playlist:" + playlist);
    }
    public void pause(){
        playerApi.pause();
    }
    public void resume(){ playerApi.resume();}
    public void previousSong(){
        playerApi.skipPrevious();
    }
    public void nextSong(){
        playerApi.skipNext();
    }
    public void seekTo(int time){
        playerApi.seekTo(time * 1000);
    }

    //getters
    public String getSongName() {return songName;}
    public String getArtistName() {return artistName;}
    public int getSongProgress() {return songProgress;}
    public int getSongDuration() {return songDuration;}
    public Bitmap getCoverImage() {return coverImage;}

 }
