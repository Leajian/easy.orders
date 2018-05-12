import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractEntityRefresher
{
	private String lastUpdate = "";
	private String entity;
	
	private DBConnect db = new DBConnect();
	protected Object obj;
	protected int lastUpdatedSize = -1;
	
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
	
	protected abstract int getObjSize(Object obj);
	
	protected void refreshTick()
	{
		System.out.println("tick");
		String lastEdit = getLastEditOf(entity);
		int lastSize = getObjSize(obj);
		
		System.out.println(lastSize);
		
		//if there is any entity at all
		if(lastEdit != null)
		{
			//if it is different from before regarding time
			if(!lastEdit.equals(lastUpdate))
			{
				//populate it how ever you like
				populator();
			
				//now we update the lastUpdate time with the one we just refreshed with
				lastUpdate = lastEdit;
			}
			
			//if it is different from before regarding size
			if(lastSize != lastUpdatedSize)
			{
				//populate it how ever you like
				populator();

				//now we update the lastUpdatedSize with the one we just refreshed with
				lastUpdatedSize = lastSize;
				
				System.out.println("last size " + lastUpdatedSize);
			}
		}
		else
			//update once if there was no entity at all
			populator();
	}
}