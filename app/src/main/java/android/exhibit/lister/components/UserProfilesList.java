package android.exhibit.lister.components;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by pal on 2/1/2015.
 */
public class UserProfilesList {
    public Map<String, ArrayList<UserProfile>> getAsyncTaskStatusMap() {
        return asyncTaskStatusMap;
    }

    public void setAsyncTaskStatusMap(Map<String, ArrayList<UserProfile>> asyncTaskStatusMap) {
        this.asyncTaskStatusMap = asyncTaskStatusMap;
    }

    Map<String, ArrayList<UserProfile>> asyncTaskStatusMap;
}
