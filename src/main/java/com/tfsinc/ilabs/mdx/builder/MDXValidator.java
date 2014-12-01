package com.tfsinc.ilabs.mdx.builder;

import org.apache.log4j.Logger;
import org.olap4j.OlapException;
import org.olap4j.mdx.SelectNode;
import org.olap4j.mdx.parser.MdxParser;
import org.olap4j.mdx.parser.MdxValidator;

import com.tfsinc.ilabs.olap.OlapClientManager;
import com.tfsinc.ilabs.olap.WrappedOlapConnection;

/**
 * Validates an MDX query.
 * @author siddharth.s
 */
public class MDXValidator {

	/**
	 * Root logger instance.
	 */
	private static final Logger LOGGER = Logger.getLogger(MDXValidator.class);

	/**
	 * @param mdx MDX query to be validated.
	 * @return Parsed select node of the query;
	 * @throws OlapException When there is an error in validating the query.
	 */
	public static final SelectNode validateQuery(final String mdx) throws OlapException {
		final WrappedOlapConnection connection = OlapClientManager
				.getConnection();
		final MdxParser parser = connection.getResource()
				.getParserFactory()
				.createMdxParser(connection.getResource());
		final MdxValidator validator = connection.getResource()
				.getParserFactory()
				.createMdxValidator(connection.getResource());

		LOGGER.info("Parsing query: " + mdx);
		final SelectNode node = parser.parseSelect(mdx);
		try {
			validator.validateSelect(node);
			OlapClientManager.returnConnection(connection);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validated query: " + mdx);
			}
			return node;
		} catch (OlapException e) {
			LOGGER.error("Unable to validate MDX query.", e);
			OlapClientManager.returnConnection(connection);
			throw e;
		}
	}

	/**
	 * @param mdx String MDX query to be parsed.
	 * @return Parsed select node.
	 */
	public static final SelectNode getParsedSelectNode(final String mdx) {
		final WrappedOlapConnection connection = OlapClientManager
				.getConnection();
		final MdxParser parser = connection.getResource()
				.getParserFactory()
				.createMdxParser(connection.getResource());
		final SelectNode node = parser.parseSelect(mdx);
		OlapClientManager.returnConnection(connection);
		return node;
	}

	/**
	 * @param node Parsed select node to be validated.
	 * @throws OlapException When there is an error in validating the query.
	 */
	public static final void validateQuery(final SelectNode node) throws OlapException {
		final WrappedOlapConnection connection = OlapClientManager
				.getConnection();
		final MdxValidator validator = connection.getResource()
				.getParserFactory()
				.createMdxValidator(connection.getResource());
		try {
			validator.validateSelect(node);
			OlapClientManager.returnConnection(connection);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Validated query: " + node.toString());
			}
		} catch (OlapException e) {
			LOGGER.error("Unable to validate MDX query.", e);
			OlapClientManager.returnConnection(connection);
			throw e;
		}
	}

	// Private constructor.
	private MDXValidator() { }

}
