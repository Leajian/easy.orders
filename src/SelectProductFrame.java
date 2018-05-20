import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Dialog;
import java.awt.Dimension;

public class SelectProductFrame extends JDialog
{
	private JTable productsTable;
	//private Product selectedProduct;
	
	public SelectProductFrame(OrderedProductsTableModel optm)
	{
		
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		

		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		JLabel SearchLabel = new JLabel("Αναζήτηση");
		getContentPane().add(SearchLabel, "2, 2, right, default");
		
		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox, "4, 2, fill, fill");
		
		JScrollPane productsScrollPane = new JScrollPane();
		getContentPane().add(productsScrollPane, "2, 4, 3, 1, fill, fill");
		
		ProductsTableModel ptm = new ProductsTableModel(DataFetcher.initializeProducts());
		
		productsTable = new JTable(ptm);
		productsScrollPane.setViewportView(productsTable);
		
		productsTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				
				//show info on double click
				if (e.getClickCount() == 1)
				{

					
					Product selectedProduct = ptm.getProductAt(selectedRow);
					if (!optm.productExists(selectedProduct))
					{
						optm.addRow(selectedProduct);
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Το προϊόν υπάρχει ήδη στην παραγγελία.", "Προσοχή!", JOptionPane.WARNING_MESSAGE);

				}
			}
		});
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setSize(new Dimension(750, 752));
		this.setLocationRelativeTo(rootPane);		
		this.setTitle("Επιλέξτε προϊόν");
		this.setResizable(false);
		this.setVisible(true);
	}
}