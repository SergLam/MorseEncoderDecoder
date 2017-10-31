package com.samurai.morseencoder;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Sergey on 24.01.2017.
 */
public class MainFrame extends Fragment{

        public static SharedPreferences preferences;
        public static SharedPreferences.Editor editor;
        Encoding obj_encode = new Encoding();
        Decoding obj_decode = new Decoding();

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view=inflater.inflate(R.layout.main_frame, container, false);
            // Set filter to morse text field
            EditText morse = (EditText)view.findViewById(R.id.morse_text);
            InputFilter filter = new InputFilter() {
                public CharSequence filter(CharSequence source, int start, int end,
                                           Spanned dest, int dstart, int dend) {
                    for (int i = start; i < end; i++) {
                        // Your condition here
                        boolean result = Character.toString(source.charAt(i)).equals(".") || Character.toString(source.charAt(i)).equals("-") ||Character.toString(source.charAt(i)).equals("*");
                        if (!result) {
                            return "";
                        }
                    }
                    return null;
                }
            };
            morse.setFilters(new InputFilter[]{filter});
            // Set radiogroup listener
            RadioGroup radGrp = (RadioGroup)view.findViewById(R.id.radio_mode);
            int checkedRadioButtonID = radGrp.getCheckedRadioButtonId();
            radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup arg0, int id) {
                    switch (id) {
                        case R.id.radio_encode:
                            get_mode(getView());
                            break;
                        case R.id.radio_decode:
                            get_mode(getView());
                            break;
                        default:
                            break;
                    }
                }
            });
            // Connect translation function to translate button
            Button button = (Button) view.findViewById(R.id.trans_btn);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText text = (EditText) getView().findViewById(R.id.eng_text);
                    EditText code = (EditText) getView().findViewById(R.id.morse_text);
                    preferences = getContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
                    String mode = preferences.getString("mode","");
                    String lang = preferences.getString("lang","");
                    String message = text.getText().toString();
                    String message_morse = code.getText().toString();
                    if(mode.equals("encode")){
                        String result = obj_encode.translate_to_code(message, lang);
                        if(obj_encode.trans_complited==true){
                            code.setText(result);
                        } else Toast.makeText(getActivity(), getResources().getString(R.string.mess_encode), Toast.LENGTH_SHORT).show();

                    }
                    if(mode.equals("decode")){
                        String result = obj_decode.code_to_text(message_morse, lang);
                        if(obj_decode.trans_complited==true){
                            text.setText(result);
                        } else Toast.makeText(getActivity(), getResources().getString(R.string.mess_decode), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            // Put preferences to flag choise
            preferences = getContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
            editor = preferences.edit();
            editor.putString("lang", "english");
            editor.putString("mode", "encode");
            editor.apply();
            set_flag(view);



            return view;

        }

    public View set_flag(View view) {
        Button btn_flag = (Button)view.findViewById(R.id.btn_flag);
        preferences = getContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
        String lang = preferences.getString("lang","");
        if(lang.equals("english")){
            btn_flag.setBackgroundResource(R.drawable.eng_flag);
        }
        if(lang.equals("russian")){
            btn_flag.setBackgroundResource(R.drawable.rus_flag);
        }
        if(lang.equals("german")){
            btn_flag.setBackgroundResource(R.drawable.german_flag);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        View v = getView();
        if (visible && v!=null){
            set_flag(v);
        }
    }

    public void get_mode(View v){
        RadioGroup radio = (RadioGroup)v.findViewById(R.id.radio_mode);
        int radioButtonID = radio.getCheckedRadioButtonId();
        RadioButton radioBut = (RadioButton)v.findViewById(radioButtonID);
        String mode = (String) radioBut.getTag();
        MainFrame.preferences = getContext().getSharedPreferences("flag", Context.MODE_PRIVATE);
        MainFrame.editor = MainFrame.preferences.edit();
        MainFrame.editor.putString("mode", mode);
        MainFrame.editor.apply();
        //Toast.makeText(getActivity(), mode, Toast.LENGTH_LONG).show();
    }

}
