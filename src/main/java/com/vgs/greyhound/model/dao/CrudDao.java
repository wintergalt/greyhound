package com.vgs.greyhound.model.dao;

import java.util.List;

import com.vgs.greyhound.model.exception.ModelException;

/**
 * 
 * @author dromoli
 */
public interface CrudDao<T> {

	T retrieve(Integer id) throws ModelException;

	void delete(T t) throws ModelException;

	List<T> findAll() throws ModelException;

	T createOrUpdate(T t) throws ModelException;
}
