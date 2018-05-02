import java.sql.ResultSet;

public class Product
{
	private String id;
	private String quantity_weight, price;

	private DBConnect db = new DBConnect();
	
	public Product(String id, String quantity_weight, String price)
	{
		db.connect();
		
		this.id = id;
		this.quantity_weight = quantity_weight;
		this.price = price;
	}

	public String getId()
	{
		return id;
	}
	
	public String getName()
	{
		try
		{
			String query = "SELECT name FROM product WHERE id = " + id;
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
	
	public String getQuality()
	{
		try
		{
			String query = "SELECT quality FROM product WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("quality");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getLocation()
	{
		try
		{
			String query = "SELECT location FROM product WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("location");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getProducer()
	{
		try
		{
			String query = "SELECT producer FROM product WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("producer");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public String getPackaging()
	{
		try
		{
			String query = "SELECT packaging FROM product WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getString("packaging");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return "";
	}
	
	public int getStock()
	{
		try
		{
			String query = "SELECT stock FROM product WHERE id = " + id;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			return rs.getInt("stock");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return 0;
	}

	public String getQuantity_weight()
	{
		return quantity_weight;
	}

	public String getPrice()
	{
		return price;
	}
}