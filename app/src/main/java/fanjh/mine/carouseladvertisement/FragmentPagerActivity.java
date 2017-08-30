package fanjh.mine.carouseladvertisement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import fanjh.mine.library.LoopFragmentPagerAdapter;
import fanjh.mine.library.LoopPager;

/**
 * Created by faker on 2017/8/30.
 */

public class FragmentPagerActivity extends FragmentActivity{
    private LoopPager loopPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        loopPager = (LoopPager) findViewById(R.id.lp_pager);
        loopPager.setAdapter(new LoopFragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getPagerCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                return DemoFragment.newInstance("fragment"+position);
            }
        });
    }
}
