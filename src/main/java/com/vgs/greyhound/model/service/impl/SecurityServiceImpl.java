package com.vgs.greyhound.model.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.vgs.greyhound.model.dao.SecurityDao;
import com.vgs.greyhound.model.exception.ModelException;
import com.vgs.greyhound.model.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {

	private SecurityDao securityDao;
	private final static Log logger = LogFactory
			.getLog(SecurityServiceImpl.class);

	@Override
	public void backupDatabase(String backupFileLocation) throws ModelException {
		File backupFile = new File(backupFileLocation);
		try {
			backupFile.createNewFile();
		} catch (IOException ioe) {
			logger.error("Error backing up database", ioe);
			throw new ModelException(ioe);
		}
		securityDao.backupDatabase(backupFileLocation);
	}

	public SecurityDao getSecurityDao() {
		return securityDao;
	}

	public void setSecurityDao(SecurityDao securityDao) {
		this.securityDao = securityDao;
	}

}
