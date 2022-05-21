package dao;

import model.Trainer;

public interface TrainerDao {

    void insert(Trainer trainer);

    Trainer findTrainerByAfm(String afm);

}
