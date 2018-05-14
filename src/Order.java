import java.sql.ResultSet;
import java.util.ArrayList;

public class Order
{
	private ArrayList<Product> products = new ArrayList<Product>();
	private String lastEdit, clientId, employeeUsername, clientName;
	private int state;
	//States
	//0 - open
	//1 - billing
	//2 - closed
	
	private DBConnect db = new DBConnect();
	
	public Order(String lastEdit, String clientId, String employeeUsername, int state)
	{	
		this.lastEdit = lastEdit;
		this.clientId = clientId;
		this.employeeUsername = employeeUsername;
		this.state = state;
		
		db.connect();
		try
		{
			String query = "SELECT name FROM client WHERE id = " + clientId;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			this.clientName = rs.getString("name");
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		db.connect();
		try
		{
			String query = "SELECT productId, quantityWeight, price \n" + 
					"FROM orders \n" + 
					"WHERE lastEdit = '" + lastEdit + "'" + "AND clientId = '" + clientId + "'";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				products.add(new Product(rs.getString("productId"), rs.getString("quantityWeight"), rs.getString("price")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
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
		return clientName;
	}

	public String getEmployeeUsername()
	{
		return employeeUsername;
	}

	public int getState()
	{
		return state;
	}
}