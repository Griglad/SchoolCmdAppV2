package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public class TypesInCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1l;

    private Integer id;
    private Course course;
    private Set<Type> types;

    public TypesInCourse(){

    }

    public TypesInCourse(Course course,Set<Type> types){
        this.course = course;
        this.types = types;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<Type> getTypes() {
        return types;
    }

    public void setTypes(Set<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypesInCourse{");
        sb.append("course=").append(course);
        sb.append(", types=").append(types);
        sb.append('}');
        return sb.toString();
    }
}
