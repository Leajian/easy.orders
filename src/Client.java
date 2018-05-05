import java.sql.ResultSet;

public class Client
{
	private String id, name, city, phoneNumber, email, address, fax, zipCode, notes;
	
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
	
	public void setName(String name)
	{
		this.name = name;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public void setZipCode(String zipCode)
	{
		this.zipCode = zipCode;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}
}