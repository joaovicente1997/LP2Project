package edu.ufp.esocial.api.util.filter;

import java.util.ArrayList;
import java.util.List;

import edu.ufp.esocial.api.model.Competence;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Util.CompetenceLevel;

// ADICIONAR FILTRO POR NOME DE COMPETENCIA

public class CompetenceFilter {

	private String area;

	private Date minDate;

	private Date maxDate;

	private CompetenceLevel minLevel;

	private CompetenceLevel maxLevel;

	public CompetenceFilter() {
		/** **/
	}

	public static List<Competence> filter(List<Competence> list, CompetenceFilter filterModel) {
		List<Competence> filteredList = new ArrayList<>();
		if (!(filterModel instanceof CompetenceFilter)) {
			return filteredList;
		}
		CompetenceFilter filter = filterModel;
		list.forEach(competence -> {
			if (filter.getArea() != null && !filter.getArea().isEmpty() && !competence.getArea().contains(filter.getArea())) {
				return;
			}
			if (filter.getMinDate() != null && filter.getMinDate().compareTo(competence.getAcquiredDate()) < 0) {
				return;
			}
			if (filter.getMaxDate() != null && filter.getMaxDate().compareTo(competence.getAcquiredDate()) > 0) {
				return;
			}
			if (filter.getMinLevel() != null && filter.getMinLevel().ordinal() < competence.getLevel().ordinal()) {
				return;
			}
			if (filter.getMaxLevel() != null && filter.getMaxLevel().ordinal() > competence.getLevel().ordinal()) {
				return;
			}
			filteredList.add(competence);
		});
		return filteredList;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getMinDate() {
		return this.minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public CompetenceLevel getMinLevel() {
		return this.minLevel;
	}

	public void setMinLevel(CompetenceLevel minLevel) {
		this.minLevel = minLevel;
	}

	public CompetenceLevel getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(CompetenceLevel maxLevel) {
		this.maxLevel = maxLevel;
	}

}
