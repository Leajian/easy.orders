import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractEntityRefresher
{
	private String lastUpdate = "";
	private String entity;
	
	private DBConnect db = new DBConnect();
	protected Object obj;
	
	public AbstractEntityRefresher(Object obj, String entity)
	{
		this.obj = obj;
		this.entity = entity;
	}
	
	protected String getLastEditOf(String entity)
	{
		db.connect();
		try
		{
			String query = "SELECT DISTINCT MAX(lastEdit) FROM " + entity;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			String lastEdit = rs.getString("MAX(lastEdit)");
			
			db.closeConnection();
			
			//System.out.println("time[" + rs.getString("MAX(lastEdit)") + "]  MAX asked.");

			return lastEdit;
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return null;
	}
	
	protected abstract void populator();
	
	protected void refreshTick()
	{
		System.out.println("tick");
		String lastEdit = getLastEditOf(entity);
		
		if(lastEdit != null)
		{
			if(!getLastEditOf(entity).equals(lastUpdate))
				populator();
		}
		else
			System.out.println("NULL");
			
		
		
		
		lastUpdate = lastEdit;
	}
}