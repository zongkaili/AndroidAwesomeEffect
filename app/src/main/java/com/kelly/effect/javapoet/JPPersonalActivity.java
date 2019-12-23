//package com.kelly.effect.javapoet;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//
//import com.kelly.modular.annotation.ARouter;
//import com.kelly.effect.R;
//
//@ARouter(path = "/app/JPPersonalActivity")
//public class JPPersonalActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_jppersonal);
//        Log.d("javaPoet >>> ", " ------> JPPersonalActivity");
//    }
//
//    public void jump(View view) {
//        Class targetClass = JPMain1Activity$$ARouter.findTargetClass("/app/JPMain1Activity");
//        startActivity(new Intent(this, targetClass));
//    }
//}
