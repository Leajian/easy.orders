import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProductsTableModel extends AbstractTableModel {
	
		public ProductsTableModel(ArrayList<Product> products) {
			ProductsTableModel.products = products;
		}
		
		
		private boolean DEBUG = true;

		String[] productColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Τιμή", "Απόθεμα"};
        
		private static ArrayList<Product> products = new ArrayList<>();

        public int getColumnCount() {
            return 7;
        }

        public int getRowCount() {
            return products.size();
        }

        public String getColumnName(int col) {
            return productColumnNames[col];
        }

        public Object getValueAt(int row, int col) {
        	switch (col) {
			case 0:
				return products.get(row).getName();
			case 1:
				return products.get(row).getLocation();
			case 2:
				return products.get(row).getProducer();
			case 3:
				return products.get(row).getQuality();
			case 4:
				return products.get(row).getPackaging();
			case 5:
				return products.get(row).getPrice();
			case 6:
				return products.get(row).getStock();
			default:
				return "ERROR";
			}
        }
        
        public static void getProductInfo(int row) {
        	new ProductInfoFrame(products.get(row).getId());
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
            //Note that the products/cell address is constant,
            //no matter where the cell appears onscreen.
                return false;
        }

        /*
         * Don't need to implement this method unless your table's
         * products can change.
         */
        public void setValueAt(Product value, int row, int col) {
            if (DEBUG) {
                System.out.println("Setting value at " + row + "," + col
                                   + " to " + value
                                   + " (an instance of "
                                   + value.getClass() + ")");
            }

            //fireTableCellUpdated(row, col);
            fireTableRowsUpdated(row, row);

            if (DEBUG) {
                System.out.println("New value of products:");
                printDebugData();
            }
        }
        
        public void removeProductByID(String id) {
			for (Product product : products) {
				if (product.getId().equals(id))
					products.remove(product);
			}
					
			//not sure if array list adds the item last
			fireTableDataChanged(); //to be changed, not efficient
		}
        
        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i=0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j=0; j < numCols; j++) {
                    System.out.print("  " + getValueAt(i, j));
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
}
