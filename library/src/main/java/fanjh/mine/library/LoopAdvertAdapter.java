package fanjh.mine.library;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public abstract class LoopAdvertAdapter<T> extends BaseLoopAdapter<T> {
    private SparseArray<View> mViews;//reuse
    private boolean shouldHoldCache = true;

    public LoopAdvertAdapter(Context mContext) {
        super(mContext);
    }

    public void shouldHoldCache(boolean shouldHoldCache) {
        this.shouldHoldCache = shouldHoldCache;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        if(null == mViews){
            mViews = new SparseArray<>();
        }
        View view = mViews.get(position);
        if(null == view) {
            if (getCount() > 1) {
                int positions[] = getOtherAdapterPosition(position);
                for (int adapterPosition : positions) {
                    View tempView = initView(container, toRealPosition(adapterPosition), adapterPosition);
                    if (adapterPosition == position) {
                        view = tempView;
                    }
                }
            } else {
                view = initView(container, position, position);
            }
        }
        bindView(view,getItem(toRealPosition(position)));
        container.addView(view);
        return view;
    }

    private View initView(ViewGroup container, int realPosition, int position){
        View view = mViews.get(position);
        if(null == view) {
            T item = getItem(realPosition);
            view = onCreateView(container, item, realPosition);
            mViews.put(position, view);
        }
        return view;
    }

    public abstract View onCreateView(ViewGroup container,T item,int realPos);
    public abstract void bindView(View view,T item);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(!shouldHoldCache){
            mViews.remove(position);
        }
        container.removeView((View) object);
    }

}