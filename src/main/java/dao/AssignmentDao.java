package dao;

import model.Assignment;

public interface AssignmentDao {

    void insert(Assignment assignment);

    Assignment findAssignmentByTitle(String title);
}
