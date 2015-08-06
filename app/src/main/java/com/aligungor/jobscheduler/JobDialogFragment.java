package com.aligungor.jobscheduler;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.aligungor.androidtools.R;

/**
 * Created by AliGungor on 15.07.2015.
 */
public class JobDialogFragment extends DialogFragment implements View.OnClickListener {

    private ToastJobScheduler toastJobScheduler;

    public static void show(FragmentManager fragmentManager) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobDialogFragment jobDialogFragment = new JobDialogFragment();
            jobDialogFragment.show(fragmentManager, "mJobDialog");
        } else {
            System.out.println("JobScheduler requires Lollipop or newer");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewDialog = inflater.inflate(R.layout.dialog_fragment_job, container, false);
        toastJobScheduler = new ToastJobScheduler(getActivity());
        viewDialog.findViewById(R.id.btnStartJob).setOnClickListener(this);
        viewDialog.findViewById(R.id.btnCancelJob).setOnClickListener(this);
        return viewDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartJob:
                toastJobScheduler.buildAndStartScheduler();
                break;
            case R.id.btnCancelJob:
                toastJobScheduler.cancel();
                break;
            default:
                break;
        }
    }
}
