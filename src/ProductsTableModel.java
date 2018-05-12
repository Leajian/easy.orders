import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

public class ProductsTableModel extends AbstractTableModel
{
	private String[] productColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Τιμή", "Απόθεμα"};
	private volatile ArrayList<Product> products = new ArrayList<>();
	private DBConnect db = new DBConnect();
	
	//private boolean locked = false;
		
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
        
//    public synchronized void getProductInfo(int row)
//    {
//    	setProductUneditable(row);
//
//    	String id = products.get(row).getId();
//    	
//    	this.
////        ProductInfoFrame pif = new ProductInfoFrame(id, row);
////        while (pif.isActive())
////        	lock("info frame");
////        unlock("info frame");
//        
//        setProductEditable(row);
//    }
    public Product getProductAt(int row)
    {
    	return products.get(row);
    }
    
    public boolean isProductEditable(int row)
	{
    	//lock("isEditable");
    	boolean isEditable = products.get(row).isEditable();
    	//unlock("isEditable");
		return isEditable;
	}
	
	public void setProductEditable(int row)
	{
		//lock("setProductEditable");
		
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
		
		//unlock("setProductEditable");
	}
	
	public void setProductUneditable(int row)
	{
		//lock("setProductUneditable");
		
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
		
		//unlock("setProductUneditable");
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
        		JOptionPane.showMessageDialog(null, "Το προϊόν χρησιμοποιείται σε παραγγελία.", "Δεν επιτρέπεται διαγραφή.", JOptionPane.ERROR_MESSAGE);
    			//ex.printStackTrace();
    		}
        	db.closeConnection();
    	}
	}
        
    //add product to the list
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
    
    //uses an list of altered existing products and changes them in the table according to their id
    //if the product in the list is new, it is added as well
    public void refresh(ArrayList<Product> newProducts)
    {
    	//lock("refresh from model"); 
    	//int oldProductsSize = this.products.size();
    	
    	for (Product oldProduct : this.products)
    	//for (int i=0; i < oldProductsSize; i++)
    	{

    		for (Product newProduct : newProducts)
    		{
    			if (oldProduct.getId().equals(newProduct.getId()))
	    		{
    	
	    			int indexOfProductToRefresh = this.products.indexOf(oldProduct);
	    			
	    			if (indexOfProductToRefresh != -1)
	    	    	{
	    				//remove old entry
	    		    	this.products.remove(indexOfProductToRefresh);
	    		    	//add altered entry
	    		    	this.products.add(indexOfProductToRefresh, newProduct);
	    		    	//remove the product from newProducts list, because it's been already added
	    		    	//newProducts.remove(indexOfProductToRefresh);
	    		    	System.out.println("b4 fireupd");
	    		    	//update only the row of the new product
	    		    	
	    		    	fireTableRowsUpdated(indexOfProductToRefresh, indexOfProductToRefresh);
	    	    	}
	    	    	else
	    	    	{
	    	    		System.out.println("Error while trying to refresh product.");
	    	    	}
	    		}
    			
    		}
    	}
    	
    	//this.populate();
    	
    	//if there are unmatched products left, it means they are new entries
//		if (!newProducts.isEmpty())
//		{
//			//max rows before adding
//			int fromHere = getRowCount() - 1;
//			//for every remaining newProduct
//			for (Product newProduct : newProducts)
//			{
//				//add new entry
//				this.products.add(newProduct);
//				
//			}
//			
//			//max rows after adding
//			int toHere = getRowCount() - 1;
//			//update only the rows of new products
//			fireTableRowsInserted(fromHere, toHere);
//		}
    	
    	//unlock("refresh from model");
    }
    
    
    //lock mechanism for AWT thread
//    public synchronized void lock(String msg)
//    {
//    	System.out.println(msg + " - lock state : " + locked);
//    	if (locked)
//		{
//			try {
//				this.wait();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//		}
//    	
//    	locked = true;
//		System.out.println(msg + " has set lock to true - lock state : " + locked);
//    	
//    }
//    
//    public synchronized void unlock(String msg)
//    {
//    	locked = false;
//    	System.out.println(msg + " has set lock to false - lock state : " + locked);
//		
//		this.notifyAll();
//    	
//    }
}