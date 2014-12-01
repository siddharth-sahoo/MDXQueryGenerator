package com.tfsinc.ilabs.olap;

import org.apache.log4j.Logger;

import com.awesome.pro.pool.IObjectPool;
import com.awesome.pro.pool.ObjectPool;

/**
 * Manages OLAP connection pooling.
 * @author siddharth.s
 */
final class OlapConnectionPoolManager {

	/**
	 * Connection pool instance.
	 */
	private static IObjectPool<WrappedOlapConnection> POOL = null;

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			OlapConnectionPoolManager.class);
	
	/**
	 * Initializes the connection pool.
	 * @param configFile Path and name of configuration file to be read.
	 */
	static final void initialize(final String configFile) {
		if (POOL == null) {
			synchronized (OlapConnectionPoolManager.class) {
				if (POOL == null) {
					POOL = new ObjectPool<WrappedOlapConnection>(configFile,
						new AcquireOlapConnection());
					LOGGER.info("Initialized OLAP connection pool.");
				}
			}
		}
	}

	/**
	 * Shuts down the connection pool.
	 * @param forceClose Whether to forcibly close active connections.
	 */
	static final void shutdown(final boolean forceClose) {
		if (POOL != null) {
			synchronized (OlapConnectionPoolManager.class) {
				if (POOL != null) {
					POOL.closePool(forceClose);
					LOGGER.info("OLAP connection pool has been shut down.");
				}
			}
		}
	}

	/**
	 * @return An OLAP connection from the POOL.
	 */
	static final WrappedOlapConnection getConnection() {
		return POOL.checkOutResource();
	}

	/**
	 * @param connection OLAP connection to be returned to the pool.
	 * @return Whether the connection was returned successfully.
	 */
	static final boolean returnConnection(final WrappedOlapConnection connection) {
		return POOL.checkInResource(connection);
	}

}
