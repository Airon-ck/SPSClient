package com.nbxuanma.spsclient.utils;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.nbxuanma.spsclient.R;

/**
 * Created by Airon on 2018/11/22.
 */

public class DialogBuild {

    /**
     * 显示自定义布局
     */
    public static void showCustom(View v, String content, String buttonText,DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        View view = View.inflate(v.getContext(), R.layout.dialog_title_top, null);
        TextView titleTop = view.findViewById(R.id.title_top);
        titleTop.setText(content);
        builder.setView(view);
        builder.setPositiveButton(buttonText, clickListener);
        builder.show();
    }

    public static void show(View v, String message, String buttonText, DialogInterface.OnClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("提示");
        builder.setMessage(message);
        builder.setPositiveButton(buttonText, clickListener);
        builder.show();
    }

    public interface OnEditClickListener {
        void onClick(String name, String link);
    }

}
