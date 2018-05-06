import java.sql.ResultSet;
import java.util.ArrayList;

public class ThreadManagement {
	
	private DBConnect db = new DBConnect();
	private ArrayList<Product> products = new ArrayList<>();

	public ThreadManagement()
	{
		db.connect();
	}
	
	public class ProductRefresher implements Runnable
	{
		private String oldEdit = "";
		private ProductsTableModel ptm;
		
		public ProductRefresher(ProductsTableModel ptm) {
			this.ptm = ptm;
		}

		
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
					
					
					try
					{
						query = "SELECT orders.lastEdit, product.id AS productId, client.id AS clientId,\r\n" + 
								"quantityWeight,\r\n" + 
								"orders.price\r\n" + 
								"FROM client, product, orders\r\n" + 
								"WHERE orders.clientId = client.id\r\n" + 
								"AND orders.productId = product.id\r\n" +
								"AND orders.lastEdit >= '" + lastEdit + "' ORDER BY orders.lastEdit";
						
						ResultSet rs2 = db.getStatement().executeQuery(query);
						
						for(int i = oldLength; i < orders.size(); i++)
						{
							while(rs2.next())
								if(orders.get(i).getLastEdit().equals(rs2.getString("orders.lastEdit")) & (orders.get(i).getClientId().equals(rs2.getString("clientId"))))
									orders.get(i).getProducts().add(new Product(rs2.getString("productId"), rs2.getString("quantityWeight"), rs2.getString("orders.price")));
							
							rs2.beforeFirst();
						}
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
										
					oldEdit = lastEdit;
				}
					
			}
			catch (Exception ex)
			{
					//ex.printStackTrace();
			}

		
		}
	}

}
