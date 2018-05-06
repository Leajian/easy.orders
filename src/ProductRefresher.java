import java.sql.ResultSet;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProductRefresher implements Runnable
{
	private ProductsTableModel ptm;
	
	private String oldEdit = "";
		
	DBConnect db = new DBConnect();
		
	public ProductRefresher(ProductsTableModel ptm)
	{
		db.connect();
		
		this.ptm = ptm;
		
		//now we call the model to populate the data to the table from the list
		ptm.populate();
	}

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
		
		//ptm.refresh(product);
	}
}