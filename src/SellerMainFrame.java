import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class SellerMainFrame extends JFrame
{	
	private ArrayList<Employee> employees = new ArrayList<>();
	
	private Employee user;
	
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
	private JButton addNewOrderButton = new JButton("+");
	
	private JRadioButton clientNameRadioButton = new JRadioButton("Όνομα");
	private JRadioButton clientIdRadioButton = new JRadioButton("ΑΦΜ");
	private JRadioButton productNameRadioButton = new JRadioButton("Προιόν");
	private JRadioButton locationNameRadioButton = new JRadioButton("Προέλευση");
	private JRadioButton producerNameRadioButton = new JRadioButton("Προμυθευτής");
	
	private JComboBox searchProductComboBox = new JComboBox();
	private JComboBox<String> ordersOfUserComboBox = new JComboBox();
	
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
	
	private ThreadManagement threadManager = new ThreadManagement(1000);


	
	
	
	public SellerMainFrame(Employee user)
	{
		this.user = user;
		
		
		
		
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
					threadManager.ManageModelUpdateAtTab(selectedTabIndex, liveOrdersTabs);
					break;
					
				//Clients Tab
				case 1:
					threadManager.ManageModelUpdateAtTab(selectedTabIndex, ctm);
					break;
					
				//Products Tab
				case 2:
					threadManager.ManageModelUpdateAtTab(selectedTabIndex, ptm);
					break;
					
				//Record Tab
				case 3:
					threadManager.ManageModelUpdateAtTab(selectedTabIndex, rtm);
					break;
					
				default:
					break;
				}
			}
		});
		
		//Fetch employees.
		db.connect();
		try
		{
			String query = "SELECT * FROM employee";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			while(rs.next())
				employees.add(new Employee(rs.getString("name"), rs.getString("username"), rs.getString("password"), rs.getInt("privilege")));
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();
		
		//Populate JComboBox.
		for(Employee employee: employees)
			ordersOfUserComboBox.addItem(employee.getName());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1284px:grow"),},
			new RowSpec[] {
				RowSpec.decode("718px:grow"),}));
		
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
				ColumnSpec.decode("51px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("641px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("196px"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:200px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		ordersTab.add(addNewOrderButton, "1, 2, right, top");
		ordersTab.add(ordersFromUserLabel, "5, 2, right, fill");
		
		ordersTab.add(ordersOfUserComboBox, "7, 2, fill, fill");
		ordersTab.add(liveOrdersTabs, "1, 4, 7, 1, fill, fill");
		
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
				ColumnSpec.decode("90px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("781px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("109px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("89px:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:543px:grow"),
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
						threadManager.stopTicking();
						ctm.setClientUneditable(selectedRow);
						JDialog cif = new ClientInfoFrame(ctm.getClientAt(selectedRow));
						while (!cif.isDisplayable()) break;
						ctm.setClientEditable(selectedRow);
						threadManager.startTicking();
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
				new NewClientFrame(ctm);
			}
		});

		clientsTab.add(removeClientButton, "2, 6, fill, fill");
		removeClientButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int selectedRow = clientsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				if (selectedRow != -1)
					ctm.removeSelectedClient(selectedRow);
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
				ColumnSpec.decode("90px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("842px:grow"),
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
				int selectedRow = productsTable.getSelectionModel().getMinSelectionIndex();
				System.out.println("Row " + selectedRow + " is now selected."); //DEBUG
				if (!productsTable.getSelectionModel().isSelectionEmpty())
				{
					removeProductButton.setEnabled(true);
					if (ptm.isProductEditable(selectedRow))
						ptm.removeSelectedProduct(selectedRow);
					
					productsTable.getSelectionModel().clearSelection();
				}
				
				
			}
		});
		
		productsTab.add(new JScrollPane(productsTable), "4, 4, 7, 5, fill, fill");
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Record
		tabbedPane.addTab("Ιστορικό Παρραγγελιών", null, recordTab, null);

		//add a fixed-size label as title
		JLabel tabRecordLabel = new JLabel("Ιστορικό Παρραγγελιών");
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
				ColumnSpec.decode("99px:grow"),
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
		
		recordTab.add(new JScrollPane(recordTable), "2, 4, 5, 1, fill, fill");
		
		
		
		
		
		
		
		
		
		
		logoutMenuItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
		
		optionsMenu.add(logoutMenuItem);
		menuBar.add(optionsMenu);
		
		
		
		
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		this.setJMenuBar(menuBar);
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(0, 0);
		this.setBounds(100, 100, 1300, 790);
		this.setMinimumSize(new Dimension(1100, 700));
		this.setSize(1300, 800);
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