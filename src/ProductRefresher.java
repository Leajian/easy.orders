import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductRefresher implements Runnable
{
	private ProductsTableModel ptm;
	
	private String lastUpdate = "";
		
	DBConnect db = new DBConnect();
		
	public ProductRefresher(ProductsTableModel ptm)
	{
		db.connect();
		
		this.ptm = ptm;
		
		//now we call the model to populate the data to the table from the list
		ptm.populate();
		
		lastUpdate = getLastEdit();
		
		System.out.println("i runned once again");
	}

	@Override
	public void run()
	{
		try
		{
			System.out.println("im still running bitch");
			System.out.println("lastUpdate B4 check : " + lastUpdate);
			if (!getLastEdit().equals(lastUpdate))
			{	
				ptm.refresh(getNewestProducts());
			}
			
			lastUpdate = getLastEdit();
			System.out.println("lastUpdate AFTER check : " + lastUpdate);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		//TODO: implement sync
	}
	
	private String getLastEdit()
	{
		String query = "SELECT DISTINCT MAX(lastEdit) FROM product";
		ResultSet rs;
		try {
			rs = db.getStatement().executeQuery(query);
			rs.next();
			System.out.println("time[" + rs.getString("MAX(lastEdit)") + "]  Refresh tick");
			return rs.getString("MAX(lastEdit)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	private ArrayList<Product> getNewestProducts()
	{
		ArrayList<Product> newProductsList = new ArrayList<>();
		
		String query = "SELECT id FROM product WHERE lastEdit > '" + lastUpdate + "'";
		ResultSet rs1;
		try
		{
			rs1 = db.getStatement().executeQuery(query);
			
			while(rs1.next())
			{
				newProductsList.add(new Product(rs1.getString("id")));
				System.out.println("id[" + rs1.getString("id") + "]  New product found or viewed!");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newProductsList;
	}
}