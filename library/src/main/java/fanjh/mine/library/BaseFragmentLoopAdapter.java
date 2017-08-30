package fanjh.mine.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* @author fanjh
* @date 2017/8/30 18:04
* @description carousel base fragment adapter
**/
public abstract class BaseFragmentLoopAdapter extends PagerAdapter{

    public abstract int getPagerCount();

    @Override
    public int getCount() {
        int pageCount = getPagerCount();
        if (pageCount > 1) {
            return pageCount + 2;
        } else {
            return pageCount;
        }
    }

    int toRealPosition(int position){
        int realPos = position;
        int adapterCount = getPagerCount();
        if(adapterCount > 1){
            if(position == 0) {
                realPos = adapterCount - 1;
            }else if(position == getCount() - 1){
                realPos = 0;
            }else{
                realPos -= 1;
            }
        }
        return realPos;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
