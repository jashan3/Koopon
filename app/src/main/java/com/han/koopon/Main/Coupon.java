package com.han.koopon.Main;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Coupon {
    public String imgURL;
    public String coupon_title;
    public String coupon_body;
    public String date;
    public boolean isUse;

    public Coupon(){

    }
    public Coupon(String imgURL, String coupon_title, String coupon_body, String date, boolean isUse) {
        this.imgURL = imgURL;
        this.coupon_title = coupon_title;
        this.coupon_body = coupon_body;
        this.date = date;
        this.isUse = isUse;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("imgURL", imgURL);
        result.put("coupon_title", coupon_title);
        result.put("coupon_body", coupon_body);
        result.put("date", date);
        result.put("isUse", isUse);
        return result;
    }

    public String CouponToString(){
        return "{ mgURL : "+imgURL+",coupon_title :"+coupon_title+", coupon_body : "+coupon_body+", date : "+date+ "date :" +date+" isUse: +"+isUse+"}";
    }
}
