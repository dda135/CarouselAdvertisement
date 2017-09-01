package fanjh.mine.carouseladvertisement;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import fanjh.mine.library.LoopAdvertAdapter;

/**
 * Created by faker on 2017/8/30.
 */

public class ImageAdapter extends LoopAdvertAdapter<Integer> {
    public ImageAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public View onCreateView(ViewGroup container, Integer item, int realPos) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(item);
        return imageView;
    }

    @Override
    public void bindView(View view, final Integer item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),item+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}
