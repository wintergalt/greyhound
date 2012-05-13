package com.vgs.greyhound.model.dao;

import com.vgs.greyhound.model.exception.ModelException;

public interface SecurityDao {

	void backupDatabase(String location) throws ModelException;
}
