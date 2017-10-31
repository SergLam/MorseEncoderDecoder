package com.samurai.morseencoder;

/**
 * Created by Sergey on 24.01.2017.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Settings extends Fragment{

    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.settings, container, false);
        //Button button = (Button)view.findViewById(R.id.btn_quit);

        mAdView = (AdView)view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return view;
    }

    public void set_lang(View view){
        MainFrame.preferences = getContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
        MainFrame.editor = MainFrame.preferences.edit();

        RadioGroup radio = (RadioGroup)view.findViewById(R.id.radioGroup);
        int radioButtonID = radio.getCheckedRadioButtonId();
        RadioButton radioBut = (RadioButton)view.findViewById(radioButtonID);
        String lang = (String) radioBut.getTag();

        MainFrame.editor.putString("lang", lang);
        MainFrame.editor.apply();
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        View v = getView();
        if (!visible && v!=null){
            set_lang(v);
        }
        if (visible && v!=null){
            set_lang(v);
        }
    }

}
