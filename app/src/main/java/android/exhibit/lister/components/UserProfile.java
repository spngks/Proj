package android.exhibit.lister.components;

/**
 * Created by pal on 1/31/2015.
 */
public class UserProfile {

    String nickname;


    String gender;
    boolean status;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
