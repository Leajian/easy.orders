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
}