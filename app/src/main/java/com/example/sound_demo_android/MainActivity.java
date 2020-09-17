package com.example.sound_demo_android;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private boolean isMediaPlayerStarted = false;
    AudioManager audioManager;

    public void play(View view){

        if(!isMediaPlayerStarted) {
            mediaPlayer.start();
            isMediaPlayerStarted = true;
        }

    }

    public void pause(View view){

        if(isMediaPlayerStarted) {
            mediaPlayer.pause();
            isMediaPlayerStarted = false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        mediaPlayer = MediaPlayer.create(this, R.raw.harley_davidson_daniel_simon);

        setUpVolumeSeeker();
        setUpScrubSeeker();



    }

    private void setUpScrubSeeker() {

        final SeekBar scrubSeeker = (SeekBar)findViewById(R.id.scrubSeekBar);
        scrubSeeker.setMax(mediaPlayer.getDuration());

        scrubSeeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                Log.i("scrubSeek changed: ", Integer.toString(i));

                if(fromUser) {
                    mediaPlayer.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scrubSeeker.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 300);

    }

    private void setUpVolumeSeeker() {
//        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume =  audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);



        SeekBar volumeControl = (SeekBar)findViewById(R.id.volumeSeekBar);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(currentVolume);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.i("volumeSeek changed: ", Integer.toString(i));

                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}