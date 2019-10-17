import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect
{	
	private Connection conn;
	private java.sql.Statement stmt;
	
	private String localIPAddress;
	
	public DBConnect()
	{
		try
		{
			localIPAddress = InetAddress.getLocalHost().getHostAddress();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void connect()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection("jdbc:mysql://" + localIPAddress + ":3306/easyorders_db?useSSL=false", "root", "");
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyorders_db?useSSL=false", "root", "kalispera");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.1.14:3306/easyorders_db?useSSL=false", "root", "");
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyorders_db?useSSL=false", "root", "ROBERTdoisneau1950");

			//?useSSL=false
			stmt = conn.createStatement();
//			StackTraceElement[] st = Thread.currentThread().getStackTrace();
//		    System.out.println(  "create connection called from " + st[2] );
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	public void closeConnection()
	{
		try
		{
			stmt.close();
			conn.close();
			
//			StackTraceElement[] st = Thread.currentThread().getStackTrace();
//		    System.out.println(  "close connection called from " + st[2] );
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public java.sql.Statement getStatement()
	{
		return stmt;
	}
	
	public Connection getConnection()
	{
		return conn;
	}
}
