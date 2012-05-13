package com.vgs.greyhound.model.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;

import com.vgs.greyhound.model.dao.SecurityDao;
import com.vgs.greyhound.model.exception.ModelException;

public class SecurityDaoDerbyHibernateImpl implements SecurityDao {

	private SessionFactory sessionFactory;
	private final static Log logger = LogFactory
			.getLog(SecurityDaoDerbyHibernateImpl.class);

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void backupDatabase(final String location) throws ModelException {
		try {
			logger.debug("BACKING UP DATABASE");
			Work jdbcWork = new Work() {

				@Override
				public void execute(Connection conn) throws SQLException {
					String sqlstmt = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
					CallableStatement cs = conn.prepareCall(sqlstmt);
					cs.setString(1, location);
					cs.execute();
					cs.close();
				}
			};
			sessionFactory.getCurrentSession().doWork(jdbcWork);
		} catch (Exception e) {
			logger.error("Error backing up database", e);
			throw new ModelException(e);
		}
	}

}
