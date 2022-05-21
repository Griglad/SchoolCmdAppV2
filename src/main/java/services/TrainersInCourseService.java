package services;

import dao.*;
import dao.impl.SubjectsInTrainerDaoJDBC;
import dao.impl.TrainerDaoJDBC;
import dao.impl.TrainersInCourseDaoJDBC;
import db.DB;
import model.*;
import utilities.Input;
import utilities.InputValidator;
import java.util.*;

public class TrainersInCourseService {

    private final Scanner input = Input.getInstance();
    private final TrainerDao trainerDao = new TrainerDaoJDBC(DB.getConnection());
    private final TrainersInCourseDao trainersInCourseDao = new TrainersInCourseDaoJDBC(DB.getConnection());
    private final SubjectsInTrainerDao subjectsInTrainerDao = new SubjectsInTrainerDaoJDBC(DB.getConnection());


    public void create(Course course) {
        TrainersInCourse trainersInCourse = new TrainersInCourse(course);
        String afm;
        String firstName;
        String lastName;
        Trainer trainer;
        boolean flag = false;
        while (!flag) {
            System.out.println("----ADDING TRAINERS TO THE COURSE " + course.getTitle());
            System.out.println("AFM:");
            afm = InputValidator.provideAfmWithNineDigits(input);
            if (findTrainerInCourseByAfm(trainersInCourse, afm) != null) {
                System.out.println("THIS TRAINER ALREADY EXISTS,PLEASE CREATE ANOTHER TRAINER FOR THIS COURSE.");
            } else {
                trainer = trainerDao.findTrainerByAfm(afm);
                if (trainer == null) {
                    System.out.println("FIRST NAME:");
                    firstName = InputValidator.provideOnlyString(input);
                    System.out.println("LAST NAME:");
                    lastName = InputValidator.provideOnlyString(input);
                    trainer = new Trainer(afm, firstName, lastName);
                }
                List<Subject> subjects = InputValidator.provideSubjects(input);
                Trainer trainerFromDb = addTrainerInDb(trainer);
                trainersInCourse.addTrainer(trainer);
                addSubjectsInTrainerInDb(trainerFromDb, subjects);
                System.out.println("DO YOU WANT TO CREATE ANOTHER TRAINER?");
                System.out.println(("1 - YES"));
                System.out.println(("2 - NO"));
                boolean more = InputValidator.yesOrNoMenu();
                if (!more) {
                    addTrainersInCourseInDb(trainersInCourse);
                    flag = true;
                }
            }
        }
    }


    private Trainer addTrainerInDb(Trainer trainer) {

        if (trainer.getId() == null) {
            trainerDao.insert(trainer);
        }
        return trainer;

    }

    private void addTrainersInCourseInDb(TrainersInCourse trainersInCourse) {
        trainersInCourseDao.insert(trainersInCourse);
    }

    private void addSubjectsInTrainerInDb(Trainer trainer, List<Subject> subjects) {
        //If the trainer is in another course and has already some subjects
        List<Subject> dbSubjects = subjectsInTrainerDao.findAll(trainer);
        //Delete all subjects from existing trainer in another course
        subjectsInTrainerDao.deleteById(trainer.getId());
        //Adding in existing subjects the new subjects from this course
        dbSubjects.addAll(subjects);
        //Taking the unique subjects in a Hashset
        Set<Subject> uniqueSubjects = new HashSet<>(dbSubjects);
        SubjectsInTrainer subjectsInTrainer = new SubjectsInTrainer(trainer, new ArrayList<>(uniqueSubjects));
        subjectsInTrainerDao.insert(subjectsInTrainer);
    }

    private Trainer findTrainerInCourseByAfm(TrainersInCourse trainersInCourse, String afm) {
        Trainer foundTrainer = null;
        Optional<Trainer> optionalTrainer = trainersInCourse.getTrainers()
                .stream().filter(trainer -> trainer.getAfm().equals(afm))
                .findFirst();
        if (optionalTrainer.isPresent()) {
            foundTrainer = optionalTrainer.get();
        }
        return foundTrainer;
    }
}
