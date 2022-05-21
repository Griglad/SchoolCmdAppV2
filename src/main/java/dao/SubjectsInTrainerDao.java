package dao;

import model.Subject;
import model.SubjectsInTrainer;
import model.Trainer;

import java.util.List;

public interface SubjectsInTrainerDao {
    List<Subject> findAll(Trainer trainer);

    void insert(SubjectsInTrainer subjectsInTrainer);

    void deleteById(Integer id);
}
