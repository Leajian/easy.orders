import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.swing.table.AbstractTableModel;

public abstract class ThreadManagement {
	
	private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	
	public static void ManageModelUpdateAtTab(int selectedTabIndex, AbstractTableModel atm)
	{
		//TODO: Create a thread pool and run one at a time
		
		switch (selectedTabIndex) {
		
		//Orders Tab
		case 0:
			
			break;
			
		//Clients Tab
		case 1:
			//Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new ClientRefresher((ClientsTableModel)atm), 0, 10, TimeUnit.MILLISECONDS);
			break;
			
		//Products Tab
		case 2:
			//Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new ProductRefresher((ProductsTableModel)atm), 0, 5000, TimeUnit.MILLISECONDS);
			scheduleAtFixedRate(new ProductRefresher((ProductsTableModel)atm), 5000);
			break;
			
		//Record Tab
		case 3:
			//Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new RecordRefresher((RecordTableModel)atm), 0, 10, TimeUnit.MILLISECONDS);
			break;
			
		default:
			break;
		}
	}
	
	private static void scheduleAtFixedRate(Runnable r, int sleepTime)
	{
		Thread t = new Thread(r);
		
		
		//while you are dead
		while(!t.isAlive())
		{
			//revive
			t.start();
			
			try {
				//wait until you finish
				t.join();
				
				//wait desired interval
				Thread.sleep(sleepTime);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	

}
