// IStudentManager.aidl
package com.kelly.effect.aidl;

import com.kelly.effect.aidl.Student;

interface IStudentManager {
    List<Student> getStudentList();
    /*
    in--表示传入数据
    out--表示传出数据
    inout--表示双向传递
    注：含有out时，Student类需要实现readFromParcel()方法
    */
    void addStudent(in Student student);
}
