package com.kelly.test.javapoet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.annotation.ARouter;
import com.kelly.test.R;

@ARouter(path = "/app/JPOrderActivity")
public class JPOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jporder);
        Log.d("javaPoet >>> ", " ------> JPOrderActivity");
    }

    public void jump(View view) {
        Class targetClass = JPPersonalActivity$$ARouter.findTargetClass("/app/JPPersonalActivity");
        startActivity(new Intent(this, targetClass));
    }
}
