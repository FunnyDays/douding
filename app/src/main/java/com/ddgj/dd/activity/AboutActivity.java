package com.ddgj.dd.activity;

import android.os.Bundle;
import android.view.View;

import com.ddgj.dd.R;


public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initViews() {

    }

    public void backClick(View v)
    {
        finish();
    }
}
