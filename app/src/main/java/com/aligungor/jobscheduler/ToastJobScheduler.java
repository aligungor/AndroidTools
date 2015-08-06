package com.aligungor.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

/**
 * Created by AliGungor on 15.07.2015.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ToastJobScheduler {

    private static final int jobId = 1;
    private static final int jobPeriod = 5000;

    private Context context;
    private JobScheduler jobScheduler;

    public ToastJobScheduler(Context context) {
        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        this.context = context;
    }

    public void buildAndStartScheduler() {
        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(context.getPackageName(), ToastSchedulerService.class.getName()));
        builder.setPeriodic(jobPeriod); // working period
        // builder.setPersisted(true); // work on start
        if (jobScheduler.schedule(builder.build()) <= 0) {
            System.out.println("Error while doing job!");
        }
    }

    public void cancel() {
        jobScheduler.cancel(jobId);
        // or you can use cancelAll() to cancel all jobs
    }
}
