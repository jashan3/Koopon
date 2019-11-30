package com.han.koopon.Main;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class CouponVO {
    private String imgURL;
    private String coupon_title;
    private String coupon_body;
    private String date;
    private boolean isUse;

    public CouponVO(){

    }
    public CouponVO(String imgURL, String coupon_title, String coupon_body, String date, boolean isUse) {
        this.imgURL = imgURL;
        this.coupon_title = coupon_title;
        this.coupon_body = coupon_body;
        this.date = date;
        this.isUse = isUse;
    }

    public String getImgURL() {
    return imgURL;
}

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getCoupon_title() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title = coupon_title;
    }

    public String getCoupon_body() {
        return coupon_body;
    }

    public void setCoupon_body(String coupon_body) {
        this.coupon_body = coupon_body;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
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
}
