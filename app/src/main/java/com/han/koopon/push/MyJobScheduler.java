package com.han.koopon.push;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.han.koopon.Main.Coupon;
import com.han.koopon.Main.FireBaseQuery;
import com.han.koopon.MainActivity;
import com.han.koopon.R;
import com.han.koopon.Util.PFUtil;
import com.han.koopon.Util.PushUtil;
import com.han.koopon.Util.StringUtil;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyJobScheduler extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Logger.i("onStartJob ~");
        String userID = PFUtil.getPreferenceString(this,PFUtil.AUTO_LOGIN_ID);
        userID = StringUtil.emailToStringID(userID);
        selectOnceFB(userID);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Logger.i("onStopJob ~");
        return false;
    }

    public void  selectOnceFB(String userID) {
        List <Coupon>list = new ArrayList<>();
        FireBaseQuery.mDatabase.child(FireBaseQuery.ROOT).child(userID).child(FireBaseQuery.TYPE1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Coupon post = snapshot.getValue(Coupon.class);
                    list.add(post);
                }
                processData(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void processData(List <Coupon>list){
        for (Coupon c : list){
           if(compareDate(c.date)&&!c.isUse){
               sendNotification(  c.coupon_title,c.date);
           }
        }
        PushUtil.wakeup(this);
    }

    public boolean compareDate(String itemDate){
        try {
            Date start = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
            Date end = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH).parse(itemDate);

            //과거 1
            if (start.compareTo(end) > 0) {
                Logger.i("과거");
                return false;
            }

            //미래 -1
            else if (start.compareTo(end) < 0) {
                Logger.i("미래");
                return true;
            }

            //현재 0
            else if (start.compareTo(end) == 0) {
                Logger.i("현재");
                return true;
            }

            else {
                Logger.i("Something weird happened...");
                return false;
            }

        } catch (ParseException e) {
            Logger.e(e.toString());
            return false;
        }
    }
    public void sendNotification(String title ,String body){
        if (title == null){
            title = "";
        }

        if (body == null){
            body = "";
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1001, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_local_offer_black_24dp)
                .setContentTitle("쿠폰이름 : "+title)
                .setContentText("기한 : ~"+body+" 까지")
                .setTicker("쿠폰 : "+title +", ~"+body+" 까지")
                .setWhen(System.currentTimeMillis())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(PushUtil.PANDING_NOTIFICATION_ID, builder.build());
    }


}
