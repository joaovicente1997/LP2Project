package edu.ufp.esocial.api.service.impl;

import java.util.List;

import edu.princeton.cs.algs4.RedBlackBST;
import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.model.Person;
import edu.ufp.esocial.api.service.CompetenceService;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.util.filter.CompetenceFilter;

public class CompetenceServiceImpl extends GenericServiceImpl<String, Competence> implements CompetenceService {

	private RedBlackBST<Integer,Person> personST;

	public CompetenceServiceImpl(Database database) {
		super(database.getCompetenceST());
		this.personST = database.getPersonST();
	}

	@Override
	public void create(Competence competence) {
		if (this.databaseST.get(competence.getTitle()) == null) {
			this.databaseST.put(competence.getTitle(), competence);
		}
	}

	@Override
	public void update(Competence competence) {
		if (this.databaseST.get(competence.getTitle()) != null) {
			Competence oldCompetence = this.databaseST.get(competence.getTitle());
			oldCompetence.setAcquiredDate(competence.getAcquiredDate());
			oldCompetence.setLevel(competence.getLevel());
			oldCompetence.setDescription(competence.getDescription());
		}
	}

	@Override
	public void delete(String key) {
		this.personST.keys().forEach(personId -> this.personST.get(personId).getCompetences().delete(key));
		this.databaseST.delete(key);
	}

	@Override
	public List<Competence> filter(List<Competence> list, CompetenceFilter filter) {
		return CompetenceFilter.filter(list, filter);
	}

	@Override
	public void deleteAll() {
		this.getAll().forEach(competence -> this.delete(competence.getTitle()));
	}
}
