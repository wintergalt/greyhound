package com.vgs.greyhound.model.service.impl;

import java.util.List;

import com.vgs.greyhound.model.dao.PageDao;
import com.vgs.greyhound.model.domain.Page;
import com.vgs.greyhound.model.exception.ModelException;
import com.vgs.greyhound.model.service.PageService;

public class PageServiceImpl implements PageService {

	private PageDao pageDao;

	@Override
	public Page createOrUpdatePage(Page p) throws ModelException {
		if (p == null) {
			throw new IllegalArgumentException("Page cannot be null");
		}
		p = pageDao.createOrUpdate(p);
		p.setDirty(false);
		return p;
	}

	@Override
	public void deletePage(Page p) throws ModelException {
		pageDao.delete(p);
	}

	@Override
	public List<Page> retrieveAllPages() throws ModelException {
		return pageDao.findAll();
	}

	@Override
	public Page retrievePage(Integer pageId) throws ModelException {
		return pageDao.retrieve(pageId);
	}

	@Override
	public List<Page> retrievePagesInNamespace(String namespace,
			boolean recursive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page updatePage(Page p) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageDao getPageDao() {
		return pageDao;
	}

	public void setPageDao(PageDao pageDao) {
		this.pageDao = pageDao;
	}

	@Override
	public List<Page> retrieveAllCategories() throws ModelException {
		return pageDao.retrieveAllCategories();
	}

}
