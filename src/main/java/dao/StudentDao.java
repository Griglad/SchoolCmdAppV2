package dao;

import model.Student;

public interface StudentDao {
    void insert(Student student);

    Student findStudentByAfm(String afm);
}
