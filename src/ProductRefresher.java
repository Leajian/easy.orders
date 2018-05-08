import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.SwingWorker;
import javax.swing.Timer;

public class ProductRefresher// extends Thread
{
	private ProductsTableModel ptm;
	
	private String lastUpdate = "";
		
	private DBConnect db = new DBConnect();
	private Timer timer;

	//private boolean locked = false;
	
	public ProductRefresher(ProductsTableModel ptm, int interval)
	{
		db.connect();
		
		this.ptm = ptm;
		
		//now we call the model to populate the data to the table from the list
		//ptm.populate();
		
		lastUpdate = getLastEdit();
		
		System.out.println("i run once again");
		
		timer = new Timer(interval, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timer.stop();
				try
				{
					System.out.println("im still running");
					System.out.println("lastUpdate B4 check : " + lastUpdate);
					if (!getLastEdit().equals(lastUpdate))
					{	
						//for (Product prd : getNewestProducts())

							ptm.refresh(getNewestProducts());
					}
					
					lastUpdate = getLastEdit();
					
					System.out.println("lastUpdate AFTER check : " + lastUpdate);
					timer.start();
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		timer.start();
		
	}

//	@Override
//	public synchronized void run()
//	{
//		lock("refresher");
//		try
//		{
//			System.out.println("im still running");
//			System.out.println("lastUpdate B4 check : " + lastUpdate);
//			if (!getLastEdit().equals(lastUpdate))
//			{	
//				ptm.refresh(getNewestProducts());
//			}
//			
//			lastUpdate = getLastEdit();
//			
//			System.out.println("lastUpdate AFTER check : " + lastUpdate);
//			
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		unlock("refresher");
//	}
	
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
		
		String query = "SELECT DISTINCT id FROM product WHERE lastEdit > '" + lastUpdate + "'";
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
	
	
//	//lock mechanism for this thread
//    public synchronized void lock(String msg)
//    {
//    	System.out.println(msg + " - lock state : " + locked);
//    	if (locked)
//		{
//			try {
//				this.wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
//    	
//    	locked = true;
//		System.out.println(msg + " has set lock to true - lock state : " + locked);
//    }
//    
//    public synchronized void unlock(String msg)
//    {
//    	locked  = false;
//    	System.out.println(msg + " has set lock to false - lock state : " + locked);
//		
//		this.notifyAll();
//    	
//    }
    
    public void stop()
    {
    	timer.stop();
    }
    
    public void start()
    {
    	timer.restart();
    }
}