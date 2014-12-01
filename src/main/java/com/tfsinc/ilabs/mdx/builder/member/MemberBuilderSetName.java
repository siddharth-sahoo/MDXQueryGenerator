package com.tfsinc.ilabs.mdx.builder.member;

/**
 * Specifies the name of the calculated member in step builder.
 * @author siddharth.s
 */
public interface MemberBuilderSetName {

	/**
	 * @param name Name of the calculated member.
	 * @return Next step in calculated member builder.
	 */
	MemberBuilderSetExpression setName(final String name);

}
