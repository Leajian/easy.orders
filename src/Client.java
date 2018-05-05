import java.sql.ResultSet;

public class Client
{
	private String lastEdit, id, name, city, phoneNumber, email, address, fax, zipCode, notes;
	
	private DBConnect db = new DBConnect();
	
	public Client(String id)
	{
		db.connect();
		
		this.id = id;
		
		try
		{
			String query = "SELECT * FROM client WHERE id = '" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			this.lastEdit = rs.getString("lastEdit");
			this.name = rs.getString("name");
			this.city = rs.getString("city");
			this.phoneNumber = rs.getString("phoneNumber");
			this.email = rs.getString("email");
			this.address = rs.getString("address");
			this.fax = rs.getString("fax");
			this.zipCode = rs.getString("zipCode");
			this.notes = rs.getString("notes");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String lastEdit()
	{
		return lastEdit;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	public String getCity()
	{
		return city;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public String getAddress()
	{
		return address;
	}

	public String getFax()
	{
		return fax;
	}

	public String getZipCode()
	{
		return zipCode;
	}

	public String getNotes()
	{
		return notes;
	}
}