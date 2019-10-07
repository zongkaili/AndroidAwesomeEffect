package com.kelly.test.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * author: zongkaili
 * data: 2018/10/6
 * desc: 服务端
 */
public class StudentManagerService extends Service {
    private static final String TAG = "StudentManagerService";
    //判断Service是否销毁
    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    //适合用于进程间传输的列表类
    private CopyOnWriteArrayList<Student> mStudentList = new CopyOnWriteArrayList<Student>();
    @Override
    public void onCreate() {
        super.onCreate();
        //在服务端手动添加两位默认的学生
        mStudentList.add(new Student(1, "BOB", "man"));
        mStudentList.add(new Student(2, "MAY", "woman"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(false);
        super.onDestroy();
    }
    private Binder mBinder = new IStudentManager.Stub() {
        @Override
        public List<Student> getStudentList() throws RemoteException {
            SystemClock.sleep(5000);//休眠5s模拟耗时操作
            return mStudentList;
        }
        @Override
        public void addStudent(Student student) throws RemoteException {
            mStudentList.add(student);
        }
    };

}
