package fanjh.mine.carouseladvertisement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by faker on 2017/8/30.
 */

public class DemoFragment extends Fragment{
    public static final String EXTRA_TEXT = "extra_text";
    private String text;
    private TextView textView;

    public static DemoFragment newInstance(String text){
        DemoFragment fragment = new DemoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TEXT,text);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        text = getArguments().getString(EXTRA_TEXT);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(null == textView) {
            textView = new TextView(getContext());
            textView.setText(text);
            textView.setTextSize(28);
        }
        return textView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewGroup viewGroup = (ViewGroup) textView.getParent();
        viewGroup.removeView(textView);
    }
}
