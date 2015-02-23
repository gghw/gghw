package com.ff.gghw.daos;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDao {
    public BaseDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	public void persistObject(Object entity) {
		getSession().persist(entity);
	}

	public void updateObject(Object entity) {
		getSession().update(entity);
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	private SessionFactory sessionFactory;
}

