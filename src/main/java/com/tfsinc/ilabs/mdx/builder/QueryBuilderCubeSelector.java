package com.tfsinc.ilabs.mdx.builder;

/**
 * First step of query building.
 * This specifies cube selection.
 * @author siddharth.s
 */
public interface QueryBuilderCubeSelector {

	/**
	 * @param name Name of the cube to be selected.
	 * e.g. cube_test
	 */
	QueryBuilderMeasureSelector setCube(final String name);

}
