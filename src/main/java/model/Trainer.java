package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Trainer  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String afm;
    private String firstName;
    private String lastName;
    private static int count;


    public Trainer() {
    }

    public Trainer(Integer id, String afm, String firstName, String lastName) {
        this(afm, firstName, lastName);
        this.id = id;
    }

    public Trainer(String afm, String firstName, String lastName) {
        this.afm = afm;
        this.firstName = firstName;
        this.lastName = lastName;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return afm.equals(trainer.afm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(afm);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count).append("-");
        sb.append("THE AFM IS:").append(afm);
        sb.append(",THE FIRST NAME IS:").append(firstName.toUpperCase());
        sb.append(",THE LAST NAME IS:").append(lastName.toUpperCase());
        return sb.toString();
    }
}
