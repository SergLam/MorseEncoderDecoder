package com.samurai.morseencoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Sergey on 24.01.2017.
 */
public class Rules extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.rules, container, false);

        Button btn_eng = (Button)view.findViewById(R.id.tr_btn_eng);
        btn_eng.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Translation_Eng.class);
                startActivity(intent);
            }
        });

        Button btn_rus = (Button)view.findViewById(R.id.tr_btn_rus);
        btn_rus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Translation_Rus.class);
                startActivity(intent);
            }
        });

        Button btn_germ = (Button)view.findViewById(R.id.tr_btn_germ);
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
