package com.tfsinc.ilabs.mdx.builder.dimension;

/**
 * Sets the axis ordinal for the dimension.
 * @author siddharth.s
 */
public interface DimensionBuilderSetAxis {

	/**
	 * @param index Axis ordinal for the dimension.
	 * @return Next step in dimension builder.
	 */
	DimensionBuilderAddMember setAxis(final int axisIndex);

}
