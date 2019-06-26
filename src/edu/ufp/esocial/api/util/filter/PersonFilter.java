package edu.ufp.esocial.api.util.filter;

import java.util.ArrayList;
import java.util.List;

import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.util.Point;

public class PersonFilter {

	private String name;

	private Point location;

	private Integer minAge;

	private Integer maxAge;

	private List<String> competences;

	public PersonFilter() {
		/** **/
	}

	public static List<Person> filter(List<Person> list, PersonFilter filter) {
		List<Person> filteredList = new ArrayList<>();
		list.forEach(person -> {
			if (filter.getName() != null && !filter.getName().isEmpty() && !person.getName().contains(filter.getName())) {
				return;
			}
			if (filter.getLocation() != null && filter.getLocation() != person.getLocation()) {
				return;
			}
			if (filter.getMinAge() != null && filter.getMinAge() < person.getAge()) {
				return;
			}
			if (filter.getMaxAge() != null && filter.getMaxAge() > person.getAge()) {
				return;
			}
			if (filter.getCompetences() != null) {
				for (String competence : filter.getCompetences()) {
					if (person.getCompetences().get(competence) == null) {
						return;
					}
				}
			}
			filteredList.add(person);
		});
		return filteredList;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Point getLocation() {
		return this.location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Integer getMinAge() {
		return this.minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return this.maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public List<String> getCompetences() {
		return this.competences;
	}

	public void setCompetences(List<String> competences) {
		this.competences = competences;
	}

}
