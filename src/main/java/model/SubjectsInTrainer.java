package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class SubjectsInTrainer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Trainer trainer;
    private List<Subject> subjects;


    public SubjectsInTrainer(Trainer trainer,List<Subject> subjects){
        this.trainer = trainer;
        this.subjects = subjects;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
