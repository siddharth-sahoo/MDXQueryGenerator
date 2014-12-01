package com.tfsinc.ilabs.olap.references;

import org.apache.log4j.Logger;

import com.awesome.pro.utilities.PropertyFileUtility;

/**
 * This class contains the configurations related to OLAP cube.
 * @author siddharth.s
 */
public class OlapConfigReferences {

	/**
	 * Configurations read from property file.
	 */
	public static PropertyFileUtility CONFIG = null;

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			OlapConfigReferences.class);

	/**
	 * Reads the specified configuration file and keeps it in memory.
	 * @param configFile Name of the configuration file to read.
	 */
	public static final void initialize(final String configFile) {
		if (CONFIG == null) {
			synchronized (OlapConfigReferences.class) {
				if (CONFIG == null) {
					if (configFile == null || configFile.length() == 0) {
						LOGGER.error("Configuration file name specified"
								+ " is null or blank. Exiting.");
						System.exit(1);
					}

					CONFIG = new PropertyFileUtility(configFile);
					LOGGER.info("Populated base configurations for OLAP cube.");
					
					loadDriver();
				}
			}
		}
	}

	/**
	 * Loads the specified OLAP driver. Exits if the class is not found.
	 */
	private static final void loadDriver() {
		try {
			Class.forName(CONFIG.getStringValue(
					PROPERTY_OLAP_DRIVER_CLASS,
					DEFAULT_OLAP_DRIVER_CLASS));
		} catch (ClassNotFoundException e) {
			LOGGER.error("Specified driver class is not found. Exiting.", e);
			System.exit(1);
		}
	}

	// Configuration properties
	public static final String PROPERTY_OLAP_DRIVER_CLASS = "OlapDriverClass";
	public static final String PROPERTY_OLAP_CONNECTION_URL = "OlapConnectionUrl";
	public static final String PROPERTY_VALIDATE_QUERY = "ValidateQuery";
	public static final String PROPERTY_DISPLAY_QUERY = "DisplayQuery";
	public static final String PROPERTY_MEASURE_AXIS = "MeasureAxis";

	// Default configurations
	public static final String DEFAULT_OLAP_DRIVER_CLASS = "org.olap4j.driver.xmla.XmlaOlap4jDriver";
	public static final String DEFAULT_OLAP_CONNECTION_URL = "jdbc:xmla:Server=http://"
			+ "olap01.qa.custportal.pool.sv2.247-inc.net/olap/msmdpump.dll";
	public static final boolean DEFAULT_VALIDATE_QUERY = true;
	public static final boolean DEFAULT_DISPLAY_QUERY = true;
	public static final String DEFAULT_MEASURE_AXIS = "COLUMNS";

	// Standard axes.
	private static final String AXIS_COLUMNS = "COLUMNS";
	private static final String AXIS_ROWS = "ROWS";
	private static final int AXIS_INDEX_COLUMNS = 0;
	private static final int AXIS_INDEX_ROWS = 1;

	/**
	 * @param axis Standard axis name.
	 * @return Corresponding axis index.
	 */
	public static final int getAxisIndex(final String axis) {
		switch (axis.toUpperCase()) {
			case AXIS_COLUMNS :
				return AXIS_INDEX_COLUMNS;
			case AXIS_ROWS :
				return AXIS_INDEX_ROWS;
			default :
				throw new IllegalArgumentException("Unknown axis: " + axis);
		}
	}

	/**
	 * @return Measure axis from configuration file.
	 */
	public static final int getMeasureAxisIndex() {
		return getAxisIndex(CONFIG.getStringValue(
				PROPERTY_MEASURE_AXIS, DEFAULT_MEASURE_AXIS));
	}

	/**
	 * @return Whether to validate the MDX query generated.
	 */
	public static final boolean shouldValidateMdx() {
		return CONFIG.getBooleanValue(PROPERTY_VALIDATE_QUERY,
				DEFAULT_VALIDATE_QUERY);
	}

	/**
	 * @return Whether to display the generated MDX query.
	 */
	public static final boolean shouldDisplayMdx() {
		return CONFIG.getBooleanValue(PROPERTY_DISPLAY_QUERY,
				DEFAULT_DISPLAY_QUERY);
	}

}
