package dao;

import model.Stream;

import java.util.List;

public interface StreamDao {

    List<Stream> findAll();

    Stream findByName(String name);
}
