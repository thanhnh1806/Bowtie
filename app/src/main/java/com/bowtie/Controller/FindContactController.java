package com.bowtie.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowtie.R;

public class FindContactController extends SlidingMenuController implements View.OnClickListener {
    private View swipeLeft, swipeRight, layoutLeft, layoutRight;
    private View viewExchangeLeft, viewExchangeRight, viewMessageLeft, viewMessageRight;
    private TextView tvName, tvPosition;
    private ImageView ivAvatar;
    private FrameLayout btnContact, layoutMessage, layoutExchange, viewExchange, viewMessage;
    private String[] arrName = new String[]{"Emma Woodgrave", "Jone Strackmore", "Mike Osborne"};
    private String[] arrPosition = new String[]{"Account Manager", "Marketing Manager EMEA", "UI UX Design"};
    private int[] arrImg = new int[]{R.drawable.img_0, R.drawable.img_1, R.drawable.img_2};

    private int currentContact = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_find_contact, container);
        //changeToolbar();
        swipeLeft = findViewById(R.id.swipeLeft);
        swipeRight = findViewById(R.id.swipeRight);
        layoutLeft = findViewById(R.id.layoutLeft);
        layoutRight = findViewById(R.id.layoutRight);
        viewExchangeLeft = findViewById(R.id.viewExchangeLeft);
        viewExchangeRight = findViewById(R.id.viewExchangeRight);
        viewMessageLeft = findViewById(R.id.viewMessageLeft);
        viewMessageRight = findViewById(R.id.viewMessageRight);

        btnContact = (FrameLayout) findViewById(R.id.btnContact);
        layoutMessage = (FrameLayout) findViewById(R.id.layoutMessage);
        layoutExchange = (FrameLayout) findViewById(R.id.layoutExchange);
        viewExchange = (FrameLayout) findViewById(R.id.viewExchange);
        viewMessage = (FrameLayout) findViewById(R.id.viewMessage);

        tvName = (TextView) findViewById(R.id.tvName);
        tvPosition = (TextView) findViewById(R.id.tvPosition);

        ivAvatar = (ImageView) findViewById(R.id.ivAvatar);

        btnContact.setOnClickListener(this);
        swipeLeft.setOnClickListener(this);
        swipeRight.setOnClickListener(this);
        layoutMessage.setOnClickListener(this);
        layoutExchange.setOnClickListener(this);
        layoutLeft.setOnClickListener(this);
        layoutRight.setOnClickListener(this);
        viewExchangeLeft.setOnClickListener(this);
        viewExchangeRight.setOnClickListener(this);
        viewMessageLeft.setOnClickListener(this);
        viewMessageRight.setOnClickListener(this);
        viewExchange.setOnClickListener(this);
        viewMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnContact:
                //TODO
                break;
            case R.id.swipeLeft:
                if (layoutMessage.isShown() || layoutExchange.isShown()) {

                } else {
                    if (currentContact >= 1) {
                        tvName.setText(String.valueOf(arrName[currentContact - 1]));
                        tvPosition.setText(String.valueOf(arrPosition[currentContact - 1]));
                        ivAvatar.setImageResource(arrImg[currentContact - 1]);
                        currentContact--;
                    } else {
                        tvName.setText(String.valueOf(arrName[arrName.length - 1]));
                        tvPosition.setText(String.valueOf(arrPosition[arrPosition.length - 1]));
                        ivAvatar.setImageResource(arrImg[arrImg.length - 1]);
                        currentContact = arrName.length - 1;
                    }
                }
                break;
            case R.id.swipeRight:
                if (layoutMessage.isShown() || layoutExchange.isShown()) {

                } else {
                    if (currentContact <= 1) {
                        tvName.setText(String.valueOf(arrName[currentContact + 1]));
                        tvPosition.setText(String.valueOf(arrPosition[currentContact + 1]));
                        ivAvatar.setImageResource(arrImg[currentContact + 1]);
                        currentContact++;
                    } else {
                        tvName.setText(String.valueOf(arrName[0]));
                        tvPosition.setText(String.valueOf(arrPosition[0]));
                        ivAvatar.setImageResource(arrImg[0]);
                        currentContact = 0;
                    }
                }
                break;
            case R.id.viewExchange:
                //TODO go to new activity
                break;
            case R.id.viewMessage:
                startActivity(new Intent(FindContactController.this, MessageActivity.class));
                break;
            case R.id.viewExchangeLeft:
                layoutMessage.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.GONE);
                break;
            case R.id.viewExchangeRight:
                layoutMessage.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.GONE);
                break;
            case R.id.viewMessageLeft:
                layoutMessage.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.GONE);
                break;
            case R.id.viewMessageRight:
                layoutMessage.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.GONE);
                break;
            case R.id.layoutLeft:
                layoutMessage.setVisibility(View.VISIBLE);
                layoutExchange.setVisibility(View.GONE);
                break;
            case R.id.layoutRight:
                layoutMessage.setVisibility(View.GONE);
                layoutExchange.setVisibility(View.VISIBLE);
                break;
        }
    }
}
