# 轮播广告
轮播广告，采用的是n-1 0 1 2 ... n-1 0的方式来进行无限轮播<br>
目前所关联的指示器采用RecyclerView.Adapter来实现，目前只支持底部居中的指示器
## 例子
初始化数据，
```
    carouselAdvertisement = (CarouselAdvertisement) findViewById(R.id.ca_view);
    carouselAdvertisement.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,getResources().getDisplayMetrics().widthPixels / 2));
    imageAdapter = new ImageAdapter(this);
    List<Integer> images = new ArrayList<>();
    ...
    imageAdapter.updateCollections(images);
    imageAdapter.shouldHoldCache(false);
    carouselAdvertisement.setContentAdapter(imageAdapter);
    roundIndicatorAdapter = new RoundIndicatorAdapter(this);
    roundIndicatorAdapter.setSize(images.size());
```
启动轮播，注意在不可见的时候应该关闭轮播，避免不必要的CPU、内存的损耗
```
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
```
