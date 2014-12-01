package com.tfsinc.ilabs.mdx.builder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.olap4j.CellSet;
import org.olap4j.OlapException;
import org.olap4j.mdx.SelectNode;

import com.tfsinc.ilabs.olap.OlapClientManager;
import com.tfsinc.ilabs.olap.references.MDXReferences;
import com.tfsinc.ilabs.olap.references.OlapConfigReferences;

/**
 * MDX query step builder class which guides the user into
 * specifying all the aspects of an MDX query.
 * @author siddharth.s
 */
public class MdxQueryBuilder implements QueryBuilderCubeSelector,
QueryBuilderMeasureSelector, QueryBuilderDimensionSelector {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			MdxQueryBuilder.class);

	/**
	 * Name of the cube.
	 */
	private String cubeName;

	/**
	 * List of measure names.
	 */
	private final Map<Integer, String> axes;

	/**
	 * List of filter members.
	 */
	private List<String> filters;

	/**
	 * List of calculated members used in the query.
	 */
	private List<String> calculatedMembers;

	/**
	 * @return A new builder instance.
	 */
	public static final QueryBuilderCubeSelector getQueryBuilder() {
		return new MdxQueryBuilder();
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#build()
	 */
	@Override
	public String build() {
		final String query = buildQuery();

		// Print query if needed.
		if (OlapConfigReferences.shouldDisplayMdx()) {
			LOGGER.info("Generated query: " + query);
		}

		// Validate query if needed.
		if (OlapConfigReferences.shouldValidateMdx()) {
			try {
				MDXValidator.validateQuery(query);
			} catch (OlapException e) {
				LOGGER.error("Could not validate query.", e);
				return null;
			}
		}

		return query;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#execute()
	 */
	@Override
	public CellSet execute() {
		final String query = buildQuery();

		// Print query if needed.
		if (OlapConfigReferences.shouldDisplayMdx()) {
			LOGGER.info("Generated query: " + query);
		}

		final SelectNode node = MDXValidator.getParsedSelectNode(query);
		// Validate query if needed.
		if (OlapConfigReferences.shouldValidateMdx()) {
			try {
				MDXValidator.validateQuery(query);
			} catch (OlapException e) {
				LOGGER.error("Could not validate the query.");
				return null;
			}
		}
		
		return OlapClientManager.executeMdxQuery(node);
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#addCalculatedMembers(java.util.List)
	 */
	@Override
	public QueryBuilderDimensionSelector addCalculatedMembers(
			final List<String> members) {
		if (members == null || members.size() == 0) {
			LOGGER.warn("Null or empty calculated members specified, ignoring.");
			return this;
		}

		if (calculatedMembers == null) {
			calculatedMembers = new LinkedList<>();
		} else {
			LOGGER.warn("Calculated members not empty, adding to existing list.");
		}

		calculatedMembers.addAll(members);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#addCalculatedMembers(java.lang.String[])
	 */
	@Override
	public QueryBuilderDimensionSelector addCalculatedMembers(final String... members) {
		if (members == null || members.length == 0) {
			LOGGER.warn("Null or empty calculated members specified, ignoring.");
			return this;
		}

		if (calculatedMembers == null) {
			calculatedMembers = new LinkedList<>();
		} else {
			LOGGER.warn("Calculated members not empty, adding to existing list.");
		}

		calculatedMembers.addAll(Arrays.asList(members));
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#setDimensions(java.util.List)
	 */
	@Override
	public QueryBuilderDimensionSelector addDimension(final int axisIndex,
			final String dimension) {
		if (dimension == null || dimension.length() == 0) {
			throw new IllegalArgumentException("Dimension specified is null or empty.");
		}

		if (this.axes.containsKey(axisIndex)) {
			LOGGER.warn("Axis index " + axisIndex
					+ " has already been defined, overwriting.");
		}

		this.axes.put(axisIndex, dimension);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#setFilter(java.util.List)
	 */
	@Override
	public QueryBuilderDimensionSelector addFilters(final List<String> filters) {
		if (filters == null || filters.size() == 0) {
			LOGGER.warn("Null or empty filters specified, ignoring.");
			return this;
		}

		if (this.filters == null) {
			this.filters = new LinkedList<>();
		} else {
			LOGGER.warn("Filters are already populated, adding to existing entries.");
		}

		this.filters.addAll(filters);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderDimensionSelector#addFilter(java.lang.String[])
	 */
	@Override
	public QueryBuilderDimensionSelector addFilter(final String... filters) {
		if (filters == null || filters.length == 0) {
			LOGGER.warn("Null or empty filters specified, ignoring.");
			return this;
		}
		if (this.filters == null) {
			this.filters = new LinkedList<>();
		} else {
			LOGGER.warn("Filters are already populated, adding to existing entries.");
		}

		this.filters.addAll(Arrays.asList(filters));
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderMeasureSelector#setMeasures(java.util.List)
	 */
	@Override
	public QueryBuilderDimensionSelector setMeasures(final List<String> names) {
		if (names == null || names.size() == 0) {
			throw new IllegalArgumentException("Measures list is null or empty.");
		}
		
		final int size = names.size();
		final int measureAxis = OlapConfigReferences.getMeasureAxisIndex();
		
		String measures = "";
		for (int i = 0; i < size; i ++) {
			measures = measures + MDXReferences.MEASURES + names.get(i)
					+ MDXReferences.SEPARATOR;
		}
		
		measures = MDXReferences.MEMBER_SET_START 
				+ measures.substring(0, measures.length() - 1)
				+ MDXReferences.MEMBER_SET_END + MDXReferences.ON
				+ measureAxis;
		
		this.axes.put(measureAxis, measures);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.QueryBuilderCubeSelector#setCube(java.lang.String)
	 */
	@Override
	public QueryBuilderMeasureSelector setCube(final String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException("Cube name is null or empty.");
		}
		this.cubeName = MDXReferences.MEMBER_START
				+ name + MDXReferences.MEMBER_END;
		return this;
	}

	// Private constructor.
	private MdxQueryBuilder() {
		this.axes = new HashMap<>();
	}

	private final String buildQuery() {
		String query = "";
		
		if (calculatedMembers != null && calculatedMembers.size() > 0) {
			query = query + MDXReferences.WITH;
			final int size = calculatedMembers.size();

			for (int i = 0; i < size; i ++) {
				query = query + calculatedMembers.get(i);
			}
		}
		
		query = query + MDXReferences.SELECT;

		// Populate axes.
		Iterator<String> iter = this.axes.values().iterator();
		while (iter.hasNext()) {
			String axis = iter.next();
			query = query + axis + MDXReferences.SEPARATOR;
		}

		query = query.substring(0, query.length() - 1)
				+ MDXReferences.FROM;

		// Apply filters.
		if (filters != null) {
			
//			query = query + MDXReferences.WHERE
//					+ MDXReferences.MEMBER_SET_START;
//
//			final int size = this.filters.size();
//			for (int i = 0; i < size; i ++) {
//				query = query + filters.get(i) + MDXReferences.SEPARATOR;
//			}
//			query = query.substring(0, query.length() - 1)
//					+ MDXReferences.MEMBER_SET_END;
			String subQuery = this.cubeName;
			final int size = filters.size();
			
			for (int i = 0; i < size; i ++) {
				subQuery = MDXReferences.FUNCTION_START + MDXReferences.SELECT
						+ filters.get(i) + MDXReferences.ON
						+ MDXReferences.DEFAULT_AXIS + MDXReferences.FROM
						+ subQuery + MDXReferences.FUNCTION_END;
			}
			query = query + subQuery;
		} else {
			query = query + this.cubeName;
		}

		return query;
	}

}
