package edu.ufp.esocial.api.service.impl;

import java.util.List;

import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Job;
import edu.ufp.esocial.api.service.CompanyService;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.util.filter.CompanyFilter;

public class CompanyServiceImpl extends GenericServiceImpl<Integer, Company> implements CompanyService {

	private Integer increment = 0;

	public CompanyServiceImpl(Database database) {
		super(database.getCompanyST());
	}

	@Override
	public void create(Company company) {
		this.databaseST.put(this.increment, company);
		company.setId(this.increment);
		this.increment++;
	}

	@Override
	public void update(Company company) {
		Company oldCompany = this.databaseST.get(company.getId());
		oldCompany.setLocation(company.getLocation());
		oldCompany.setName(company.getName());
	}

	@Override
	public void delete(Integer key) {
		Company company = this.databaseST.get(key);
		company.getMeetingsGone().keys().forEach(meetingId ->
			company.getMeetingsGone().get(meetingId).getCompanies().delete(key));
		company.getMeetingsCreated().keys().forEach(meetingId ->
			company.getMeetingsCreated().get(meetingId).getCompanies().delete(key));
		company.getPreviousJobST().keys().forEach(jobId -> {
			Job previousJob = company.getPreviousJobST().get(jobId);
			previousJob.getPerson().getPreviousJobs().delete(previousJob.getStartDate());
		});
		company.getCurrentJobST().keys().forEach(jobId -> {
			Job currentJob = company.getCurrentJobST().get(jobId);
			currentJob.getPerson().setCurrentJob(null);
		});
		this.databaseST.delete(key);
	}

	@Override
	public List<Company> filter(List<Company> list, CompanyFilter filter) {
		return CompanyFilter.filter(list, filter);
	}

	@Override
	public void deleteAll() {
		this.increment = 0;
		this.getAll().forEach(company -> this.delete(company.getId()));
	}
}
