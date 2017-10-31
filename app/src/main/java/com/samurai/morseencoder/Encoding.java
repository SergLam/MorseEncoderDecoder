package com.samurai.morseencoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sergey on 10.02.2017.
 */
public class Encoding {

    public Encoding(){}
    boolean trans_complited = false;

    public String[] alpha_eng = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+",  };
    public String[] dottie_eng = { ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
            ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".-*-*.",
            "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
            "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
            "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", "*******",".*-*-*-*-*.",
            "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-", ".*.*-*-*.*.",
            "-*.*-*.*-*.", "-*.*.*-*.  ", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-", ".*-*-*.*-*.",
            ".*-*.*-*."};

    public String[] alpha_germ = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
            "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "0", " ", "'", ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+", "ä", "ö", "ü", "ß"};
    public String[] dottie_germ = { ".*-", "-*.*.*.", "-*.*-*.", "-*.*.", ".", ".*.*-*.", "-*-*.",
            ".*.*.*.", ".*.", ".*-*-*-", "-*.*-", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
            "-*-*.*-", ".*-*.", ".*.*.", "-", ".*.*-", ".*.*.*-", ".*-*-", "-*.*.*-",
            "-*.*-*-", "-*-*.*.", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-", ".*.*.*.*-", ".*.*.*.*.",
            "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-", "*******",
            ".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-", ".*-*.*-*.*-",
            ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.", "-*.*.*.*-",
            ".*-*-*.*-*.", ".*-*.*-*.", ".*-*.*-", "-*-*-*.", ".*.*-*-", ".*.*.*-*-*.*."};


    public String[] alpha_rus = { "а", "б", "в", "г", "д", "е", "ж", "з",
            "и", "й", "к", "л", "м", "н", "о", "п", "р", "с", "т",
            "у", "ф", "х", "ц", "ш", "щ", "э", "ю", "я", "ь", "ы",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " ", "'",
            ":", ",", "-", "(", ".", "?", ";", "/", "-", ")",
            "=", "@", "+",  };
    public String[] dottie_rus = { ".*-", "-*.*.*.", ".*-*-", "-*-*.", "-*.*.", ".", ".*.*.*-",
            "-*-*.*.", ".*.", ".*-*-*-", "-*.*-*.", ".*-*.*.", "-*-", "-*.", "-*-*-", ".*-*-*.",
            ".*-*.", ".*.*.", "-", ".*.*-", ".*.*-*.", ".*.*.*.", "-*.*-*.", "-*-*-*-", "-*-*.*-",
            ".*.*-*.*.", ".*.*-*-", ".*-*.*-", "-*.*.*-", "-*.*-", ".*-*-*-*-", ".*.*-*-*-", ".*.*.*-*-",
            ".*.*.*.*-", ".*.*.*.*.", "-*.*.*.*.", "-*-*.*.*.", "-*-*-*.*.", "-*-*-*-*.", "-*-*-*-*-",
            "*******",".*-*-*-*-*.", "-*-*-*.*.*.", "-*-*.*.*-*-", "-*.*.*.*.*-", "-*.*-*-*.*-",
            ".*-*.*-*.*-", ".*.*-*-*.*.", "-*.*-*.*-*.", "-*.*.*-*.", ".*.*-*-*.*-", "-*-*-*.*.",
            "-*.*.*.*-", ".*-*-*.*-*.", ".*-*.*-*."};

    //. . . -   . . .   - - .       . - - -   . . . -   . - . - .
    //.*.*.*-***.*.*.***-*-*.*******.*-*-*-***.*.*.*-***.*-*.*-*.

    public String translate_to_code(String text, String lang){

        char[] translates = (text.toLowerCase()).toCharArray();
        // Get language selected in settings
        String result = "";
        trans_complited = false;
        if(lang.equals("russian") && isRussian(text)) {
            result = rus_to_morse(translates);
            trans_complited = true;
        }
        if(lang.equals("german") && isGerman(text)){
            result = germ_to_morse(translates);
            trans_complited = true;
        }
        if(lang.equals("english") && isEnglish(text)){
            result = eng_to_morse(translates);
            trans_complited = true;
        }
        return result;


    }

    public boolean isRussian(String text){
        boolean result = false;

        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "а-яА-ЯёЁ" +    //буквы русского алфавита
                        "\\d" +         //цифры
                        "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве
        Matcher matcher = pattern.matcher(text);

        result = matcher.matches();

        return result;
    }

    public boolean isGerman(String text){
        boolean result = false;

        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "a-zA-ZäÄöÖüÜß" +
                        "\\d" +         //цифры
                        "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве
        Matcher matcher = pattern.matcher(text);

        result = matcher.matches();

        return result;
    }

    public boolean isEnglish(String text){
        boolean result = false;

        Pattern pattern = Pattern.compile(
                "[" +                   //начало списка допустимых символов
                        "a-zA-Z" +
                        "\\d" +         //цифры
                        "\\s" +         //знаки-разделители (пробел, табуляция и т.д.)
                        "\\p{Punct}" +  //знаки пунктуации
                        "]" +                   //конец списка допустимых символов
                        "*");                   //допускается наличие указанных символов в любом количестве
        Matcher matcher = pattern.matcher(text);

        result = matcher.matches();

        return result;
    }


    public String eng_to_morse(char [] translates){
        String morse = "";

        for (int j = 0; j < translates.length; j++){
            char a = translates[j];
            String b = Character.toString(a);
            for(int i=0;i<alpha_eng.length;i++){
                if(j<translates.length-1){
                    if(alpha_eng[i].equals(b) && !Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_eng[i]+"***";
                    }
                    if(alpha_eng[i].equals(b) && Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_eng[i];
                    }

                }else if(alpha_eng[i].equals(b))
                {
                    morse += dottie_eng[i];
                }
            }
        }
        return morse;
    }

    public String germ_to_morse(char [] translates){
        String morse = "";

        for (int j = 0; j < translates.length; j++){
            char a = translates[j];
            String b = Character.toString(a);
            for(int i=0;i<alpha_germ.length;i++){
                if(j<translates.length-1){
                    if(alpha_germ[i].equals(b) && !Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_germ[i]+"***";
                    }
                    if(alpha_germ[i].equals(b) && Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_germ[i];
                    }

                }else if(alpha_germ[i].equals(b))
                {
                    morse += dottie_germ[i];
                }
            }
        }
        return morse;
    }

    public String rus_to_morse(char [] translates){
        String morse = "";

        for (int j = 0; j < translates.length; j++){
            char a = translates[j];
            String b = Character.toString(a);
            for(int i=0;i<alpha_rus.length;i++){
                if(j<translates.length-1){
                    if(alpha_rus[i].equals(b) && !Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_rus[i]+"***";
                    }
                    if(alpha_rus[i].equals(b) && Character.toString(translates[j+1]).equals(" "))
                    {
                        morse+=dottie_rus[i];
                    }

                }else if(alpha_rus[i].equals(b))
                {
                    morse += dottie_rus[i];
                }
            }
        }
        return morse;
    }
}
