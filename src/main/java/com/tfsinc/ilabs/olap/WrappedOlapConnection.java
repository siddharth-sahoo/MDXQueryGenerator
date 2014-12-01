package com.tfsinc.ilabs.olap;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.olap4j.OlapConnection;

import com.awesome.pro.pool.WrappedResource;

/**
 * Wrapper class for OLAP connection object to conform to custom
 * object pool interfaces.
 * @author siddharth.s
 */
public final class WrappedOlapConnection implements WrappedResource<OlapConnection> {

	/**
	 * OLAP connection which is wrapped.
	 */
	private final OlapConnection connection;

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			WrappedOlapConnection.class);

	// Constructor.
	public WrappedOlapConnection(final OlapConnection olapConnection) {
		connection = olapConnection;
	}

	/* (non-Javadoc)
	 * @see com.awesome.pro.pool.WrappedResource#close()
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			LOGGER.error("Error in closing OLAP connection.");
			System.exit(1);
		}
	}

	/* (non-Javadoc)
	 * @see com.awesome.pro.pool.WrappedResource#isClosed()
	 */
	public boolean isClosed() {
		try {
			return connection.isClosed();
		} catch (SQLException e) {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.awesome.pro.pool.WrappedResource#getResource()
	 */
	public OlapConnection getResource() {
		return connection;
	}

}
