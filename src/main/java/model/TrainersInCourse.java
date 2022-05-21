package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainersInCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Course course;
    private List<Trainer> trainers = new ArrayList<>();
    private static int count;


    public TrainersInCourse(Course course) {
        this.course = course;
    }

    public TrainersInCourse(Course course, List<Trainer> trainers) {
        this(course);
        this.trainers = trainers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }


    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count + "-");
        long numberOfTrainers = trainers.size();
        sb.append("THE COURSE IS:").append(course.getTitle());
        if (numberOfTrainers > 1) {
            sb.append(",THE TRAINERS ARE:");
        } else {
            sb.append(",THE TRAINER IS:");
        }
        sb.append(trainers
                .stream()
                .map(trainer -> trainer.getFirstName().toUpperCase() + " " + trainer.getLastName().toUpperCase())
                .collect(Collectors.joining(",")));

        return sb.toString();
    }
}
