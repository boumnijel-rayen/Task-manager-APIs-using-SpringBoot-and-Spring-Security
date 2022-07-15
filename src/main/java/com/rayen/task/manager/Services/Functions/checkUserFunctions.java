package com.rayen.task.manager.Services.Functions;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class checkUserFunctions {

    public static Boolean haveLettre(String chaine){
        int i = 0;
        Boolean res = false;
        while ( (i<chaine.length()) && (res==false) ){
            if ( ((chaine.charAt(i) >= 'A') && (chaine.charAt(i) <= 'Z')) || ((chaine.charAt(i) >= 'a') && (chaine.charAt(i) <= 'z')) ){
                res = true;
            }
            i++;
        }
        return res;
    }

    public static Boolean haveNum(String chaine){
        int i = 0;
        Boolean res = false;
        while ( (i<chaine.length()) && (res==false) ){
            if ((chaine.charAt(i) >= '0') && (chaine.charAt(i) <= '9')) {
                res = true;
            }
            i++;
        }
        return res;
    }

    public static Boolean isEmail(String chaine){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(chaine);
        return matcher.matches();
    }
}
