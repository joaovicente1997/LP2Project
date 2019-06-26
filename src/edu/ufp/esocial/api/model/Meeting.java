package edu.ufp.esocial.api.model;

import java.util.ArrayList;

import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Point;

public class Meeting extends Entity {

    private Date startDate;
    private Date endDate;
    private Float price;
    private Company creator;
    private ArrayList<String> areasAborded = new ArrayList<>();
    private SeparateChainingHashST<Integer, Person> persons = new SeparateChainingHashST<>();
    private SeparateChainingHashST<Integer, Company> companies = new SeparateChainingHashST<>();

    public Meeting(String name, Point location, Date startDate, Date endDate, Float price, Company creator) {
        super(name, location);
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.creator = creator;
    }

    public Meeting(Integer id, String name, Point location, Date startDate, Date endDate, Float price, Company creator) {
        super(id, name, location);
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.creator = creator;
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

    public Float getPrice() {
        return this.price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Company getCreator() {
        return this.creator;
    }

    public void setCreator(Company creator) {
        this.creator = creator;
    }

    public ArrayList<String> getAreasAborded() {
        return this.areasAborded;
    }

    public SeparateChainingHashST<Integer, Person> getPersons() {
        return this.persons;
    }

    public SeparateChainingHashST<Integer, Company> getCompanies() {
        return this.companies;
    }

    public void printCompaniesParticipated() {
        for (Integer i : this.companies.keys()) {
            Company c = this.companies.get(i);
            System.out.println("\t\t" + c.toString());
        }
    }

    @Override
    public String toString() {
        System.out.println("Meeting\n{" + "\n\t id = " + this.getId() + ",\n"
                + "\t Name = " + this.getName() + ",\n" +
                "\t startDate = " + this.startDate + ",\n" +
                "\t endDate = " + this.endDate + ",\n" +
                "\t price = " + this.price + "€,\n" +
                "\t creator = " + this.creator + ",\n" +
                "\t " + "areasAborded" + " = " + this.areasAborded + "\n" +
                "\t " + "companies = {");
        this.printCompaniesParticipated();
        System.out.println("\t }\n}");
        return null;
    }
}