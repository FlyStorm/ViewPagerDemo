package com.fi.viewpagerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fi.viewpagerdemo.adapter.MyPagerAdapter;
import com.fi.viewpagerdemo.listener.MyOnPageChangeListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //声明控件
    private ViewPager    mViewPager;
    private TextView     mTextView;
    private LinearLayout mLl_point_group;

    private MyPagerAdapter mMyPagerAdapter;

    /**
     * 图片资源id
     */
    private final int    imageIds[] = {R.drawable.a, R.drawable.b, R.drawable.c
            , R.drawable.d, R.drawable.e};
    /**
     * 图片标题数组
     */
    private final String texts[]    = {"标题一:一张图片", "标题二:一张图片"
            , "标题三:一张图片", "标题四:一张图片", "标题五:一张图片"};

    /**
     * 上一次高亮显示的位置
     */
    private int prePosition = 0;

    /**
     * 定义容器集合，用于存储图片
     */
    private ArrayList<ImageView> mImageViews;

    /**
     * 定义Handler用于处理消息，实现自动滑动
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int item = mViewPager.getCurrentItem() + 1;
            mViewPager.setCurrentItem(item);

            //延迟发消息
            mHandler.sendEmptyMessageDelayed(0,4000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTextView = (TextView) findViewById(R.id.tv);
        mLl_point_group = (LinearLayout) findViewById(R.id.ll_point_group);

        //ViewPager的使用
        //1.在布局文件中定义ViewPager
        //2.在代码中实例化ViewPager
        //3.准备数据
        mImageViews = new ArrayList<>();
        for(int i=0;i<imageIds.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);

            //把图片添加到集合中
            mImageViews.add(imageView);

            //添加点
            ImageView point=new ImageView(this);
            point.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(30,30);
            if(i==0){
                point.setEnabled(true);//显示红色
            }else {
                point.setEnabled(false);//显示灰色
                params.leftMargin=20;
            }
            point.setLayoutParams(params);

            mLl_point_group.addView(point);
        }
        //4.设置适配器（PagerAdapter）-item布局-绑定数据
        mMyPagerAdapter=new MyPagerAdapter(mImageViews,mHandler,texts,MainActivity.this);
        mViewPager.setAdapter(mMyPagerAdapter);

        MyOnPageChangeListener myOnPageChangeListener=
                new MyOnPageChangeListener(mTextView, mLl_point_group, texts, prePosition,mImageViews,mHandler);
        //设置监听ViewPager页面的改变，圆点的改变
        mViewPager.addOnPageChangeListener(myOnPageChangeListener);

        //设置中间位置,实现无限滑动
        int item=Integer.MAX_VALUE/2-Integer.MAX_VALUE/2%mImageViews.size();//要保证金是mImageViews长度的整数倍
        mViewPager.setCurrentItem(item);//设置当前初始位置

        mTextView.setText(texts[prePosition]);

        //发消息
        mHandler.sendEmptyMessageDelayed(0,2000);
    }
}
