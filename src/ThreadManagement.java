import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

public class ThreadManagement
{
	private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	//private final static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private ScheduledFuture<?> future;
	private ProductRefresher pr;
	private ClientRefresher cl;
	
	public void ManageModelUpdateAtTab(int selectedTabIndex, AbstractTableModel atm)
	{
		//TODO: Create a thread pool and run one at a time
		
		switch (selectedTabIndex)
		{
		
		//Orders Tab
		case 0:
			
			break;
			
		//Clients Tab
		case 1:
			((ClientsTableModel)atm).populate();
			//startProductsTableModelUpdates(atm);
			cl = new ClientRefresher((ClientsTableModel) atm, 1000);
			break;
			
		//Products Tab
		case 2:
			((ProductsTableModel)atm).populate();
			//startProductsTableModelUpdates(atm);
			pr = new ProductRefresher((ProductsTableModel) atm, 1000);
			break;
			
		//Record Tab
		case 3:
			
			break;
			
		default:
			break;
		}
	}
	
	public void stopProductRefresher()
	{
		pr.stop();
	}
	
	public void startProductRefresher()
	{
		pr.start();
	}
	
//	public void startProductsTableModelUpdates(AbstractTableModel atm)
//	{
//		//stopModelUpdates();
//		try {
//		this.future = executorService.scheduleAtFixedRate(new ProductRefresher((ProductsTableModel) atm), 0, 1500, TimeUnit.MILLISECONDS);
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
