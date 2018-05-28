import java.sql.ResultSet;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public abstract class DataFetcher
{	
	static DBConnect db = new DBConnect();
	
	public static ArrayList<Client> initializeClients()
	{
		ArrayList<Client> clients = new ArrayList<>();
		
		db.connect();
		try
		{
			String query = "SELECT id FROM client ORDER BY id";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				clients.add(new Client(rs.getString("id")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return clients;
	}
	
	public static ArrayList<Product> initializeProducts()
	{
		ArrayList<Product> products = new ArrayList<>();
		
		db.connect();
		try
		{
			String query = "SELECT id FROM product ORDER BY id";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				products.add(new Product(rs.getString("id")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return products;
	}
	
	public static ArrayList<Order> RecordOfClient(Client client)
	{
		ArrayList<Order> ordersRecord = new ArrayList<>();
		
		db.connect();		
		try
		{
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, state FROM orders WHERE state = 2 AND clientId = '" + client.getId() + "' ORDER BY lastEdit";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				ordersRecord.add(new Order(rs.getString("lastEdit"), rs.getString("clientId"), rs.getString("employeeUsername"), rs.getInt("state")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return ordersRecord;
	}
	
	public static ArrayList<Order> initializeOrders(Employee user)
	{
		ArrayList<Order> liveOrders = new ArrayList<>();
		
		db.connect();
		try
		{
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, state FROM orders WHERE state = 2 ORDER BY lastEdit";
			
			if(user != null)
			{
				switch(user.getPrivilege())
				{
				case 1:
					query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, state FROM orders WHERE state = 1 OR (employeeUsername = '" + user.getUsername() + "' AND state = 0) ORDER BY lastEdit";
					break;
				case 2:
					query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, state FROM orders WHERE state = 0 AND employeeUsername = '" + user.getUsername() + "' ORDER BY lastEdit";
					break;
				default:
					break;
				}
			}

			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				liveOrders.add(new Order(rs.getString("lastEdit"), rs.getString("clientId"), rs.getString("employeeUsername"), rs.getInt("state")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return liveOrders;
	}
	
	public static ArrayList<Employee> initializeEmployees()
	{
		ArrayList<Employee> employees = new ArrayList<>();
		
		db.connect();
		try
		{
			String query = "SELECT * FROM employee";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				employees.add(new Employee(rs.getString("username")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return employees;
	}
}