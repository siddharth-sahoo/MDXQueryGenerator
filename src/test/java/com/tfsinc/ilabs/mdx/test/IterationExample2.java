package com.tfsinc.ilabs.mdx.test;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.olap4j.Axis;
import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.Position;
import org.olap4j.layout.CellSetFormatter;
import org.olap4j.layout.RectangularCellSetFormatter;
import org.olap4j.metadata.Member;

import com.tfsinc.ilabs.olap.OlapClientManager;

public class IterationExample2 {

	private static final Logger LOGGER = Logger.getLogger(IterationExample2.class);

	private static final String QUERY_LOCAL =
			"SELECT "
					+ "{[Measures].[Internet Average Sales Amount], "
					+ "[Measures].[Internet Gross Profit]} "
					+ "ON 0, "
					+ "NON EMPTY "
					+ "([Date].[Date].[Date], "
					+ "[Product].[Product].[Product], "
					+ "[Sales Territory].[Sales Territory Region].[Sales Territory Region]) "
					+ "ON 1 "
					+ "FROM "
					+ "(SELECT [Date].[Date].&[20110101] ON 0 "
					+ "FROM [Adventure Works])";
	private static final String QUERY =
			"SELECT {[Measures].[Chats],[Measures].[InteractiveChats],[Measures].[WaitTime]} ON 0," +
			" NON EMPTY ([DimScenario].[Scenario].CHILDREN,[DimQueue].[Queue].CHILDREN,[DimRuleCategory].[RuleCategory].CHILDREN) ON 1" +
			" FROM (SELECT [DimTime].[Calendermonth].&[2014]&[7] ON 0 FROM (SELECT [DimClient].[Client].&[1015] ON 0 FROM [247OLAP]))";

	public static void main(String[] args) {
		OlapClientManager.initialize("olapconfig.properties");
		LOGGER.info(QUERY);
		CellSet cellSet = OlapClientManager.executeMdxQuery(QUERY);
		CellSetFormatter formatter = new RectangularCellSetFormatter(false);
		formatter.format(cellSet, new PrintWriter(System.out, true));
		//print(cellSet);
		stop();
	}

	private static void print(final CellSet cellSet) {
		List<CellSetAxis> axes = cellSet.getAxes();

		// Print column names (measure names)
		CellSetAxis columnAxis = axes.get(Axis.COLUMNS.axisOrdinal());
		List<Position> columnPositions = columnAxis.getPositions();
		final int columnCount = columnPositions.size();
		for (int i = 0; i < columnCount; i ++) {
			LOGGER.info("Column position " + i + ": " + columnPositions.get(i).getMembers().get(0).getName());
		}

		// Print row names (dimension values)
		CellSetAxis rowAxis = axes.get(Axis.ROWS.axisOrdinal());
		List<Position> rowPositions = rowAxis.getPositions();
		final int rowCount = rowAxis.getPositionCount();
		for (int i = 0; i < rowCount; i ++) {
			List<Member> members = rowPositions.get(i).getMembers();
			String row = "";
			for (int j = 0; j < members.size(); j ++) {
				row = row + members.get(j).getName() + " ";
			}
			LOGGER.info("Row position " + i + ": " + members.size() + " " + row);
		}

		// Print cell values (counts/numbers)
		int cellOrdinal = 0;
		for (int i = 0; i < rowCount; i ++) {
			String row = "";
			for (int j = 0; j < columnCount; j++) {
				Cell cell = cellSet.getCell(cellOrdinal);
				row = row + cell.getFormattedValue() + " ";
				cellOrdinal ++;
			}
			LOGGER.info(row);
		}
	}

	private static void stop() {
		OlapClientManager.shutdown(true);
		System.exit(1);
	}

}
