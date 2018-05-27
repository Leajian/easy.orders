import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class OrdersRefresher extends AbstractEntityRefresher
{	
	private ArrayList<Order> orders;
	
	private DBConnect db = new DBConnect();
	
	private static Employee user;
	private String states;
	
	public OrdersRefresher(JTabbedPane aTabbedPane, Employee user)
	{
		super(aTabbedPane, "orders");
		OrdersRefresher.user = user;
		
		switch (user.getPrivilege())
		{
		//Biller level
		case 1:
			//open and billing states
			states = "0, 1";
			break;
			
		//Seller level	
		case 2:
			//billing state
			states = "0";
			break;

		default:
			break;
		}

		System.out.println("Orders refresher instancieted");
		
//		aTabbedPane.addChangeListener(new ChangeListener()
//	    {
//			@Override
//			public void stateChanged(ChangeEvent evt) 
//			{
//				JTabbedPane tabbedPane = (JTabbedPane)evt.getSource();
//
//	            if((tabbedPane.getSelectedIndex() != tabbedPane.indexOfTab("Νέα Παραγγελία")) & (tabbedPane.indexOfTab("Νέα Παραγγελία") != -1) )
//	            {
//	            	String ObjButtons[] = {"Ναι", "Όχι"};			
//					int PromptResult = JOptionPane.showOptionDialog(null, "Η παραγγελία σας δεν έχει αποθηκευτεί, τα δεδομένα θα χαθούν. Είστε σίγουροι ότι θέλετε να αλλάξετε καρτέλα;", "Προσοχή!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
//				
//					if(PromptResult == JOptionPane.YES_OPTION)
//						aTabbedPane.removeTabAt(tabbedPane.indexOfTab("Νέα Παραγγελία"));
//					else
//						aTabbedPane.setSelectedIndex(tabbedPane.indexOfTab("Νέα Παραγγελία"));
//	            }
//			}
//	    });
		
		aTabbedPane.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent arg0)
			{
				if((aTabbedPane.indexOfTab("Νέα Παραγγελία") != -1) )
		        {
		            String ObjButtons[] = {"Ναι", "Όχι"};			
					int PromptResult = JOptionPane.showOptionDialog(null, "Η παραγγελία σας δεν έχει αποθηκευτεί, τα δεδομένα θα χαθούν. Είστε σίγουροι ότι θέλετε να αλλάξετε καρτέλα;", "Προσοχή!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					
					if(PromptResult == JOptionPane.YES_OPTION)
						aTabbedPane.removeTabAt(aTabbedPane.indexOfTab("Νέα Παραγγελία"));
					else
						aTabbedPane.setSelectedIndex(aTabbedPane.indexOfTab("Νέα Παραγγελία"));
		        }
			}
		});
	}

	protected int getObjSize()
	{
		String query = "";
		
		db.connect();
		try
		{
			switch(user.getPrivilege())
			{
			case 1:
				query = "SELECT COUNT(DISTINCT lastEdit, clientId) AS ordersCount FROM orders WHERE state = 1 OR (employeeUsername = '" + user.getUsername() + "' AND state = 0)";
				break;
			case 2:
				query = "SELECT COUNT(DISTINCT lastEdit, clientId) AS ordersCount FROM orders WHERE employeeUsername = '" + user.getUsername() + "' AND state = 0";
				break;
			default:
				break;
			}
			
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			int count = rs.getInt("ordersCount");
			
			db.closeConnection();
			
			return count;
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		db.closeConnection();

		return 0;
	}
	
	@Override
	protected void populator()
	{	
		//recreate orders array on demand	
		orders = DataFetcher.initializeOrders(user);
		
		//if there orders available
		if (!orders.isEmpty())
		{
			//delete the local copies
			((JTabbedPane) obj).removeAll();
			
			//add each new one as a tab
			for (Order order: orders)
				createNewTab((JTabbedPane) obj, order);
		}
		else //if there no orders
		{	
			//add the new order tab if it doesn't exist already and empty the tabbed pane once, if there are leftovers
			if ((((JTabbedPane) obj).indexOfTab("Νέα Παραγγελία") == -1 ) & (((JTabbedPane) obj).getTabCount() > 0 ))
			{
				((JTabbedPane) obj).removeAll();
				createNewTab((JTabbedPane) obj, null);
			}
			
			if (((JTabbedPane) obj).indexOfTab("Νέα Παραγγελία") == -1)
				createNewTab((JTabbedPane) obj, null);
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void createNewTab(JTabbedPane aTabbedPane, Order order)
	{	
		DBConnect db = new DBConnect();
		
		JPanel newOrderPanel = new JPanel();
		
		JTable ordersTable = null;
		
		OrderedProductsTableModel optm = new OrderedProductsTableModel(order);
		
		JLabel nameLabel = new JLabel("Ονοματεπώνυμο");
		JLabel productsLabel = new JLabel("Προϊόντα");
		
		JComboBox<Client> nameComboBox = new JComboBox<Client>();
	
		JButton addProductButton = new JButton("+");
		JButton removeProductButton = new JButton("-");
		JButton saveOrderButton = new JButton("Αποθήκευση");
		JButton deleteOrderButton = new JButton("Διαγραφή");
		JButton sendBackToSellerButton = new JButton("<html>" + "Επιστροφή" + "<br>" + "στον πωλητή" + "</html>");
		JButton changeStateOfOrderButton = new JButton();
		
		removeProductButton.setVisible(false);
		deleteOrderButton.setVisible(false);
		sendBackToSellerButton.setVisible(false);
		
		switch(user.getPrivilege())
		{
		//Biller level
		case 1:
			//closed state
			changeStateOfOrderButton.setText("Τιμολογήθηκε");
			
			if(order != null)
				if(!order.getEmployeeUsername().equals(user.getUsername()))
					sendBackToSellerButton.setVisible(true);
			
			break;
			
		//Seller level	
		case 2:
			//billing state
			changeStateOfOrderButton.setText("Τιμολόγηση");
			break;

		default:
			break;
		}
		
		JScrollPane ordersTableScrollPane;
		
		FormLayout fl_newOrderPanel = new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("1px"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
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
				RowSpec.decode("fill:105px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30dlu"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,});
		
		fl_newOrderPanel.setRowGroups(new int[][]{new int[]{6, 8}});
		newOrderPanel.setLayout(fl_newOrderPanel);
		
		newOrderPanel.add(nameLabel, "3, 2, right, fill");
		newOrderPanel.add(productsLabel, "3, 4, right, fill");
		
		newOrderPanel.add(nameComboBox, "5, 2, fill, fill");
		
		newOrderPanel.add(addProductButton, "3, 6, fill, fill");
		newOrderPanel.add(removeProductButton, "3, 8, fill, fill");
		
		newOrderPanel.add(sendBackToSellerButton, "3, 12, fill, fill");
		
		newOrderPanel.add(changeStateOfOrderButton, "3, 14, fill, fill");
		
		newOrderPanel.add(saveOrderButton, "3, 18, fill, fill");
		newOrderPanel.add(deleteOrderButton, "3, 16, fill, fill");
		
		ordersTable = new JTable(optm);
		ordersTable.setBounds(117, 39, 568, 161);
		
		ordersTableScrollPane = new JScrollPane(ordersTable);
		//ordersTableScrollPane.setVisible(false);
		newOrderPanel.add(ordersTableScrollPane, "5, 4, 1, 15, fill, fill");
		
		//this disallows reordering of columns
		ordersTable.getTableHeader().setReorderingAllowed(false);
		ordersTable.getTableHeader().setResizingAllowed(false);
		
		//these make that so you can only select a whole lines on click
		ordersTable.setCellSelectionEnabled(true);
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
			//nameComboBox.setText(order.getClientName());
			nameComboBox.addItem(new Client(order.getClientId()));
			nameComboBox.setEnabled(false);
			
			saveOrderButton.setEnabled(false);
			
			removeProductButton.setVisible(true);
			deleteOrderButton.setVisible(true);
			
			nameComboBox.setRenderer(new ClientComboBoxRenderer());
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
					
			noOrderLabel.setIcon(new ImageIcon(MainFrame.class.getResource("/images/contract.png")));
			noOrderLabel.setHorizontalAlignment(SwingConstants.CENTER);
			noOrderLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
			noOrderLabel.setBounds(117, 39, 568, 161);
			
			saveOrderButton.setEnabled(false);
			changeStateOfOrderButton.setVisible(false);
			sendBackToSellerButton.setVisible(false);

			ArrayList<Client> clients = new ArrayList<>();
			//Fetch clients.

			nameComboBox.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent arg0)
				{
					clients.clear();
					nameComboBox.removeAllItems();
					
					db.connect();
					try
					{
						String query = "SELECT id FROM client";
						ResultSet rs = db.getStatement().executeQuery(query);
						
						while(rs.next())
							clients.add(new Client(rs.getString("id")));
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					db.closeConnection();
					
					//Populate JComboBox.
					for(Client client : clients)
						nameComboBox.addItem(client);
				}
			});

			nameComboBox.setRenderer(new ClientComboBoxRenderer());
			
			
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
				int lastSize = optm.getOrderedProducts().size();
				
				SelectProductFrame spf = new SelectProductFrame(optm);
	
				while(!spf.isDisplayable()) break;
				
				if(lastSize != optm.getOrderedProducts().size())
				{
					saveOrderButton.setEnabled(true);
					removeProductButton.setVisible(true);
				}
				else
				{
					saveOrderButton.setEnabled(false);
				}
					
				
//				if (!optm.getOrderedProducts().isEmpty())
//				{
//					removeProductButton.setVisible(true);
//					ordersTableScrollPane.setVisible(true);
//				}
				
				
//				ordersTableScrollPane.setVisible(true);
				
			}
		});
		
		removeProductButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int selectedProduct = rowSM.getMinSelectionIndex();
				
				if(selectedProduct != -1)
				{
					optm.removeRow(selectedProduct);
					saveOrderButton.setEnabled(true);
					removeProductButton.setVisible(true);
				}
					
				else		
					JOptionPane.showMessageDialog(null, "Παρακαλώ επιλέξτε το προϊόν προς διαγραφή.", "Προσοχή!", JOptionPane.WARNING_MESSAGE);
			}
		});		
		
		changeStateOfOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				db.connect();				
				try
				{
					int state = 0;
					switch (user.getPrivilege())
					{
					//Biller level
					case 1:
						//closed state
						state = 2;
						
						String prodIds = "";
						
						for(int i = 0; i < optm.getRowCount(); i++)
							prodIds += optm.getOrderedProducts().get(i).getId() + ",";
						
						prodIds = prodIds.substring(0, prodIds.length() - 1);
						prodIds = "(" + prodIds + ")";
						//System.out.println(prodId);
						
						ArrayList<Integer> stockOfProducts = new ArrayList<>();
						
						try
						{
							String query = "SELECT stock FROM product WHERE id IN " + prodIds;
							ResultSet rs = db.getStatement().executeQuery(query);
							
							while(rs.next())
								stockOfProducts.add(rs.getInt("stock"));
						} 
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
						
						boolean forClose = true;
						
						for(int i = 0; i < optm.getRowCount(); i++)
						{
							int newStock = stockOfProducts.get(i) - Integer.parseInt(optm.getOrderedProducts().get(i).getQuantityWeight());
								
							if(newStock < 0)
							{
								forClose = false;
								JOptionPane.showMessageDialog(null, "Το απόθεμα δεν επαρκεί.", "Easy Orders 1.0", JOptionPane.WARNING_MESSAGE);
								break;
							}
									
								
							try
							{
								String query = "UPDATE product SET stock = '" + newStock + "' WHERE id = '" + optm.getOrderedProducts().get(i).getId() + "'";
								int rs1 = db.getStatement().executeUpdate(query);
							}
							catch (Exception ex)
							{
								ex.printStackTrace();
							}
								
							
							
								
								
							
						}
						
						if(forClose)
						{
							String query = "UPDATE orders SET state = " + state + " WHERE lastEdit = '" + order.getLastEdit() + "' " + "AND clientId = '" + order.getClientId() + "'";
							int rs = db.getStatement().executeUpdate(query);
							changeStateOfOrderButton.setEnabled(false);
						}
						
						
						break;
						
					//Seller level	
					case 2:
						//billing state
						state = 1;
						break;

					default:
						break;
					}
					
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				db.closeConnection();
				
				
			}
		});
		
		saveOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{	
				if (optm.hasProducts())
				{
					
					String CURRENT_TIMESTAMP = null;
					
					db.connect();
					try
					{
						String query = "SELECT CURRENT_TIMESTAMP AS curr_time";
						ResultSet rs = db.getStatement().executeQuery(query);
						
						rs.next();
						
						CURRENT_TIMESTAMP = rs.getString("curr_time");
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
					try
					{
						String query = "DELETE FROM orders WHERE lastEdit = '" + order.getLastEdit() + "'" + "AND clientId = '" + order.getClientId() + "'";
						int rs = db.getStatement().executeUpdate(query);
					} 
					catch (Exception ex)
					{
						//ex.printStackTrace();
					}
					
					try
					{
						int state = 0;
						
						if(user.getPrivilege() == 1)
							state = 1;
						
						String query = "INSERT INTO orders (lastEdit, productId, clientId, quantityWeight, price, employeeUsername, state) VALUES ";
						
						for(int i = 0; i < optm.getRowCount(); i++)
							query += "(CURRENT_TIMESTAMP, " + "'" + optm.getOrderedProducts().get(i).getId() + "', " + "'" + ((Client) nameComboBox.getSelectedItem()).getId() + "', '" + optm.getOrderedProducts().get(i).getQuantityWeight() + "', '" + optm.getOrderedProducts().get(i).getPrice() + "', '" + user.getUsername() + "', " + state + "),";
						
						
						query = query.substring(0, query.length() - 1);
						int rs = db.getStatement().executeUpdate(query);
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
					/*String prodIds = "";
					
					for(int i = 0; i < optm.getRowCount(); i++)
						prodIds += optm.getOrderedProducts().get(i).getId() + ",";
					
					prodIds = prodIds.substring(0, prodIds.length() - 1);
					prodIds = "(" + prodIds + ")";
					//System.out.println(prodId);
					
					ArrayList<Integer> stockOfProducts = new ArrayList<>();
					
					try
					{
						String query = "SELECT stock FROM product WHERE id IN " + prodIds;
						ResultSet rs = db.getStatement().executeQuery(query);
						
						while(rs.next())
							stockOfProducts.add(rs.getInt("stock"));
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					
					for(int i = 0; i < optm.getRowCount(); i++)
					{
						int newStock = stockOfProducts.get(i) - Integer.parseInt(optm.getOrderedProducts().get(i).getQuantityWeight());
						
						try
						{
							String query = "UPDATE product SET stock = '" + newStock + "' WHERE id = '" + optm.getOrderedProducts().get(i).getId() + "'";
							int rs1 = db.getStatement().executeUpdate(query);
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}*/
					db.closeConnection();
					
					deleteOrderButton.setVisible(true);
					saveOrderButton.setEnabled(false);
					
					//aTabbedPane.setSelectedIndex(aTabbedPane.indexOfTab(((Client) nameComboBox.getSelectedItem()).getId() + CURRENT_TIMESTAMP));
				}
				else
					JOptionPane.showMessageDialog(null, "Παρακαλώ προσθέστε προϊόντα στην παραγγελία πριν την αποθηκεύσετε.", "Κενή παραγγελία", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		deleteOrderButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				String ObjButtons[] = {"Ναι", "Όχι"};			
				int PromptResult = JOptionPane.showOptionDialog(null, "Διαγραφή;", "Easy Orders 1.0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
					
				if(PromptResult == JOptionPane.YES_OPTION)
				{
					db.connect();
					try
					{
						String query = "DELETE FROM orders WHERE lastEdit = '" + order.getLastEdit() + "'" + "AND clientId = '" + order.getClientId() + "'";
						int rs = db.getStatement().executeUpdate(query);
					} 
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
					db.closeConnection();
				}
			}
		});
		
		sendBackToSellerButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				db.connect();
				try
				{
					String query = "UPDATE orders SET state = 0 WHERE lastEdit = '" + order.getLastEdit() + "' " + "AND clientId = '" + order.getClientId() + "'";
					int rs = db.getStatement().executeUpdate(query);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				db.connect();
				
				sendBackToSellerButton.setEnabled(false);
			}
		});
	}
}