package edu.ufp.esocial.api;

import java.util.ArrayList;
import java.util.List;

import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.model.Job;
import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.CompanyService;
import edu.ufp.esocial.api.service.CompetenceService;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.service.MeetingService;
import edu.ufp.esocial.api.service.PersonService;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.FileUtil;
import edu.ufp.esocial.api.util.Point;
import edu.ufp.esocial.api.util.Util;
import edu.ufp.esocial.api.util.filter.CompanyFilter;
import edu.ufp.esocial.api.util.filter.MeetingFilter;
import edu.ufp.esocial.api.util.filter.PersonFilter;

public class Test {

    private Database database;
    private PersonService personService;
    private CompetenceService competenceService;
    private CompanyService companyService;
    private MeetingService meetingService;

    public Test(Database database, PersonService personService, CompetenceService competenceService, CompanyService companyService, MeetingService meetingService) {
        this.database = database;
        this.personService = personService;
        this.competenceService = competenceService;
        this.companyService = companyService;
        this.meetingService = meetingService;
        //FileUtil.populateMeetingST(this.meetingService, this.companyService);
        //FileUtil.populateCompanyST(this.companyService);
        //FileUtil.populatePersonST(this.personService);
        for (int i = 0; i < 50; i++) {
			System.out.println();
		}
    }

    public void printAllPersons() {
        System.out.println("\nALL PERSONS: \n");
        this.personService.getAll().forEach(System.out::println);
    }

    public void printAllCompanies() {
        System.out.println("\nALL COMPANIES: \n");
        this.companyService.getAll().forEach(System.out::println);
    }

    public void printAllMeetings() {
        System.out.println("\nALL MEETINGS: \n");
        this.meetingService.getAll().forEach(System.out::println);
    }

    public void printAllFriendships() {
        System.out.println("\nALL FRIENDSHIPS: \n");
        System.out.println(database.getAmizades().toString());
    }

    public void printFilteredPersons(String name, Point location, Integer minAge, Integer maxAge, List<String> competences) {
        System.out.println("\nFILTERED PERSONS: \n");
        List<Person> persons = this.personService.getAll();
        PersonFilter personFilter = new PersonFilter();
        personFilter.setName(name);
        personFilter.setLocation(location);
        personFilter.setMaxAge(maxAge);
        personFilter.setMinAge(minAge);
        personFilter.setCompetences(competences);
        List<Person> filtered = this.personService.filter(persons, personFilter);
        filtered.forEach(System.out::println);
    }

    public void printFilteredCompany(String name, Point location) {
        System.out.println("\nFILTERED COMPANIES: \n");
        List<Company> companies = this.companyService.getAll();
        CompanyFilter companyFilter = new CompanyFilter();
        companyFilter.setName(name);
        companyFilter.setLocation(location);
        List<Company> filtered = this.companyService.filter(companies, companyFilter);
        filtered.forEach(System.out::println);
    }

    public void printFilteredMeetings(String name, Point location, Date start, Date end, Float min, Float max, Company creator, ArrayList<String> areas) {
        System.out.println("\nFILTERED MEETINGS: \n");
        List<Meeting> meetings = this.meetingService.getAll();
        MeetingFilter meetingFilter = new MeetingFilter();
        meetingFilter.setName(name);
        meetingFilter.setStartDate(start);
        meetingFilter.setEndDate(end);
        meetingFilter.setMinPrice(min);
        meetingFilter.setMaxPrice(max);
        meetingFilter.setCreator(creator);
        meetingFilter.setAreasAborded(areas);
        List<Meeting> filtered = this.meetingService.filter(meetings, meetingFilter);
        filtered.forEach(System.out::println);
    }

    public void addPerson(String name, Point location, Date birth) {
        Person p = new Person(name, location, birth);
        this.personService.create(p);
    }

    public void addCompetence(String title, String area, String description, Date acquiredDate, Util.CompetenceLevel level) {
        Competence c = new Competence(title, area, description, acquiredDate, level);
        this.competenceService.create(c);
    }

    public void addCompany(String name, Point location) {
        Company c = new Company(name, location);
        this.companyService.create(c);
    }

    public void addMeeting(String name, Point location, Date start, Date end, Float price, Company creator) {
        Meeting m = new Meeting(name, location, start, end, price, creator);
        this.meetingService.create(m);
    }

    public void addAreaAbordedToMeeting(Integer meeting, String area) {
        Meeting m = this.meetingService.findByKey(meeting);
        m.getAreasAborded().add(area);
    }

    public void addCompetenceToPerson(String competence, int id) {
        Competence c = this.competenceService.findByKey(competence);
        if (c != null) {
            Person p = this.personService.findByKey(id);
            if (p != null) {
                p.getCompetences().put(c.getTitle(), c);
            }
        }
    }

    public void addMeetingToPerson(Integer meeting, int id) {
        Meeting m = this.meetingService.findByKey(meeting);
        if (m != null) {
            Person p = this.personService.findByKey(id);
            if (p != null) {
                p.addMeetingsGone(m);
                if (p.checkMeetingsGone(m)) {
                    m.getPersons().put(p.getId(), p);
                }
            }
        }
    }

    public void addMeetingToCreatorCompany(Integer meeting, String creator) {
        Meeting m = this.meetingService.findByKey(meeting);
        if (m != null) {
            for (int i = 0; i < this.companyService.getAll().size(); i++) {
                Company c = this.companyService.findByKey(i);
                if (c.getName().compareTo(creator) == 0) {
                    c.getMeetingsCreated().put(meeting, m);
                    m.setCreator(c);
                }
            }
        }
    }

    public void addMeetingToCompany(Integer meeting, String company) {
        Meeting m = this.meetingService.findByKey(meeting);
        if (m != null) {
            for (int i = 0; i < this.companyService.getAll().size(); i++) {
                Company c = this.companyService.findByKey(i);
                if (c.getName().compareTo(company) == 0) {
                    c.getMeetingsGone().put(meeting, m);
                    m.getCompanies().put(c.getId(), c);
                }
            }
        }
    }

    public void addJobToPerson(String company, int id) {
        Person p = this.personService.findByKey(id);
        if (p != null) {
            for (int i = 0; i < this.companyService.getAll().size(); i++) {
                Company c = this.companyService.findByKey(i);
                if (c.getName().compareTo(company) == 0) {
                    Job job = new Job(p, c);
                    if (p.getCurrentJob() != null) {
                        p.getCurrentJob().setEndDate(new Date());
                        p.getPreviousJobs().put(p.getCurrentJob().getEndDate(), p.getCurrentJob());
                    }
                    p.setCurrentJob(job);
                    c.getCurrentJobST().put(job.getStartDate(), job);
                }
            }
        }
    }

    public void removeCompetence(String competence) {
        if (this.competenceService.findByKey(competence) != null) {
            Competence c = this.competenceService.findByKey(competence);
            FileUtil.writeCompetenceToArchiveFile(c);
            for (int i = 0; i < this.personService.getAll().size(); i++) {
                if (this.personService.findByKey(i).getCompetences().contains(competence)) {
                    this.personService.findByKey(i).getCompetences().delete(competence);
                }
            }
        }
    }

    public void removePerson(String person) {
        for (int i = 0; i < this.personService.getAll().size(); i++) {
            if (this.personService.findByKey(i).getName().compareTo(person) == 0) {
                Person p = this.personService.findByKey(i);
                if (p.getCurrentJob() != null) {
                    Job j = p.getCurrentJob();
                    Company c = j.getCompany();
                    c.getCurrentJobST().delete(j.getStartDate());
                    c.getPreviousJobST().put(new Date(), j);
                }
                FileUtil.writePersonToArchiveFile(p);
                this.personService.delete(i);
            }
        }
    }

    public void removeCompany(String company) {
        for (int i = 0; i < this.companyService.getAll().size(); i++) {
            if (this.companyService.findByKey(i).getName().compareTo(company) == 0) {
                Company c = this.companyService.findByKey(i);
                for (Date d : c.getCurrentJobST().keys()) {
                    Job j = c.getCurrentJobST().get(d);
                    Person p = j.getPerson();
                    this.removeCurrentJobFromPerson(p.getId());
                    c.getCurrentJobST().delete(d);
                }
                FileUtil.writeCompanyToArchiveFile(c);
                this.companyService.delete(i);
            }
        }
    }

    public void removeMeeting(Integer meeting) {
        Meeting m = this.meetingService.findByKey(meeting);
        FileUtil.writeMeetingToArchiveFile(m);
        this.meetingService.delete(m.getId());
    }

    public void removeCompetenceFromPerson(String competence, int id) {
        Person p = this.personService.findByKey(id);
        p.getCompetences().delete(competence);
    }

    public void removeCurrentJobFromPerson(int id) {
        Person p = this.personService.findByKey(id);
        Job aux = p.getCurrentJob();
        aux.setEndDate(new Date());
        p.getPreviousJobs().put(aux.getEndDate(), aux);
        p.setCurrentJob(null);
    }

    public void printPerson(int id) {
        Person p = this.personService.findByKey(id);
        System.out.println("\nPRINT " + p.getName());
        p.toString();
    }

    public void printMeeting(Integer meeting) {
        Meeting m = this.meetingService.findByKey(meeting);
        m.toString();
    }

    public void printCompany(int id) {
        Company c = this.companyService.findByKey(id);
        System.out.println(c.toString());
    }

    public int pesoComum(int id, int id2) {
        int value = 0;
        Person p1 = this.database.getPersonST().get(id);
        Person p2 = this.database.getPersonST().get(id2);
        if (p1.getCurrentJob() != null && p2.getCurrentJob() != null) {
            if (p1.getCurrentJob().getCompany().equals(p2.getCurrentJob().getCompany())) {
                value++;
            }
        }
        for (int i = 0; i < p1.getInterestAreas().size(); i++) {
            for (int j = 0; j < p2.getInterestAreas().size(); j++) {
                if (p1.getInterestAreas().get(i).compareTo(p2.getInterestAreas().get(j)) == 0) {
                    value++;
                }
            }
        }
        if (p1.getCompetences().size() > 0 && p2.getCompetences().size() > 0) {
            for (String c1 : p1.getCompetences().keys()) {
                for (String c2 : p2.getCompetences().keys()) {
                    if (c1.equals(c2)) {
                        value++;
                    }
                }
            }
        }
        if (p1.getPreviousJobs().size() > 0 && p2.getPreviousJobs().size() > 0) {
            for (Date job1 : p1.getPreviousJobs().keys()) {
                for (Date job2 : p2.getPreviousJobs().keys()) {
                    if (p1.getPreviousJobs().get(job1).getCompany().equals(p2.getPreviousJobs().get(job2).getCompany())) {
                        value++;
                    }
                }
            }
        }
        return value;
    }
}
