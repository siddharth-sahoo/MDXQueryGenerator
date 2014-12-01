package com.tfsinc.ilabs.mdx.builder.member;

/**
 * Specifies the expression of the calculated member in step builder.
 * @author siddharth.s
 */
public interface MemberBuilderSetExpression {

	/**
	 * @param expression Expression of the calculated member.
	 * @return Next step in calculated member builder.
	 */
	MemberBuilderSetFormatString setExpression(final String expression);

}
