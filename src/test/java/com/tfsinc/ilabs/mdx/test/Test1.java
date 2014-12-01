package com.tfsinc.ilabs.mdx.test;

import java.io.PrintWriter;
import java.util.Arrays;

import org.olap4j.CellSet;
import org.olap4j.layout.CellSetFormatter;
import org.olap4j.layout.RectangularCellSetFormatter;

import com.tfsinc.ilabs.mdx.builder.MdxQueryBuilder;
import com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilder;
import com.tfsinc.ilabs.mdx.builder.member.CalculatedMemberBuilder;
import com.tfsinc.ilabs.mdx.builder.member.FormatString;
import com.tfsinc.ilabs.olap.OlapClientManager;

public class Test1 {

	public static void main(String[] args) {
		OlapClientManager.initialize("olapconfig.properties");

		final CellSet cellSet = MdxQueryBuilder.getQueryBuilder()
				.setCube("Adventure Works")
				.setMeasures(Arrays.asList("[Internet Sales Amount]", "[Internet Sales Amount - Prev Mo.]", "[Internet Sales Amount - Delta]"))
				.addDimension(1, DimensionBuilder.getDimensionBuilder()
						.setNonEmpty(true)
						.setCrossJoin(false)
						.setAxis(1)
						.addMembers("[Date].[Calendar].[Month].MEMBERS")
						.build())
				.addCalculatedMembers(Arrays.asList(
						CalculatedMemberBuilder.getBuilder()
							.setName("[Measures].[Internet Sales Amount - Prev Mo.]")
							.setExpression("([Date].[Calendar].CURRENTMEMBER.PREVMEMBER, [Measures].[Internet Sales Amount])")
							.setFormatString(FormatString.CURRENCY)
							.build(),
							CalculatedMemberBuilder.getBuilder()
							.setName("[Measures].[Internet Sales Amount - Delta]")
							.setExpression("([Date].[Calendar].CURRENTMEMBER, [Measures].[Internet Sales Amount]) - ([Date].[Calendar].PREVMEMBER, [Measures].[Internet Sales Amount])")
							.setFormatString(FormatString.CURRENCY)
							.build()
						))
				.execute();

		CellSetFormatter formatter = new RectangularCellSetFormatter(false);
		formatter.format(cellSet, new PrintWriter(System.out, true));

		OlapClientManager.shutdown(true);
	}

}
