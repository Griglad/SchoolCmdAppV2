package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class StreamsInCourse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Course course;
    private List<Stream> streams;


    public StreamsInCourse() {

    }

    public StreamsInCourse(Course course, List<Stream> streams) {
        this.course = course;
        this.streams = streams;
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

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }


    public void addStream(Stream stream) {
        streams.add(stream);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StreamsInCourse{");
        sb.append("id=").append(id);
        sb.append(", course=").append(course);
        sb.append(", streams=").append(streams);
        sb.append('}');
        return sb.toString();
    }
}
