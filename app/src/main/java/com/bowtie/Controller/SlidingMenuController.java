package com.bowtie.Controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowtie.R;

public class SlidingMenuController extends AppCompatActivity {
    protected FrameLayout btnEdit, ivToolBarMenu, btnNoti, btnContact, btnBack, btnCheck;
    protected FrameLayout container;
    protected FrameLayout toolbar, toolbarEdit;
    protected DrawerLayout drawer;
    protected TextView tvTittle, tvEdit;
    protected ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_menu);

        container = (FrameLayout) findViewById(R.id.slide_menu_container);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        tvTittle = (TextView) findViewById(R.id.tvTittle);
        btnNoti = (FrameLayout) findViewById(R.id.btnNoti);
        btnContact = (FrameLayout) findViewById(R.id.btnContact);
        btnEdit = (FrameLayout) findViewById(R.id.btnEdit);
        toolbar = (FrameLayout) findViewById(R.id.toolbar);
        toolbarEdit = (FrameLayout) findViewById(R.id.toolbarEdit);
        ivToolBarMenu = (FrameLayout) findViewById(R.id.ivToolBarMenu);
        btnBack = (FrameLayout) findViewById(R.id.btnBack);
        btnCheck = (FrameLayout) findViewById(R.id.btnCheck);
        tvEdit = (TextView) findViewById(R.id.tvEdit);

        String fontPath = "fonts/Roboto-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(), fontPath);
        tvTittle.setTypeface(tf);
        tvEdit.setTypeface(tf);

        ivToolBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    protected void toolbarEditVisible(boolean b) {
        if (b) {
            toolbar.setVisibility(View.GONE);
            toolbarEdit.setVisibility(View.VISIBLE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
            toolbarEdit.setVisibility(View.GONE);
        }
    }
}
