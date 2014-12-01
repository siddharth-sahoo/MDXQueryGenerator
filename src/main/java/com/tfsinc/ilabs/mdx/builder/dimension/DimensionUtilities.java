package com.tfsinc.ilabs.mdx.builder.dimension;

import java.util.List;

import org.olap4j.mdx.IdentifierNode;
import org.olap4j.mdx.IdentifierSegment;

/**
 * Utilities used in dimension building.
 * @author siddharth.s
 */
class DimensionUtilities {

	/**
	 * @param members List of members for the dimension.
	 * @return Whether they all follow the same hierarchy or not.
	 */
	public static final boolean isSameHierarchy(final List<String> members) {
		final int size = members.size();
		for (int i = 0; i < size - 1; i ++) {
			final List<IdentifierSegment> primaryNode = IdentifierNode
					.parseIdentifier(members.get(i))
					.getSegmentList();
			
			for (int j = i + 1; j < size; j ++) {
				final List<IdentifierSegment> node = IdentifierNode
						.parseIdentifier(members.get(j))
						.getSegmentList();
				if (!isSameHierarchy(primaryNode, node)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * @param list1 Identifier node list.
	 * @param list2 Identifier node list.
	 * @return Whether they follow the same hierarchy.
	 */
	private static final boolean isSameHierarchy(final List<IdentifierSegment> list1,
			final List<IdentifierSegment> list2) {
		final int size = Math.min(list1.size(), list2.size());
		for (int i = 0; i < size; i ++) {
			if (!list1.get(i).equals(list2.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	// Private constructor.
	private DimensionUtilities() { }

}
