package com.tfsinc.ilabs.mdx.builder.dimension;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.tfsinc.ilabs.olap.references.MDXReferences;

/**
 * Builds the dimension for an axis.
 * @author siddharth.s
 */
public class DimensionBuilder implements DimensionBuilderNonEmpty,
	DimensionBuilderSetCrossJoin, DimensionBuilderSetAxis,
	DimensionBuilderAddMember {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			DimensionBuilder.class);

	/**
	 * List of members. e.g. [Date].[Calendar].[Month].MEMBERS,
	 * [Product].[Product Categories].[Category].CHILDREN
	 */
	private List<String> members;

	/**
	 * Whether to apply the non empty filter.
	 */
	private boolean isNonEmpty;

	/**
	 * Whether to use a cross join or not.
	 */
	private boolean isCrossJoin;

	/**
	 * Type of sort order.
	 */
	private DimensionSort sortOrder;

	/**
	 * Custom numerical values to sort by. Applicable only for
	 * CUSTOM_ASCENDING & CUSTOM_DESCENDING sort types.
	 */
	private String customSortEntity;

	/**
	 * Axis index for the dimension.
	 */
	private int axis;

	// Private constructor.
	private DimensionBuilder() {
		this.members = new LinkedList<>();
	}

	/**
	 * @return Dimension builder instance.
	 */
	public static DimensionBuilderNonEmpty getDimensionBuilder() {
		return new DimensionBuilder();
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderNonEmpty#setNonEmpty(boolean)
	 */
	@Override
	public DimensionBuilderSetCrossJoin setNonEmpty(final boolean isNonEmpty) {
		this.isNonEmpty = isNonEmpty;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderSetCrossJoin#isCrossJoin(boolean)
	 */
	@Override
	public DimensionBuilderSetAxis setCrossJoin(boolean isCrossJoin) {
		this.isCrossJoin = isCrossJoin;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderSetAxis#setAxis(int)
	 */
	@Override
	public DimensionBuilderAddMember setAxis(final int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Axis can't be negative.");
		}
		this.axis = index;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderAddMember#setSortOrder(com.tfsinc.ilabs.mdx.builder.dimension.DimensionSort, java.lang.String)
	 */
	@Override
	public DimensionBuilderAddMember setSortOrder(final DimensionSort type,
			final String customEntity) {
		if (type == null || customEntity == null || customEntity.length() == 0) {
			LOGGER.warn("Sort type or sort entity was specified as null, ignoring.");
			return this;
		}

		this.sortOrder = type;
		this.customSortEntity = customEntity;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderAddMember#addMembers(java.lang.String[])
	 */
	@Override
	public DimensionBuilderAddMember addMembers(final String... members) {
		if (members == null || members.length == 0) {
			throw new IllegalArgumentException("No members were specified.");
		}
		this.members.addAll(Arrays.asList(members));
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilderAddMember#build()
	 */
	@Override
	public String build() {
		if (members == null || members.size() == 0) {
			throw new IllegalStateException("No members are present.");
		}

		String query = "";
		if (this.isNonEmpty) {
			query = query + MDXReferences.NON_EMPTY;
		}

		if (sortOrder != null) {
			query = query + MDXReferences.ORDER + MDXReferences.FUNCTION_START
					+ getMembers() + MDXReferences.SEPARATOR;

			if (customSortEntity != null) {
				query = query + customSortEntity + MDXReferences.SEPARATOR;
			}

			query = query + DimensionSort.getMDXEquivalent(sortOrder)
					+ MDXReferences.FUNCTION_END;
		} else {
			query = query + getMembers();
		}

		query = query + MDXReferences.ON + this.axis;
		return query;
	}

	/**
	 * @return List of members taking into account whether they all
	 * follow the same hierarchy or not. If no, it constructs a
	 * cross join.
	 */
	private final String getMembers() {
		char separator = MDXReferences.SEPARATOR;
		if (this.isCrossJoin) {
			separator = MDXReferences.SEPARATOR_CROSS;
		}

		String list = "";
		final int size = members.size();
		for (int i = 0; i < size; i ++) {
			list = list + members.get(i) + separator;
		}

		list = list.substring(0, list.length() - 1);
		if (this.isCrossJoin) {
			return MDXReferences.MEMBER_SET_START + list
					+ MDXReferences.MEMBER_SET_END;
		} else {
			return MDXReferences.FUNCTION_START + list
					+ MDXReferences.FUNCTION_END;
		}
	}

}
