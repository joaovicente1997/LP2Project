package edu.ufp.esocial.api.model;

import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Util.CompetenceLevel;

public class Competence {

    private String title;

    private String area;

    private String description;

    private Date acquiredDate;

    private CompetenceLevel level;

    public Competence(String title, String area, String description, Date acquiredDate, CompetenceLevel level) {
        this.title = title;
        this.area = area;
        this.description = description;
        this.acquiredDate = acquiredDate;
        this.level = level;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArea() {
        return this.area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAcquiredDate() {
        return this.acquiredDate;
    }

    public void setAcquiredDate(Date acquiredDate) {
        this.acquiredDate = acquiredDate;
    }

    public CompetenceLevel getLevel() {
        return this.level;
    }

    public void setLevel(CompetenceLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Competence{" + "\n" +
                "\t" + "title='" + title + "\',\n" +
                "\tarea='" + area + "\',\n" +
                "\tdescription='" + description + "\',\n" +
                "\tacquiredDate=" + acquiredDate + "\',\n" +
                "\tlevel=" + level + "\',\n" +
                '}';
    }
}
