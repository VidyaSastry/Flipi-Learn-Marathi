package com.sreevidya.learnmarathi;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if ((focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)  && mMediaPlayer != null) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("Red", "Lāla", R.raw.colors_1_red, R.drawable.color_red));
        colors.add(new Word("Green", "Hiravā", R.raw.colors_2_green, R.drawable.color_green));
        colors.add(new Word("Blue", "Niḷā", R.raw.colors_3_blue, R.drawable.color_blue));
        colors.add(new Word("Yellow", "Pivaḷā", R.raw.colors_7_yellow, R.drawable.color_yellow));
        colors.add(new Word("Pink", "Gulābī", R.raw.colors_4_pink, R.drawable.color_pink));
        colors.add(new Word("White", "Pāṇḍharā", R.raw.colors_6_white, R.drawable.color_white));
        colors.add(new Word("Black", "Kāḷā", R.raw.colors_5_black, R.drawable.color_black));

        WordAdapter adapter = new WordAdapter(this, colors, R.color.section_colors);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = colors.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                releaseMediaPlayer();

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, number.getmAudioResource());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null)
            mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
