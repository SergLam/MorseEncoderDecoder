package com.samurai.morseencoder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.samurai.morseencoder.R;
import com.samurai.morseencoder.activities.Translation_Eng;
import com.samurai.morseencoder.activities.Translation_Germ;
import com.samurai.morseencoder.activities.Translation_Rus;

/**
 * Created by Sergey on 24.01.2017.
 */
public class Rules extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rules, container, false);

        Button btn_eng = view.findViewById(R.id.tr_btn_eng);
        btn_eng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Translation_Eng.class);
                startActivity(intent);
            }
        });

        Button btn_rus = view.findViewById(R.id.tr_btn_rus);
        btn_rus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Translation_Rus.class);
                startActivity(intent);
            }
        });

        Button btn_germ = view.findViewById(R.id.tr_btn_germ);
        btn_germ.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Translation_Germ.class);
                startActivity(intent);
            }
        });

        return view;

    }

}
