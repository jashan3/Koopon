package com.han.koopon.Home.SQL;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "coupon")
public class Coupon {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "coupon_title")
    public String coupon_title;

    @ColumnInfo(name = "coupon_date")
    public String coupon_date;

    @ColumnInfo(name = "coupon_use")
    public String coupon_use;
}
