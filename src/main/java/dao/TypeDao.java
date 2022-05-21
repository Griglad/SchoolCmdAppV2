package dao;

import model.Type;

import java.util.List;

public interface TypeDao {

    List<Type> findAll();

    Type findByTypeName(String name);
}
