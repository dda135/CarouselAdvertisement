package fanjh.mine.carouseladvertisement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import fanjh.mine.library.advertisement.CarouselAdvertisement;

/**
 * Created by faker on 2017/8/31.
 */

public class CarouselAdvertisementActivity extends FragmentActivity{
    private CarouselAdvertisement carouselAdvertisement;
    private ImageAdapter imageAdapter;
    private RoundIndicatorAdapter roundIndicatorAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carousel_advertisement);
        carouselAdvertisement = (CarouselAdvertisement) findViewById(R.id.ca_view);
        carouselAdvertisement.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDisplayMetrics().widthPixels / 2));
        imageAdapter = new ImageAdapter(this);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.share_pengyouquan_btn);
        images.add(R.drawable.share_qq_btn);
        images.add(R.drawable.share_weibo_btn);
        images.add(R.drawable.share_weixin_btn);
        imageAdapter.updateCollections(images);
        imageAdapter.shouldHoldCache(false);
        carouselAdvertisement.setContentAdapter(imageAdapter);
        roundIndicatorAdapter = new RoundIndicatorAdapter(this);
        roundIndicatorAdapter.setSize(images.size());
        carouselAdvertisement.setIndicatorAdapter(roundIndicatorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carouselAdvertisement.setChangePageTime((int) (Math.random() * 1000 + 100));
        carouselAdvertisement.startCarousel(3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        carouselAdvertisement.stopCarousel();
    }
}
