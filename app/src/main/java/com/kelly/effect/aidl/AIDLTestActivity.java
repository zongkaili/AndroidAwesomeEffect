package com.kelly.effect.aidl;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.kelly.effect.R;

import java.util.List;

/**
 * author: zongkaili
 * data: 2018/10/6
 * desc: 客户端
 */
public class AIDLTestActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity_Client";
    private static final int MESSAGE_QUERY_STUDENTLIST = 1;
    private int student_size = 3;

    private IStudentManager mRemoteStudentManager;

    //将服务端返回的数据显示在界面上
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_QUERY_STUDENTLIST:
                    Toast.makeText(AIDLTestActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                default:
                    super.handleMessage(msg);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidltest);
        Intent intent = new Intent(this, StudentManagerService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        //onServiceConnected与onServiceDisconnected都在主线程中
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //获取到IStudentManager对象
            mRemoteStudentManager = IStudentManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mRemoteStudentManager = null;
            Log.d(TAG, "onServiceDisconnected.threadname:" + Thread.currentThread().getName());
        }
    };

    /**
     * 在客户端向服务端添加一名学生
     *
     * @param view
     */
    public void addStudent(View view) {
        if (mRemoteStudentManager != null) {
            try {
                int student_id = student_size + 1;
                Student newStudent;
                if (student_id % 2 == 0) {
                    newStudent = new Student(student_id, "新学生" + student_id, "man");
                }
                else {
                    newStudent = new Student(student_id, "新学生" + student_id, "woman");
                }
                mRemoteStudentManager.addStudent(newStudent);
                Log.d(TAG, "添加一位学生：" + newStudent.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在客户端向服务端发起查询学生的请求
     *
     * @param view
     */
    public void getStudentList(View view) {
        Toast.makeText(this, "正在获取学生列表", Toast.LENGTH_SHORT).show();
        //由于服务端的查询操作是耗时操作，所以客户端需要开启子线程进行工作
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mRemoteStudentManager != null) {
                    try {
                        final List<Student> students = mRemoteStudentManager.getStudentList();
                        student_size = students.size();
                        Log.d(TAG, "从服务器成功获取到学生列表:" + students.toString());
                        mHandler.obtainMessage(MESSAGE_QUERY_STUDENTLIST, students).sendToTarget();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
