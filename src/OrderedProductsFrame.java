import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class OrderedProductsFrame extends JFrame
{
	private JTable orderedProductsTable;
	
	JPanel panel = new JPanel();
	private JProgressBar progressBar;
	
	public OrderedProductsFrame(Order order)
	{
		OrderedProductsTableModel optm = new OrderedProductsTableModel(order);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("452px:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:434px:grow"),}));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "1, 1");
		orderedProductsTable = new JTable(optm);
		scrollPane.setViewportView(orderedProductsTable);
		
		//this disallows reordering of columns
		orderedProductsTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		orderedProductsTable.setCellSelectionEnabled(false);
		orderedProductsTable.setColumnSelectionAllowed(false);		
		orderedProductsTable.setRowSelectionAllowed(true);
		
		orderedProductsTable.setSelectionModel(new ForcedListSelectionModel());
		
		
		
		orderedProductsTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		orderedProductsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		orderedProductsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		orderedProductsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		orderedProductsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		orderedProductsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		
		
		
		
		this.setContentPane(panel);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(350, 150);
		this.setSize(821, 548);
		this.setResizable(false);
		this.setTitle("Προιόντα Παραγγελίας");
		this.setVisible(true);
	}
}