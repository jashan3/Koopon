package com.han.koopon.Home.SQL;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Coupon.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CouponDAO userDao();
}
