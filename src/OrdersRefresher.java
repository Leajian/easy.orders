import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class OrdersRefresher extends AbstractEntityRefresher
{
	public OrdersRefresher(JTabbedPane aTabbedPane)
	{
		super(aTabbedPane, "orders");
		
		System.out.println("Orders refresher instancieted");
		
		aTabbedPane.addChangeListener(new ChangeListener()
	    {
			@Override
			public void stateChanged(ChangeEvent evt) 
			{
				JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();

	            if((tabbedPane.getSelectedIndex() != tabbedPane.indexOfTab("Νέα Παραγγελία")) & (tabbedPane.indexOfTab("Νέα Παραγγελία") != -1))
	            {
	            	String ObjButtons[] = {"Ναι", "Όχι"};			
					int PromptResult = JOptionPane.showOptionDialog(null, "Η παραγγελία σας δεν έχει αποθηκευτεί, τα δεδομένα θα χαθούν. Είστε σίγουροι ότι θέλετε να αλλάξετε καρτέλα;", "Προσοχή!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				
					if(PromptResult == JOptionPane.YES_OPTION)
						aTabbedPane.removeTabAt(tabbedPane.indexOfTab("Νέα Παραγγελία"));
					else
						aTabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Νέα Παραγγελία"));
	            }
			}
	    });
		
	}

	@Override
	protected void populator()
	{
		((JTabbedPane) obj).removeAll();
		
		ArrayList<Order> orders = DataFetcher.initalizeOrders();
		
		if(!orders.isEmpty())
			for (Order order: orders)
				createNewTab((JTabbedPane) obj, order);
		else
			createAddNewOrderTab((JTabbedPane)obj);
		
		
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void createNewTab(JTabbedPane aTabbedPane, Order order)
	{		
		JPanel newOrderPanel = new JPanel();
		
		JTable ordersTable = null;
		
		OrderedProductsTableModel optm = new OrderedProductsTableModel(order);
		
		JLabel nameLabel = new JLabel("Ονοματεπώνυμο");
		JLabel productsLabel = new JLabel("Προϊόντα");
		
		JTextField nameTextField = new JTextField();
		nameTextField.setColumns(10);
		
		JButton addProductButton = new JButton("+");
		JButton removeProductButton = new JButton("-");
		JButton saveButton = new JButton("Αποθήκευση");
		JButton deleteOrderButton = new JButton("Διαγραφή Παραγγελίας");
		
		JScrollPane ordersTableScrollPane;
		
		FormLayout fl_newOrderPanel = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("155px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(430dlu;min):grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;min)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:223px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,});
		
		fl_newOrderPanel.setRowGroups(new int[][]{new int[]{6, 8}});
		newOrderPanel.setLayout(fl_newOrderPanel);
		

		
		newOrderPanel.add(nameLabel, "3, 2, right, fill");
		newOrderPanel.add(productsLabel, "3, 4, right, fill");
		
		newOrderPanel.add(nameTextField, "5, 2, fill, fill");
		
		newOrderPanel.add(addProductButton, "3, 6, fill, fill");
		newOrderPanel.add(removeProductButton, "3, 8, fill, fill");
		
		newOrderPanel.add(saveButton, "3, 14, fill, fill");
		newOrderPanel.add(deleteOrderButton, "3, 12, fill, fill");

		removeProductButton.setVisible(false);
		deleteOrderButton.setVisible(false);
		

		
		ordersTable = new JTable(optm);
		ordersTable.setBounds(117, 39, 568, 161);
		
		ordersTableScrollPane = new JScrollPane(ordersTable);
		//ordersTableScrollPane.setVisible(false);
		newOrderPanel.add(ordersTableScrollPane, "5, 4, 1, 11, fill, fill");
		
		//this disallows reordering of columns
		ordersTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		ordersTable.setCellSelectionEnabled(false);
		ordersTable.setColumnSelectionAllowed(false);		
		ordersTable.setRowSelectionAllowed(true);
		
		//these make that so you can only select a single line on click
		ordersTable.setSelectionModel(new ForcedListSelectionModel());
		
		//a selection model, which allows us to call it and get the selected row at any time
		ListSelectionModel rowSM = ordersTable.getSelectionModel();
		
		if(order != null)
		{
			JLabel tabTitleLabel = new JLabel("<html>" + order.getClientName() + "<br>" + order.getLastEdit() + "</html>");
			tabTitleLabel.setPreferredSize(new Dimension(200, 30));
			
			//create a unique name as key for each tab
			String tabKey = order.getClientId() + order.getLastEdit();
			aTabbedPane.addTab(tabKey, newOrderPanel);
			
			//add a fixed-size label on each tab as title
		    aTabbedPane.setTabComponentAt(aTabbedPane.indexOfTab(tabKey), tabTitleLabel);
		    
		    //set each nameField to the owner's name 
			nameTextField.setText(order.getClientName());
			nameTextField.setEditable(false);
			
			removeProductButton.setVisible(true);
			deleteOrderButton.setVisible(true);
			
			//ordersTableScrollPane.setVisible(true);
		}
		else
		{
			//if there is already a New Order tab, just switch to this without creating a new one -- avoid cluster
			if (aTabbedPane.indexOfTab("Νέα Παραγγελία") != -1) //exists
			{
				aTabbedPane.setSelectedIndex(aTabbedPane.indexOfTab("Νέα Παραγγελία"));
				return;
			}
			
			//remove "+" tab if exists to add it later
//			if (aTabbedPane.indexOfTab(" + ") != -1) //exists
//				aTabbedPane.remove(aTabbedPane.indexOfTab(" + "));

			JLabel tabTitleLabel = new JLabel("Νέα Παραγγελία");
			tabTitleLabel.setPreferredSize(new Dimension(200, 30));
			
			JLabel noOrderLabel = new JLabel("Παρακαλώ προσθέστε ένα προϊόν στην παραγγελία σας.");
			
			aTabbedPane.insertTab("Νέα Παραγγελία", null, newOrderPanel, null, aTabbedPane.getTabCount());
					
			noOrderLabel.setIcon(new ImageIcon(SellerMainFrame.class.getResource("/images/contract.png")));
			noOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
			noOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
			noOrderLabel.setBounds(117, 39, 568, 161);
			//newOrderPanel.add(noOrderLabel, "5, 4, 1, 11, fill, fill");
			
			//addPlusSignTabAtTheEndOf(aTabbedPane);
			
			//the pre-last index is the New Order tab (index starts from 0)
			aTabbedPane.setSelectedIndex(aTabbedPane.getTabCount() - 1);
			
		    //add a fixed-size label on each tab as title
		    aTabbedPane.setTabComponentAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), tabTitleLabel);
		    
		}
		
		
		//FOR DEBUG
		ordersTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		//Action Listeners
		addProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				SelectProductFrame spf = new SelectProductFrame(optm);
	
				while (!spf.isDisplayable()) break;
				
//				if (!optm.getOrderedProducts().isEmpty())
//				{
//					removeProductButton.setVisible(true);
//					ordersTableScrollPane.setVisible(true);
//				}
				
				removeProductButton.setVisible(true);
//				ordersTableScrollPane.setVisible(true);
			}
		});
		
		removeProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectedProduct = rowSM.getMinSelectionIndex();
				
				if (selectedProduct != -1)
					optm.removeRow(selectedProduct);
				else		
					JOptionPane.showMessageDialog(null, "Παρακαλώ επιλέξτε το προϊόν προς διαγραφή.", "Προσοχή!", JOptionPane.WARNING_MESSAGE);
				System.out.println("remove " + selectedProduct);
			}
		});
			
		
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//make each tab distinct by name
				//aTabbedPane.setTitleAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), nameTextField.getText());
				
				//set each label name to the desired name 
				//tabTitleLabel.setText(nameTextField.getText());
				
				//SQL query for name with nameTextField.getText() or get/set it from search bar automatically. To be added.
				
				//add label to tab, replacing/overriding the title of it
				//aTabbedPane.setTabComponentAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), tabTitleLabel);
				
				//the delete button now has purpose
				deleteOrderButton.setVisible(true);
			}
		});
		
		deleteOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				removeSelectedTabFrom(aTabbedPane);
				//SQL query for deletion
			}
		});
	}
	
	private static void removeSelectedTabFrom(JTabbedPane aTabbedPane)
	{
		if (aTabbedPane.getTabCount() > 2) 
		{
			//when a tab is removed, the next one is selected, but we want the previous one to be selected
			//if we didn't take care of this, there is a case where a tab just before "+" tab is removed, causing the "+" itself
			//to be selected again and create a new tab, while there are already other tabs, without user's intent
			if (aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()) != 0)
			{
				//so we select the previous first
				aTabbedPane.setSelectedIndex(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent())-1);
				//and then we remove the next of the one we just selected, which is the desired one to remove
				aTabbedPane.remove(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent())+1);
			}
			//otherwise, remove method, conveniently works for us and creates a new order tab, because there are no tabs
			else
				aTabbedPane.remove(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()));
		}
		else
		{
			aTabbedPane.removeAll();
			//addPlusSignTabAtTheEndOf(aTabbedPane); //alternative method which triggers the stateChanged event of JTabbedPane
			createNewTab(aTabbedPane, null);
		}
	}
	
	private void addPlusSignTabAtTheEndOf(JTabbedPane aTabbedPane)
	{
		JPanel plusSignPanel = new JPanel();
		
		//puts the "+" sign tab last
		aTabbedPane.insertTab(" + ",null,plusSignPanel,null, aTabbedPane.getTabCount());
		JLabel plusSignlabel = new JLabel("+");
	}
	
	private void createAddNewOrderTab(JTabbedPane aTabbedPane)
	{
		createNewTab(aTabbedPane, null);
	}
}