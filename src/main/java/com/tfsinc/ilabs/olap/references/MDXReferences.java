package com.tfsinc.ilabs.olap.references;

/**
 * Constants and references related to MDX query building.
 * @author siddharth.s
 */
public class MDXReferences {

	// Query key words.
	public static final String SELECT = "SELECT ";
	public static final String ON = " ON ";
	public static final String FROM = " FROM ";
	public static final String WHERE = " WHERE ";
	public static final String NON_EMPTY = " NON EMPTY ";
	public static final String ORDER = " ORDER ";
	public static final String FORMAT_STRING = " FORMAT_STRING =";
	public static final String MEMBER = " MEMBER ";
	public static final String AS = " AS ";
	public static final String WITH = " WITH ";
	
	public static final char SEPARATOR = ',';
	public static final char SEPARATOR_CROSS = '*';
	public static final char DEFAULT_AXIS = '0';

	// Member specifiers.
	public static final char MEMBER_START = '[';
	public static final char MEMBER_END = ']';
	public static final char MEMBER_SET_START = '{';
	public static final char MEMBER_SET_END = '}';
	public static final char MEMBER_HIERARCHY_SEPARATOR = '.';
	public static final char MEMBER_SPECIFIER = '&';

	public static final char FUNCTION_START = '(';
	public static final char FUNCTION_END = ')';

	// Standard member terminology.
	public static final String MEASURES = "[Measures].";

	// Sort order types.
	public static final String ORDER_ASCENDING = " ASC ";
	public static final String ORDER_DESCENDING = " DESC ";
	public static final String ORDER_CUSTOM_ASCENDING = " BASC ";
	public static final String ORDER_CUSTOM_DESCENDING = " BDESC ";

	// Format string types.
	public static final String FORMAT_STRING_GENERAL_NUMBER = " \"General Number\" ";
	public static final String FORMAT_STRING_CURRENCY = " \"Currency\" ";
	public static final String FORMAT_STRING_FIXED = " \"Fixed\" ";
	public static final String FORMAT_STRING_STANDARD = " \"Standard\" ";
	public static final String FORMAT_STRING_PERCENT = " \"Percent\" ";
	public static final String FORMAT_STRING_SCIENTIFIC = " \"Scientific\" ";
	public static final String FORMAT_STRING_YES_NO = " \"Yes/No\" ";
	public static final String FORMAT_STRING_TRUE_FALSE = " \"True/False\" ";
	public static final String FORMAT_STRING_ON_OFF = " \"On/Off\" ";

}
