package edu.ufp.esocial.api.util.filter;

import edu.ufp.esocial.api.model.Company;
import edu.ufp.esocial.api.model.Meeting;
import edu.ufp.esocial.api.util.Date;
import edu.ufp.esocial.api.util.Point;

import java.util.ArrayList;
import java.util.List;

public class MeetingFilter {
	private String name;
    private Point location;
    private Date startDate;
    private Date endDate;
    private Float minPrice;
    private Float maxPrice;
    private Company creator;
    private ArrayList<String> areasAborded = new ArrayList<>();

    public MeetingFilter() {

    }

    public static List<Meeting> filter(List<Meeting> list, MeetingFilter filter) {
        List<Meeting> filteredList = new ArrayList<>();
        if (filter == null) {
            return filteredList;
        }
        list.forEach(meeting -> {
        	if (filter.getName() != null && !meeting.getName().contains(filter.getName())) {
        		return;
        	}
            if (filter.getLocation() != null && filter.getLocation() != meeting.getLocation()) {
                return;
            }
            if (filter.getMinPrice() != null && filter.getMinPrice() > meeting.getPrice()) {
                return;
            }
            if (filter.getMaxPrice() != null && filter.getMaxPrice() < meeting.getPrice()) {
                return;
            }
            if (filter.getCreator() != null && filter.getCreator().getId().compareTo(meeting.getCreator().getId()) == 0) {
                return;
            }
            if (filter.getStartDate() != null && filter.getStartDate().compareTo(meeting.getStartDate()) <= 0) {
                return;
            }
            if (filter.getEndDate() != null && filter.getEndDate().compareTo(meeting.getEndDate()) >= 0) {
                return;
            }
            if (filter.getAreasAborded() != null) {
                for (String area : filter.getAreasAborded()) {
                    if (meeting.getAreasAborded().contains(area)) {
                        return;
                    }
                }
            }
            filteredList.add(meeting);
        });
        return filteredList;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Float getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(Float minPrice) {
        this.minPrice = minPrice;
    }

    public Float getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(Float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Company getCreator() {
        return this.creator;
    }

    public void setCreator(Company creator) {
        this.creator = creator;
    }

    public ArrayList<String> getAreasAborded() {
        return this.areasAborded;
    }

    public void setAreasAborded(ArrayList<String> areasAborded) {
        this.areasAborded = areasAborded;
    }


    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
