import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class OrderedProductsTableModel extends AbstractTableModel
{
	private String[] orderdedProductColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Ποσότητα/Βάρος", "Τιμή"};
	private ArrayList<Product> orderedProducts = new ArrayList<>();
	private boolean allowOrderSpecificCellEdit = true; //model is used for orders by default
	
	public OrderedProductsTableModel(Order order)
	{
		if (order != null)
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
	
	public void setValueAt(Object value, int row, int col)
    {
    	switch (col)
    	{
		case 5:
			orderedProducts.get(row).setQuantityWeight((String)value);
			fireTableCellUpdated(row, col);
			break;
		case 6:
			orderedProducts.get(row).setPrice((String)value);
			fireTableCellUpdated(row, col);
			break;
		default:
			break;
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
		for(int rowIndex = 0; rowIndex < getRowCount(); rowIndex++)
		{
			Product row = orderedProducts.get(rowIndex);
			if (getValueAt(rowIndex, c) != null)
			{
				return getValueAt(rowIndex, c).getClass();
			}
		}
		return String.class;
	}
	
	/*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col)
    {
        //Note that the products/cell address is constant,
        //no matter where the cell appears onscreen.
    	if (allowOrderSpecificCellEdit)
    		if (col > 4)
    			return true;
    	return false;
    }
    
    public void addRow(Product product)
    {
    	int lastRowBeforeUpdate = this.getRowCount() - 1;
    	orderedProducts.add(product);
        int lastRowAfterUpdate = this.getRowCount() - 1;
        fireTableRowsInserted(lastRowBeforeUpdate, lastRowAfterUpdate);
    }
    
    //remove product from the list
    public void removeRow(int selectedRow)
    {
    	orderedProducts.remove(selectedRow);
        fireTableRowsDeleted(selectedRow, selectedRow);
    }
    
	public ArrayList<Product> getOrderedProducts()
	{
		return orderedProducts;
	}
	
	public boolean productExists(Product product)
	{
		for (Product p : orderedProducts)
			if (p.getId().equals(product.getId()))
				return true;
		return false;
	}
	
	public void allowOrderSpecificCellsEdit(boolean b)
	{
		allowOrderSpecificCellEdit = b;
	}
	
	public boolean hasProducts()
	{
		return !orderedProducts.isEmpty();
	}
}