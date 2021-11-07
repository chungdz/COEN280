hw3.java is under src/GUIDemo
populate.java is under src/TableGenerated
createdb.sql is under src/TableGenerated
dropdb.sql is under src/TableGenerated

To run the code, first thing to do is to put data and configure files in right place.

1. Make new directory data and config.

2. Unziped Yelp data should be store in data/ for checking running of populate.java.

3. connect_config.json under config/ is confidential information for JDBC connnection .
	Its format shoule be:
	{
		"connectionDriver": "oracle.jdbc.driver.OracleDriver",
		"connectHeader": "jdbc:oracle:thin:@",
		"connectionPort": "1521",
		"connectionHost": "localhost",
		"dbName": "XE",
		"username": "xxx",
		"password": "xxxxxxxxx"
	}

Run the code by following order, note that populate.java will automatically fetch four Yelp data store in data/

1. Use any sql developer/sql commandline to exexute createdb.sql;
2. Make sure in nbproject/project.properties, main.class=TableGenerated.populate;
	Compile and run populate.java
3. Make sure in nbproject/project.properties, main.class=GUIDemo.hw3;
	Compile and run hw3.java