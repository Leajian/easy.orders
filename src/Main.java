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
	
		//new SellerMainFrame();
		
		
		executorService.scheduleAtFixedRate((new ProductRefresher(new ProductsTableModel(DataFetchingManagment.initializeProducts()))), 0, 10, TimeUnit.MILLISECONDS);
		
		
		
		/*try
		{	
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, closed\r\n" + 
					"FROM orders\r\n" + 
					"WHERE closed = '0' ORDER BY lastEdit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				orders.add(new Order(rs.getString("lastEdit"), rs.getString("clientId"), rs.getString("employeeUsername"), rs.getString("closed")));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		if(!orders.isEmpty())
			orders.remove(orders.size() - 1);
		
		for(Order order: orders)
		{
			System.out.println(order.getClientId() + " " + order.getLastEdit());
			
			for(Product product: order.getProducts())
				System.out.println(product.getId());
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
								orders.add(new Order(rs1.getString("orders.lastEdit"), rs1.getString("clientId"), rs1.getString("employeeUsername"), rs1.getString("closed")));
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
											
						oldEdit = lastEdit;
						
						for(int i = oldLength; i < orders.size(); i++)
						{
							System.out.println(orders.get(i).getClientId() + " " + orders.get(i).getLastEdit());
							
							for(Product product: orders.get(i).getProducts())
								System.out.println(product.getId());
						}
						
						System.out.println("Size = " + orders.size());
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