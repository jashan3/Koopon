package com.han.koopon.Util;

import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date StringToDate(String dtStart){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = format.parse(dtStart);
            return date;
        } catch (ParseException e) {
            Logger.e("ParseException : %s",e.toString());
            return null;
        }
    }

    public static String dateToString(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            String dateTime = dateFormat.format(date);
            return dateTime;
        } catch (Exception e) {
            Logger.e("ParseException : %s",e.toString());
            return null;
        }
    }


}
