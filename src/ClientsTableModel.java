import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ClientsTableModel extends AbstractTableModel {
	
		public ClientsTableModel(ArrayList<Product> clients) {
			this.clients = clients;
		}

		String[] clientColumnNames = {"Ονοματεπώνυμο", "ΑΦΜ", "Περιοχή", "Διεύθυνση", "Τ.Κ.", "Τηλέφωνο", "ΦΑΞ", "E-mail", "Σημειώσεις"};
        
		private ArrayList<Product> clients = new ArrayList<>();

        public int getColumnCount() {
            return 9;
        }

        public int getRowCount() {
            return clients.size();
        }

        public String getColumnName(int col) {
            return clientColumnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	switch (col) {
			case 0:
				return clients.get(row).getName();
			case 1:
				return clients.get(row).getLocation();
			case 2:
				return clients.get(row).getSupplier();
			case 3:
				return clients.get(row).getQuality();
			case 4:
				return clients.get(row).getPackaging();
			case 5:
				return clients.get(row).getPrice();
			case 6:
				return clients.get(row).getStock();
			default:
				return "ERROR";
			}
        }
        
        public void getProductInfo(int row) {
        	new ProductInfoFrame(clients.get(row).getId());
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the clients/cell address is constant,
            //no matter where the cell appears onscreen.
                return false;
        }
        
        public void removeProductByID(String id) {
        	if (!clients.isEmpty()) {
				for (Product client : clients) {
					if (client.getId().equals(id))
						clients.remove(client);
				}
        	}
        	
			//INSERT DELETION FROM DATABASE CODE HERE
					
			//not sure if array list adds the item last
			fireTableDataChanged(); //to be changed, not efficient
		}
        
        public void removeSelectedProduct(int row) {
        	if (!clients.isEmpty())
        		clients.remove(clients.get(row));
			
			//INSERT DELETION FROM DATABASE CODE HERE
        	
			fireTableDataChanged(); //to be changed, not efficient
		}
        
        //alternative way to add a row
        public void addRow(Product client) {
        	int lastRowBeforeUpdate = this.getRowCount() - 1;
        	clients.add(client);
        	int lastRowAfterUpdate = this.getRowCount() - 1;
        	fireTableRowsInserted(lastRowBeforeUpdate, lastRowAfterUpdate);
        }
        
        //this updates the table model, using a data structure and informs the table that it must refresh it's contents
		public void update(ArrayList<Product> clients) {
			this.clients = clients;
			fireTableDataChanged();
		}
}
