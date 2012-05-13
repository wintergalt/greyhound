package com.vgs.greyhound.model.service;

import java.util.List;

import com.vgs.greyhound.model.domain.Page;
import com.vgs.greyhound.model.exception.ModelException;

public interface PageService {

	Page createOrUpdatePage(Page p) throws ModelException;

	Page retrievePage(Integer pageId) throws ModelException;

	Page updatePage(Page p) throws ModelException;

	void deletePage(Page p) throws ModelException;

	List<Page> retrieveAllPages() throws ModelException;

	List<Page> retrievePagesInNamespace(String namespace, boolean recursive)
			throws ModelException;

	List<Page> retrieveAllCategories() throws ModelException;
}
