import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

public class ThreadManagement {
	
	private Timer timer;
	private int interval;
	
	private ClientRefresher cl;
	private ProductRefresher pr;
	private RecordRefresher rr;
	
	public ThreadManagement(int interval)
	{
		this.interval = interval;
		this.timer = new Timer(interval, null);
	}

	public void ManageModelUpdateAtTab(int selectedTabIndex, AbstractTableModel atm)
	{
		//TODO: Create a thread pool and run one at a time
		
		switch (selectedTabIndex) {
		
		//Orders Tab
		case 0:
			
			break;
			
		//Clients Tab
		case 1:
			if (timer.isRunning())
				timer.stop();
			cl = new ClientRefresher((ClientsTableModel) atm);
			timer = new Timer(interval, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cl.refreshTick();
					
				}
			});
			timer.setInitialDelay(0);
			timer.start();
			break;
			
		//Products Tab
		case 2:
			if (timer.isRunning())
				timer.stop();
			pr = new ProductRefresher((ProductsTableModel) atm);
			timer = new Timer(interval, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					pr.refreshTick();
					
				}
			});
			timer.setInitialDelay(0);
			timer.start();
			break;
			
		//Record Tab
		case 3:
			if (timer.isRunning())
				timer.stop();
			rr = new RecordRefresher((RecordTableModel) atm);
			timer = new Timer(interval, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					rr.refreshTick();
					
				}
			});
			timer.setInitialDelay(0);
			timer.start();
			break;
			
		default:
			break;
		}
	}
	
	public void stopTicking()
	{
		timer.stop();
	}
	
	public void startTicking()
	{
		timer.start();
	}
}
