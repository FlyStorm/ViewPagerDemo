package com.fi.viewpagerdemo.listener;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fi.viewpagerdemo.MainActivity;

import java.util.ArrayList;

/**
 * 创建者     yangyanfei
 * 创建时间   2017/8/14 0014 17:58
 * 作用	      ${TODO}
 * <p/>
 * 版本       $$Rev$$
 * 更新者     $$Author$$
 * 更新时间   $$Date$$
 * 更新描述   ${TODO}
 */
public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private int                  prePostion;
    private TextView             mTextView;
    private LinearLayout         mLinearLayout;
    private String               arr[];
    private ArrayList<ImageView> list;
    private Handler mHandler;
    /**
     * 是否已经滚动
     */
    private boolean isDragging = false;

    public MyOnPageChangeListener(TextView textView, LinearLayout linearLayout,
                                  String arr[], int prePosition, ArrayList list,Handler handler) {
        this.mLinearLayout = linearLayout;
        this.mTextView = textView;
        this.arr = arr;
        this.prePostion = prePosition;
        this.list = list;
        this.mHandler=handler;
    }

    /**
     * 当页面滚动了的时候回调这个方法
     * @param position  当前页面的位置
     * @param positionOffset    滑动页面的百分比
     * @param positionOffsetPixels  在屏幕上滑动的像数
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //对位置信息取模
        int realPosition=position%list.size();

        //设置对应页面的文本信息
        mTextView.setText(arr[realPosition]);
        //把上一个高亮设置默认-灰色
        mLinearLayout.getChildAt(prePostion).setEnabled(false);
        //当前的设置为高亮-红色
        mLinearLayout.getChildAt(realPosition).setEnabled(true);

        prePostion=realPosition;
    }

    /**
     * 当某个页面被选中了的时候回调
     * @param position　被选中页面的位置
     */
    @Override
    public void onPageSelected(int position) {

    }

    /**
     * 当页面滚动状态变化的时候回调这个方法
     * 静止->滑动
     * 滑动->静止
     * 静止->拖拽
     * @param state
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if(state==ViewPager.SCROLL_STATE_DRAGGING){
            isDragging=true;
            mHandler.removeCallbacksAndMessages(null);
            Log.e(TAG,"SCROLL_STATE_DRAGGING状态......");
        }else if(state==ViewPager.SCROLL_STATE_SETTLING){
            Log.e(TAG,"SCROLL_STATE_SETTLING状态......");
        }else if(state==ViewPager.SCROLL_STATE_IDLE&&isDragging){
            isDragging=false;
            Log.e(TAG,"SCROLL_STATE_IDLE状态......");
            mHandler.removeCallbacksAndMessages(null);
            mHandler.sendEmptyMessageDelayed(0,4000);
        }
    }
}
