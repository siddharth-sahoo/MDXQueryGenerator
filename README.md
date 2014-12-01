MDXQueryGenerator
=================

Provides step building of MDX queries and abstracts connection to OLAP hosts.

Sample code:

````java
// Initialize connection pool and other query building preferences.
OlapClientManager.initialize("olapconfig.properties");

// Shut down the pool and close the connections.
OlapClientManager.shutdown(true);
````

Generate MDX query fluently:

````java
String query = MdxQueryBuilder.getQueryBuilder()
  .setCube("Adventure Works")
  .setMeasures(Arrays.asList("[Internet Sales Amount]", "[Internet Order Quantity]"))
  .setDimension(1, DimensionBuilder.getDimensionBuilder()
    .setNonEmpty(true)
	.setCrossJoin(true)
	.setAxis(1)
	.setSortOrder(DimensionSort.CUSTOM_DESCENDING, "[Measures].[Internet Sales Amount]")
	.addMembers("[Date].[Calendar].[Month].MEMBERS",
	  "[Product].[Product Categories].[Category].MEMBERS")
	.build()
  )
  .build();

// Execute the query:
CellSet cellSet = OlapClientManager.executeMdxQuery(query);
````

Generate and execute in a single step:

````java
CellSet cellSet = MdxQueryBuilder.getQueryBuilder()
  .setCube("Adventure Works")
  .setMeasures(Arrays.asList("[Internet Sales Amount]", "[Internet Order Quantity]"))
  .setDimension(1, DimensionBuilder.getDimensionBuilder()
	.setNonEmpty(true)
	.setCrossJoin(true)
	.setAxis(1)
	.setSortOrder(DimensionSort.CUSTOM_DESCENDING, "[Measures].[Internet Sales Amount]")
	.addMembers("[Date].[Calendar].[Month].MEMBERS",
	  "[Product].[Product Categories].[Category].MEMBERS")
	.build()
  )
  .execute();
````

The above piece of code would generate the following query:

````mdx
SELECT { [Measures].[Internet Sales Amount], [Measures].[Internet Order Quantity] }
  ON 0,
NON EMPTY ORDER ( { [Date].[Calendar].[Month].MEMBERS * [Product].[Product Categories].[Category].MEMBERS },
  [Measures].[Internet Sales Amount], BDESC )
  ON 1
FROM [Adventure Works]
````

Validate the query against cube meta data before executing the query:

````java
MDXValidator.validateQuery("< MDX query >");
````

Sample configuration file:
````conf
## Connection Parameters
OlapDriverClass org.olap4j.driver.xmla.XmlaOlap4jDriver
OlapConnectionUrl jdbc:xmla:Server=http://localhost/olap/msmdpump.dll

## Connection Pool Parameters
AcquireIncrement 5
MinPoolSize 10
MaxPoolSize 20
ConnectRetry 3

## Query Configurations
# Whether the query should be validated against meta data before execution.
ValidateQuery true
# Whether to display the query to be executed for debugging purposes.
DisplayQuery true
# Whether the measures are to be displayed on ROWS or COLUMNS.
# ROWS : 0
# COLUMNS : 1
MeasureAxis COLUMNS
````
