import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class OrderedProductsTableModel extends AbstractTableModel
{
	private String[] orderdedProductColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Ποσότητα/Βάρος", "Τιμή"};
	private ArrayList<Product> orderedProducts = new ArrayList<>();
	
	public OrderedProductsTableModel(Order order)
	{
		orderedProducts = order.getProducts();
	}
	
	public int getColumnCount()
	{
		return 7;
	}
	
	public int getRowCount()
	{
		return orderedProducts.size();
	}
	
	public String getColumnName(int col)
    {
        return orderdedProductColumnNames[col];
    }
	
	public Object getValueAt(int row, int col)
    {
    	switch (col)
    	{
    	case 0:
    		return orderedProducts.get(row).getName();
    	case 1:
			return orderedProducts.get(row).getLocation();
		case 2:
			return orderedProducts.get(row).getProducer();
		case 3:
			return orderedProducts.get(row).getQuality();
		case 4:
			return orderedProducts.get(row).getPackaging();
		case 5:
			return orderedProducts.get(row).getQuantityWeight();
		case 6:
			return orderedProducts.get(row).getPrice();
		default:
			return "ERROR";
		}
    }
	
	/*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
	public Class getColumnClass(int c)
	{
		return getValueAt(0, c).getClass();
	}
	
	/*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col)
    {
        //Note that the products/cell address is constant,
        //no matter where the cell appears onscreen.
    	return false;
    }
}