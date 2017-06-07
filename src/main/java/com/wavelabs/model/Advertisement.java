package com.wavelabs.model;

import com.wavelabs.model.enums.AdvertisementType;

/**
 * @author gopikrishnag
 *
 */
public class Advertisement {

	private int id;
	private String name;
	private AdvertisementType type;
	private String description;
	private User user;
	private String location;

	public Advertisement(int id, String name, AdvertisementType type, String description, User user, String location) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.user = user;
		this.location = location;
	}

	public Advertisement() {

	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AdvertisementType getType() {
		return type;
	}

	public void setType(AdvertisementType type) {
		this.type = type;
	}

}
