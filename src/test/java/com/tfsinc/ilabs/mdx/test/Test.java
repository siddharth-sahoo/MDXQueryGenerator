package com.tfsinc.ilabs.mdx.test;

import java.io.PrintWriter;
import java.util.Arrays;

import org.olap4j.CellSet;
import org.olap4j.layout.CellSetFormatter;
import org.olap4j.layout.RectangularCellSetFormatter;

import com.tfsinc.ilabs.mdx.builder.MdxQueryBuilder;
import com.tfsinc.ilabs.mdx.builder.dimension.DimensionBuilder;
import com.tfsinc.ilabs.mdx.builder.dimension.DimensionSort;
import com.tfsinc.ilabs.olap.OlapClientManager;

public class Test {

	public static void main(String[] args) {
		OlapClientManager.initialize("olapconfig.properties");

		final CellSet cellSet = MdxQueryBuilder.getQueryBuilder()
				.setCube("Adventure Works")
				.setMeasures(Arrays.asList("[Internet Sales Amount]", "[Internet Order Quantity]"))
				.addDimension(1, DimensionBuilder.getDimensionBuilder()
						.setNonEmpty(true)
						.setCrossJoin(true)
						.setAxis(1)
						.setSortOrder(DimensionSort.CUSTOM_DESCENDING, "[Measures].[Internet Sales Amount]")
						.addMembers("[Date].[Calendar].[Month].MEMBERS",
								"[Product].[Product Categories].[Category].MEMBERS")
								.build()
						)
						.execute();

		CellSetFormatter formatter = new RectangularCellSetFormatter(false);
		formatter.format(cellSet, new PrintWriter(System.out, true));

		OlapClientManager.shutdown(true);
	}

}
