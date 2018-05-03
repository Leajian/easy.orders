import java.sql.ResultSet;

public class Client
{
	private String id;
	
	private DBConnect db = new DBConnect();
	
	public Client(String id)
	{
		db.connect();
		
		this.id = id;
	}
	
	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		try
		{
			String query = "SELECT name FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("name");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getCity()
	{
		try
		{
			String query = "SELECT city FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("city");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getPhoneNumber()
	{
		try
		{
			String query = "SELECT phoneNumber FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("phoneNumber");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getEmail()
	{
		try
		{
			String query = "SELECT email FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("email");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getAddress()
	{
		try
		{
			String query = "SELECT address FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("address");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getFax()
	{
		try
		{
			String query = "SELECT fax FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("fax");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getZipCode()
	{
		try
		{
			String query = "SELECT zipCode FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("zipCode");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";	
	}
	
	public String getNotes()
	{
		try
		{
			String query = "SELECT notes FROM client WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("notes");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
}