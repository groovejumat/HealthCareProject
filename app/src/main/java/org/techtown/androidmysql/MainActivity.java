package org.techtown.androidmysql;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends Activity {

    String strNickname, strProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvNickname = findViewById(R.id.tvNickname);
        TextView tvProfile = findViewById(R.id.tvProfile);

        Intent intent = getIntent();
        strNickname = intent.getStringExtra("name");
        strProfile = intent.getStringExtra("email");

        tvNickname.setText(strNickname);
        tvProfile.setText(strProfile);
    }
}
