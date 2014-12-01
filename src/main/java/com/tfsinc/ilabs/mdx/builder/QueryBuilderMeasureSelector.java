package com.tfsinc.ilabs.mdx.builder;

import java.util.List;

/**
 * Second step of query building.
 * This specifies measure selection.
 * @author siddharth.s
 */
public interface QueryBuilderMeasureSelector {

	/**
	 * @param names Names of measures to be added.
	 * e.g. [Internet Sales Amount], [Internet Order Quantity] etc.
	 */
	QueryBuilderDimensionSelector setMeasures(final List<String> names);

}
