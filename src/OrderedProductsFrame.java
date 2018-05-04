import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

public class OrderedProductsFrame extends JFrame
{
	private JTable productsTable;
	
	JPanel panel = new JPanel();
	
	public OrderedProductsFrame(Order order)
	{
		ProductsTableModel ptm = new ProductsTableModel(OrderingManagment.fetchProductsFromOrder(order));
		productsTable = new JTable(ptm);
		
		//this disallows reordering of columns
		productsTable.getTableHeader().setReorderingAllowed(false);
				
		//these make that so you can only select a whole lines on click
		productsTable.setCellSelectionEnabled(false);
		productsTable.setColumnSelectionAllowed(false);		
		productsTable.setRowSelectionAllowed(true);
		
		productsTable.setSelectionModel(new ForcedListSelectionModel());
		
		productsTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				
				//show info on double click
				if (e.getClickCount() == 2) {
					ptm.getProductInfo(selectedRow);
				}
			}
		});
		
		
		
		productsTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		productsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		productsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		productsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		productsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		productsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		
		
		panel.add(productsTable);
		
		
		
		
		this.setContentPane(panel);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(270, 50);
		this.setSize(850, 576);
		this.setResizable(true);
		this.setTitle("Προιόντα Παραγγελίας");
		this.setVisible(true);
	}
}