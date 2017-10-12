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


public class FamilyActivity extends AppCompatActivity {
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

        final ArrayList<Word> family = new ArrayList<>();
        family.add(new Word("Father", "Bābā", R.raw.family_1_father, R.drawable.family_father));
        family.add(new Word("Mother", "Āī", R.raw.family_2_mother, R.drawable.family_mother));
        family.add(new Word("Son", "Mulagā", R.raw.family_3_son, R.drawable.family_son));
        family.add(new Word("Daughter", "Mulagī", R.raw.family_4_daughter, R.drawable.family_daughter));
        family.add(new Word("Older brother", "Mōṭhā bhā'ū", R.raw.family_5_older_brother, R.drawable.family_older_brother));
        family.add(new Word("Younger brother", "Dhākaṭā bhā'ū", R.raw.family_6_younger_brother, R.drawable.family_younger_brother));
        family.add(new Word("Older sister", "Mōṭhī bahīṇa", R.raw.family_7_older_sister, R.drawable.family_older_sister));
        family.add(new Word("Younger sister", "Dhākaṭī bahīṇa", R.raw.family_8_younger_sister, R.drawable.family_younger_sister));
        family.add(new Word("Grandfather", "Ājobā", R.raw.family_10_grandfather, R.drawable.family_grandfather));
        family.add(new Word("Grandmother", "Ājī", R.raw.family_9_grandmother, R.drawable.family_grandmother));

        WordAdapter adapter = new WordAdapter(this, family, R.color.section_family);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word number = family.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                releaseMediaPlayer();

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, number.getmAudioResource());
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
