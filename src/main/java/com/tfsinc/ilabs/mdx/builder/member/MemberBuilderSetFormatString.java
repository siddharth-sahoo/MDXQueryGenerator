package com.tfsinc.ilabs.mdx.builder.member;

/**
 * Specifies the format string for the calculated member or
 * builds it.
 * @author siddharth.s
 */
public interface MemberBuilderSetFormatString {

	/**
	 * @param formatString Format string for the calculated member.
	 * @return Next step in calculated member builder.
	 */
	MemberBuilderSetFormatString setFormatString(final FormatString formatString);

	/**
	 * @return Built calculated member declaration statement.
	 */
	String build();

}
