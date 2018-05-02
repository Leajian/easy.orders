import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	private JTable historyTable;
	
	private JTextField searchClientTextField = new JTextField();
	private JTextField searchProductTextField = new JTextField();
	
	private JButton addClientButton = new JButton("+");
	private JButton removeClientButton = new JButton("-");
	private JButton addProductButton = new JButton("+");
	private JButton removeProductButton = new JButton("-");
	
	private JRadioButton clientNameRadioButton = new JRadioButton("Όνομα");
	private JRadioButton clientIdRadioButton = new JRadioButton("ΑΦΜ");
	private JRadioButton productNameRadioButton = new JRadioButton("Προιόν");
	private JRadioButton locationNameRadioButton = new JRadioButton("Προέλευση");
	private JRadioButton supplierNameRadioButton = new JRadioButton("Προμυθευτής");
	
	private JLabel clientsearchLabel = new JLabel("Αναζήτηση");
	private JLabel productsearchLabel = new JLabel("Αναζήτηση");
	private JLabel DateLabel = new JLabel("Ημερομηνία");

	private JPanel ordersTab = new JPanel();
	private JPanel clientsTab = new JPanel();
	private JPanel productsTab = new JPanel();
	private JPanel historyTab = new JPanel();
	
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
		
		String[] clientColumnNames = {"Ονοματεπώνυμο", "ΑΦΜ", "Περιοχή", "Διεύθυνση", "Τ.Κ.", "Τηλέφωνο", "ΦΑΞ", "E-mail", "Σημειώσεις"};
		String[][] clientData = {{"", ""}};
		
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
		
		//this makes all cells not editable
		clientsTable = new JTable(clientData, clientColumnNames);
		clientsTable.setModel(new DefaultTableModel(clientData, clientColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
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
			public void mouseReleased(MouseEvent e)
			{
				ListSelectionModel rowSM = clientsTable.getSelectionModel();
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		clientsTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		clientsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		clientsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		clientsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		clientsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		clientsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		clientsTable.getColumnModel().getColumn(7).setPreferredWidth(159);
		clientsTable.getColumnModel().getColumn(8).setPreferredWidth(227);
		
		clientsTab.add(addClientButton, "2, 4");
		clientsTab.add(removeClientButton, "2, 6");
		
		clientsTable.setBounds(46, 35, 881, 319);
		
		clientsTab.add(new JScrollPane(clientsTable), "4, 4, 5, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Products.
		tabbedPane.addTab("Προϊόντα", null, productsTab, null);
		
		String[] productColumnNames = {"Προιόν", "Προέλευση", "Προμυθευτής", "Ποιότητα", "Συσκευασία", "Τιμή", "Απόθεμα"};
		String[][] productData = {{"", ""}};
		
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
		
		productsTab.add(searchProductTextField, "4, 2, fill, default");
		searchProductTextField.setColumns(10);
		
		productNameRadioButton.setSelected(true);
		productsTab.add(productNameRadioButton, "6, 2, left, top");
		productsTab.add(locationNameRadioButton, "8, 2, left, top");
		productsTab.add(supplierNameRadioButton, "10, 2");
		
		ButtonGroup productsGroup = new ButtonGroup();	
		productsGroup.add(productNameRadioButton);
		productsGroup.add(locationNameRadioButton);
		productsGroup.add(supplierNameRadioButton);
		
		//this makes all cells not editable
		productsTable = new JTable(productData, productColumnNames);
		productsTable.setModel(new DefaultTableModel(productData, productColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
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
				ListSelectionModel rowSM = productsTable.getSelectionModel();
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		productsTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		productsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		productsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		productsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		productsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		productsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		
		productsTab.add(addProductButton, "2, 4");
		productsTab.add(removeProductButton, "2, 6");
		
		productsTab.add(new JScrollPane(productsTable), "4, 4, 7, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//History
		tabbedPane.addTab("Ιστορικό Παρραγγελιών", null, historyTab, null);
		
		String[] historyColumnNames = {"Ονοματεπώνυμο", "ΑΦΜ", "Ημερομηνία", "Πωλητής"};
		String[][] historytData = {{"", ""}};
		
		historyTab.setLayout(new FormLayout(new ColumnSpec[] {
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
		
		historyTab.add(DateLabel, "2, 2, right, center");
		
		//this makes all cells not editable
		historyTable = new JTable(historytData, historyColumnNames);
		historyTable.setModel(new DefaultTableModel(historytData, historyColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		
		//this disallows reordering of columns
		historyTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		historyTable.setCellSelectionEnabled(false);
		historyTable.setColumnSelectionAllowed(false);		
		historyTable.setRowSelectionAllowed(true);

		//these make that so you can only select a single line on click
		historyTable.setSelectionModel(new ForcedListSelectionModel());
		
		historyTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				ListSelectionModel rowSM = historyTable.getSelectionModel();
				System.out.println("Row " + rowSM.getMinSelectionIndex() + " is now selected.");
			}
		});
		
		historyTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		historyTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		historyTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		historyTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		
		historyTab.add(new JScrollPane(historyTable), "2, 4, 5, 1, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
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
					dispose();
			}
		});
	}
	
	//model for allowing only single line selection
	public class ForcedListSelectionModel extends DefaultListSelectionModel {

	    public ForcedListSelectionModel () {
	        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    }

	    @Override
	    public void clearSelection() {
	    }

	    @Override
	    public void removeSelectionInterval(int index0, int index1) {
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
		ordersTable.setModel(new DefaultTableModel(ordersData, ordersColumnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
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
