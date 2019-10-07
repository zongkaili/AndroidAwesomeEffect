// IStudentManager.aidl
package com.kelly.test.aidl;

import com.kelly.test.aidl.Student;

interface IStudentManager {
    List<Student> getStudentList();
    void addStudent(in Student student);
}
