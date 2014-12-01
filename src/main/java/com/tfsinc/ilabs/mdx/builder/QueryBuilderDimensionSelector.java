package com.tfsinc.ilabs.mdx.builder;

import java.util.List;

import org.olap4j.CellSet;

import com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilder;
import com.tfsinc.ilabs.mdx.builder.member.CalculatedMemberBuilder;

/**
 * Third step of query building.
 * This specifies dimension selection.
 * @author siddharth.s
 */
public interface QueryBuilderDimensionSelector {

	/**
	 * @param axisIndex Axis where the dimension is to be added.
	 * @param dimension Dimension expression.
	 * @see DimensionBuilder
	 * @return Next step in query builder.
	 */
	QueryBuilderDimensionSelector addDimension(final int axisIndex,
			final String dimension);

	/**
	 * @param filters Filters to be added. It is an optional step.
	 * @return Next step in query builder.
	 */
	QueryBuilderDimensionSelector addFilters(final List<String> filters);

	/**
	 * @param filters Filters to be added. It is an optional step.
	 * @return Next step in query builder.
	 */
	QueryBuilderDimensionSelector addFilter(final String... filters);

	/**
	 * @param members Calculated member declarative statements to be added.
	 * @see CalculatedMemberBuilder
	 * @return Next step in query builder.
	 */
	QueryBuilderDimensionSelector addCalculatedMembers(final List<String> members);

	/**
	 * @param members Calculated member declarative statements to be added.
	 * @see CalculatedMemberBuilder
	 * @return Next step in query builder.
	 */
	QueryBuilderDimensionSelector addCalculatedMembers(final String... members);

	/**
	 * @return String formatted query.
	 */
	String build();

	/**
	 * @return Executes the generated MDX query and returns the cell set.
	 */
	CellSet execute();

}
