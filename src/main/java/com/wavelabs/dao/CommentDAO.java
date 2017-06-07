package com.wavelabs.dao;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.wavelabs.model.Advertisement;
import com.wavelabs.model.Comment;
import com.wavelabs.utility.Helper;

@Component
public class CommentDAO {

	private static final Logger LOGGER = Logger.getLogger(CommentDAO.class);

	public Boolean persistComment(Comment comment) {
		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(comment);
			tx.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		} finally {
			session.close();
		}
	}

	public Comment[] getAdvertisementComments(int id) {
		Session session = Helper.getSession();
		Comment[] arrayOfComments = {};
		try {
			Advertisement advertisement = (Advertisement) session.get(Advertisement.class, id);
			String hql = "from " + Comment.class.getName() + " where advertisement=:advertisement";
			Query query = session.createQuery(hql);
			query.setParameter("advertisement", advertisement);
			List<Comment> listOfComments = query.list();
			arrayOfComments = new Comment[listOfComments.size()];
			int j = 0;
			for (Comment comment : listOfComments) {
				comment.getAdvertisement().setUser(null);
				arrayOfComments[j++] = comment;
			}
			return arrayOfComments;
		} catch (Exception e) {
			LOGGER.error(e);
			return arrayOfComments;
		} finally {
			session.close();
		}
	}

	public Comment updateComment(Comment comment) {

		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(comment);
			tx.commit();
			return comment;
		} catch (Exception e) {
			LOGGER.error(e);
			return null;
		} finally {
			session.close();
		}

	}

	public Boolean deleteComment(int id) {

		Session session = Helper.getSession();
		Transaction tx = session.beginTransaction();
		try {
			Comment comment = (Comment) session.get(Comment.class, id);
			session.delete(comment);
			tx.commit();
			return true;
		} catch (Exception e) {
			LOGGER.error(e);
			return false;
		} finally {
			session.close();
		}
	}
}
