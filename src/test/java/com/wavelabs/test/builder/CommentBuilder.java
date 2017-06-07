package com.wavelabs.test.builder;

import com.wavelabs.model.Comment;
import com.wavelabs.model.User;

public class CommentBuilder {

	public static Comment buildComment() {

		Comment comment = new Comment();
		comment.setId(1);
		comment.setAdvertisement(AdvertisementBuilder.advertisementBuild());
		comment.setUser(new User(2, "Nutana", "N@gmail.com", "9133213559"));
		comment.setText("This is sample comment");
		comment.setTimeStamp(null);
		return comment;
	}
}
