package edu.ufp.esocial.api.service;

import java.util.List;

import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.util.filter.MeetingFilter;

public interface MeetingService extends GenericService<Integer, Meeting> {

	public List<Meeting> filter(List<Meeting> list, MeetingFilter filter);

    void deleteAll();
}