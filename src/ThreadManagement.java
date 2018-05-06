import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
			executorService.scheduleAtFixedRate(new ProductRefresher((ProductsTableModel)atm), 0, 5000, TimeUnit.MILLISECONDS);
			break;
			
		//Record Tab
		case 3:
			//Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new RecordRefresher((RecordTableModel)atm), 0, 10, TimeUnit.MILLISECONDS);
			break;
			
		default:
			break;
		}
	}
	

}
