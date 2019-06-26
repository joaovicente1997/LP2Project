package edu.ufp.esocial.api.service;

import java.util.List;

import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.util.filter.PersonFilter;

public interface PersonService extends GenericService<Integer, Person> {

	public List<Person> filter(List<Person> list, PersonFilter filter);

    void deleteAll();
}
