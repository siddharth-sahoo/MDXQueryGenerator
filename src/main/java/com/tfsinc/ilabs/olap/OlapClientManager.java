package com.tfsinc.ilabs.olap;

import org.apache.log4j.Logger;
import org.olap4j.CellSet;
import org.olap4j.OlapException;
import org.olap4j.mdx.SelectNode;

import com.tfsinc.ilabs.olap.references.OlapConfigReferences;

/**
 * Base class for MDX query generator.
 * @author siddharth.s
 */
public class OlapClientManager {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			OlapClientManager.class);

	/**
	 * Initializes everything.
	 * @param configFile Name and path of configuration file.
	 */
	public static final void initialize(final String configFile) {
		OlapConfigReferences.initialize(configFile);
		OlapConnectionPoolManager.initialize(configFile);
	}

	/**
	 * Shuts down everything.
	 * @param forceClose Whether to close active connections forcibly.
	 */
	public static final void shutdown(final boolean forceClose) {
		OlapConnectionPoolManager.shutdown(forceClose);
	}

	/**
	 * @return An OLAP connection from the POOL.
	 */
	public static WrappedOlapConnection getConnection() {
		return OlapConnectionPoolManager.getConnection();
	}

	/**
	 * @param connection OLAP connection to be returned to the pool.
	 * @return Whether the connection was returned successfully.
	 */
	public static final boolean returnConnection(
			final WrappedOlapConnection connection) {
		return OlapConnectionPoolManager.returnConnection(connection);
	}

	/**
	 * @param query String MDX query to be executed.
	 * @return Cell set of the query.
	 */
	public static final CellSet executeMdxQuery(final String query) {
		final WrappedOlapConnection connection = getConnection();
		try {
			final long start = System.currentTimeMillis();
			final CellSet cellSet = connection.getResource()
					.createStatement().executeOlapQuery(query);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Query execution time: " + (System.currentTimeMillis() - start) + " ms.");
			}

			returnConnection(connection);
			return cellSet;
		} catch (OlapException e) {
			LOGGER.error("Unable to execute query.", e);
			returnConnection(connection);
			return null;
		}
	}

	/**
	 * @param query Parsed select MDX query.
	 * @return Cell set of the query.
	 */
	public static final CellSet executeMdxQuery(final SelectNode query) {
		final WrappedOlapConnection connection = getConnection();
		try {
			final long start = System.currentTimeMillis();
			final CellSet cellSet = connection.getResource()
					.createStatement().executeOlapQuery(query);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Query execution time: " + (System.currentTimeMillis() - start) + " ms.");
			}
			returnConnection(connection);
			return cellSet;
		} catch (OlapException e) {
			LOGGER.error("Unable to execute query.", e);
			returnConnection(connection);
			return null;
		}
	}

}
