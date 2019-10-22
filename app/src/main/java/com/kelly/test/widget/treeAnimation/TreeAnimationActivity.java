package com.kelly.test.widget.treeAnimation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TreeAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TreeView(this));
    }
}
