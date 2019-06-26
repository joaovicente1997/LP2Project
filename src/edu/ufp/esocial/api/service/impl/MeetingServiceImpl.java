package edu.ufp.esocial.api.service.impl;

import java.util.List;

import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.service.Database;
import edu.ufp.esocial.api.service.MeetingService;
import edu.ufp.esocial.api.util.filter.MeetingFilter;

public class MeetingServiceImpl extends GenericServiceImpl<Integer, Meeting> implements MeetingService {

	private Integer increment = 0;

	public MeetingServiceImpl(Database database) {
		super(database.getMeetingST());
	}

	@Override
	public void create(Meeting meeting) {
		this.databaseST.put(this.increment, meeting);
		meeting.setId(this.increment);
		this.increment++;
	}

	@Override
	public void update(Meeting meeting) {
		Meeting oldMeeting = this.databaseST.get(meeting.getId());
		oldMeeting.setCreator(meeting.getCreator());
		oldMeeting.setEndDate(meeting.getEndDate());
		oldMeeting.setPrice(meeting.getPrice());
		oldMeeting.setStartDate(meeting.getStartDate());
	}

	@Override
	public void delete(Integer key) {
		Meeting meeting = this.databaseST.get(key);
		meeting.getPersons().keys().forEach(personId ->
			meeting.getPersons().get(personId).getMeetingsGone().delete(meeting.getStartDate()));
		meeting.getCompanies().keys().forEach(companyId -> {
			meeting.getCompanies().get(companyId).getMeetingsGone().delete(key);
			meeting.getCompanies().get(companyId).getMeetingsCreated().delete(key);
		});
		this.databaseST.delete(key);
	}

	@Override
	public List<Meeting> filter(List<Meeting> list, MeetingFilter filter) {
		return MeetingFilter.filter(list, filter);
	}

	@Override
	public void deleteAll() {
		this.increment = 0;
		this.getAll().forEach(meeting -> this.delete(meeting.getId()));
	}
}
