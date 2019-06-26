package edu.ufp.esocial.api.model;

import edu.ufp.esocial.api.util.Point;

public abstract class Entity {

	private Integer id;

	private String name;

	private Point location;

	public Entity(String name, Point location) {
        this.id = 1;
		this.name = name;
		this.location = location;
	}

	public Entity(Integer id, String name, Point location) {
		this.id = id;
		this.name = name;
		this.location = location;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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
}
