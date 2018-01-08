package com.example.yhao.floatwindow;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yhao.fixedfloatwindow.R;
import com.yhao.floatwindow.CircularRevealView;
import com.yhao.floatwindow.FabAnimationUtils;

public class A_Activity extends AppCompatActivity {


    private CircularRevealView revealView;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        setTitle("A");
        revealView = (CircularRevealView) findViewById(R.id.reveal);
        BaseApplication.setOnclick(new OnclickInterface() {
            @Override
            public void onclick(View view) {
                launchPowerMenu(view);
            }
        });
    }

    public void change(View view) {
        startActivity(new Intent(A_Activity.this, B_Activity.class));
    }

    android.os.Handler handler;

    private void launchPowerMenu(final View v) {
        this.v = v;
        final Point p = getLocationInView(revealView, v);

        revealView.reveal(p.x, p.y, getResources().getColor(R.color.tran_11), v.getHeight() / 2, 370, null);

        FabAnimationUtils.scaleOut(v, new FabAnimationUtils.ScaleCallback() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
//                FabAnimationUtils.scaleIn(v);
            }
        });

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNormalDialog();
            }
        }, 160);
    }

    private void dismiss(final View view) {
        final Point p = getLocationInView(revealView, view);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.hide(p.x, p.y, getResources().getColor(R.color.tran), 0, 330, null);
                FabAnimationUtils.scaleOut(view, new FabAnimationUtils.ScaleCallback() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {
                        FabAnimationUtils.scaleIn(view);
                    }
                });
            }
        }, 300);
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private void showNormalDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.dialog_custom);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dismiss(v);
            }
        });
    }
}
