package com.aligungor.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by AliGungor on 15.07.2015.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ToastSchedulerService extends JobService {

    //Mesajı mJobHandler olarak tanımladığımız Handler sınıfında tuttuk
    private Handler jobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "JobService task running", Toast.LENGTH_SHORT).show();
            jobFinished((JobParameters) msg.obj, false);
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        jobHandler.sendMessage(Message.obtain(jobHandler, 1, params));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobHandler.removeMessages(1);
        return false;
    }
}
