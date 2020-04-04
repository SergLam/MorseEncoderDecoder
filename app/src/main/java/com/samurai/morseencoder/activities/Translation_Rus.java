package com.samurai.morseencoder.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samurai.morseencoder.R;

/**
 * Created by Sergey on 13.01.2017.
 */
public class Translation_Rus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation_rus);
        Button rus_back = (Button)findViewById(R.id.btn_rus_back);
        rus_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
