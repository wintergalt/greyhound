package com.vgs.greyhound.model.facade;

import java.util.List;

import com.vgs.greyhound.model.domain.Page;
import com.vgs.greyhound.model.exception.ModelException;
import com.vgs.greyhound.model.service.PageService;
import com.vgs.greyhound.model.service.SecurityService;

public class GreyhoundFacade {

	private PageService pageService;
	private SecurityService securityService;

	public Page createOrUpdatePage(Page p) throws ModelException {
		return pageService.createOrUpdatePage(p);
	}

	public void deletePage(Page p) throws ModelException {
		pageService.deletePage(p);
	}

	public List<Page> retrieveAllPages() throws ModelException {
		return pageService.retrieveAllPages();
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	public Page retrievePageById(Integer id) throws ModelException {
		return this.pageService.retrievePage(id);
	}

	public List<Page> retrieveAllCategories() throws ModelException {
		return pageService.retrieveAllCategories();
	}

	public void backupDatabase(String backupLocation) throws ModelException {
		securityService.backupDatabase(backupLocation);
	}

}
