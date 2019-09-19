package de.github.draggabledialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;


public class DraggableAlertDialog   {

    AlertDialog dialog ;
    DraggableLayout layout;
    public DraggableAlertDialog(Activity activity ) {

        AlertDialog.Builder builder=new AlertDialog.Builder(activity ,R.style.DraggableDialog);

        layout = new DraggableLayout(activity);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.gravity = Gravity.CENTER ;
        layout. setLayoutParams(layoutParams);

        builder.setView(layout);
        builder.setCancelable(true);
        dialog=builder.create();
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        width = (int)(display.getWidth()); //设置宽度
        height =  (int)(display.getHeight()); //设置宽度
        layout.setBackgroundClick(new DraggableLayout.OnBackgroundClick() {
            @Override
            public void onClick() {
                dismiss();
            }
        });
    }
    int  width = 0;
    int  height = 0;
    public void show() {
        if (dialog!=null){
            dialog.show();

            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = width; //设置宽度
            lp.height = height - 50; //设置宽度
            ViewGroup.LayoutParams params = layout.getLayoutParams();
            params.width = lp.width ;
            params.height =  lp.height ;
            layout.setGravity(Gravity.CENTER);

            dialog.getWindow().setAttributes(lp);


        }
    }
    public void dismiss() {
        if (dialog!=null)
        dialog.dismiss();
    }

    public void setView(View view) {
        if (layout!= null)
        layout.addView(view);
    }
}
