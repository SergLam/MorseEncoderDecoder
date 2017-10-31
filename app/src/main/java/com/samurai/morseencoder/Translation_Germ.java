package com.samurai.morseencoder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sergey on 13.01.2017.
 */
public class Translation_Germ extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.translation_germ);
        Button germ_back = (Button)findViewById(R.id.btn_germ_back);
        germ_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}

