package com.bowtie.View;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowtie.Interfaces.ProfileCardInterface;
import com.bowtie.Object.UserObject;
import com.bowtie.R;

public class ProfileCardEditFragment extends Fragment implements View.OnClickListener, TextWatcher {
    private Context context;
    private ProfileCardInterface.Listener listener;
    private ProfileCardInterface.Datasource datasource;

    public void setListener(ProfileCardInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ProfileCardInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public ProfileCardEditFragment() {

    }

    private ImageView ivAvatar;
    private TextView tvName;
    private EditText tvPosition, tvPhoneNumber1, tvPhoneNumber2, tvMail, tvWebsite;
    private EditText tvLocation, tvLinkedProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_card_edit, container, false);
        context = getActivity();
        ivAvatar = (ImageView) v.findViewById(R.id.ivAvatar);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvPosition = (EditText) v.findViewById(R.id.tvPosition);
        tvPhoneNumber1 = (EditText) v.findViewById(R.id.tvPhoneNumber1);
        tvPhoneNumber2 = (EditText) v.findViewById(R.id.tvPhoneNumber2);
        tvMail = (EditText) v.findViewById(R.id.tvMail);
        tvWebsite = (EditText) v.findViewById(R.id.tvWebsite);
        tvLocation = (EditText) v.findViewById(R.id.tvLocation);
        tvLinkedProfile = (EditText) v.findViewById(R.id.tvLinkedProfile);

        setTypeface();
        tvPosition.addTextChangedListener(this);
        tvPhoneNumber1.addTextChangedListener(this);
        tvPhoneNumber2.addTextChangedListener(this);
        tvMail.addTextChangedListener(this);
        tvWebsite.addTextChangedListener(this);
        tvLocation.addTextChangedListener(this);
        tvLinkedProfile.addTextChangedListener(this);
        ivAvatar.setOnClickListener(this);
        return v;
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

    @Override
    public void onResume() {
        super.onResume();
        listener.onProfileCardEditCreateViewFinish();
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
            case R.id.ivAvatar:
                listener.onProfileCardEditAvatarClick();
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        datasource = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (tvPosition.getText().hashCode() == s.hashCode()) {
            datasource.setPosition(String.valueOf(tvPosition.getText()));
        } else if (tvPhoneNumber1.getText().hashCode() == s.hashCode()) {
            datasource.setPhoneNumber1(String.valueOf(tvPhoneNumber1.getText()));
        } else if (tvPhoneNumber2.getText().hashCode() == s.hashCode()) {
            datasource.setPhoneNumber2(String.valueOf(tvPhoneNumber2.getText()));
        } else if (tvMail.getText().hashCode() == s.hashCode()) {
            datasource.setEmail(String.valueOf(tvMail.getText()));
        } else if (tvWebsite.getText().hashCode() == s.hashCode()) {
            datasource.setWebsite(String.valueOf(tvWebsite.getText()));
        } else if (tvLocation.getText().hashCode() == s.hashCode()) {
            datasource.setLocation(String.valueOf(tvLocation.getText()));
        } else if (tvLinkedProfile.getText().hashCode() == s.hashCode()) {
            datasource.setLinkedIn(String.valueOf(tvLinkedProfile.getText()));
        }
    }
}
