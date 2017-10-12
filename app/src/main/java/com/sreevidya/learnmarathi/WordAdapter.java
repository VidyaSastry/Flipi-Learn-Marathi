package com.sreevidya.learnmarathi;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

class WordAdapter extends ArrayAdapter<Word>{

    private int mBackgroundColor;

    WordAdapter(Activity context, ArrayList<Word> words, int backgroundColor){
        super(context, 0, words);
        this.mBackgroundColor = backgroundColor;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );
        }

        Word currentWord = getItem(position);

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.icon_image_view);
        if(currentWord.hasWordImage()) {
            imageView.setImageResource(currentWord.getmImageResource());
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
        }

        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.text_details);
        linearLayout.setBackgroundResource(mBackgroundColor);

        TextView marathiView = (TextView)listItemView.findViewById(R.id.marathi_text_view);
        marathiView.setText(currentWord.getmMaratiWord());

        TextView englishView = (TextView) listItemView.findViewById(R.id.english_text_view);
        englishView.setText(currentWord.getmEnglishWord());

        return  listItemView;
    }
}
