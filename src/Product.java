import java.sql.ResultSet;

public class Product
{
	private String lastEdit, id, name, quality, location, producer, packaging, price, stock, quantityWeight;

	private DBConnect db = new DBConnect();
	
	public Product(String id, String price, String quantityWeight)
	{
		db.connect();
		
		this.id = id;
		this.quantityWeight = quantityWeight;
		this.price = price;
		
		try
		{
			String query = "SELECT * FROM product WHERE id = '" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			this.lastEdit = rs.getString("lastEdit");
			this.name = rs.getString("name");
			this.quality = rs.getString("quality");
			this.location = rs.getString("location");
			this.producer = rs.getString("producer");
			this.packaging = rs.getString("packaging");
			this.stock = rs.getString("stock");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public Product(String id)
	{
		db.connect();
		
		this.id = id;
		
		try
		{
			String query = "SELECT * FROM product WHERE id = '" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			this.lastEdit = rs.getString("lastEdit");
			this.name = rs.getString("name");
			this.quality = rs.getString("quality");
			this.location = rs.getString("location");
			this.producer = rs.getString("producer");
			this.packaging = rs.getString("packaging");
			this.price = rs.getString("price");
			this.stock = rs.getString("stock");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String getLastEdit()
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

	public String getQuality()
	{
		return quality;
	}

	public String getLocation()
	{
		return location;
	}

	public String getProducer()
	{
		return producer;
	}

	public String getPackaging()
	{
		return packaging;
	}

	public String getPrice()
	{
		return price;
	}

	public String getStock()
	{
		return stock;
	}

	public String getQuantityWeight()
	{
		return quantityWeight;
	}
	
	public boolean isEditable()
	{
		try
		{
			String query = "SELECT isEditable from product WHERE id = '" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			if(rs.getInt("isEditable") == 1)
				return true;
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return false;
	}
}