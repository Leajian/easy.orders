import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main 
{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException
	{	
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		DBConnect db = new DBConnect();
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		ArrayList<Order> orders = new ArrayList<Order>();

		//db.connect();
	
		Employee admin = new Employee("admin");
		new SellerMainFrame(admin);
		
		/*try
		{	
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, closed\r\n" + 
					"FROM orders\r\n" + 
					"WHERE closed = '0' ORDER BY lastEdit";
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				orders.add(new Order(rs.getString("lastEdit"), rs.getString("clientId"), rs.getString("employeeUsername"), rs.getInt("closed")));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		for(Order order: orders)
		{
			System.out.println(order.getClientId() + " " + order.getLastEdit());
			
			for(Product product: order.getProducts())
				System.out.println(product.getId());
		}
		
		
		
		String query = "SELECT DISTINCT MAX(lastEdit) FROM orders";
		ResultSet rs = db.getStatement().executeQuery(query);
		
		rs.next();
		
		String temp = rs.getString("MAX(lastEdit)");
		
		
		
		Timer timer = new Timer(100, new ActionListener()
		{
			String oldEdit = temp;
			@Override
			public void actionPerformed(ActionEvent e)
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
									"AND lastEdit = '" + lastEdit + "'\r\n" +
									"ORDER BY lastEdit";
							
							ResultSet rs1 = db.getStatement().executeQuery(query);
							
							while(rs1.next())
								orders.add(new Order(rs1.getString("lastEdit"), rs1.getString("clientId"), rs1.getString("employeeUsername"), rs1.getInt("closed")));
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
		});
		
		timer.start();*/
	}
}