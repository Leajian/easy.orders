import java.sql.ResultSet;
import java.util.ArrayList;

public class Order
{
	private ArrayList<Product> products = new ArrayList<Product>();
	private String last_edit, customer_id, employee_username, closed;
	
	private DBConnect db = new DBConnect();
	
	public Order(String last_edit, String customer_id, String employee_username, String closed)
	{
		db.connect();
		
		this.last_edit = last_edit;
		this.customer_id = customer_id;
		this.employee_username = employee_username;
		this.closed = closed;
	}

	public void addProduct(Product aProduct)
	{
		products.add(aProduct);
	}

	public ArrayList<Product> getProducts()
	{
		return products;
	}

	public String getLast_edit()
	{
		return last_edit;
	}

	public String getCustomer_id()
	{
		return customer_id;
	}
	
	public String getCustomer_name()
	{
		try
		{
			String query = "SELECT name FROM customer WHERE id = " + customer_id;
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

	public String getEmployee_username()
	{
		return employee_username;
	}

	public String isClosed()
	{
		return closed;
	}
}