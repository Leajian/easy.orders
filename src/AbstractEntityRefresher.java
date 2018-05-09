import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

public abstract class AbstractEntityRefresher {

	private String lastUpdate = "";
	
	private DBConnect db = new DBConnect();
	private Timer timer;
	
	public AbstractEntityRefresher(AbstractTableModel atm, int interval, String entity) {
		
		db.connect();
		
		lastUpdate = getLastEditOf(entity);
		
		System.out.println("i run once again");
		
		timer = new Timer(interval, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!getLastEditOf(entity).equals(lastUpdate))
				{
					populator(atm);
				}
			}
		});
		
		timer.start();
	}
	
	protected String getLastEditOf(String entity)
	{
		db.connect();
		try
		{
			String query = "SELECT DISTINCT MAX(lastEdit) FROM " + entity;
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			System.out.println("time[" + rs.getString("MAX(lastEdit)") + "]  Refresh tick");
			return rs.getString("MAX(lastEdit)");
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return null;	
	}
	
	protected abstract void populator(AbstractTableModel atm);
	
	protected void stop()
    {
    	timer.stop();
    }
    
	protected void start()
    {
    	timer.restart();
    }
	
}
