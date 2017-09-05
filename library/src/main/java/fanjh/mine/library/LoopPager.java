package fanjh.mine.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
* @author fanjh
* @date 2017/3/24 17:50
* @description carousel ViewPager
* @note
**/
public class LoopPager extends ViewPager {
    private boolean shouldLoop;
    private Scroller mScroller;
    private int smoothTime;

    public LoopPager(Context context) {
        this(context,null);
    }

    public LoopPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        addOnPageChangeListener(mListener);
        setHorizontalFadingEdgeEnabled(false);
        setVerticalFadingEdgeEnabled(false);
        setOverScrollMode(OVER_SCROLL_NEVER);

        mScroller = new Scroller(getContext(),new DecelerateInterpolator()){
            @Override
            public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                super.startScroll(startX, startY, dx, dy, 0 != smoothTime?smoothTime:duration);
            }
        };
        try{
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this,mScroller);
        }catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setSmoothTime(int smoothTime) {
        this.smoothTime = smoothTime;
    }

    @Override
    public void setCurrentItem(int item) {
        int newItem = item;
        if(shouldLoop) {
            newItem = calculateBoundIndex(item);
        }
        super.setCurrentItem(newItem);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int newItem = item;
        if(shouldLoop) {
            newItem = calculateBoundIndex(item);
            if (smoothScroll) {
                smoothScroll = (newItem == item);
            }
        }
        super.setCurrentItem(newItem, smoothScroll);
    }

    private int calculateBoundIndex(int item){
        if(null != getAdapter()){
            int sumCount = getAdapter().getCount();
            if(item >= sumCount){
                return Math.min(1,sumCount-1);
            }
        }
        return item;
    }

    private OnPageChangeListener mListener = new OnPageChangeListener() {
        private float prePositionOffset;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if(shouldLoop) {
                if (null != getAdapter() && getAdapter().getCount() > 1) {
                    int count = getAdapter().getCount();
                    if (((position == (count - 2) && positionOffset >= 0.9)
                            || ((position == count - 1) && positionOffset == 0))
                            && prePositionOffset < 0.9) {
                        changeItem(1);
                    } else if (position == 0 && positionOffset <= 0.1 && prePositionOffset > 0.1) {
                        changeItem(getAdapter().getCount() - 2);
                    }
                }
            }
            prePositionOffset = positionOffset;
        }

        private int prePos;

        @Override
        public void onPageSelected(int position) {
            if(shouldLoop) {
                if (null != getAdapter()) {
                    int count = getAdapter().getCount();
                    if (position == count - 2 && prePos == 0) {
                        prePos = position;
                        return;
                    } else if (position == 1 && prePos == count - 1) {
                        prePos = position;
                        return;
                    }
                }
                prePos = position;
            }
            callback(toRealPosition(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    };

    private void changeItem(final int position){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setCurrentItem(position,false);
            }
        },1);
    }

    private void callback(int position){
        if(null != realListeners && position >= 0) {
            for (OnPageChangeListener temp : realListeners) {
                temp.onPageSelected(position);
            }
        }
    }

    private int toRealPosition(int position){
        int result = position;
        if(shouldLoop) {
            PagerAdapter adapter = getAdapter();
            if (null != adapter && adapter.getCount() > 1) {
                if (position == 0) {
                    result = adapter.getCount() - 3;
                } else if (position == adapter.getCount() - 1) {
                    result = 0;
                } else {
                    result = position - 1;
                }
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(shouldLoop) {
            if (null != getAdapter() && getAdapter().getCount() > 1) {
                int nowItem = getCurrentItem();
                if (nowItem == 0) {
                    setCurrentItem(getAdapter().getCount() - 2, false);
                } else if (nowItem == getAdapter().getCount() - 1) {
                    setCurrentItem(1, false);
                }
            }
        }
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        if(adapter instanceof BaseLoopAdapter || adapter instanceof BaseFragmentLoopAdapter){
            shouldLoop = true;
        }
        super.setAdapter(adapter);
        if(shouldLoop) {
            if (null != adapter && adapter.getCount() > 1) {
                setCurrentItem(1, false);
            }
        }
    }
}
