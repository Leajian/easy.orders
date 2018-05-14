import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

public class Employee
{
	private DBConnect db = new DBConnect();
	
	private String name, username, password;
	private int privilege;
	
	public Employee(String username)
	{
		this.username = username;
		
		db.connect();
		try
		{
			String query = "SELECT password, name, privilege WHERE username = '" + username + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			this.name = rs.getString("name");
			this.password = rs.getString("password");
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

	public String getPassword()
	{
		return password;
	}

	public int getPrivilege()
	{
		return privilege;
	}
	
	static String md5(String password)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes(), 0, password.length());
			
			return new BigInteger(1, md5.digest()).toString(16);
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return password;
	}
}