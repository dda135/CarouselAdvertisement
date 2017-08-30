package fanjh.mine.carouseladvertisement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import fanjh.mine.library.LoopPager;

/**
 * Created by faker on 2017/8/30.
 */

public class ViewActivity extends FragmentActivity{
    private LoopPager loopPager;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        loopPager = (LoopPager) findViewById(R.id.lp_pager);
        imageAdapter = new ImageAdapter(this);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.share_pengyouquan_btn);
        images.add(R.drawable.share_qq_btn);
        images.add(R.drawable.share_weibo_btn);
        images.add(R.drawable.share_weixin_btn);
        imageAdapter.updateCollections(images);
        loopPager.setAdapter(imageAdapter);
    }

}
