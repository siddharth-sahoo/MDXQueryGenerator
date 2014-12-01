package com.tfsinc.ilabs.mdx.builder.dimension;

import com.tfsinc.ilabs.olap.references.MDXReferences;

public enum DimensionSort {

	/**
	 * In ascending order considering the dimension hierarchy.
	 */
	ASCENDING,

	/**
	 * In descending order considering dimension hierarchy.
	 */
	DESCENDING,

	/**
	 * In ascending order with respect to custom numerical values.
	 */
	CUSTOM_ASCENDING,

	/**
	 * In descending order with respect to custom numerical values.
	 */
	CUSTOM_DESCENDING;

	/**
	 * @param sort Sort order type.
	 * @return MDX query equivalent.
	 */
	public static final String getMDXEquivalent(final DimensionSort sort) {
		switch (sort) {
			case ASCENDING :
				return MDXReferences.ORDER_ASCENDING;
			case DESCENDING :
				return MDXReferences.ORDER_DESCENDING;
			case CUSTOM_ASCENDING :
				return MDXReferences.ORDER_CUSTOM_ASCENDING;
			case CUSTOM_DESCENDING :
				return MDXReferences.ORDER_CUSTOM_DESCENDING;
			default :
				throw new IllegalStateException("Unhandled sort order type: "
						+ sort);
		}
	}

}
