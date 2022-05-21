package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class TypesInStreamsInCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private TypesInCourse typesInCourse;
    private StreamsInCourse streamsInCourse;


    public TypesInStreamsInCourse() {

    }

    public TypesInStreamsInCourse(TypesInCourse typesInCourse, StreamsInCourse streamsInCourse) {
        this.typesInCourse = typesInCourse;
        this.streamsInCourse = streamsInCourse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypesInCourse getTypesInCourse() {
        return typesInCourse;
    }

    public void setTypesInCourse(TypesInCourse typesInCourse) {
        this.typesInCourse = typesInCourse;
    }

    public StreamsInCourse getStreamsInCourse() {
        return streamsInCourse;
    }

    public void setStreamsInCourse(StreamsInCourse streamsInCourse) {
        this.streamsInCourse = streamsInCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypesInStreamsInCourse that = (TypesInStreamsInCourse) o;
        return id.equals(that.id) && typesInCourse.equals(that.typesInCourse) && streamsInCourse.equals(that.streamsInCourse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typesInCourse, streamsInCourse);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TypesInStreamsInCourse{");
        sb.append("id=").append(id);
        sb.append(", typesInCourse=").append(typesInCourse);
        sb.append(", streamsInCourse=").append(streamsInCourse);
        sb.append('}');
        return sb.toString();
    }
}