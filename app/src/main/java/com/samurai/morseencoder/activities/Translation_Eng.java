package com.samurai.morseencoder.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samurai.morseencoder.R;

/**
 * Created by Sergey on 13.01.2017.
 */
public class Translation_Eng extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation_eng);

        Button eng_back = (Button)findViewById(R.id.btn_eng_back);
        eng_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


}
