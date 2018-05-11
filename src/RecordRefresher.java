import javax.swing.table.AbstractTableModel;

public class RecordRefresher extends AbstractEntityRefresher
{
	public RecordRefresher(AbstractTableModel atm)
	{
		super(atm, "orders");
	}

	@Override
	protected void populator()
	{
		((RecordTableModel) obj).populate();
	}
	
	@Override
	protected int getObjSize(Object obj) {
		return ((RecordTableModel) obj).getRowCount();
	}
}