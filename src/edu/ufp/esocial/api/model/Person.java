package edu.ufp.esocial.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Point;

public class Person extends Entity {

	private Date birth;

	private RedBlackBST<Date, Job> previousJobs = new RedBlackBST<>();

    private RedBlackBST<Date, Meeting> meetingsGone = new RedBlackBST<>();

	private Job currentJob;

	private List<String> interestAreas = new ArrayList<>();

	private SeparateChainingHashST<String, Competence> competences = new SeparateChainingHashST<>();

	public Person(String name, Point location, Date birth) {
		super(name, location);
		this.birth = birth;
	}

	public Person(Integer id, String name, Point location, Date birth) {
		super(id, name, location);
		this.birth = birth;
	}

	public int getAge() {
		Date date = new Date();
		int years = this.birth.getYearsDifference(date);
		Date dAux = new Date(this.birth.getDay(), this.birth.getMonth(), date.getYear());
		if (dAux.isDateAfter(date)) {
			years--;
		}
		return years;
	}

	public boolean isOlderThan(Person p) {
		return this.birth.isDateBefore(p.birth);
	}

	public Date getBirth() {
		return this.birth;
	}

	public boolean checkPreviousJob(Job job) {
		return this.previousJobs.get(job.getStartDate()) != null;
	}

	public void addPreviousJob(Job job) {
		this.previousJobs.put(job.getStartDate(), job);
	}

	public RedBlackBST<Date, Job> getPreviousJobs() {
		return this.previousJobs;
	}

    public boolean checkMeetingsGone(Meeting m) {
        return this.meetingsGone.get(m.getStartDate()) != null;
    }

    public void addMeetingsGone(Meeting m) {
        this.meetingsGone.put(m.getStartDate(), m);
    }

    public RedBlackBST<Date, Meeting> getMeetingsGone() {
        return this.meetingsGone;
    }

	public boolean hasWorkedFor(Company company) {
		return searchCompany(this.previousJobs, company) != null;
	}

	public Job getCurrentJob() {
		return this.currentJob;
	}

	public void setCurrentJob(Job job) {
		if(this.currentJob != null) {
			this.previousJobs.put(this.currentJob.getStartDate(), this.currentJob);
		}
		this.currentJob = job;
	}

	public List<String> getInterestAreas() {
		return this.interestAreas;
	}

	public SeparateChainingHashST<String, Competence> getCompetences() {
		return this.competences;
	}

	private static Company searchCompany(RedBlackBST<Date, Job> jobList, Company company) {
		for (Date date : jobList.keys()) {
			Company c = jobList.get(date).getCompany();
			if (c == company) {
				return c;
			}
		}
		return null;
	}

	public void printCompetences() {
		for (String s : this.getCompetences().keys()) {
			System.out.println();
			System.out.println(this.getCompetences().get(s).toString());
		}
	}

    public void printMeetings() {
        for (Date d : this.getMeetingsGone().keys()) {
            System.out.println();
            System.out.println(this.getMeetingsGone().get(d).toString());
        }
    }

	@Override
	public String toString() {
        String s = "Person{\n" + "\tid= " + this.getId() + "\n" + "\tname= " + this.getName() + "\n" + "\tlocation= "
                + this.getLocation() + "\n" + "\tbirth= " + this.birth + "\n" + "\tcompetences {";
		s = getCompetencesOut(s);
		s = s.concat("\n\t},\n}");
        s = printInterestAreas(s);
		return s;
	}

	public String getCompetencesOut(String aux) {
		for (String s : this.getCompetences().keys()) {
			aux = aux.concat("\t\t");
			aux = aux.concat(s + "\n");
		}
		return aux;
	}

    public String printInterestAreas(String aux) {
        for (String s : this.getInterestAreas()) {
            aux = aux.concat("\t\t");
            aux = aux.concat(s + "\n");
        }
        return aux;
    }
}