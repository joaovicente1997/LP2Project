package edu.ufp.esocial.api.service;

import java.util.List;

import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.util.filter.CompetenceFilter;

public interface CompetenceService extends GenericService<String, Competence> {

	public List<Competence> filter(List<Competence> list, CompetenceFilter filter);

    void deleteAll();
}
