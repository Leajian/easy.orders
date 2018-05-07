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
		
		db.closeConnection();
	}

	@Override
	public void run()
	{
		try
		{
			if(!getLastEdit().equals(lastUpdate))
			{
				//System.out.println(lastUpdate);				
				ptm.refresh(getNewestProducts());
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
			
		lastUpdate = getLastEdit();
	}
	
	private String getLastEdit()
	{
		db.connect();
		
		try
		{
			String query = "SELECT DISTINCT MAX(lastEdit) FROM product";	
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			return rs.getString("MAX(lastEdit)");
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		db.closeConnection();
		
		return null;	
	}
	
	private ArrayList<Product> getNewestProducts()
	{
		ArrayList<Product> newProductsList = new ArrayList<>();
		
		db.connect();
		
		try
		{
			String query = "SELECT id FROM product WHERE lastEdit > '" + lastUpdate + "'";
			ResultSet rs1 = db.getStatement().executeQuery(query);
			
			while(rs1.next())
				newProductsList.add(new Product(rs1.getString("id")));
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		
		db.closeConnection();
		
		return newProductsList;
	}
}