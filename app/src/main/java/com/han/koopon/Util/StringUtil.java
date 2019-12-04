package com.han.koopon.Util;

public class StringUtil {

    public static String emailToStringID(String email){
        email =  email.substring(0,email.indexOf("@"));
        return email;
    }
}
