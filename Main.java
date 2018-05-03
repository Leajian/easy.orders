import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	
		new SellerMainFrame();
		
		//new ClientInfoFrame("555555555");
		
		/*try
		{	
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, closed\r\n" + 
					"FROM orders\r\n" + 
					"WHERE closed = '0' ORDER BY lastEdit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				orders.add(new Order(rs.getString("lastEdit"), rs.getString("customerId"), rs.getString("employeeUsername"), rs.getString("closed")));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		try
		{
			//Return products.
			String query = "SELECT lastEdit, product.id AS productId,\r\n" + 
					"quantityWeight,\r\n" + 
					"orders.price\r\n" + 
					"FROM client, product, orders\r\n" + 
					"WHERE orders.clientId = client.id\r\n" + 
					"AND orders.productId = product.id\r\n" +
					"AND closed = '0' ORDER BY lastEdit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			for(Order order: orders)
			{
				while(rs.next())
					if(order.getLastEdit().equals(rs.getString("lastEdit")))
						order.getProducts().add(new Product(rs.getString("productId"), rs.getString("quantityWeight"), rs.getString("orders.price")));
				
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
			System.out.println(order.getClientName() + " " + order.getLastEdit());
			
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
					
					String query = "SELECT DISTINCT MAX(lastEdit) FROM orders";
					ResultSet rs = db.getStatement().executeQuery(query);
					
					rs.next();
					
					String lastEdit = rs.getString("MAX(lastEdit)");
					
					if (!lastEdit.equals(oldEdit))
					{
						try
						{
							query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, closed\r\n" + 
									"FROM orders\r\n" + 
									"WHERE closed = '0'\r\n" + 
									"AND lastEdit >= '" + lastEdit + "'\r\n" +
									"ORDER BY lastEdit";
							
							ResultSet rs1 = db.getStatement().executeQuery(query);
							
							while(rs1.next())
								orders.add(new Order(rs1.getString("lastEdit"), rs1.getString("clientId"), rs1.getString("employeeUsername"), rs1.getString("closed")));
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
						
						try 
						{
							query = "SELECT lastEdit, product.id AS productId,\r\n" + 
									"quantityWeight,\r\n" + 
									"orders.price\r\n" + 
									"FROM client, product, orders\r\n" + 
									"WHERE orders.clientId = client.id\r\n" + 
									"AND orders.productId = product.id\r\n" +
									"AND last_edit >= '" + lastEdit + "' ORDER BY lastEdit";
							
							ResultSet rs2 = db.getStatement().executeQuery(query);
							
							for(int i = oldLength; i < orders.size(); i++)
							{
								while(rs2.next())
									if(orders.get(i).getLastEdit().equals(rs2.getString("lastEdit")))
										orders.get(i).getProducts().add(new Product(rs2.getString("productId"), rs2.getString("quantityWeight"), rs2.getString("orders.price")));
								
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
							System.out.println(orders.get(i).getClientName() + " " + orders.get(i).getLastEdit());
							
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