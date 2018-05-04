import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ClientsTableModel extends AbstractTableModel
{
	String[] clientColumnNames = {"Ονοματεπώνυμο", "ΑΦΜ", "Περιοχή", "Διεύθυνση", "Τ.Κ.", "Τηλέφωνο", "ΦΑΞ", "E-mail", "Σημειώσεις"};
	private ArrayList<Client> clients = new ArrayList<>();
	
	public ClientsTableModel(ArrayList<Client> clients)
	{
		this.clients = clients;
	}
	
	public int getColumnCount()
    {
        return 9;
    }
	
	public int getRowCount()
    {
        return clients.size();
    }
	
	public String getColumnName(int col)
    {
        return clientColumnNames[col];
    }
	
	public Object getValueAt(int row, int col)
    {
    	switch (col)
    	{
    	case 0:
    		return clients.get(row).getName();
    	case 1:
    		return clients.get(row).getId();
    	case 2:
    		return clients.get(row).getCity();
    	case 3:
    		return clients.get(row).getAddress();
    	case 4:
    		return clients.get(row).getZipCode();
    	case 5:
    		return clients.get(row).getPhoneNumber();
    	case 6:
    		return clients.get(row).getFax();
    	case 7:
    		return clients.get(row).getEmail();
    	case 8:
    		return clients.get(row).getNotes();
    	default:
    		return "ERROR.";
    	}
    }
	
	public void getClientInfo(int row)
    {
    	new ClientInfoFrame(clients.get(row).getId(), this);
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
	
	public void removeClientByID(String id)
    {
    	for (Client client: clients)
    		if (client.getId().equals(id))
    			clients.remove(client);
					
		//not sure if array list adds the item last
		fireTableDataChanged(); //to be changed, not efficient
	}
	
	public void removeSelectedClient(int row)
    {
		DBConnect db = new DBConnect();    	
    	db.connect();
		
    	if (!clients.isEmpty())
    	{
    		try
    		{
    			String query = "DELETE FROM client WHERE client.id = " + "'" + clients.get(row).getId() + "'";
    			int rs = db.getStatement().executeUpdate(query);
    			
    			clients.remove(clients.get(row));
    			fireTableDataChanged();
			} 
    		catch (Exception ex)
    		{
				ex.printStackTrace();
			}

    		
    	}
	}
	
	//alternative way to add a row
    public void addRow(Client client)
    {
    	int lastRowBeforeUpdate = this.getRowCount() - 1;
    	clients.add(client);
    	int lastRowAfterUpdate = this.getRowCount() - 1;
    	fireTableRowsInserted(lastRowBeforeUpdate, lastRowAfterUpdate);
    }
    
    //re-fetch data and refresh table
	public void update()
	{
		this.clients = OrderingManagment.fetchClients();
		fireTableDataChanged();
	}
	
	//just refreshes table's existing content
	public void refresh()
	{
		fireTableDataChanged();
	}
}