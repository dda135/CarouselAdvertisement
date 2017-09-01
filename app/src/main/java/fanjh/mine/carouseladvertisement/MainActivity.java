package fanjh.mine.carouseladvertisement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import fanjh.mine.library.advertisement.CarouselAdvertisement;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button viewButton = (Button) findViewById(R.id.btn_view_pager_adapter);
        Button fragmentPagerButton = (Button) findViewById(R.id.btn_fragment_pager_adapter);
        Button fragmentStatePagerButton = (Button) findViewById(R.id.btn_fragment_state_pager_adapter);
        Button carouselAdvertisementButton = (Button) findViewById(R.id.btn_carousel_advertisement);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ViewActivity.class);
                startActivity(intent);
            }
        });
        fragmentPagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FragmentPagerActivity.class);
                startActivity(intent);
            }
        });
        fragmentStatePagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FragmentStateActivity.class);
                startActivity(intent);
            }
        });
        carouselAdvertisementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CarouselAdvertisementActivity.class);
                startActivity(intent);
            }
        });
    }

}
