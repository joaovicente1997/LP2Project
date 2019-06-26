package edu.ufp.esocial.api.util.filter;

import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.util.Point;

import java.util.ArrayList;
import java.util.List;

public class CompanyFilter {

	private String name;

    private Point location;

	public static List<Company> filter(List<Company> list, CompanyFilter filter) {
		List<Company> filteredList = new ArrayList<>();
		if (filter == null) {
            return filteredList;
        }
		list.forEach(company -> {
			if(filter.getName() != null && !filter.getName().isEmpty() && !company.getName().contains(filter.getName())) {
				return;
			}
            if (filter.getLocation() != null && filter.getLocation() != company.getLocation()) {
                return;
            }
			filteredList.add(company);
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
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
