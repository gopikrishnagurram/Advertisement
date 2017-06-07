package com.wavelabs.model;

public class UserAdvertIntrested {

	private int id;
	private User user;
	private Advertisement advert;

	public UserAdvertIntrested(int id, User user, Advertisement advert) {

		this.id = id;
		this.user = user;
		this.advert = advert;
	}

	public UserAdvertIntrested() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Advertisement getAdvert() {
		return advert;
	}

	public void setAdvert(Advertisement advert) {
		this.advert = advert;
	}
}
