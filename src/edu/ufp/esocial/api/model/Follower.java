package edu.ufp.esocial.api.model;

import edu.ufp.esocial.api.service.Database;

public class Follower {

    private static final String DATA = "./data/";
    private static final String PERSON_LIST = "personList.txt";
    private static final String COMPANY_LIST = "companyList.txt";
    private int id;
    private int realId;
    private String type;
    private Database database;

    private Follower(int id, int realId, String type) {
        this.id = id;
        this.realId = realId;
        this.type = type;
    }

    public Follower(Database database) {
        this.database = database;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void loadFollowers(Database database) {
        id = 0;
        for (Integer i : database.getPersonST().keys()) {
            Follower f = new Follower(id, i, "Person");
            database.getFollowerST().put(f.id, f);
            id++;
        }
        for (Integer i : database.getCompanyST().keys()) {
            Follower f = new Follower(id, i, "Company");
            database.getFollowerST().put(f.id, f);
            id++;
        }
    }

    public void printFollowers(Database database) {
        for (Integer id : database.getFollowerST().keys()) {
            System.out.println(database.getFollowerST().get(id).toString());
        }
    }

    public String getNameOfFollower(Database database, Follower f) {
        if (f.getType().equals("Person")) {
            return database.getPersonST().get(f.getRealId()).getName();
        } else if (f.getType().equals("Company")) {
            return database.getCompanyST().get(f.getRealId()).getName();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Follower{" +
                "id=" + id +
                ", realId=" + realId +
                ", type='" + type + '\'' +
                '}';
    }
}
