package edu.ufp.esocial.api.model;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Point;

public class Company extends Entity {

    public Company(String name, Point location) {
		super(name, location);
	}

    public Company(Integer id, String name, Point location) {
		super(id, name, location);
	}

	private RedBlackBST<Date, Job> currentJobST = new RedBlackBST<>();

    private RedBlackBST<Date, Job> previousJobST = new RedBlackBST<>();

    private SeparateChainingHashST<Integer, Meeting> meetingsGone = new SeparateChainingHashST<>();

    private SeparateChainingHashST<Integer, Meeting> meetingsCreated = new SeparateChainingHashST<>();

    public SeparateChainingHashST<Integer, Meeting> getMeetingsGone() {
        return this.meetingsGone;
    }

    public SeparateChainingHashST<Integer, Meeting> getMeetingsCreated() {
        return this.meetingsCreated;
    }

	public RedBlackBST<Date, Job> getCurrentJobST() {
		return this.currentJobST;
	}

    public RedBlackBST<Date, Job> getPreviousJobST() {
		return this.previousJobST;
	}

	@Override
	public String toString() {
		return "Company {\n\tid= " + this.getId() + "\n\tname= " + this.getName() + "\n\tlocation= " + this.getLocation() + "\n}";
	}

}