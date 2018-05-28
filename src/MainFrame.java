import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class MainFrame extends JFrame
{	
	private ArrayList<Employee> employees = new ArrayList<>();
	
	private final Employee user;
	
	private DBConnect db = new DBConnect();
	
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
	private JTabbedPane liveOrdersTabs = new JTabbedPane(JTabbedPane.LEFT);
	
	private JMenuItem logoutMenuItem = new JMenuItem("Αποσύνδεση");
	
	private JMenu optionsMenu = new JMenu("Επιλογές");
	
	private JMenuBar menuBar = new JMenuBar();

	private JTable clientsTable;
	private JTable productsTable;
	private JTable recordTable;
	
	private JTextField searchClientTextField = new JTextField();
	
	private JButton addClientButton = new JButton("+");
	private JButton removeClientButton = new JButton("-");
	private JButton addProductButton = new JButton("+");
	private JButton removeProductButton = new JButton("-");
	private JButton addNewOrderButton = new JButton("Νέα Παραγγελία");
	private JButton clearButton = new JButton("Έξοδος");
	
	private JRadioButton clientNameRadioButton = new JRadioButton("Όνομα");
	private JRadioButton clientIdRadioButton = new JRadioButton("ΑΦΜ");
	private JRadioButton productNameRadioButton = new JRadioButton("Προιόν");
	private JRadioButton locationNameRadioButton = new JRadioButton("Προέλευση");
	private JRadioButton producerNameRadioButton = new JRadioButton("Προμυθευτής");
	
	private JComboBox searchProductComboBox = new JComboBox();
	private JComboBox<Employee> ordersOfUserComboBox = new JComboBox();
	
	private JLabel clientsearchLabel = new JLabel("Αναζήτηση");
	private JLabel productsearchLabel = new JLabel("Αναζήτηση");
	private JLabel dateLabel = new JLabel("Ημερομηνία");
	private JLabel ordersFromUserLabel = new JLabel("Παραγγελίες του χρήστη :");

	private JPanel ordersTab = new JPanel();
	private JPanel clientsTab = new JPanel();
	private JPanel productsTab = new JPanel();
	private JPanel recordTab = new JPanel();
	
	//we create table models so that we can manipulate tables' data
	private ClientsTableModel ctm = new ClientsTableModel(new ArrayList<>());
	private ProductsTableModel ptm = new ProductsTableModel(new ArrayList<>());
	private RecordTableModel rtm = new RecordTableModel(new ArrayList<>());
	
	private ThreadManagement threadManager;
	private final JButton employeesListRefreshButton = new JButton("Ανανέωση χρηστών");

	public MainFrame(Employee user)
	{
		this.user = user;
		threadManager = new ThreadManagement(1000, user);
		
		
		
		liveOrdersTabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		tabbedPane.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent evt)
			{
				JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
				int selectedTabIndex = tabbedPane.getSelectedIndex();
				
				switch (selectedTabIndex)
				{
				
				//Orders Tab
				case 0:
					threadManager.manageUpdatesAtTab(selectedTabIndex, liveOrdersTabs);
					break;
					
				//Clients Tab
				case 1:
					threadManager.manageUpdatesAtTab(selectedTabIndex, ctm);
					break;
					
				//Products Tab
				case 2:
					threadManager.manageUpdatesAtTab(selectedTabIndex, ptm);
					break;
					
				//Record Tab
				case 3:
					threadManager.manageUpdatesAtTab(selectedTabIndex, rtm);
					break;
					
				default:
					break;
				}
			}
		});
		
		//visiting another userspace
		ordersOfUserComboBox.addItemListener(new ItemListener() {
	        public void itemStateChanged(ItemEvent event) {
	        	if (event.getStateChange() == ItemEvent.SELECTED) {         
	        		threadManager.stopTicking();
					Employee selectedUser = (Employee) event.getItem();
					threadManager = new ThreadManagement(1000, selectedUser);
					threadManager.manageUpdatesAtTab(0, liveOrdersTabs);
	        	}
	        }
	    });
		
		//set the user name in the combobox
		
		employees = DataFetcher.initializeEmployees();
		
		//Populate JComboBox.
		for(Employee employee : employees)
			//disallow viewing from admin level accounts, unless you are admin
			if (employee.getPrivilege() >= user.getPrivilege())
				ordersOfUserComboBox.addItem(employee);
		ordersOfUserComboBox.setSelectedItem(user);
		
		ordersOfUserComboBox.setRenderer(new EmployeeComboBoxRenderer());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("830px:grow"),},
			new RowSpec[] {
				RowSpec.decode("488px:grow"),}));
		
		this.getContentPane().add(tabbedPane, "1, 1, fill, fill");
		
		
		
		
		
		
		//Orders.
		tabbedPane.addTab("Παραγγελίες", null, ordersTab, null);
		
		//add a fixed-size label as title
		JLabel tabOrdersLabel = new JLabel("Παραγγελίες");
		tabOrdersLabel.setPreferredSize(new Dimension(125, 35));
		tabOrdersLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tabOrdersLabel.setVerticalAlignment(SwingConstants.CENTER);
	    tabbedPane.setTabComponentAt(0, tabOrdersLabel);
		addNewOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				OrdersRefresher.createNewTab(liveOrdersTabs, null);
			}
		});
		ordersTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("97px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("196px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:534px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		ordersTab.add(addNewOrderButton, "2, 2, right, fill");
		ordersTab.add(ordersFromUserLabel, "6, 2, right, fill");
		
		ordersTab.add(ordersOfUserComboBox, "8, 2, fill, fill");
		employeesListRefreshButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Employee lastSelectedEmployee = (Employee) ordersOfUserComboBox.getSelectedItem();
				employees.clear();
				ordersOfUserComboBox.removeAllItems();
				
				employees = DataFetcher.initializeEmployees();
				
				//Populate JComboBox.
				for(Employee employee : employees)
					//disallow viewing from admin level accounts, unless you are admin
					if (employee.getPrivilege() >= user.getPrivilege())
						ordersOfUserComboBox.addItem(employee);
				ordersOfUserComboBox.setSelectedItem(lastSelectedEmployee);
			}
		});
		
		ordersTab.add(employeesListRefreshButton, "10, 2, fill, fill");
		ordersTab.add(liveOrdersTabs, "2, 4, 9, 1, fill, fill");
		
//		createNewTab(liveOrdersTabs, null);
//		liveOrdersTabs.addChangeListener(new ChangeListener()
//	    {
//			@Override
//			public void stateChanged(ChangeEvent evt) 
//			{
//				JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
//
//	            if(tabbedPane.getSelectedIndex() == tabbedPane.indexOfTab(" + "))
//	            	createNewTab(liveOrdersTabs, null);
//			}
//	    });
		
		
		
		
		
		
		
		
		
		
		
		

		//Clients.
		tabbedPane.addTab("Πελάτες", null, clientsTab, null);
		
		//add a fixed-size label as title
		JLabel tabClientsLabel = new JLabel("Πελάτες");
		tabClientsLabel.setPreferredSize(new Dimension(125, 35));
		tabClientsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tabClientsLabel.setVerticalAlignment(SwingConstants.CENTER);
	    tabbedPane.setTabComponentAt(1, tabClientsLabel);
		
		FormLayout fl_clientsTab = new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("93px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("613px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("109px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("-23px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:397px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,});
		fl_clientsTab.setRowGroups(new int[][]{new int[]{4, 6}});
		fl_clientsTab.setColumnGroups(new int[][]{new int[]{6, 8}});
		clientsTab.setLayout(fl_clientsTab);
		
		clientsTab.add(clientsearchLabel, "2, 2, right, center");
		
		clientsTab.add(searchClientTextField, "4, 2, fill, default");
		searchClientTextField.setColumns(10);
		
		clientNameRadioButton.setSelected(true);
		clientsTab.add(clientNameRadioButton, "6, 2, left, top");
		clientsTab.add(clientIdRadioButton, "8, 2, left, top");
		
		ButtonGroup clientsGroup = new ButtonGroup();	
		clientsGroup.add(clientNameRadioButton);
		clientsGroup.add(clientIdRadioButton);
		
		clientsTable = new JTable(ctm);

		//this disallows reordering of columns
		clientsTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		clientsTable.setCellSelectionEnabled(false);
		clientsTable.setColumnSelectionAllowed(false);		
		clientsTable.setRowSelectionAllowed(true);
		clientsTable.getTableHeader().setResizingAllowed(false);
		
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
				if (e.getClickCount() == 2)
				{
					//threadManager.stopModelUpdates();
					if (ctm.isClientEditable(selectedRow))
					{
						//threadManager.stopTicking();
						ctm.setClientUneditable(selectedRow);
						JDialog cif = new ClientInfoFrame(ctm.getClientAt(selectedRow), tabbedPane, rtm, threadManager);
						while (!cif.isDisplayable()) break;
						ctm.setClientEditable(selectedRow);
						//threadManager.startTicking();
					}
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
		
		clientsTab.add(addClientButton, "2, 4, fill, fill");
		addClientButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				new NewClientFrame();
			}
		});

		clientsTab.add(removeClientButton, "2, 6, fill, fill");
		removeClientButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String ObjButtons[] = {"Ναι", "Όχι"};			
				int PromptResult = JOptionPane.showOptionDialog(null, "Διαγραφή;", "Easy Orders 1.0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					
				if(PromptResult == JOptionPane.YES_OPTION)
				{
					int selectedRow = clientsTable.getSelectionModel().getMinSelectionIndex();
					System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
					if (selectedRow != -1)
						ctm.removeSelectedClient(selectedRow);
					
				}
			}
		});
		
		clientsTable.setBounds(46, 35, 881, 319);
		
		clientsTab.add(new JScrollPane(clientsTable), "4, 4, 5, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Products.
		tabbedPane.addTab("Προϊόντα", null, productsTab, null);
		
		//add a fixed-size label as title
		JLabel tabProductsLabel = new JLabel("Προϊόντα");
		tabProductsLabel.setPreferredSize(new Dimension(125, 35));
		tabProductsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tabProductsLabel.setVerticalAlignment(SwingConstants.CENTER);
		tabbedPane.setTabComponentAt(2, tabProductsLabel);
		
		FormLayout fl_productsTab = new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("93px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("431px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("99px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("89px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:0px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:415px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,});
		fl_productsTab.setRowGroups(new int[][]{new int[]{4, 6}});
		fl_productsTab.setColumnGroups(new int[][]{new int[]{6, 8, 10}});
		productsTab.setLayout(fl_productsTab);
		
		productsTab.add(productsearchLabel, "2, 2, right, center");

		//searchProductComboBox.setEditable(true);
		productsTab.add(searchProductComboBox, "4, 2, fill, default");
		
		productNameRadioButton.setSelected(true);
		productsTab.add(productNameRadioButton, "6, 2, left, top");
		productsTab.add(locationNameRadioButton, "8, 2, left, top");
		productsTab.add(producerNameRadioButton, "10, 2, left, top");
		
		ButtonGroup productsGroup = new ButtonGroup();
		productsGroup.add(productNameRadioButton);
		productsGroup.add(locationNameRadioButton);
		productsGroup.add(producerNameRadioButton);
		
		productsTable = new JTable(ptm);
		
		
		//this disallows reordering of columns
		productsTable.getTableHeader().setReorderingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		productsTable.setCellSelectionEnabled(false);
		productsTable.setColumnSelectionAllowed(false);		
		productsTable.setRowSelectionAllowed(true);
		productsTable.getTableHeader().setResizingAllowed(false);
		
		//these make that so you can only select a single line on click
		productsTable.setSelectionModel(new ForcedListSelectionModel());
		
		productsTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				
				//show info on double click
				if (e.getClickCount() == 2)
				{
					if (ptm.isProductEditable(selectedRow))
					{
						threadManager.stopTicking();
						ptm.setProductUneditable(selectedRow);
						ProductInfoFrame pif = new ProductInfoFrame(ptm.getProductAt(selectedRow));
						while (!pif.isDisplayable()) break;
						ptm.setProductEditable(selectedRow);
						threadManager.startTicking();
					}
				}	
			}
		});
		
		productsTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		productsTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		productsTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		productsTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		productsTable.getColumnModel().getColumn(5).setPreferredWidth(115);
		productsTable.getColumnModel().getColumn(6).setPreferredWidth(115);
		
		productsTab.add(addProductButton, "2, 4, fill, fill");
		addProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
//				threadManager.stopModelUpdates();
//				JDialog pf = new NewProductFrame();
//				while (true)
//				{
//					if (!pf.isDisplayable()) break;
//				}
//				threadManager.startProductsTableModelUpdates(ptm);
				
				new NewProductFrame();
			}
		});
		
		//BUG: after you delete the last item, pressing it again causes error. it remembers the last selection for some reason
		productsTab.add(removeProductButton, "2, 6, fill, fill");
		removeProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String ObjButtons[] = {"Ναι", "Όχι"};			
				int PromptResult = JOptionPane.showOptionDialog(null, "Διαγραφή;", "Easy Orders 1.0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					
				if(PromptResult == JOptionPane.YES_OPTION)
				{
					int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
					System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
					if (selectedRow != -1)
						ptm.removeSelectedProduct(selectedRow);
					
				}
			}
		});
		
		productsTab.add(new JScrollPane(productsTable), "4, 4, 7, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Record
		tabbedPane.addTab("Ιστορικό Παραγγελιών", null, recordTab, null);

		//add a fixed-size label as title
		JLabel tabRecordLabel = new JLabel("Ιστορικό Παραγγελιών");
		tabRecordLabel.setPreferredSize(new Dimension(125, 35));
		tabRecordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tabRecordLabel.setVerticalAlignment(SwingConstants.CENTER);
		tabbedPane.setTabComponentAt(3, tabRecordLabel);
		
		recordTab.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("114px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("817px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		recordTab.add(dateLabel, "2, 2, right, center");

		recordTable = new JTable(rtm);
		
		//this disallows reordering of columns
		recordTable.getTableHeader().setReorderingAllowed(false);
		recordTable.getTableHeader().setResizingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		recordTable.setCellSelectionEnabled(false);
		recordTable.setColumnSelectionAllowed(false);		
		recordTable.setRowSelectionAllowed(true);

		//these make that so you can only select a single line on click
		recordTable.setSelectionModel(new ForcedListSelectionModel());
		
		recordTable.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int selectedRow = recordTable.getSelectionModel().getMinSelectionIndex();

				if (e.getClickCount() == 2)
					new OrderedProductsFrame(rtm.getOrderAt(selectedRow));
			}
		});
		
		recordTable.getColumnModel().getColumn(0).setPreferredWidth(165);
		recordTable.getColumnModel().getColumn(1).setPreferredWidth(104);
		recordTable.getColumnModel().getColumn(2).setPreferredWidth(143);
		recordTable.getColumnModel().getColumn(3).setPreferredWidth(145);
		
		
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				//clearButton.setVisible(false);
				threadManager.startTicking();		
			}
		});
		
		recordTab.add(clearButton, "6, 2");
		
		recordTab.add(new JScrollPane(recordTable), "2, 4, 5, 1, fill, fill");
		
		
		
		
		
		
		
		
		
		
		logoutMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				new LoginFrame();
				threadManager.stopTicking();
				dispose();
				//System.exit(0);
			}
		});
		
		optionsMenu.add(logoutMenuItem);
		menuBar.add(optionsMenu);
		
		
		
		
		
		


		
		
		
		
		
		this.setJMenuBar(menuBar);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(0, 0);
		this.setBounds(100, 100, 1100, 700);
		this.setMinimumSize(new Dimension(1200, 700));
		this.setSize(1100, 700);
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
}