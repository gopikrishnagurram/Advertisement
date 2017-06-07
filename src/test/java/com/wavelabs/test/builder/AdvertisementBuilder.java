package com.wavelabs.test.builder;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.User;
import com.wavelabs.model.enums.AdvertisementType;

public class AdvertisementBuilder {

	public static Advertisement advertisementBuild() {

		return new Advertisement(1, "Gopi krishna", AdvertisementType.BUSINESS, "Something",
				new User(1, "Gopi", "g@gmail.com", "9032118864"), "KMM");
	}

}
