package edu.ufp.esocial.api.service;

import edu.princeton.cs.algs4.*;
import edu.ufp.esocial.api.model.*;
import edu.ufp.esocial.api.util.DatabaseST;

public class Database {

    private static final String DATA = "./data/";

    private In friendships = new In(DATA + "friendships.txt");

    private In followersTXT = new In(DATA + "followers.txt");

    private DatabaseST<Integer, Person> personST = new RedBlackBST<>();

    private DatabaseST<Integer, Company> companyST = new RedBlackBST<>();

    private DatabaseST<Integer, Meeting> meetingST = new RedBlackBST<>();

    private DatabaseST<String, Competence> competenceST = new SeparateChainingHashST<>();

    private EdgeWeightedGraph amizades = new EdgeWeightedGraph(friendships);

    private Digraph followers = new Digraph(followersTXT);

    private DatabaseST<Integer, Follower> followerST = new RedBlackBST<>();

    public Database() {
        /** All variables are already initialized **/
    }

    public RedBlackBST<Integer, Person> getPersonST() {
        return (RedBlackBST<Integer, Person>) this.personST;
    }

    public RedBlackBST<Integer, Company> getCompanyST() {
        return (RedBlackBST<Integer, Company>) this.companyST;
    }

    public RedBlackBST<Integer, Meeting> getMeetingST() {
        return (RedBlackBST<Integer, Meeting>) this.meetingST;
    }

    public SeparateChainingHashST<String, Competence> getCompetenceST() {
        return (SeparateChainingHashST<String, Competence>) this.competenceST;
    }

    public EdgeWeightedGraph getAmizades() {
        return amizades;
    }

    public DatabaseST<Integer, Follower> getFollowerST() {
        return followerST;
    }

    public Digraph getFollowers() {
        return followers;
    }
}
