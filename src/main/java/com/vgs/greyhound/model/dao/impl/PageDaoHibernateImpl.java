package com.vgs.greyhound.model.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.vgs.greyhound.model.dao.PageDao;
import com.vgs.greyhound.model.domain.Page;
import com.vgs.greyhound.model.exception.ModelException;

public class PageDaoHibernateImpl implements PageDao {

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void delete(Page p) throws ModelException {
		this.sessionFactory.getCurrentSession().delete(p);
	}

	@Override
	public List<Page> findAll() throws ModelException {
		Query q = this.sessionFactory.getCurrentSession().createQuery(
				"from Page order by parent.pageId, title");
		q.setReadOnly(true);
		return q.list();
	}

	@Override
	public Page retrieve(Integer id) throws ModelException {
		return (Page) this.sessionFactory.getCurrentSession().load(Page.class,
				id);
	}

	public Page createOrUpdate(Page p) throws ModelException {
		this.sessionFactory.getCurrentSession().saveOrUpdate(p);
		return p;
	}

	@Override
	public List<Page> retrieveAllCategories() {
		Query q = this.sessionFactory.getCurrentSession().createQuery(
				"from Page where category = true");
		q.setReadOnly(true);
		return q.list();
	}

}
