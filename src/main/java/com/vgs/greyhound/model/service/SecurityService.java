package com.vgs.greyhound.model.service;

import com.vgs.greyhound.model.exception.ModelException;

public interface SecurityService {

	void backupDatabase(String backupFileLocation) throws ModelException;
}
