import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProductsTableModel extends AbstractTableModel
{
	private String[] productColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Τιμή", "Απόθεμα"};
	private ArrayList<Product> products = new ArrayList<>();
	private DBConnect db = new DBConnect();
		
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
        new ProductInfoFrame(products.get(row).getId(), this, row);
    }
    
    public boolean isProductEditable(int row)
	{
		return products.get(row).isEditable();
	}
	
	public void setProductEditable(int row)
	{
		db.connect();
		
		try
		{
			String query = "UPDATE product SET isEditable = 1 WHERE id = '" + products.get(row).getId() + "'";
			int rs = db.getStatement().executeUpdate(query);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		db.closeConnection();
	}
	
	public void setProductUneditable(int row)
	{
		db.connect();
		
		try
		{
			String query = "UPDATE product SET isEditable = 0 WHERE id = '" + products.get(row).getId() + "'";
			int rs = db.getStatement().executeUpdate(query);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		db.closeConnection();
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
    	if(!products.isEmpty())
    	{
    		db.connect();
    		
        	try
        	{
    			String query = "DELETE FROM product WHERE product.id = " + products.get(row).getId();
    			int rs = db.getStatement().executeUpdate(query);
            	
    			products.remove(products.get(row));
        		fireTableDataChanged();
    		}
        	catch (Exception ex)
        	{
    			ex.printStackTrace();
    		}
        	
        	db.closeConnection();
    	}
	}
        
    //alternative way to add a row
    public void addRow(Product product)
    {
    	int lastRowBeforeUpdate = this.getRowCount() - 1;
        products.add(product);
        int lastRowAfterUpdate = this.getRowCount() - 1;
        fireTableRowsInserted(lastRowBeforeUpdate, lastRowAfterUpdate);
    }
        
    //re-fetch data and refresh table
    public void populate()
	{
		this.products = DataFetcher.initializeProducts();
		fireTableDataChanged();
	}
    
    //uses an list of new products and changes them in the table according to their id
    public void refresh(ArrayList<Product> products)
    {
    	int indexOfProductToRefresh = -1;
    	
    	for(Product oldProduct: products)
    		for(Product newProduct : products)
    			if (oldProduct.getId().equals(newProduct.getId()))
	    		{
	    			indexOfProductToRefresh = products.indexOf(oldProduct);
	    			
	    			if (indexOfProductToRefresh != -1)
	    	    	{
	    		    	products.remove(indexOfProductToRefresh);
	    		    	products.add(indexOfProductToRefresh, newProduct);
	    		
	    		        fireTableRowsUpdated(indexOfProductToRefresh, indexOfProductToRefresh);
	    	    	}
	    	    	else
	    	    		System.out.println("Error while trying to refresh product.");
	    		}
    }
}