import java.sql.ResultSet;

import java.util.ArrayList;

public class ProductRefresher implements Runnable
{
	private ArrayList<Product> refreshedProducts = new ArrayList<>();
	private ProductsTableModel ptm;
		
	DBConnect db = new DBConnect();
		
	public ProductRefresher(ProductsTableModel ptm)
	{
		db.connect();
		
		this.ptm = ptm;
		ptm.populate();
	}
	
	String oldEdit = "";

	@Override
	public void run()
	{
		try
		{
			String query = "SELECT DISTINCT MAX(lastEdit) FROM product";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			String lastEdit = rs.getString("MAX(lastEdit)");
			
			if (!lastEdit.equals(oldEdit))
			{
				System.out.println(lastEdit);
				oldEdit = lastEdit;
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}