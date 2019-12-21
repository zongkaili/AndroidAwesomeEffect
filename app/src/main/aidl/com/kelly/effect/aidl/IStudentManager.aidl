// IStudentManager.aidl
package com.kelly.effect.aidl;

import com.kelly.effect.aidl.Student;

interface IStudentManager {
    List<Student> getStudentList();
    void addStudent(in Student student);
}
