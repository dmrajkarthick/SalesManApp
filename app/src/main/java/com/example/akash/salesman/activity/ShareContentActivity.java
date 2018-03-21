package com.example.akash.salesman.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rkarthic on 21/03/18.
 */

public class ShareContentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String contentToBeShared = getIntent().getExtras().getString("contentToBeShared");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, contentToBeShared);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        finish();

    }
}
