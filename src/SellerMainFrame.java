import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class SellerMainFrame extends JFrame
{
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
	
	private JTabbedPane liveOrdersTabs = new JTabbedPane(JTabbedPane.LEFT);;
	
	private JTable ordersTable;
	private JTable clientsTable;
	private JTable productsTable;
	private JTable recordTable;
	
	private JTextField searchClientTextField = new JTextField();
	
	private JButton addClientButton = new JButton("+");
	private JButton removeClientButton = new JButton("-");
	private JButton addProductButton = new JButton("+");
	private JButton removeProductButton = new JButton("-");
	
	private JRadioButton clientNameRadioButton = new JRadioButton("Όνομα");
	private JRadioButton clientIdRadioButton = new JRadioButton("ΑΦΜ");
	private JRadioButton productNameRadioButton = new JRadioButton("Προιόν");
	private JRadioButton locationNameRadioButton = new JRadioButton("Προέλευση");
	private JRadioButton producerNameRadioButton = new JRadioButton("Προμυθευτής");
	
	private JComboBox searchProductComboBox = new JComboBox();
	
	private JLabel clientsearchLabel = new JLabel("Αναζήτηση");
	private JLabel productsearchLabel = new JLabel("Αναζήτηση");
	private JLabel DateLabel = new JLabel("Ημερομηνία");

	private JPanel ordersTab = new JPanel();
	private JPanel clientsTab = new JPanel();
	private JPanel productsTab = new JPanel();
	private JPanel recordTab = new JPanel();
	
	
	
	public SellerMainFrame()
	{
		setResizable(false);	
		liveOrdersTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		this.setBounds(100, 100, 1300, 750);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
		this.getContentPane().add(tabbedPane);
		
		
		
		
		
		
		//Orders.
		tabbedPane.addTab("Παραγγελίες", null, ordersTab, null);
		ordersTab.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1136px:grow"),},
			new RowSpec[] {
				RowSpec.decode("fill:681px:grow"),}));
		ordersTab.add(liveOrdersTabs, "1, 1, fill, fill");
		
		createTab(liveOrdersTabs);
		liveOrdersTabs.addChangeListener(new ChangeListener()
	    {
			@Override
			public void stateChanged(ChangeEvent evt) 
			{
				JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();

	            if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab(" + "))
	            	createTab(liveOrdersTabs);
			}
	    });
		
		
		
		
		
		
		
		
		
		
		
		

		//Clients.
		tabbedPane.addTab("Πελάτες", null, clientsTab, null);
		
		clientsTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("114px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("781px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("109px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("89px:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:543px:grow"),}));
		
		clientsTab.add(clientsearchLabel, "2, 2, right, center");
		
		clientsTab.add(searchClientTextField, "4, 2, fill, default");
		searchClientTextField.setColumns(10);
		
		clientNameRadioButton.setSelected(true);
		clientsTab.add(clientNameRadioButton, "6, 2, left, top");
		clientsTab.add(clientIdRadioButton, "8, 2, left, top");
		
		ButtonGroup clientsGroup = new ButtonGroup();	
		clientsGroup.add(clientNameRadioButton);
		clientsGroup.add(clientIdRadioButton);
		
		//we create a table model so that we can manipulate it's data
		ClientsTableModel ctm = new ClientsTableModel(new ArrayList<>());
		clientsTable = new JTable(ctm);
		
		ctm.update();

		//this disallows reordering of columns
		clientsTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		clientsTable.setCellSelectionEnabled(false);
		clientsTable.setColumnSelectionAllowed(false);		
		clientsTable.setRowSelectionAllowed(true);
		
		//these make that so you can only select a single line on click
		clientsTable.setSelectionModel(new ForcedListSelectionModel());
		
		clientsTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int selectedRow = clientsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				
				//show info on double click
				if (e.getClickCount() == 2) {
					ctm.getClientInfo(selectedRow);
				}
			}
		});
		
		clientsTable.getColumnModel().getColumn(0).setPreferredWidth(270);
		clientsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		clientsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		clientsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		clientsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		clientsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		clientsTable.getColumnModel().getColumn(7).setPreferredWidth(159);
		clientsTable.getColumnModel().getColumn(8).setPreferredWidth(227);
		
		addClientButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new NewClientFrame(ctm);
			}
		});
		
		clientsTab.add(addClientButton, "2, 4");
		clientsTab.add(removeClientButton, "2, 6");
		
		clientsTable.setBounds(46, 35, 881, 319);
		
		clientsTab.add(new JScrollPane(clientsTable), "4, 4, 5, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Products.
		tabbedPane.addTab("Προϊόντα", null, productsTab, null);
		
		productsTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("114px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("842px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("99px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("89px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:415px:grow"),}));
		
		productsTab.add(productsearchLabel, "2, 2, right, center");

		//searchProductComboBox.setEditable(true);
		productsTab.add(searchProductComboBox, "4, 2, fill, default");
		
		productNameRadioButton.setSelected(true);
		productsTab.add(productNameRadioButton, "6, 2, left, top");
		productsTab.add(locationNameRadioButton, "8, 2, left, top");
		productsTab.add(producerNameRadioButton, "10, 2");
		
		ButtonGroup productsGroup = new ButtonGroup();
		productsGroup.add(productNameRadioButton);
		productsGroup.add(locationNameRadioButton);
		productsGroup.add(producerNameRadioButton);
		
		//we create a table model so that we can manipulate it's data
		ProductsTableModel ptm = new ProductsTableModel(new ArrayList<>());
		productsTable = new JTable(ptm);
		
		//now we call the model to populate the data to the table from the list
		ptm.update();
		
		//this disallows reordering of columns
		productsTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		productsTable.setCellSelectionEnabled(false);
		productsTable.setColumnSelectionAllowed(false);		
		productsTable.setRowSelectionAllowed(true);
		
		//these make that so you can only select a single line on click
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
		
		productsTab.add(addProductButton, "2, 4");
		addProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new NewProductFrame(ptm);
			}
		});
		
		//BUG: after you delete the last item, pressing it again causes error. it remembers the last selection for some reason
		productsTab.add(removeProductButton, "2, 6");
		removeProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				if (selectedRow != -1)
					ptm.removeSelectedProduct(selectedRow);
			}
		});
		
		productsTab.add(new JScrollPane(productsTable), "4, 4, 7, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Record
		tabbedPane.addTab("Ιστορικό Παρραγγελιών", null, recordTab, null);
		
		recordTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("114px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("817px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("99px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),}));
		
		recordTab.add(DateLabel, "2, 2, right, center");

		//we create a table model so that we can manipulate it's data
		ArrayList<Order> testOrdersRecordArray = new ArrayList<>();
		testOrdersRecordArray = OrderingManagment.fetchRecord();
		
		RecordTableModel rtm = new RecordTableModel(testOrdersRecordArray);
		recordTable = new JTable(rtm);
		
		rtm.update(testOrdersRecordArray);
		
		//this disallows reordering of columns
		recordTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		recordTable.setCellSelectionEnabled(false);
		recordTable.setColumnSelectionAllowed(false);		
		recordTable.setRowSelectionAllowed(true);

		//these make that so you can only select a single line on click
		recordTable.setSelectionModel(new ForcedListSelectionModel());
		
		recordTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ListSelectionModel rowSM = recordTable.getSelectionModel();
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		recordTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		recordTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		recordTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		recordTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		
		recordTab.add(new JScrollPane(recordTable), "2, 4, 5, 1, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(100, 100);
		this.setVisible(true);
		this.setTitle("Easy Orders 1.0");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				String ObjButtons[] = {"Ναι", "Όχι"};			
				int PromptResult = JOptionPane.showOptionDialog(null, "Έξοδος;", "Easy Orders 1.0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					
				if(PromptResult == JOptionPane.YES_OPTION)
				{
					//if this is not set, if a stray window stays open, the application won't stop
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose();
				}
			}
		});
	}
	
	//model for allowing only single line selection
	public class ForcedListSelectionModel extends DefaultListSelectionModel
	{

	    public ForcedListSelectionModel () 
	    {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection()
	    {
	    	
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1)
	    {
	    	
	    }

	}
	
	private void createTab(JTabbedPane aTabbedPane)
	{	
		//if there is already a New Order tab, just switch to this without creating a new one -- avoid cluster
		if (aTabbedPane.indexOfTab("Νέα Παραγγελία") != -1) //exists
		{	
			aTabbedPane.setSelectedIndex(aTabbedPane.indexOfTab("Νέα Παραγγελία"));
			return;
		}
		
		//remove "+" tab if exists to add it later
		if (aTabbedPane.indexOfTab(" + ") != -1) //exists
			aTabbedPane.remove(aTabbedPane.indexOfTab(" + "));

		JLabel tabTitleLabel = new JLabel("Νέα Παραγγελία");
		tabTitleLabel.setPreferredSize(new Dimension(200, 30));
		
		JPanel newOrderPanel = new JPanel();
		aTabbedPane.addTab("Νέα Παραγγελία",newOrderPanel);
		
		JTextField nameTextField;
		
		JButton deleteOrderButton = new JButton("Διαγραφή Παραγγελίας");
		deleteOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				removeSelectedTabFrom(aTabbedPane);
			}
		});
		deleteOrderButton.setVisible(false);
		newOrderPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(67dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("755px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:123px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("481px"),
				FormSpecs.GLUE_ROWSPEC,
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.GLUE_ROWSPEC,}));
		
		JLabel nameLabel = new JLabel("Ονοματεπώνυμο");
		newOrderPanel.add(nameLabel, "3, 2, fill, fill");
		nameTextField = new JTextField();
		newOrderPanel.add(nameTextField, "5, 2, fill, fill");
		nameTextField.setColumns(10);
		
		JLabel productsLabel = new JLabel("Προϊόντα");
		newOrderPanel.add(productsLabel, "3, 4, fill, fill");

		
		newOrderPanel.add(deleteOrderButton, "7, 7, fill, fill");
		
		JButton saveButton = new JButton("Αποθήκευση");
		newOrderPanel.add(saveButton, "7, 9, fill, fill");
		
		saveButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//make each tab distinct by name
				aTabbedPane.setTitleAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), nameTextField.getText());
				
				//set each label name to the desired name 
				tabTitleLabel.setText(nameTextField.getText());
				
				//add label to tab, replacing/overriding the title of it
				aTabbedPane.setTabComponentAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), tabTitleLabel);
				
				//the delete button now has purpose
				deleteOrderButton.setVisible(true);
			}
		});
		
		
		
		
		String[] ordersColumnNames = {"Προιόν", "Προέλευση", "Προμηθευτής", "Ποιότητα", "Συσκευασία", "Τεμάχια", "Ποσότητα/Βάρος", "Τιμή"};	
		String[][] ordersData = {
				{"Τομάτα", "Από το σπίτι μου", "Η μάνα σου", "ΑΑ", "Καλή", "6", "23", "69"},
				{"Ακκούρι", "Από το σπίτι μου", "Η μάνα σου", "ΑΑ", "Καλή", "6", "23", "69"},
		};

		
		//this makes all cells not editable
		ordersTable = new JTable(ordersData, ordersColumnNames);
		ordersTable.setBounds(117, 39, 568, 161);
		ordersTable.setModel(new DefaultTableModel(ordersData, ordersColumnNames)
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}
		});
		
		//this disallows reordering of columns
		ordersTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		ordersTable.setCellSelectionEnabled(false);
		ordersTable.setColumnSelectionAllowed(false);		
		ordersTable.setRowSelectionAllowed(true);
		
		//these make that so you can only select a single line on click
		ordersTable.setSelectionModel(new ForcedListSelectionModel());
		
		ordersTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ListSelectionModel rowSM = ordersTable.getSelectionModel();
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		
		JScrollPane clientsTabScrollPane = new JScrollPane(ordersTable);
		newOrderPanel.add(clientsTabScrollPane, "5, 4, 1, 6, fill, fill");
		

		addPlusSignTabAtTheEndOf(aTabbedPane);
		
		//the pre-last index is the New Order tab (index starts from 0)
		aTabbedPane.setSelectedIndex(aTabbedPane.getTabCount()-2);
	    	
	    //add a fixed-size label on each tab as title
	    aTabbedPane.setTabComponentAt(aTabbedPane.indexOfComponent(aTabbedPane.getSelectedComponent()), tabTitleLabel);
	}
	
	public void removeSelectedTabFrom(JTabbedPane aTabbedPane)
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
			createTab(aTabbedPane);
		}
	}
	
	public void addPlusSignTabAtTheEndOf(JTabbedPane aTabbedPane)
	{
		JPanel plusSignPanel = new JPanel();
		
		//puts the "+" sign tab last
		aTabbedPane.insertTab(" + ",null,plusSignPanel,null, aTabbedPane.getTabCount());
		
		JLabel label = new JLabel("\u0394\u03B7\u03BC\u03B9\u03BF\u03C5\u03C1\u03B3\u03AE\u03C3\u03C4\u03B5 \u03BC\u03AF\u03B1 \u03BD\u03AD\u03B1 \u03C0\u03B1\u03C1\u03B1\u03B3\u03B3\u03B5\u03BB\u03AF\u03B1");
		plusSignPanel.add(label);
		
		label.setIcon(new ImageIcon(SellerMainFrame.class.getResource("/images/contract.png")));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 45));
	}
}