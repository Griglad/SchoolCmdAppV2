package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String afm;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private double tuitionFees;
    private static int count;


    public Student() {

    }

    public Student(Integer id, String afm, String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this(afm, firstName, lastName, dateOfBirth, tuitionFees);
        this.id = id;
    }

    public Student(String afm, String firstName, String lastName, LocalDate dateOfBirth, double tuitionFees) {
        this.afm = afm;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.tuitionFees = tuitionFees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAfm() {
        return afm;
    }

    public void setAfm(String afm) {
        this.afm = afm;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return afm.equals(student.afm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(afm);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count + "-");
        sb.append("THE FIRST NAME IS:").append(firstName.toUpperCase());
        sb.append(",THE LAST NAME IS:").append(lastName.toUpperCase());
        sb.append(",THE DATE OF BIRTH IS:").append(dateOfBirth);
        sb.append(",THE TUITION FEES ARE:").append(tuitionFees);
        return sb.toString();
    }
}
