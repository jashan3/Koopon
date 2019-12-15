package com.han.koopon.Util;

import com.orhanobut.logger.Logger;

import org.apache.commons.codec.binary.Base64;

public class StringUtil {

    public static String emailToStringID(String email){
        email =  email.substring(0,email.indexOf("@"));
        return email;
    }

    public static String base64(String text) {
        /* base64 encoding */
        byte[] encodedBytes = Base64.encodeBase64(text.getBytes());
        return new String(encodedBytes);
    }

    public static String base64ToString(String strBase64){
        /* base64 decoding */
        byte[] decodedBytes = Base64.decodeBase64(strBase64);
        return new String(decodedBytes);
    }

}
