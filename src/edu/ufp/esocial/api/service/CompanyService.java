package edu.ufp.esocial.api.service;

import java.util.List;

import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.util.filter.CompanyFilter;

public interface CompanyService extends GenericService<Integer, Company> {

    public List<Company> filter(List<Company> list, CompanyFilter filter);

    public void deleteAll();
}
