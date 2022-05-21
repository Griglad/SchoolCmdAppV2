package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Stream implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;

    private List<Type> types = new ArrayList<>();


    public Stream(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Stream(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return name;
    }
}
