package android.exhibit.lister.fragments;

import android.exhibit.lister.R;
import android.exhibit.lister.adapter.ListerTabsAdapter;
import android.exhibit.lister.components.SlidingTabLayout;
import android.exhibit.lister.components.UserProfile;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by pal on 1/30/2015.
 */

public class SlidingFragment extends Fragment {

    private SlidingTabLayout mSlidingTabLayout;

    private ViewPager mViewPager;

    public SlidingFragment() {
    }

    Map<String, ArrayList<UserProfile>> asyncTaskStatusMap;

    public SlidingFragment(Map<String, ArrayList<UserProfile>> asyncTaskStatusMap) {
        this.asyncTaskStatusMap = asyncTaskStatusMap;
    }

    public static SlidingFragment newInstance(Map<String, ArrayList<UserProfile>> asyncTaskStatusMap) {
        SlidingFragment f = new SlidingFragment();
        Bundle args = new Bundle();
        //this.asyncTaskStatusMap =  asyncTaskStatusMap;
        args.putAll(args);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lister
                , container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setUpPager(view);
        setUpTabColor();
    }

    void setUpPager(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        String[] tabs = {getResources().getString(R.string.tab_likes),
                getResources().getString(R.string.tab_dislikes)};
        mViewPager.setAdapter(new ListerTabsAdapter(getActivity(), tabs, asyncTaskStatusMap));
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    void setUpTabColor() {
        mSlidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                // TODO Auto-generated method stub
                return SlidingFragment.this.getResources().getColor(R.color.red);
            }

            @Override
            public int getDividerColor(int position) {
                // TODO Auto-generated method stub
                return SlidingFragment.this.getResources().getColor(R.color.red_light);
            }
        });
    }


}