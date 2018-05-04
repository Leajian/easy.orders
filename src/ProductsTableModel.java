import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProductsTableModel extends AbstractTableModel
{
	String[] productColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Τιμή", "Απόθεμα"};
	private ArrayList<Product> products = new ArrayList<>();
		
	public ProductsTableModel(ArrayList<Product> products)
	{
		this.products = products;
	}

    public int getColumnCount()
    {
        return 7;
    }

    public int getRowCount()
    {
        return products.size();
    }

    public String getColumnName(int col)
    {
        return productColumnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
    	switch (col)
    	{
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
        
    public void getProductInfo(int row)
    {
        new ProductInfoFrame(products.get(row).getId(), this);
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
        
    public void removeProductByID(String id)
    {
    	if (!products.isEmpty())
    		for (Product product: products)
    			if(product.getId().equals(id))
    				products.remove(product);
    	
    	//INSERT DELETION FROM DATABASE CODE HERE		
		//not sure if array list adds the item last
		fireTableDataChanged(); //to be changed, not efficient
	}
        
    public void removeSelectedProduct(int row)
    {
    	DBConnect db = new DBConnect();    	
    	db.connect();

    	if(!products.isEmpty())
    	{
        	try
        	{
    			String query = "DELETE FROM product WHERE product.id = " + products.get(row).getId();
    			int rs = db.getStatement().executeUpdate(query);
    		}
        	catch (Exception ex)
        	{
    			ex.printStackTrace();
    		}
        	
        	products.remove(products.get(row));
    	}

		fireTableDataChanged(); //to be changed, not efficient
	}
        
    //alternative way to add a row
    public void addRow(Product product)
    {
    	int lastRowBeforeUpdate = this.getRowCount() - 1;
        products.add(product);
        int lastRowAfterUpdate = this.getRowCount() - 1;
        fireTableRowsInserted(lastRowBeforeUpdate, lastRowAfterUpdate);
    }
        
    //this updates the table model, using a data structure and informs the table that it must refresh it's contents
	public void update()
	{
		this.products = OrderingManagment.fetchProducts();
		fireTableDataChanged();
	}
	
	//just refreshes table's existing content
	public void refresh()
	{
		fireTableDataChanged();
	}
}