package fanjh.mine.library.advertisement;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import fanjh.mine.library.LoopPager;

/**
* @author fanjh
* @date 2017/8/31 16:24
* @description
* @note
**/
public class CarouselAdvertisement extends FrameLayout{
    public static final int INVALID_TIME = -1;
    private LoopPager mLoopPager;
    private RecyclerView mIndicator;
    private PagerAdapter mPagerAdapter;
    private LinearLayoutManager mLayoutManager;
    private BaseIndicatorAdapter mIndicatorAdapter;
    private boolean hasIndicator;
    private int mCarouselTime;//ms
    private int mChangePageTime;//ms
    private boolean canCarouselWhenTouch;
    private boolean isRunning;
    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            if(null != mLoopPager && null != mPagerAdapter){
                mLoopPager.setCurrentItem(mLoopPager.getCurrentItem() + 1,true);
                if(INVALID_TIME != mCarouselTime){
                    mMainHandler.sendEmptyMessageDelayed(0,mCarouselTime);
                }
            }
        }
    };

    public CarouselAdvertisement(@NonNull Context context) {
        this(context,null);
    }

    public CarouselAdvertisement(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarouselAdvertisement(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLoopPager();
    }

    public void setChangePageTime(int mChangePageTime) {
        this.mChangePageTime = mChangePageTime;
        mLoopPager.setSmoothTime(mChangePageTime);
    }

    public void canCarouselWhenTouch(boolean canCarouselWhenTouch) {
        this.canCarouselWhenTouch = canCarouselWhenTouch;
    }

    private void initLoopPager(){
        mLoopPager = new LoopPager(getContext());
        mLoopPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLoopPager.addRealListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(null != mIndicatorAdapter){
                    mIndicatorAdapter.setSelectedIndex(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addView(mLoopPager);
    }

    private void addIndicator(){
        if(null == mIndicator){
            mIndicator = new RecyclerView(getContext());
            mLayoutManager = new LinearLayoutManager(getContext());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            mIndicator.setLayoutParams(params);
            mIndicator.setLayoutManager(mLayoutManager);
            if(null != mIndicatorAdapter){
                mIndicator.setAdapter(mIndicatorAdapter);
            }
        }
        if(null == mIndicator.getParent()){
            addView(mIndicator);
        }
    }

    public void useIndicator(boolean use){
        if(hasIndicator != use) {
            hasIndicator = use;
            if(hasIndicator){
                addIndicator();
            }else if(null != mIndicator){
                removeView(mIndicator);
            }
        }
    }

    public void setIndicatorAdapter(BaseIndicatorAdapter indicatorAdapter){
        mIndicatorAdapter = indicatorAdapter;
        useIndicator(true);
    }

    public void setContentAdapter(PagerAdapter pagerAdapter){
        mPagerAdapter = pagerAdapter;
        mLoopPager.setAdapter(mPagerAdapter);
    }

    private void setAdapter(){
        if(null != mIndicator){
            mIndicator.setAdapter(mIndicatorAdapter);
        }
    }

    public void startCarousel(int time){
        if(isRunning){
            return;
        }
        stopCarousel();
        mCarouselTime = time;
        if(INVALID_TIME != mCarouselTime) {
            isRunning = true;
            mMainHandler.sendEmptyMessageDelayed(0, mCarouselTime);
        }
    }

    public void stopCarousel(){
        if(!isRunning){
            return;
        }
        mCarouselTime = -1;
        pauseCarousel();
    }

    public void pauseCarousel(){
        if(!isRunning){
            return;
        }
        isRunning = false;
        mMainHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        pauseCarousel();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startCarousel(mCarouselTime);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (MotionEventCompat.getActionMasked(ev)){
            case MotionEvent.ACTION_DOWN:
                if(!canCarouselWhenTouch){
                    pauseCarousel();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                startCarousel(mCarouselTime);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
