package com.wavelabs.test.builder;

import com.wavelabs.model.User;
import com.wavelabs.model.UserAdvertIntrested;

public class UserIntrestAdvertBuilder {

	public static UserAdvertIntrested userAdvertIntrestBuild() {

		return new UserAdvertIntrested(1, new User(1, "Nutana", "N@gmail.com", "9494458679"),
				AdvertisementBuilder.advertisementBuild());

	}
}
