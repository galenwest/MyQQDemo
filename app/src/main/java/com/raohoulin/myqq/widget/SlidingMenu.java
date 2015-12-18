package com.raohoulin.myqq.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.raohoulin.myqq.R;
import com.raohoulin.myqq.util.ScreenUtils;

public class SlidingMenu extends HorizontalScrollView {
    private static final float SLIDING_SPEED = 760f;
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * dp
     */
    private int mMenuRightPadding;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    private int mHalfMenuWidth;
    /**
     * 用于计算手指滑动的速度。
     */
    private VelocityTracker mVelocityTracker;
    private int speed;
    /**
     * 判断方向变量
     */
/*    private int thisX;
    private int thisY;
    private int moveX;
    private int moveY;
    private double sin;
    private boolean isFirstMove = true;*/

    private boolean isOpen;
    private boolean once;

    private ViewGroup mMenu;
    private ViewGroup mContent;

    private OnChildClickableListener onChildClickableListener;

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScreenWidth = ScreenUtils.getScreenWidth(context);
        speed = ScreenUtils.dip2px(context, SLIDING_SPEED);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.SlidingMenu, defStyle, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    // 默认75
                    mMenuRightPadding = a.getDimensionPixelSize(attr,
                            (int) TypedValue.applyDimension(
                                    TypedValue.COMPLEX_UNIT_DIP, 75f,
                                    getResources().getDisplayMetrics()));// 默认为10DP
                    break;
            }
        }
        a.recycle();
    }

    public SlidingMenu(Context context) {
        this(context, null, 0);
    }

    public void setOnChildClickableListener(OnChildClickableListener onChildClickableListener) {
        this.onChildClickableListener = onChildClickableListener;
    }

    public boolean getIsOpen() {
        return isOpen;
    }

    /**
     * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中。
     *
     * @param event 右侧布局监听控件的滑动事件
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 获取手指在右侧布局的监听View上的滑动速度。
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
//        return Math.abs(velocity); // 取绝对值
        return velocity;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 显示的设置一个宽度
         */
        if (!once) {
            FrameLayout frameLayout = (FrameLayout) getChildAt(0);
            LinearLayout wrapper = (LinearLayout) frameLayout.getChildAt(1);
            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mContent = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            mMenu.getLayoutParams().width = mMenuWidth;
            mContent.getLayoutParams().width = mScreenWidth;

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            once = true;
        }
    }

    /* @Override
     public boolean onInterceptTouchEvent(MotionEvent ev) {
         int action = ev.getAction();
         switch (action) {
             case MotionEvent.ACTION_DOWN:
                 isFirstMove = true;
                 thisX = (int) ev.getX();
                 thisY = (int) ev.getY();
                 Log.d("RHL", "Down thisX=" + thisX + "; thisY=" + thisY);
                 break;
             case MotionEvent.ACTION_MOVE:
                 Log.d("RHL", "Intercept Move");
                 if (isFirstMove) {
                     moveX = (int) ev.getX() - thisX;
                     moveY = (int) ev.getY() - thisY;
                     thisX = (int) ev.getX();
                     thisY = (int) ev.getY();
                     sin = (moveX == 0 || moveY == 0) ? 0 : (moveY / Math.sqrt(moveX * moveX + moveY * moveY));
                     Log.d("RHL", "thisX=" + thisX + "; thisY=" + thisY + "; moveX=" + moveX + "; moveY=" + moveY + "; sin=" + sin);
                     if (Math.abs(sin) < 0.32) {
                         isFirstMove = false;
                         return true;
                     }
                 }
                 break;
         }
         isFirstMove = true;
         return super.onInterceptTouchEvent(ev);
     }
 */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        createVelocityTracker(ev);
        int action = ev.getAction();
        switch (action) {
            // Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏。如果滑动速度超过预设值则显示或隐藏
            case MotionEvent.ACTION_UP:
                int xScroll = getScrollX();
                if (!isOpen) {
                    if (xScroll < mHalfMenuWidth) {
                        openMenu();
                    } else if (getScrollVelocity() > speed) {
                        openMenu();
                    } else {
                        closeMenu();
                    }
                } else {
                    if (xScroll > mHalfMenuWidth) {
                        closeMenu();
                    } else if (getScrollVelocity() < (0 - speed)) {
                        closeMenu();
                    } else {
                        openMenu();
                    }
                }

                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        this.smoothScrollTo(0, 0);
        isOpen = true;
        onChildClickableListener.setIsOpen(!isOpen);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
        onChildClickableListener.setIsOpen(!isOpen);
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float leftScale = 1 - 0.3f * scale;
//        float rightScale = 0.8f + scale * 0.2f;

//        ViewHelper.setScaleX(mMenu, leftScale);
//        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scale * 0.7f);

		/*ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, rightScale);
		ViewHelper.setScaleY(mContent, rightScale);*/

    }

}
