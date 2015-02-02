package android.exhibit.lister.activities;

import android.exhibit.lister.R;
import android.exhibit.lister.components.UserProfile;
import android.exhibit.lister.components.UserProfilesList;
import android.exhibit.lister.fragments.SlidingFragment;
import android.exhibit.lister.util.EventBus;
import android.exhibit.lister.util.ListerConstants;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ListerActivity extends FragmentActivity {


    ArrayList<UserProfile> userProfilesList;

    public ArrayList<UserProfile> getUserProfilesList() {
        return userProfilesList;
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getInstance().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpView();

        GetProfilesAsync getProfilesAsync = new GetProfilesAsync();
        getProfilesAsync.execute();

    }

    void setUpView() {
        setContentView(R.layout.activity_lister);
    }

    @Subscribe
    public void populateData(UserProfilesList userProfilesList) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SlidingFragment fragment = new SlidingFragment(userProfilesList.getAsyncTaskStatusMap());
        transaction.replace(R.id.sample_content_fragment, fragment);
        transaction.commit();
    }


    /*
     * Fetches Data and then parses the JSON results
     */

    private class GetProfilesAsync extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... input) {


            BufferedReader reader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.personlistjson)));
            String rawJSON = "";
            try {
                String currentLine = reader.readLine();
                while (currentLine != null) {
                    rawJSON += currentLine + "\n";
                    currentLine = reader.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // trims any whitespaces
            return rawJSON.trim();
        }

        @Override
        protected void onPostExecute(String result) {
            // we have an error to the call
            if (result != null){
                // all things went right
                Map<String, ArrayList<UserProfile>> asyncTaskStatusMap = parseGoogleParse(result);
                UserProfilesList profilesList = new UserProfilesList();
                profilesList.setAsyncTaskStatusMap(asyncTaskStatusMap);
                EventBus.post(profilesList);
            }
        }
    }


    private Map<String, ArrayList<UserProfile>> parseGoogleParse(final String response) {

        Map<String, ArrayList<UserProfile>> asyncTaskResultMap = new HashMap<>();
        ArrayList<UserProfile> likesList = new ArrayList<UserProfile>();
        ArrayList<UserProfile> dislikesList = new ArrayList<>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has(ListerConstants.JSON_DATA)) {

                JSONArray jsonArray = jsonObject.getJSONArray(ListerConstants.JSON_DATA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    UserProfile userProfile = new UserProfile();
                    if (jsonArray.getJSONObject(i).has(ListerConstants.JSON_NICKNAME)) {
                        userProfile.setNickname(jsonArray.getJSONObject(i).optString(ListerConstants.JSON_NICKNAME));
                        userProfile.setGender(jsonArray.getJSONObject(i).optString(ListerConstants.JSON_GENDER));
                        userProfile.setStatus(jsonArray.getJSONObject(i).optBoolean(ListerConstants.JSON_STATUS));

                        if (userProfile.isStatus()) {
                            likesList.add(userProfile);
                        } else {
                            dislikesList.add(userProfile);
                        }

                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncTaskResultMap.put(ListerConstants.CONS_LIKES, likesList);
        asyncTaskResultMap.put(ListerConstants.CONS_DISLIKES, dislikesList);

        return asyncTaskResultMap;

    }


}
