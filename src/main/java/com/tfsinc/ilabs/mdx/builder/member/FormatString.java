package com.tfsinc.ilabs.mdx.builder.member;

import com.tfsinc.ilabs.olap.references.MDXReferences;

/**
 * Format string types used in MDX queries, particularly in
 * calculated member types.
 * @author siddharth.s
 */
public enum FormatString {

	/**
	 * Displays the number with no thousand separator.
	 */
	GENERAL_NUMBER,

	/**
	 * Displays the number with a thousand separator, if appropriate.
	 * Displays two digits to the right of the decimal separator.
	 * Output is based on system locale settings.
	 */
	CURRENCY,

	/**
	 * Displays at least one digit to the left and two digits to
	 * the right of the decimal separator.
	 */
	FIXED,

	/**
	 * Displays the number with thousand separator, at least one
	 * digit to the left and two digits to the right of the decimal separator.
	 */
	STANDARD,

	/**
	 * Displays the number multiplied by 100 with a percent sign (%)
	 * appended to the right. Always displays two digits to the right
	 * of the decimal separator.
	 */
	PERCENT,

	/**
	 * Uses standard scientific notation.
	 */
	SCIENTIFIC,

	/**
	 * Displays No if the number is 0; otherwise, displays Yes.
	 */
	YES_NO,

	/**
	 * Displays False if the number is 0; otherwise, displays True.
	 */
	TRUE_FALSE,

	/**
	 * Displays Off if the number is 0; otherwise, displays On.
	 */
	ON_OFF;

	/**
	 * @param formatString Format string enumeration type.
	 * @return Format string as is should appear in the MDX query.
	 */
	public static final String getFormatString(final FormatString formatString) {
		switch (formatString) {
			case CURRENCY:
				return MDXReferences.FORMAT_STRING_CURRENCY;
			case FIXED:
				return MDXReferences.FORMAT_STRING_FIXED;
			case GENERAL_NUMBER:
				return MDXReferences.FORMAT_STRING_GENERAL_NUMBER;
			case ON_OFF:
				return MDXReferences.FORMAT_STRING_ON_OFF;
			case PERCENT:
				return MDXReferences.FORMAT_STRING_PERCENT;
			case SCIENTIFIC:
				return MDXReferences.FORMAT_STRING_SCIENTIFIC;
			case STANDARD:
				return MDXReferences.FORMAT_STRING_STANDARD;
			case TRUE_FALSE:
				return MDXReferences.FORMAT_STRING_TRUE_FALSE;
			case YES_NO:
				return MDXReferences.FORMAT_STRING_YES_NO;
			default:
				throw new IllegalStateException(
						"Unhandled format string: " + formatString);
		}
	}

}
