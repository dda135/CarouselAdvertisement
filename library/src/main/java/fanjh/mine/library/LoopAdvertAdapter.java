package fanjh.mine.library;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public abstract class LoopAdvertAdapter<T> extends BaseLoopAdapter<T> {
    private SparseArray<View> mViews;//reuse

    public LoopAdvertAdapter(Context mContext) {
        super(mContext);
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
            int realPos = toRealPosition(position);
            T item = getItem(realPos);
            view = onCreateView(container,item,realPos);
            mViews.put(position,view);
        }
        container.removeView(view);
        container.addView(view);
        return view;
    }

    public abstract View onCreateView(ViewGroup container,T item,int realPos);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}