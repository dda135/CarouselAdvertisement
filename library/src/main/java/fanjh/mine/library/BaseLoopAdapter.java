package fanjh.mine.library;

import android.content.Context;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* @author fanjh
* @date 2017/8/30 18:04
* @description carousel base adapter
**/
public abstract class BaseLoopAdapter<T> extends PagerAdapter{
    private List<T> mCollections;
    private Context mContext;

    public BaseLoopAdapter(Context mContext) {
        this.mContext = mContext;
    }

    protected Context getContext(){
        return mContext;
    }

    public void updateCollections(List<T> collections){
        if(null == mCollections){
            mCollections = new ArrayList<>(collections);
        }else{
            mCollections.clear();
            mCollections.addAll(collections);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if(null != mCollections) {
            int count = mCollections.size();
            if (count > 1) {
                return count + 2;
            } else {
                return count;
            }
        }
        return 0;
    }

    int toRealPosition(int position){
        int realPos = position;
        if(mCollections.size() > 1){
            if(position == 0) {
                realPos = mCollections.size() - 1;
            }else if(position == getCount() - 1){
                realPos = 0;
            }else{
                realPos -= 1;
            }
        }
        return realPos;
    }

    public T getItem(int realPosition){
        if(null == mCollections){
            return null;
        }
        return mCollections.get(realPosition);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
