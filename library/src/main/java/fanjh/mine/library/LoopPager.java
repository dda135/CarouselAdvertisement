package fanjh.mine.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
* @author fanjh
* @date 2017/3/24 17:50
* @description carousel ViewPager
* @note
**/
public class LoopPager extends ViewPager {
    public LoopPager(Context context) {
        this(context,null);
    }

    public LoopPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(mListener);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
    }

    @Override
    public void setCurrentItem(int item) {
        int newItem = calculateBoundIndex(item);
        super.setCurrentItem(newItem);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int newItem = calculateBoundIndex(item);
        if(smoothScroll) {
            smoothScroll = (newItem == item);
        }
        super.setCurrentItem(newItem, smoothScroll);
    }

    /**
     * 处理边界问题
     * @param item 当前想要设置的页码
     * @return 处理后的合理页码
     */
    private int calculateBoundIndex(int item){
        if(null != getAdapter()){//处理一下边界问题
            int sumCount = getAdapter().getCount();
            if(item >= sumCount){
                return Math.min(1,sumCount-1);
            }
        }
        return item;
    }

    private OnPageChangeListener mListener = new OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(null != getAdapter() && getAdapter().getCount() > 1) {
                int count = getAdapter().getCount();
                if (position == (count - 2) && positionOffset >= 0.9){
                    setCurrentItem(1,false);
                }else if(position == 0 && positionOffset <= 0.1){
                    setCurrentItem(getAdapter().getCount()-2,false);
                }
            }
        }

        private int prePos;

        @Override
        public void onPageSelected(int position) {
            if(null != getAdapter()){
                int count = getAdapter().getCount();
                if(position == count - 2 && prePos == 0){
                    prePos = position;
                    return;
                }else if(position == 1 && prePos == count - 1){
                    prePos = position;
                    return;
                }
            }
            callback(toRealPosition(position));
            prePos = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            PagerAdapter adapter = getAdapter();
            if(null != adapter && adapter.getCount() > 1) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (getCurrentItem() == 0){
                        setCurrentItem(adapter.getCount()-2,false);
                    }else if(getCurrentItem() == adapter.getCount()-1){
                        setCurrentItem(1,false);
                    }
                }
            }
        }
    };

    private void callback(int position){
        if(null != realListeners && position >= 0) {
            for (OnPageChangeListener temp : realListeners) {
                temp.onPageSelected(position);
            }
        }
    }

    private int toRealPosition(int position){
        int result = position;
        PagerAdapter adapter = getAdapter();
        if(null != adapter && adapter.getCount() > 1){
            if (position == 0){
                result = adapter.getCount()-3;
            }else if(position == adapter.getCount()-1){
                result = 0;
            }else {
                result = position - 1;
            }
        }
        return result;
    }

    private List<OnPageChangeListener> realListeners = new ArrayList<>();

    public void addRealListener(OnPageChangeListener realListener) {
        if(null == realListeners){
            realListeners = new ArrayList<>();
        }
        realListeners.add(realListener);
    }

    public int getCount(){
        if(null != getAdapter()){
            int count = getAdapter().getCount();
            if(count > 1){
                return count - 2;
            }else{
                return count;
            }
        }
        return 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(null != getAdapter() && getAdapter().getCount() > 1){
            int nowItem = getCurrentItem();
            if(nowItem == 0){
                setCurrentItem(getAdapter().getCount()-2,false);
            }else if(nowItem == getAdapter().getCount() - 1){
                setCurrentItem(1,false);
            }
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if(null != adapter && adapter.getCount() > 1){
            setCurrentItem(1,false);
        }
    }
}
