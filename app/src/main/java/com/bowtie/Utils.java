package com.bowtie;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by thanh_nguyen02 on 30/01/2016.
 */
public class Utils {
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+");
        Pattern pattern2 = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z0-9_-]+\\.+[a-z]+\\.+[a-z]+");
        if (pattern.matcher(email).matches() || pattern2.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    public static void removeFocusAndHideKeyboard(final Context context, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    InputMethodManager inputMgr = (InputMethodManager) context
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    inputMgr.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        editText.requestFocus();
    }
}
