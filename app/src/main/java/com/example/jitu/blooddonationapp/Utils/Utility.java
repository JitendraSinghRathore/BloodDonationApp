package com.example.jitu.blooddonationapp.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Window;

import com.example.jitu.blooddonationapp.Activity.FrontPageActivity;
import com.example.jitu.blooddonationapp.R;


public class Utility
{
    public static Dialog dialog;
    Context context;
    public static void showProgressDialog(Context context_dilog, String Title) {
        dialog = new Dialog(context_dilog);
        if (dialog != null || dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable((new ColorDrawable(android.graphics.Color.TRANSPARENT)));
        dialog.setContentView(R.layout.prgress_bar_layout);
        dialog.setCancelable(true);
        dialog.show();
    }
    public static void hideProgressDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }
    public static void createAlertDilog(final Context context_dilog, String Title, String PositiveButtonTitle, String NegativeButtonTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context_dilog);
        builder.setMessage(Title)
                .setCancelable(false)
                .setPositiveButton(PositiveButtonTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context_dilog, FrontPageActivity.class);
                        int mPendingIntentId = 123456;
                        PendingIntent mPendingIntent = PendingIntent.getActivity(context_dilog, mPendingIntentId, intent,
                                PendingIntent.FLAG_CANCEL_CURRENT);

                        context_dilog.startActivity(intent);
                        ((Activity)context_dilog).finish();
                    }
                })
                .setNegativeButton(NegativeButtonTitle, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        AlertDialog alert = builder.create();

        if (alert != null || alert.isShowing()) {
            alert.dismiss();
        }

        alert.show();
    }

    public static AlertDialog createAlertDialog( final Context context_dilog_NEW, String Title, String PositiveButtonTitle, String NegativeButtonTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context_dilog_NEW);
        builder.setMessage(Title)
                .setCancelable(false);


        return builder.create();
    }
}