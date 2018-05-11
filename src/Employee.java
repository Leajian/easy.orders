import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Employee
{
	private String name, username, password;
	private int privilege;
	
	public Employee(String name, String username, String password, int privilege)
	{
		this.name = name;
		this.username = username;
		this.password = password;
		this.privilege = privilege;
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