import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class Employee
{
	private DBConnect db = new DBConnect();
	
	private String name, username;
	private int privilege;
	
	public Employee(String username)
	{
		this.username = username;
		
		db.connect();
		try
		{
			String query = "SELECT password, name, privilege FROM employee WHERE username = '" + username + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			this.name = rs.getString("name");
			this.privilege = rs.getInt("privilege");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getUsername()
	{
		return username;
	}

	public int getPrivilege()
	{
		return privilege;
	}
}