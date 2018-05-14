import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class RecordTableModel extends AbstractTableModel
{
	private String[] recordColumnNames = {"Ονοματεπώνυμο", "ΑΦΜ", "Ημερομηνία", "Πωλητής"};
	private ArrayList<Order> ordersRecord = new ArrayList<>();
	
	public RecordTableModel(ArrayList<Order> ordersRecord)
	{
		this.ordersRecord = ordersRecord;
	}
	
	public int getColumnCount()
    {
        return 4;
    }
	
	public int getRowCount()
    {
        return ordersRecord.size();
    }
	
	public String getColumnName(int col)
    {
        return recordColumnNames[col];
    }
	
	public Object getValueAt(int row, int col)
    {
    	switch (col)
    	{
    	case 0:
    		return ordersRecord.get(row).getClientName();
    	case 1:
    		return ordersRecord.get(row).getClientId();
    	case 2:
    		return ordersRecord.get(row).getLastEdit();
    	case 3:
    		return ordersRecord.get(row).getEmployeeUsername();
    	default:
    		return "ERROR.";
    	}
    }
	
	public Order getOrderAt(int row)
    {
    	return ordersRecord.get(row);
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
	
	public void populate()
	{
		this.ordersRecord = DataFetcher.initializeOrders("2");
		fireTableDataChanged();
	}
	
	public void lockOnClient(Client client)
	{
		this.ordersRecord = DataFetcher.RecordOfClient(client);
		fireTableDataChanged();
	}
}