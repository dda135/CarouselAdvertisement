package fanjh.mine.carouseladvertisement;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fanjh.mine.library.advertisement.BaseIndicatorAdapter;

/**
 * Created by faker on 2017/8/31.
 */

public class RoundIndicatorAdapter extends BaseIndicatorAdapter{
    private int mSize;

    public RoundIndicatorAdapter(Context mContext) {
        super(mContext);
    }

    public void setSize(int mSize) {
        this.mSize = mSize;
    }

    @Override
    public void bindView(boolean isSelected, RecyclerView.ViewHolder holder, int position) {
        IndicatorHolder indicatorHolder = (IndicatorHolder) holder;
        indicatorHolder.mIndicator.setBackgroundResource(isSelected?R.drawable.red_round:R.drawable.black_round);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IndicatorHolder holder = new IndicatorHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_round_indicator,parent,false));
        return holder;
    }

    class IndicatorHolder extends RecyclerView.ViewHolder{
        View mIndicator;

        public IndicatorHolder(View itemView) {
            super(itemView);
            mIndicator = itemView.findViewById(R.id.indicator);
        }
    }

    @Override
    public int getItemCount() {
        return mSize;
    }
}
