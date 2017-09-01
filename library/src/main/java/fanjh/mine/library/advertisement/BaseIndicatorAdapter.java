package fanjh.mine.library.advertisement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
* @author fanjh
* @date 2017/8/31 16:47
* @description base indicator adapter
**/
public abstract class BaseIndicatorAdapter extends RecyclerView.Adapter{
    private int mSelectedIndex;
    private Context mContext;

    public BaseIndicatorAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public Context getContext() {
        return mContext;
    }

    void setSelectedIndex(int selectedIndex){
        if(mSelectedIndex != selectedIndex) {
            notifyItemChanged(mSelectedIndex);
            notifyItemChanged(selectedIndex);
            mSelectedIndex = selectedIndex;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindView(mSelectedIndex == position,holder,position);
    }

    public abstract void bindView(boolean isSelected,RecyclerView.ViewHolder holder, int position);

}
