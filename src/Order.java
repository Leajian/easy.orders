import java.sql.ResultSet;
import java.util.ArrayList;

public class Order
{
	private ArrayList<Product> products = new ArrayList<Product>();
	private String lastEdit, clientId, employeeUsername, closed;
	
	private DBConnect db = new DBConnect();
	
	public Order(String lastEdit, String clientId, String employeeUsername, String closed)
	{
		db.connect();
		
		this.lastEdit = lastEdit;
		this.clientId = clientId;
		this.employeeUsername = employeeUsername;
		this.closed = closed;
		
		try
		{
			String query = "SELECT productId, quantityWeight, price \n" + 
					"FROM orders \n" + 
					"WHERE lastEdit = '" + lastEdit + "'" + "AND clientId = '" + clientId + "'";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				this.getProducts().add(new Product(rs.getString("productId"), rs.getString("quantityWeight"), rs.getString("price")));
			
			rs.beforeFirst();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void addProduct(Product aProduct)
	{
		products.add(aProduct);
	}

	public ArrayList<Product> getProducts()
	{
		return products;
	}

	public String getLastEdit()
	{
		return lastEdit;
	}

	public String getClientId()
	{
		return clientId;
	}
	
	public String getClientName()
	{
		try
		{
			String query = "SELECT name FROM client WHERE id = " + clientId;
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

	public String getEmployeeUsername()
	{
		return employeeUsername;
	}

	public String isClosed()
	{
		return closed;
	}
}