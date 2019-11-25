package com.han.koopon.Home.SQL;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Dao
public interface CouponDAO {
    @Query("SELECT * FROM coupon")
    List<Coupon> getAll();

    @Query("SELECT * FROM coupon WHERE uid IN (:userIds)")
    List<Coupon> loadAllByIds(int[] userIds);

//    @Query("SELECT * FROM coupon WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    Coupon findByName(String first, String last);

    @Insert
    void insertAll(Coupon... users);

    @Delete
    void delete(Coupon user);
}

