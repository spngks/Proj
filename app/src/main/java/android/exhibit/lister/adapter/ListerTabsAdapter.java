package android.exhibit.lister.adapter;

import android.app.Activity;
import android.content.Context;
import android.exhibit.lister.R;
import android.exhibit.lister.components.UserProfile;
import android.exhibit.lister.util.ListerConstants;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by pal on 1/29/2015.
 */

public class ListerTabsAdapter extends PagerAdapter {

    String tabs[] = null;

    Map<String, ArrayList<UserProfile>> asyncTaskStatusMap;

    Activity activity;
    Context context;

    public ListerTabsAdapter(Activity activity, String[] tabs, Map<String, ArrayList<UserProfile>> map) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.tabs = tabs;
        this.asyncTaskStatusMap = map;
    }


    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources

        View view = null;
        view = activity.getLayoutInflater().inflate(R.layout.list_item, container, false);

        container.addView(view);

        ListView profileListView = null;

        // Using ProfileListAdapter
        ProfileListAdapter profileList_adapter;

        if (asyncTaskStatusMap != null && asyncTaskStatusMap.get("likes") != null) {

            profileListView = (ListView) view.findViewById(R.id.profilelist_listview);
            if (position % 2 == 0) {
                profileList_adapter = new ProfileListAdapter(activity, R.layout.part_profile, asyncTaskStatusMap.get(ListerConstants.CONS_LIKES));
            } else {
                profileList_adapter = new ProfileListAdapter(activity, R.layout.part_profile, asyncTaskStatusMap.get(ListerConstants.CONS_DISLIKES));
            }

            profileListView.setAdapter(profileList_adapter);
            // Add the newly created View to the ViewPager

        } else {
            // TODO handle this with empty list
            // profileList_adapter = new ProfileListAdapter(activity, R.layout.part_profile, new UserProfile[]{});
        }

        // Return the View
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
