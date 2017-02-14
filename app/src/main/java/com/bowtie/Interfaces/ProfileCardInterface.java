package com.bowtie.Interfaces;

import com.bowtie.Object.UserObject;

/**
 * Created by thanh_nguyen02 on 30/01/2016.
 */
public class ProfileCardInterface {
    public interface Listener {
        void onProfileCardCreateViewFinish();

        void onProfileCardDetailCreateViewFinish();

        void onProfileCardEditCreateViewFinish();

        void onProfileCardButtonNameClick();

        void onButtonFindClick();

        void onProfileCardDetailButtonNameClick();

        void onProfileCardEditAvatarClick();
    }

    public interface Datasource {
        UserObject getUser();

        void setPosition(String s);

        void setPhoneNumber1(String s);

        void setPhoneNumber2(String s);

        void setEmail(String s);

        void setWebsite(String s);

        void setLocation(String s);

        void setLinkedIn(String s);
    }
}
