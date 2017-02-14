package com.bowtie.View;


import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowtie.Interfaces.ProfileCardInterface;
import com.bowtie.Object.UserObject;
import com.bowtie.R;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.bitmap.Transform;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ProfileCardFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private ProfileCardInterface.Listener listener;
    private ProfileCardInterface.Datasource datasource;

    public void setListener(ProfileCardInterface.Listener listener) {
        this.listener = listener;
    }

    public void setDatasource(ProfileCardInterface.Datasource datasource) {
        this.datasource = datasource;
    }

    public ProfileCardFragment() {

    }

    private FrameLayout btnName;
    private TextView tvFind, tvName, tvPosition;
    private ImageView ivAvatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View v = inflater.inflate(R.layout.fragment_profile_card, container, false);
        ivAvatar = (ImageView) v.findViewById(R.id.ivAvatar);
        tvName = (TextView) v.findViewById(R.id.tvName);
        tvPosition = (TextView) v.findViewById(R.id.tvPosition);
        btnName = (FrameLayout) v.findViewById(R.id.btnName);
        tvFind = (TextView) v.findViewById(R.id.tvFind);

        setTypeface();

        btnName.setOnClickListener(this);
        tvFind.setOnClickListener(this);
        return v;
    }

    private void setTypeface() {
        String fontPathRegular = "fonts/Roboto-Regular.ttf";
        String fontPathBold = "fonts/Roboto-Bold.ttf";
        Typeface tfRegular = Typeface.createFromAsset(context.getAssets(), fontPathRegular);
        Typeface tfBold = Typeface.createFromAsset(context.getAssets(), fontPathBold);

        tvName.setTypeface(tfBold);
        tvPosition.setTypeface(tfRegular);
        tvFind.setTypeface(tfRegular);
    }

    @Override
    public void onResume() {
        super.onResume();
        listener.onProfileCardCreateViewFinish();
    }

    public void reloadView() {
        UserObject user = datasource.getUser();

        Uri uri = Uri.parse("http://45.32.247.228:3000/" + user.getImage());
        ivAvatar.setImageURI(uri);
        Ion.with(context).load("http://45.32.247.228:3000/" + user.getImage())
                .withBitmap().transform(new Transform() {
            @Override
            public Bitmap transform(Bitmap b) {

                return null;
            }

            @Override
            public String key() {
                return null;
            }
        })
                .placeholder(R.drawable.profile_cards_user_avatar)
                .intoImageView(ivAvatar);
//        ImageLoader imageLoader = ImageLoader.getInstance();
//        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisk(true).resetViewBeforeLoading(true)
//                .showImageForEmptyUri(R.drawable.profile_cards_user_avatar)
//                .showImageOnFail(R.drawable.profile_cards_user_avatar)
//                .displayer(new RoundedBitmapDisplayer(14))
//                .build();
//        imageLoader.displayImage(url, ivAvatar, options);

        tvName.setText(user.getFirstName() + " " + user.getLastName());
        tvPosition.setText(user.getPosition());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnName:
                listener.onProfileCardButtonNameClick();
                break;
            case R.id.tvFind:
                listener.onButtonFindClick();
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
