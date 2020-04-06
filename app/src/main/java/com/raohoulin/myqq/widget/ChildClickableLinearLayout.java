package com.raohoulin.myqq.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.raohoulin.myqq.R;

/**
 * 可以直接控制所有子控件是否可点击的LinearLayout
 */
public class ChildClickableLinearLayout extends FrameLayout implements OnChildClickableListener {
    //子控件是否可以接受点击事件
    private boolean childClickable = true;
    private Context mContent;
    private View frontView;
    private SlidingMenu menu;

    public ChildClickableLinearLayout(Context context) {
        super(context);
        mContent = context;
    }

    public ChildClickableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContent = context;
    }

    public ChildClickableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContent = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ChildClickableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContent = context;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void show() {
        if (frontView == null) {
            frontView = new View(mContent);
            frontView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    menu.closeMenu();
                }
            });
            frontView.setBackgroundColor(R.color.colorFront_1);
            frontView.bringToFront();  //设置在最上层
            this.addView(frontView);
        }
    }

    @Override
    public void hide() {
        if (frontView != null) {
            this.removeView(frontView);
            frontView = null;
        }
    }

    @Override
    public void setSlidingMenu(SlidingMenu menu) {
        this.menu = menu;
    }
}