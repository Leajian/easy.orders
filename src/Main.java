import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main 
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException
	{	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		DBConnect db = new DBConnect();
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		ArrayList<Order> orders = new ArrayList<Order>();

		db.connect();
		
		//new NewCustomerFrame();
		new SellerMainFrame();
		
		/*try
		{	
			String query = "SELECT DISTINCT last_edit, customer_id, employee_username, closed\r\n" + 
					"FROM orders\r\n" + 
					"WHERE closed = '0' ORDER BY last_edit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				orders.add(new Order(rs.getString("last_edit"), rs.getString("customer_id"), rs.getString("employee_username"), rs.getString("closed")));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			//Return products.
			String query = "SELECT last_edit, product.id AS product_id,\r\n" + 
					"quantity_weight,\r\n" + 
					"orders.price\r\n" + 
					"FROM customer, product, orders\r\n" + 
					"WHERE orders.customer_id = customer.id\r\n" + 
					"AND orders.product_id = product.id\r\n" +
					"AND closed = '0' ORDER BY last_edit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			for(Order order: orders)
			{
				while(rs.next())
					if(order.getLast_edit().equals(rs.getString("last_edit")))
						order.getProducts().add(new Product(rs.getString("product_id"), rs.getString("quantity_weight"), rs.getString("orders.price")));
				
				rs.beforeFirst();
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(!orders.isEmpty())
			orders.remove(orders.size() - 1);
		
		for(Order order: orders)
		{
			System.out.println(order.getCustomer_name() + " " + order.getLast_edit());
			
			for(Product product: order.getProducts())
				System.out.println(product.getName());
		}
			
		executorService.scheduleAtFixedRate(new Runnable()
		{
			String oldEdit = "";
			
			@Override
			public void run()
			{	
				try
				{
					int oldLength = orders.size();
					
					String query = "SELECT DISTINCT MAX(last_edit) FROM orders";
					ResultSet rs = db.getStatement().executeQuery(query);
					
					rs.next();
					
					String lastEdit = rs.getString("MAX(last_edit)");
					
					if (!lastEdit.equals(oldEdit))
					{
						try
						{
							query = "SELECT DISTINCT last_edit, customer_id, employee_username, closed\r\n" + 
									"FROM orders\r\n" + 
									"WHERE closed = '0'\r\n" + 
									"AND last_edit >= '" + lastEdit + "'\r\n" +
									"ORDER BY last_edit";
							
							ResultSet rs1 = db.getStatement().executeQuery(query);
							
							while(rs1.next())
								orders.add(new Order(rs1.getString("last_edit"), rs1.getString("customer_id"), rs1.getString("employee_username"), rs1.getString("closed")));
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
						
						try 
						{
							query = "SELECT last_edit, product.id AS product_id,\r\n" + 
									"quantity_weight,\r\n" + 
									"orders.price\r\n" + 
									"FROM customer, product, orders\r\n" + 
									"WHERE orders.customer_id = customer.id\r\n" + 
									"AND orders.product_id = product.id\r\n" +
									"AND last_edit >= '" + lastEdit + "' ORDER BY last_edit";
							
							ResultSet rs2 = db.getStatement().executeQuery(query);
							
							for(int i = oldLength; i < orders.size(); i++)
							{
								while(rs2.next())
									if(orders.get(i).getLast_edit().equals(rs2.getString("last_edit")))
										orders.get(i).getProducts().add(new Product(rs2.getString("product_id"), rs2.getString("quantity_weight"), rs2.getString("orders.price")));
								
								rs2.beforeFirst();
							}
						} 
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
											
						oldEdit = lastEdit;
						
						for(int i = oldLength; i < orders.size(); i++)
						{
							System.out.println(orders.get(i).getCustomer_name() + " " + orders.get(i).getLast_edit());
							
							for(Product product: orders.get(i).getProducts())
								System.out.println(product.getName());
						}
						
						System.out.println(orders);
					}
				}
				catch (Exception ex)
				{
					//ex.printStackTrace();
				}
			}
		}, 0, 10, TimeUnit.MILLISECONDS);*/
	}
}