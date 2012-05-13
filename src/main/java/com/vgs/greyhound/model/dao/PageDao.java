package com.vgs.greyhound.model.dao;

import java.util.List;

import com.vgs.greyhound.model.domain.Page;

public interface PageDao extends CrudDao<Page> {

	List<Page> retrieveAllCategories();

}
