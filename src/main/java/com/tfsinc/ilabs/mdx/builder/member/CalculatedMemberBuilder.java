package com.tfsinc.ilabs.mdx.builder.member;

import org.apache.log4j.Logger;

import com.tfsinc.ilabs.olap.references.MDXReferences;

/**
 * Builder class calculated members.
 * @author siddharth.s
 */
public class CalculatedMemberBuilder implements MemberBuilderSetName,
	MemberBuilderSetExpression, MemberBuilderSetFormatString {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(
			CalculatedMemberBuilder.class);

	/**
	 * Name of the calculated member.
	 */
	private String memberName;

	/**
	 * Expression for the calculated member.
	 */
	private String memberExpression;

	/**
	 * Optional formatting of the values.
	 */
	private String memberFormatString;

	/**
	 * @return Calculated member builder instance.
	 */
	public static final MemberBuilderSetName getBuilder() {
		return new CalculatedMemberBuilder();
	}

	// Private constructor.
	private CalculatedMemberBuilder() { }

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.member.MemberBuilderSetFormatString#setFormatString(com.tfsinc.ilabs.mdx.builder.member.FormatString)
	 */
	@Override
	public MemberBuilderSetFormatString setFormatString(
			final FormatString formatString) {
		if (formatString == null) {
			LOGGER.warn("Specified format string is null, ignoring.");
			return this;
		}

		if (memberFormatString != null) {
			LOGGER.warn("Overwriting format string.");
		}

		memberFormatString = FormatString.getFormatString(formatString);
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.member.MemberBuilderSetFormatString#build()
	 */
	@Override
	public String build() {
		String member = MDXReferences.MEMBER + memberName
				+ MDXReferences.AS + memberExpression;
		if (memberFormatString != null) {
			member = member + MDXReferences.SEPARATOR
					+ MDXReferences.FORMAT_STRING + memberFormatString;
		}
		return member;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.member.MemberBuilderSetExpression#setExpression(java.lang.String)
	 */
	@Override
	public MemberBuilderSetFormatString setExpression(final String expression) {
		if (expression == null || expression.length() == 0) {
			throw new IllegalArgumentException(
					"Expression of the calculated member is null or empty.");
		}
		memberExpression = expression;
		return this;
	}

	/* (non-Javadoc)
	 * @see com.tfsinc.ilabs.mdx.builder.member.MemberBuilderSetName#setName(java.lang.String)
	 */
	@Override
	public MemberBuilderSetExpression setName(final String name) {
		if (name == null || name.length() == 0) {
			throw new IllegalArgumentException(
					"Name of the calculated member is null or empty.");
		}
		memberName = name;
		return this;
	}

}
