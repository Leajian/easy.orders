import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public class RecordRefresher// extends Thread
{	
	private String lastUpdate = "";
		
	private DBConnect db = new DBConnect();
	private Timer timer;

	//private boolean locked = false;
	
	public RecordRefresher(RecordTableModel rtm, int interval)
	{
		db.connect();
		
		lastUpdate = getLastEdit();
		
		System.out.println("i run once again");
		
		timer = new Timer(interval, new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (!getLastEdit().equals(lastUpdate))
					rtm.populate();
			}
		});
		
		timer.start();
		
	}
	
	private String getLastEdit()
	{
		db.connect();
		try
		{
			String query = "SELECT DISTINCT MAX(lastEdit) FROM orders";
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
	
	private ArrayList<Order> getNewestOrders()
	{
		ArrayList<Order> newOrdersList = new ArrayList<>();
		
		db.connect();
		try
		{
			String query = "SELECT DISTINCT lastEdit, clientId, employeeUsername, closed FROM orders WHERE lastEdit > '" + lastUpdate + "'";
			ResultSet  rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
			{
				newOrdersList.add(new Order(rs.getString("lastEdit"), rs.getString("clientId"), rs.getString("employeeUsername"), rs.getString("closed")));
				System.out.println("id[" + rs.getString("id") + "]  New product found or viewed!");
			}
			
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		return newOrdersList;
	}
    
    public void stop()
    {
    	timer.stop();
    }
    
    public void start()
    {
    	timer.restart();
    }
}