import javax.swing.table.AbstractTableModel;

public class ProductRefresher extends AbstractEntityRefresher
{
	public ProductRefresher(AbstractTableModel atm) {
		super(atm, "product");
		System.out.println("Product refresher instancieted");
	}
	
	@Override
	protected void populator() {
		((ProductsTableModel) obj).populate();
	}
}