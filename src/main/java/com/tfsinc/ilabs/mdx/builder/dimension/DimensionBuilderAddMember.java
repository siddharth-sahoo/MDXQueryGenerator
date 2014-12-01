package com.tfsinc.ilabs.mdx.builder.dimension;

/**
 * Adds members to the dimension.
 * @author siddharth.s
 */
public interface DimensionBuilderAddMember {

	/**
	 * @param type Type of sorting to use.
	 * @param customEntity Custom numerical values to sort on. This can be
	 * passed as null if standard sorting is used, i.e. considering the
	 * dimension hierarchies. If type is custom sorting and this field
	 * is null, it will throw an error.
	 * @return Next step in dimension builder.
	 */
	DimensionBuilderAddMember setSortOrder(final DimensionSort type,
			final String customEntity);

	/**
	 * @param members Members to be added.
	 * @return Next step in dimension builder.
	 */
	DimensionBuilderAddMember addMembers(final String... members);

	/**
	 * @return Dimension expression on the axis.
	 */
	String build();

}
