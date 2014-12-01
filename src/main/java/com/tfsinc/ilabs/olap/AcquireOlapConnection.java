package com.tfsinc.ilabs.olap;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.olap4j.OlapConnection;

import com.awesome.pro.pool.AcquireResource;
import com.tfsinc.ilabs.olap.references.OlapConfigReferences;

/**
 * Specifies the procedure to acquire a new OLAP connection.
 * @author siddharth.s
 */
final class AcquireOlapConnection implements AcquireResource<WrappedOlapConnection> {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			AcquireOlapConnection.class);

	/* (non-Javadoc)
	 * @see com.awesome.pro.pool.AcquireResource#acquireResource()
	 */
	public WrappedOlapConnection acquireResource() {
		try {
			final OlapConnection connection = DriverManager.getConnection(
					OlapConfigReferences.CONFIG.getStringValue(
							OlapConfigReferences.PROPERTY_OLAP_CONNECTION_URL,
							OlapConfigReferences.DEFAULT_OLAP_CONNECTION_URL))
							.unwrap(OlapConnection.class);
			return new WrappedOlapConnection(
					connection.unwrap(OlapConnection.class));
		} catch (SQLException e) {
			LOGGER.error("Unable to acquire a connection.", e);
			return null;
		}
	}

}
