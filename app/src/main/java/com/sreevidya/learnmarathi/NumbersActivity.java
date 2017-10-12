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

public class NumbersActivity extends AppCompatActivity {

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

        final ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("One", "Ēka", R.raw.number_1_one, R.drawable.number_one));
        numbers.add(new Word("Two", "Dōna", R.raw.number_2_two, R.drawable.number_two));
        numbers.add(new Word("Three", "Tīna", R.raw.number_3_three, R.drawable.number_three));
        numbers.add(new Word("Four", "Cāra", R.raw.number_4_four, R.drawable.number_four));
        numbers.add(new Word("Five", "Pāca", R.raw.number_5_five, R.drawable.number_five));
        numbers.add(new Word("Six", "Sahā", R.raw.number_6_six, R.drawable.number_six));
        numbers.add(new Word("Seven", "Sāta", R.raw.number_7_seven, R.drawable.number_seven));
        numbers.add(new Word("Eight", "Āṭha", R.raw.number_8_eight, R.drawable.number_eight));
        numbers.add(new Word("Nine", "Na'ū", R.raw.number_9_nine, R.drawable.number_nine));
//        numbers.add(new Word("Ten", "Dahā", R.raw.number_10_ten, R.drawable.number_ten));

        WordAdapter adapter = new WordAdapter(this, numbers, R.color.section_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = numbers.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                releaseMediaPlayer();

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, number.getmAudioResource());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                }
            }
        });
    }

    private void releaseMediaPlayer(){
        if(mMediaPlayer != null)
            mMediaPlayer.release();
        mMediaPlayer = null;
    }
}
