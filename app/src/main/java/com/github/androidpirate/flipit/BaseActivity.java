package com.github.androidpirate.flipit;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void addView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        addView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean handleReturn = super.dispatchTouchEvent(ev);

        View view = getCurrentFocus();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if(view instanceof EditText) {
            View innerView = getCurrentFocus();

            if(ev.getAction()== MotionEvent.ACTION_UP &&
                    !getLocationOnScreen(innerView).contains(x, y)) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(
                            getWindow().getCurrentFocus().getWindowToken(),
                            0);
                }
                FrameLayout frameLayout = findViewById(R.id.base_container);
                frameLayout.requestFocus();
            }
        }
        return handleReturn;
    }

    protected Rect getLocationOnScreen(View editText) {
        Rect mRect = new Rect();
        int[] location = new int[2];

        editText.getLocationOnScreen(location);

        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + editText.getWidth();
        mRect.bottom = location[1] + editText.getHeight();

        return mRect;
    }
}
