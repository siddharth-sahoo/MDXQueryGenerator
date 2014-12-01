package com.tfsinc.ilabs.mdx.test;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.olap4j.Axis;
import org.olap4j.Cell;
import org.olap4j.CellSet;
import org.olap4j.CellSetAxis;
import org.olap4j.Position;
import org.olap4j.metadata.Member;

import com.tfsinc.ilabs.olap.OlapClientManager;

public class IterationExample {

	private static final Logger LOGGER = Logger.getLogger(
			IterationExample.class);

	private static final void start() {
		OlapClientManager.initialize("olapconfig.properties");
	}

	public static void main(String[] args) throws SQLException {
		start();

		CellSet cellSet = OlapClientManager.executeMdxQuery("SELECT {[Measures].[Chats],[Measures].[InteractiveChats],[Measures].[WaitTime]} ON 0, NON EMPTY ([DimScenario].[Scenario].CHILDREN,[DimQueue].[Queue].CHILDREN,[DimRuleCategory].[RuleCategory].CHILDREN,[DimChatCancelled].[ChatCancelled].CHILDREN,[DimVisitorGroup].[VisitorGroup].CHILDREN,[DimChatType].[ChatType].CHILDREN,[DimAgent].[Agent].CHILDREN,[DimScope].[Scope].CHILDREN,[DimApplication].[Application].CHILDREN,[DimBrowser].[Browser].CHILDREN,[DimOs].[Os].CHILDREN,[DimTargetpopulation].[Targetpopulation].CHILDREN,[DimCancelReason].[CancelReason].CHILDREN,[DimTerminatedBy].[TerminatedBy].CHILDREN) ON 1 FROM (SELECT [DimTime].[Calendermonth].&[2014]&[7] ON 0 FROM (SELECT [DimClient].[Client].&[1015] ON 0 FROM [247OLAP]))");

		List<CellSetAxis> axes = cellSet.getAxes();
		final int axisCount = axes.size();

		if (axisCount != 2) {
			LOGGER.error("Don't know what to do.");
			stop();
		}

		CellSetAxis columnsAxis = axes.get(Axis.COLUMNS.ordinal());
		for (Position position : columnsAxis.getPositions()) {
			Member measure = position.getMembers().get(0);
			System.out.print(measure.getName());
		}

		// Print rows.
		CellSetAxis rowsAxis = axes.get(Axis.ROWS.axisOrdinal());
		int cellOrdinal = 0;
		for (Position rowPosition : rowsAxis.getPositions()) {
			boolean first = true;
			for (Member member : rowPosition.getMembers()) {
				if (first) {
					first = false;
				} else {
					System.out.print('\t');
				}
				System.out.print(member.getName());
			}

			// Print the value of the cell in each column.
			for (Position columnPosition : columnsAxis.getPositions()) {
				// Access the cell via its ordinal. The ordinal is kept in step
				// because we increment the ordinal once for each row and
				// column.
				Cell cell = cellSet.getCell(cellOrdinal);
				// Just for kicks, convert the ordinal to a list of coordinates.
				// The list matches the row and column positions.
				List<Integer> coordList = cellSet.ordinalToCoordinates(cellOrdinal);
				assert coordList.get(0) == rowPosition.getOrdinal();
				assert coordList.get(1) == columnPosition.getOrdinal();
				++cellOrdinal;
				System.out.print('\t');
				System.out.print(cell.getFormattedValue());
			}
			System.out.println();
		}

		stop();
	}

	private static final void stop() {
		OlapClientManager.shutdown(true);
	}

}
