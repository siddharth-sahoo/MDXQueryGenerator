package com.tfsinc.ilabs.mdx.builder.dimension;

/**
 * Specifies whether to use the non empty filter.
 * @author siddharth.s
 */
public interface DimensionBuilderNonEmpty {

	/**
	 * @param isNonEmpty Whether to apply the non empty filter.
	 * @return Next step in dimension builder.
	 */
	DimensionBuilderSetCrossJoin setNonEmpty(final boolean isNonEmpty);

}
