package com.fi.viewpagerdemo.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.fi.viewpagerdemo.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者     yangyanfei
 * 创建时间   2017/8/14 0014 15:51
 * 作用	      ${TODO}
 * <p/>
 * 版本       $$Rev$$
 * 更新者     $$Author$$
 * 更新时间   $$Date$$
 * 更新描述   ${TODO}
 */
public class MyPagerAdapter extends PagerAdapter {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<ImageView> list;
    private Handler mHandler;
    private String arr[];
    private Context mContext;

    public MyPagerAdapter(ArrayList list,Handler handler,String arr[],Context context) {
        this.list = list;
        this.mHandler=handler;
        this.arr=arr;
        this.mContext=context;
    }

    /**
     * 得到图片的总数
     * @return
     */
    @Override
    public int getCount() {
//        return list.size();
        return Integer.MAX_VALUE;//也可以设置一个比较大的数值,实现无限滑动
    }

    /**
     * 相当于getView方法
     * @param container ViewPager自身
     * @param position 当前实例化页面的位置
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //对位置取模
        int realPosition=position%list.size();

        ImageView imageView = list.get(realPosition);
        container.addView(imageView);
//        Log.e(TAG,"instantiateItem:页面被创建的位置position=="+position+"...图片实例imageView"
//        +imageView);
        //设置ViewPager里面图片的触摸事件,在这里，如果手指滑动图片，图片还是会自动滑动，就要监听viewpager里面
        //的状态了。
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.e(TAG,"onTouch==手指按下");
                        mHandler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e(TAG,"onTouch==手指移动");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e(TAG,"onTouch==手指离开");
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(0,4000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        Log.e(TAG,"onTouch==取消");
                        /*mHandler.removeCallbacksAndMessages(null);
                        mHandler.sendEmptyMessageDelayed(0,4000);*/
                        break;
                }
                return false;
            }
        });

        //设置图片的点击事件
        imageView.setTag(realPosition);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"点击事件");
                int position= (int) v.getTag();
                String text=arr[position];
                Toast.makeText(mContext,"text=="+text,Toast.LENGTH_SHORT).show();
            }
        });

        return imageView;
    }

    /**
     * 比较View和Object是否同一个实例
     * @param view 页面
     * @param object 这个方法instantiateItem返回的结果
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 释放资源
     * @param container viewpager
     * @param position 要释放的位置
     * @param object　要释放的页面
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
//        Log.e(TAG,"destroyItem:被释放页面的位置position=="+position+",object=="+object);
        container.removeView((View) object);
    }
}
