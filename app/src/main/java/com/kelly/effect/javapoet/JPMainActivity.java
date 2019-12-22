package com.kelly.effect.javapoet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.modular.annotation.ARouter;
import com.kelly.effect.R;

@ARouter(path = "/app/JPMainActivity")
public class JPMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpmain);
        Log.d("javaPoet >>> ", " ------> JPMainActivity");
    }

    public void jump(View view) {
    }
}
