package com.hoanghiep.hustcare.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DatabaseOperator {

	public static String dbClassName = "com.mysql.jdbc.Driver";	
    public static String CONNECTION = "jdbc:mysql://127.0.0.1/";
    private static String currentDatabaseName;
    public Connection connection;
    
    public DatabaseOperator(String dbClassName, String connection){
		DatabaseOperator.dbClassName = dbClassName;
		DatabaseOperator.CONNECTION = connection;
	}
    
    public void connect(String userName, String password) throws ClassNotFoundException,SQLException {
		Class.forName(dbClassName);
		
        Properties p = new Properties();
        p.put("user", userName);
        p.put("password", password);
        // Try to connect
        this.connection = DriverManager.getConnection(CONNECTION, p);
	}
	
	public void close() throws ClassNotFoundException,SQLException { 
		connection.close();
	}
	
	public void useDatabase(String databaseName) throws ClassNotFoundException,SQLException{
		String sql = "USE " + databaseName + ";";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
			System.out.println("\n### Database in use: " + databaseName+"###\n");
			currentDatabaseName = databaseName;
			rs.close();
		}
		catch(Exception e)
		{
			System.out.println("No Such Database exist!!!");
		}
		finally
		{
			
			stmt.close();
		}
	}
	
	public ArrayList<ArrayList<String>> showTableData(String tableName) throws ClassNotFoundException,SQLException {
		ArrayList<ArrayList<String>> data = null;
		try
		{
			String sql = "SELECT * FROM " + tableName + ";";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int noOfColumns = rsmd.getColumnCount();
			
			
			String[] columnNames = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++)
			{
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			
			System.out.println("\n\nRetreiving records from the table...\n");
			
			for (String cName : columnNames)
			{
				System.out.print(cName + " | ");
			}
			System.out.print("\n\n");
			
			
			data = new ArrayList<ArrayList<String>>();
			String fieldValue;
			while(rs.next())
			{
				//Retrieve by column name
				ArrayList<String> tmpRow = new ArrayList<String>(); 
				for (int i = 0; i < noOfColumns; i++ )
				{
					fieldValue = rs.getString(columnNames[i]);
					//System.out.print(fieldValue + "\t");
					tmpRow.add(fieldValue);
				}
				data.add(tmpRow);
				System.out.println();
			}
			
	        rs.close();
	        stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("\n\nTable Doesn't Exist!");
		}
		return data;
	}
	
	public void showTableData(String tableName, String columNames) throws ClassNotFoundException,SQLException
	{

		String[] splitedColumns = columNames.split(",");
		int length = splitedColumns.length;
		
		String sql = "SELECT "+ columNames +" FROM "+ tableName + ";";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		System.out.println("\n\nRetreiving records from the table...\n");
		
		String fieldValue;
		while(rs.next())
		{
			//Retrieve by column name
			for (int i = 0; i < length; i++)
			{
				fieldValue  = rs.getString(splitedColumns[i]);
				System.out.print(fieldValue + "\t");
			}
			System.out.println();
		}
        rs.close();
        stmt.close();
		
	}
	
	public ArrayList<ArrayList<String>> showTableData(String tableName, String columNames, String rowsAttributes) throws ClassNotFoundException,SQLException
	{
		String[] splitedColumns = columNames.split(",");
		int length = splitedColumns.length;
		
		String sql = "SELECT "+ columNames +" FROM "+ tableName + " WHERE " + rowsAttributes + ";";
                //System.out.println(sql);
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery(sql);
		
		//System.out.println("\n\nRetreiving records from the table...\n");
		
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		
		
		String fieldValue;
		while(rs.next())
		{	
			ArrayList<String> row = new ArrayList<String>();
			//Retrieve by column name
			for (int i = 0; i < length; i++)
			{
				fieldValue  = rs.getString(splitedColumns[i]);
				//System.out.print(fieldValue + "\t");
				row.add(fieldValue);
			}
			table.add(row);
			//System.out.println();
		}
        rs.close();
        stmt.close();
        return table;
	}
	
	public boolean customInsertion(String sql) throws ClassNotFoundException,SQLException
	{
		boolean result = true;
		try
		{
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
                        try{
                            ResultSetMetaData rsmd = rs.getMetaData();
                            int noOfColumns = rsmd.getColumnCount();
                        }catch(Exception e){}
                            
	        rs.close();
	        stmt.close();
		}
		catch(Exception e)
		{
			//System.out.println("\n\nTable Doesn't Exist!");
                        e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	public ArrayList<ArrayList<String>> customSelection(String sql) throws ClassNotFoundException,SQLException {
		ArrayList<ArrayList<String>> data = null;
		try
		{
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int noOfColumns = rsmd.getColumnCount();
			
			
			String[] columnNames = new String[noOfColumns];
			for (int i = 0; i < noOfColumns; i++)
			{
				columnNames[i] = rsmd.getColumnName(i+1);
			}
			
			/*
			System.out.println("\n\nRetreiving records from the table...\n");
			
			for (String cName : columnNames)
			{
				System.out.print(cName + " | ");
			}
			System.out.print("\n\n");
			*/
			
			data = new ArrayList<ArrayList<String>>();
			data.add(new ArrayList<String>(Arrays.asList(columnNames)));
			String fieldValue;
			while(rs.next())
			{
				//Retrieve by column name
				ArrayList<String> tmpRow = new ArrayList<String>(); 
				for (int i = 0; i < noOfColumns; i++ )
				{
					fieldValue = rs.getString(columnNames[i]);
					//System.out.print(fieldValue + "\t");
					tmpRow.add(fieldValue);
				}
				data.add(tmpRow);
				System.out.println();
			}
			
	        rs.close();
	        stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("\n\nError: "+sql);
			e.printStackTrace();
		}
		return data;
	}
	
	public void customDeletion(String sql) throws ClassNotFoundException,SQLException {
		PreparedStatement stmt = connection.prepareStatement(sql);

		try
		{
			stmt.executeUpdate();
			System.out.println("Deleted records from the table...");	
		}catch(SQLException e)
		{
			System.out.println("Error in Deleting records from the table...");
		}
        stmt.close();
	}
	
	public void deleteTable(String tableName) throws ClassNotFoundException,SQLException {
		
		String sql = "DROP TABLE "+ tableName + ";";
		PreparedStatement stmt = connection.prepareStatement(sql);
		try
		{
			stmt.executeUpdate();
			System.out.println("Table Deleted...");
        }catch(Exception e)
        {
			e.printStackTrace();
			System.out.println("Error in deleting the table...");
		}
		finally
		{
			stmt.close();
		}
	}
	
	public ArrayList<String> showDatabases() throws ClassNotFoundException,SQLException {
		System.out.println("Retreiving data from the Database...\n");
		
		int i = 1;
		String sql = "SHOW DATABASES;";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery(sql);
		ArrayList<String> dbNames = new ArrayList<String>();
		
		
		while(rs.next())
		{
			//Retrieve by column name
			String dbName  = rs.getString("Database");
			dbNames.add(dbName);
			//Display values
			System.out.print(i+" " + dbName+"\n");i++;
		}
		
		rs.close();
        stmt.close();
        return dbNames;
	}
		
	public ArrayList<String> showTables() throws ClassNotFoundException,SQLException {
		
		String sql = "SHOW TABLES;";
		PreparedStatement stmt = connection.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery(sql);
		ArrayList<String> tables = new ArrayList<String>();
		
		System.out.println("Tables of " + currentDatabaseName + "...\n");
		int tableIndex = 0;
		while(rs.next())
		{
			//Retrieve by column name
			String table  = rs.getString("Tables_in_" + currentDatabaseName);
			//Display values
			tables.add(table);
			System.out.println(tableIndex + " " + table);tableIndex++;
		}
		rs.close();
        stmt.close();
        return tables;
	}
	
	
    public static void main(String[] args) throws ClassNotFoundException,SQLException {
	// Testing 		
    	DatabaseOperator dbOperator1 = new DatabaseOperator();
        dbOperator1.connect("root","");
        //dbOperator1.createDatabase("healthhust");
        dbOperator1.showDatabases();
        dbOperator1.useDatabase("healthhust");
        dbOperator1.showTables();
        /*
        dbOperator1.showTableData("person");
        dbOperator1.showTableData("person","NIC,ID,age");
        dbOperator1.createTable("test3Table","( id INT NOT NULL,age INT NOT NULL,first VARCHAR(255),last VARCHAR(255),PRIMARY KEY ( id ))");
        dbOperator1.addTableRow("test3Table","123,20,Hoang,Hiep");
        dbOperator1.showTableData("test3Table");
        dbOperator1.deleteTableRow("test3Table","first","Heshan");
        dbOperator1.showTableData("test3Table");
        
        System.out.println(dbOperator1.showTableMetaData("healthhust").get(1).get(2));
        
        
        
        dbOperator1.deleteTable("healthhust");
        */
        
       // dbOperator1.showTableData("Person", "NIC,User_ID,Age","Last_name = 'HiepDeptrai'") ;
        
        //dbOperator1.createTable("Admin","( id INT NOT NULL,age INT NOT NULL,User_Name VARCHAR(30),password VARCHAR(25),PRIMARY KEY ( id ))");
        //dbOperator1.addTableRow("Admin","123,20,admin123,admiN123@");
        //dbOperator1.addTableRow("Admin","456,25,doc123,docT123@");
        
       // System.out.println(dbOperator1.customSelection("select Person.First_Name, Doctor.Available_Days FROM Person LEFT JOIN Doctor ON Person.User_ID = Doctor.User_ID;"));
        
        
        dbOperator1.close();
        
	
    }
}
