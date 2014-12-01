package com.tfsinc.ilabs.mdx.builder.dimension;

/**
 * Specifies whether to use cross join or not.
 * @author siddharth.s
 */
public interface DimensionBuilderSetCrossJoin {

	/**
	 * @param isCrossJoin Whether to use a cross join query.
	 * @return Next step in dimension builder.
	 */
	DimensionBuilderSetAxis setCrossJoin(final boolean isCrossJoin);

}
