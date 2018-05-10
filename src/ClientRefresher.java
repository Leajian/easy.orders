import javax.swing.table.AbstractTableModel;

public class ClientRefresher extends AbstractEntityRefresher
{
	public ClientRefresher(AbstractTableModel atm) {
		super(atm, "client");
		System.out.println("Client refresher instancieted");
	}
	
	@Override
	protected void populator() {
		((ClientsTableModel) obj).populate();
	}
}