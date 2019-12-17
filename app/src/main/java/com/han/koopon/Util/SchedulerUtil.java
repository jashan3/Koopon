package com.han.koopon.Util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.han.koopon.push.MyJobScheduler;

import java.util.concurrent.TimeUnit;

public class SchedulerUtil {

    public static final int MY_BACKGROUND_JOB = 0;

    public static void runScheudler(Context context){
        long latency =  TimeUnit.MINUTES.toMillis(60);   //1분
        long deadline = TimeUnit.MINUTES.toMillis(90);   //3분

        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName serviceComponent = new ComponentName(context, MyJobScheduler.class);
        JobInfo jobInfo = new JobInfo.Builder(MY_BACKGROUND_JOB, serviceComponent)
                .setMinimumLatency(5000)
                .setOverrideDeadline(10000)
                .build();
        js.schedule(jobInfo);
    }

    public static void runScheudlerAlways(Context context){
        long period =  TimeUnit.MINUTES.toMillis(60*6);   // 시간
        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName serviceComponent = new ComponentName(context, MyJobScheduler.class);
        JobInfo jobInfo = new JobInfo.Builder(MY_BACKGROUND_JOB, serviceComponent)
                .setPeriodic(period)
                .build();
        js.schedule(jobInfo);
    }
}
