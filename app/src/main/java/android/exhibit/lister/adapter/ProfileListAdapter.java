package android.exhibit.lister.adapter;

import android.content.Context;
import android.exhibit.lister.R;
import android.exhibit.lister.components.UserProfile;
import android.exhibit.lister.util.ListerConstants;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by pal on 2/1/2015.
 */
public class ProfileListAdapter extends ArrayAdapter<UserProfile> {
    private final static String TAG = ProfileListAdapter.class.getSimpleName();
    private int layoutResourceId = R.layout.part_profile;

    public ProfileListAdapter(Context context, int resource) {
        super(context, resource);
        layoutResourceId = resource;
    }


    public ProfileListAdapter(Context context, int resource, List<UserProfile> objects) {
        super(context, resource, objects);
        layoutResourceId = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final UserProfile userProfile = getItem(position);
        if (view == null) {
            // No view created yet
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResourceId, parent, false);
        }
        if (view != null) {
            TextView nickname = (TextView) view.findViewById(R.id.nick_name);
            TextView gender = (TextView) view.findViewById(R.id.gender);

            ImageView profileLogo = (ImageView) view.findViewById(R.id.profile_pic);

            if (userProfile != null) {

                if (nickname != null) {
                    nickname.setText(userProfile.getNickname());
                }

                if (gender != null) {
                    gender.setText(userProfile.getGender());
                }


                if (profileLogo != null) {

                        // TODO replace the above code to handle Picasso with cache
                        Picasso.with(profileLogo.getContext().getApplicationContext())
                                .load(userProfile.getGender().equalsIgnoreCase("m") ? ListerConstants.URL_MALE: ListerConstants.URL_FEMALE)
                                .resize(getContext().getResources().getDimensionPixelSize(R.dimen.list_pic_sq), getContext().getResources().getDimensionPixelSize(R.dimen.list_pic_sq)).centerCrop()
                                .error(R.drawable.social_person)
                                .placeholder(R.drawable.social_person)
                                .noFade()
                                .into(profileLogo);


                }
            }
        }

        return view;
    }
}