import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderingManagment
{	
	public OrderingManagment(DBConnect db)
	{
		
	}
	
	public static ArrayList<Product> fetchProducts()
	{
		ArrayList<Product> products = new ArrayList<>();
		DBConnect db = new DBConnect();
		
		db.connect();
		
		try
		{
			String query = "SELECT id FROM product";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				products.add(new Product(rs.getString("id")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return products;
	}
	
	public static ArrayList<Client> fetchClients()
	{
		ArrayList<Client> clients = new ArrayList<>();
		DBConnect db = new DBConnect();
		
		db.connect();
		
		try
		{
			String query = "SELECT id FROM client";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				clients.add(new Client(rs.getString("id")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return clients;
	}
}