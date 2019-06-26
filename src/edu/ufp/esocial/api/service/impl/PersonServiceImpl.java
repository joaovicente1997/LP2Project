package edu.ufp.esocial.api.service.impl;

import java.util.List;

import edu.ufp.esocial.api.model.Job;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.service.PersonService;
import edu.ufp.esocial.api.util.filter.PersonFilter;

public class PersonServiceImpl extends GenericServiceImpl<Integer, Person> implements PersonService {

	private Integer increment = 0;

	public PersonServiceImpl(Database database) {
		super(database.getPersonST());
	}

	@Override
	public void create(Person person) {
		this.databaseST.put(this.increment, person);
		person.setId(this.increment);
		this.increment++;
	}

	@Override
	public void update(Person person) {
		Person oldPerson = this.databaseST.get(person.getId());
		oldPerson.setName(person.getName());
		oldPerson.setLocation(person.getLocation());
	}

	@Override
	public void delete(Integer key) {
		Person person = this.databaseST.get(key);
		person.getPreviousJobs().keys().forEach(jobDate ->
			person.getPreviousJobs().get(jobDate).getCompany().getPreviousJobST().delete(jobDate));
		Job currentJob = person.getCurrentJob();
		if (currentJob != null) {
			currentJob.getCompany().getCurrentJobST().delete(currentJob.getStartDate());
		}
		this.databaseST.delete(key);
	}

	@Override
	public List<Person> filter(List<Person> list, PersonFilter filter) {
		return PersonFilter.filter(list, filter);
	}

	@Override
	public void deleteAll() {
		this.increment = 0;
		this.getAll().forEach(person -> this.delete(person.getId()));
	}
}
