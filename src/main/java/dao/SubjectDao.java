package dao;

import model.Subject;

import java.util.List;

public interface SubjectDao {


    List<Subject> findAll();

    Subject findByName(String name);
}
