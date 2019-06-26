package edu.ufp.esocial.api.model;

import edu.ufp.esocial.api.util.Date;

public class Job {

    private Person person;

    private Company company;

    private Date startDate;

    private Date endDate;

    public Job(Person person, Company company) {
        this.person = person;
        this.company = company;
        this.startDate = new Date();
    }

    public Job(Person person, Company company, Date startDate, Date endDate) {
        this.person = person;
        this.company = company;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Job [person=" + this.person + ", company=" + this.company + ", startDate=" + this.startDate
                + ", endDate=" + this.endDate + "]";
    }
}
