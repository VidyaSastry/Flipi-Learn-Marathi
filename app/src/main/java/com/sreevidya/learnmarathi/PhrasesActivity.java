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

public class PhrasesActivity extends AppCompatActivity {
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

        final ArrayList<Word> phrases = new ArrayList<>();
        phrases.add(new Word("Where are you going?","Kuṭhē jāla", R.raw.phrases_1_where_are_you_going));
        phrases.add(new Word("What's your name?","Tujhē nāva kāya āhē", R.raw.phrases_2_whats_your_name));
        phrases.add(new Word("My name is?","Mājhaṁ nāvaṁ āhē", R.raw.phrases_3_my_name_is));
        phrases.add(new Word("How are you?","Tū kasā āhēsa", R.raw.phrases_4_how_are_you));
        phrases.add(new Word("I'm well","Mī barī āhē", R.raw.phrases_5_im_good));
        phrases.add(new Word("Are you coming?","Yēnar āhēsa kā", R.raw.phrases_6_are_you_coming));
        phrases.add(new Word("Yes I am coming","Hō mī yēta āhē", R.raw.phrases_7_yes_im_coming));
        phrases.add(new Word("Let's go","Cala jā'ūyā", R.raw.phrases_9_lets_go));

        WordAdapter adapter = new WordAdapter(this, phrases, R.color.section_phrases);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word phrase = phrases.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                releaseMediaPlayer();

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, phrase.getmAudioResource());
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
