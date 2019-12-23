package com.kelly.effect.javapoet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.kelly.modular.annotation.ARouter;
import com.kelly.effect.R;
import com.kelly.modular.annotation.Parameter;

@ARouter(path = "/app/JPMain1Activity")
public class JPMain1Activity extends AppCompatActivity {
    @Parameter
    String sex;
    @Parameter(name = "marry")
    boolean isMarried;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpmain1);
        Log.d("javaPoet >>> ", " ------> JPMain1Activity");
    }

    public void jump(View view) {
    }
}
