package model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Assignment  implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String description;
    private LocalDate subDateTime;
    private double oralMark;
    private double totalMark;
    private static int count;


    public Assignment(Integer id, String title, String description, LocalDate subDateTime, double oralMark, double totalMark) {
        this(title, description, subDateTime, oralMark, totalMark);
        this.id = id;
    }


    public Assignment(String title, String description, LocalDate subDateTime, double oralMark, double totalMark) {
        this.title = title;
        this.description = description;
        this.subDateTime = subDateTime;
        this.oralMark = oralMark;
        this.totalMark = totalMark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getSubDateTime() {
        return subDateTime;
    }

    public void setSubDateTime(LocalDate subDateTime) {
        this.subDateTime = subDateTime;
    }

    public double getOralMark() {
        return oralMark;
    }

    public void setOralMark(double oralMark) {
        this.oralMark = oralMark;
    }

    public double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(double totalMark) {
        this.totalMark = totalMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return title.equalsIgnoreCase(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(++count + "-");
        sb.append("THE TITLE IS:").append(title.toUpperCase());
        sb.append(",THE DESCRIPTION IS:").append(description.toUpperCase());
        sb.append(",THE SUBMISSION DATE IS:").append(subDateTime);
        sb.append(",THE ORAL MARK IS:").append(oralMark);
        sb.append(",THE TOTAL MARK IS:").append(totalMark);
        return sb.toString();
    }
}
