package com.bowtie.Controller;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bowtie.Interfaces.ProfileCardInterface;
import com.bowtie.Model.ProfileCardModel;
import com.bowtie.Object.NetworkObject;
import com.bowtie.R;
import com.bowtie.View.ProfileCardDetailFragment;
import com.bowtie.View.ProfileCardEditFragment;
import com.bowtie.View.ProfileCardFragment;

import java.io.File;

public class ProfileCardController extends SlidingMenuController implements ProfileCardInterface.Listener, View.OnClickListener {
    private Context context;
    private ProgressDialog progressDialog;
    private ProfileCardFragment profileCardView;
    private ProfileCardDetailFragment profileCardDetailView;
    private ProfileCardEditFragment profileCardEditView;
    private ProfileCardModel model;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        try {
            View view = getCurrentFocus();
            boolean ret = super.dispatchTouchEvent(event);

            if (view instanceof EditText) {
                View w = getCurrentFocus();
                int scrcoords[] = new int[2];
                w.getLocationOnScreen(scrcoords);
                float x = event.getRawX() + w.getLeft() - scrcoords[0];
                float y = event.getRawY() + w.getTop() - scrcoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
            return ret;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ProfileCardController.this;
        getLayoutInflater().inflate(R.layout.activity_profile_card, container);
        toolbarEditVisible(false);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Update profile");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);

        model = new ProfileCardModel();
        if (NetworkObject.isNetworkConnect(context)) {
            if (!model.setUser(context)) {
                model.insertFirstUser();
            }
            getProfileFromServer();
        } else {
            if (!model.setUser(context)) {
                model.insertFirstUser();
            }
            init();
        }
    }

    public void getProfileFromServer() {
        progressDialog.show();
        model.requestGetProfile(context, new ProfileCardModel.onGetProfileServer() {
            @Override
            public void onFinish(boolean isSuccess) {
                init();
                progressDialog.dismiss();
            }
        });
    }

    public void init() {
        profileCardView = new ProfileCardFragment();
        profileCardDetailView = new ProfileCardDetailFragment();
        profileCardEditView = new ProfileCardEditFragment();
        profileCardView.setListener(this);
        profileCardView.setDatasource(model);
        profileCardDetailView.setListener(this);
        profileCardDetailView.setDatasource(model);
        profileCardEditView.setListener(this);
        profileCardEditView.setDatasource(model);

        btnEdit.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnCheck.setOnClickListener(this);

        addFragment(R.id.profilecard_container, profileCardView);
        addFragment(R.id.profilecard_container, profileCardDetailView);
        addFragment(R.id.profilecard_container, profileCardEditView);
        hideAllFragment();
        showFragment(profileCardView);
    }

    private void addFragment(int container, Fragment fragment) {
        getFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void addFragmentv4(int container, android.support.v4.app.Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(container, fragment).commit();
    }

    public void hideAllFragment() {
        hideFragment(profileCardView);
        hideFragment(profileCardDetailView);
        hideFragment(profileCardEditView);
    }

    public void showFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().show(fragment).commit();
    }

    public void hideFragment(Fragment fragment) {
        getFragmentManager().beginTransaction().hide(fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onProfileCardCreateViewFinish() {
        profileCardView.reloadView();
    }

    @Override
    public void onProfileCardDetailCreateViewFinish() {
        profileCardDetailView.reloadView();
    }

    @Override
    public void onProfileCardEditCreateViewFinish() {
        profileCardEditView.reloadView();
    }

    @Override
    public void onProfileCardButtonNameClick() {
        toolbarEditVisible(false);
        hideAllFragment();
        showFragment(profileCardDetailView);
        profileCardDetailView.reloadView();
    }

    @Override
    public void onButtonFindClick() {
        //startActivity(new Intent(context, FindContactController.class));
    }

    @Override
    public void onProfileCardDetailButtonNameClick() {
        toolbarEditVisible(false);
        hideAllFragment();
        showFragment(profileCardView);
        profileCardView.reloadView();
    }

    @Override
    public void onProfileCardEditAvatarClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Uri selectedImageUri = data.getData();
                String path = getPath(context, selectedImageUri);
                if (path == null) {
                    Toast.makeText(context, "Wrong format", Toast.LENGTH_SHORT).show();
                } else {
                    File file = new File(path);
                    if (NetworkObject.isNetworkConnect(context)) {
                        progressDialog.show();
                        model.updateAvatar(context, file, new ProfileCardModel.onUpdateAvatar() {
                            @Override
                            public void onFinish(boolean isSuccess) {
                                if (isSuccess) {
                                    model.updateProfileServer(context, new ProfileCardModel.onUpdateProfileServer() {
                                        @Override
                                        public void onFinish(boolean isSuccess) {
                                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                                            profileCardEditView.reloadView();
                                        }
                                    });
                                } else {
                                    Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                                    model.updateProfileDatabase();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(context, "Please check you connect network, we can't update right now", Toast.LENGTH_SHORT).show();
                        model.updateProfileDatabase();
                    }
                }
            } catch (Exception e) {
                Log.e("Exception", e.toString());
                Toast.makeText(context, "Wrong format", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else {
                    return null;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }


    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEdit:
                toolbarEditVisible(true);
                hideAllFragment();
                showFragment(profileCardEditView);
                profileCardEditView.reloadView();
                break;
            case R.id.btnBack:
                toolbarEditVisible(false);
                hideAllFragment();
                showFragment(profileCardView);
                profileCardView.reloadView();
                break;
            case R.id.btnCheck:
                if (model.validateEmail()) {
                    if (NetworkObject.isNetworkConnect(context)) {
                        progressDialog.show();
                        model.updateProfileServer(context, new ProfileCardModel.onUpdateProfileServer() {
                            @Override
                            public void onFinish(boolean isSuccess) {
                                if (isSuccess) {
                                    Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Update fail", Toast.LENGTH_SHORT).show();
                                    model.updateProfileDatabase();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    } else {
                        Toast.makeText(context, "Please check you connect network", Toast.LENGTH_SHORT).show();
                        model.updateProfileDatabase();
                    }
                } else {
                    Toast.makeText(context, "Format email wrong", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}