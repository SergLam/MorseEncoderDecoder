package com.samurai.morseencoder;

import org.w3c.dom.DOMStringList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey on 10.02.2017.
 */
public class Decoding {

    public Decoding(){}
    boolean trans_complited = true;

    public String[] alpha_eng = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+",  ""};
    public String[] dottie_eng = { ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
            ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".-*-*.",
            "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
            "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
            "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ",".*-*-*-*-*.",
            "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-", ".*.*-*-*.*.",
            "-*.*-*.*-*.", "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-", ".*-*-*.*-*.",
            ".*-*.*-*.",""};

    public String[] alpha_germ = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+", "ä", "ö", "ü", "ß",""};
    public String[] dottie_germ = { ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
            ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
            "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
            "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
            "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", " ",
            ".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-",
            ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-",
            ".*-*-*.*-*.", ".*-*.*-*.", ".*-*.*-", "-*-*-*.", ".*.*-*-", ".*.*.*-*-*.*.",""};


    public String[] alpha_rus = { "а", "б", "в", "г", "д", "е", "ж", "з",
            "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
            "у", "ф", "х", "ц", "ш", "щ", "э", "ю", "я", "ь", "ы",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "'",
            ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+", "" };
    public String[] dottie_rus = { ".*-", "-*.*.*.", ".*-*-", "-*-*.", "-*.*.", ".", ".*.*.*-",
            "-*-*.*.", ".*.", ".*-*-*-", "-*.*-*.", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
            ".*-*.", ".*.*.", "-", ".*.*-", ".*.*-*.", ".*.*.*.", "-*.*-*.", "-*-*-*-", "-*-*.*-",
            ".*.*-*.*.", ".*.*-*-", ".*-*.*-", "-*.*.*-", "-*.*-", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-",
            ".*.*.*.*-", ".*.*.*.*.", "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-",
            " ",".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-",
            ".*-*.*-*.*-", ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.",
            "-*.*.*.*-", ".*-*-*.*-*.", ".*-*.*-*.", ""};

    //. . . -   . . .   - - .       . - - -   . . . -   . - . - .
    //.*.*.*-***.*.*.***-*-*.*******.*-*-*-***.*.*.*-***.*-*.*-*.

    public String code_to_text(String code, String lang){
        // Get language selected in settings
        String result = "";
        trans_complited = true;
        if(lang.equals("russian")) {
            result = morse_to_rus(code);
        }
        if(lang.equals("german")){
            result =morse_to_germ(code);
        }
        if(lang.equals("english")){
            result = morse_to_eng(code);
        }
        return result;


    }

    public String morse_to_eng(String code){
        String result = "";
        boolean is_match = false;
        code=code.replaceAll("\\*\\*\\*\\*\\*\\*\\*",", ,");
        code=code.replaceAll("\\*\\*\\*",",");
        String[] code_split = code.split(",");
        for (int j = 0; j < code_split.length; j++) {
            is_match = false;
            for(int i=0; i<alpha_eng.length; i++){
                if(dottie_eng[i].equals(code_split[j]))
                {
                    is_match = true;
                    result +=alpha_eng[i];
                }
            }
            if(is_match==true){
            } else trans_complited=false;
        }
        return result;
    }

    public String morse_to_germ(String code){
        String result = "";
        boolean is_match = false;
        code=code.replaceAll("\\*\\*\\*\\*\\*\\*\\*",", ,");
        code=code.replaceAll("\\*\\*\\*",",");
        String[] code_split = code.split(",");
        for (int j = 0; j < code_split.length; j++) {
            is_match = false;
            for(int i=0; i<alpha_germ.length; i++){
                if(dottie_germ[i].equals(code_split[j]))
                {
                    is_match = true;
                    result +=alpha_germ[i];
                }
            }
            if(is_match==true){
            } else trans_complited=false;
        }
        return result;
    }

    public String morse_to_rus(String code){
        String result = "";
        boolean is_match = false;
        code=code.replaceAll("\\*\\*\\*\\*\\*\\*\\*",", ,");
        code=code.replaceAll("\\*\\*\\*",",");
        String[] code_split = code.split(",");
        for (int j = 0; j < code_split.length; j++) {
            is_match = false;
            for(int i=0; i<alpha_rus.length; i++){
                if(dottie_rus[i].equals(code_split[j]))
                {
                    is_match = true;
                    result +=alpha_rus[i];
                }
            }
            if(is_match==true){
            } else trans_complited=false;
        }
        return result;
    }

}