import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractEntityRefresher
{
	protected String lastUpdate = "";
	private String entity;
	
	private DBConnect db = new DBConnect();
	private AbstractTableModel atm;
	
	public AbstractEntityRefresher(AbstractTableModel atm, String entity)
	{
		this.atm = atm;
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
	
	protected abstract void populator(AbstractTableModel atm);
	
	protected void refreshTick()
	{
		System.out.println("tick");
		
		if (!getLastEditOf(entity).equals(lastUpdate))
		{
			//System.out.println("populator called");
			populator(atm);
		}
		
		lastUpdate = getLastEditOf(entity);
	}
}
