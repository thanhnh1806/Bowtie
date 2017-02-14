package com.bowtie.View;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bowtie.Interfaces.ProfileCardInterface;
import com.bowtie.Object.UserObject;
import com.bowtie.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ProfileCardDetailFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ProfileCardInterface.Listener listener;
    private ProfileCardInterface.Datasource datasource;

    public void setListener(ProfileCardInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ProfileCardInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public ProfileCardDetailFragment() {

    }

    private FrameLayout btnName;
    private TextView tvName, tvPosition;
    private TextView tvPhoneNumber1, tvPhoneNumber2, tvMail, tvWebsite, tvLocation, tvLinkedProfile;
    private SimpleDraweeView ivAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_profile_card_detail, container, false);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvPosition = (TextView) v.findViewById(R.id.tvPosition);
        tvPhoneNumber1 = (TextView) v.findViewById(R.id.tvPhoneNumber1);
        tvPhoneNumber2 = (TextView) v.findViewById(R.id.tvPhoneNumber2);
        tvMail = (TextView) v.findViewById(R.id.tvMail);
        tvWebsite = (TextView) v.findViewById(R.id.tvWebsite);
        tvLocation = (TextView) v.findViewById(R.id.tvLocation);
        tvLinkedProfile = (TextView) v.findViewById(R.id.tvLinkedProfile);
        btnName = (FrameLayout) v.findViewById(R.id.btnName);
        ivAvatar = (SimpleDraweeView) v.findViewById(R.id.ivAvatar);

        setTypeface();

        btnName.setOnClickListener(this);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onProfileCardDetailCreateViewFinish();
    }

    private void setTypeface() {
        String fontPathRegular = "fonts/Roboto-Regular.ttf";
        String fontPathBold = "fonts/Roboto-Bold.ttf";
        Typeface tfRegular = Typeface.createFromAsset(context.getAssets(), fontPathRegular);
        Typeface tfBold = Typeface.createFromAsset(context.getAssets(), fontPathBold);
        tvName.setTypeface(tfBold);
        tvPosition.setTypeface(tfRegular);
        tvPhoneNumber1.setTypeface(tfRegular);
        tvPhoneNumber2.setTypeface(tfRegular);
        tvMail.setTypeface(tfRegular);
        tvWebsite.setTypeface(tfRegular);
        tvLocation.setTypeface(tfRegular);
        tvLinkedProfile.setTypeface(tfRegular);
    }

    public void reloadView() {
        UserObject user = datasource.getUser();

        Uri uri = Uri.parse("http://45.32.247.228:3000/" + user.getImage());
        ivAvatar.setImageURI(uri);
        

        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvPosition.setText(user.getPosition());
        tvPhoneNumber1.setText(user.getPhoneNumber1());
        tvPhoneNumber2.setText(user.getPhoneNumber2());
        tvMail.setText(user.getEmail());
        tvWebsite.setText(user.getWebsite());
        tvLocation.setText(user.getLocation());
        tvLinkedProfile.setText(user.getLinkedIn());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnName:
                listener.onProfileCardDetailButtonNameClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }
}
