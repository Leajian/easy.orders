import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

public class ClientInfoFrame extends JDialog
{
	private JTextField nameField = new JTextField();
	private JTextField idField = new JTextField();
	private JTextField cityField = new JTextField();
	private JTextField addressField = new JTextField();
	private JTextField phoneNumberField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField zipCodeField = new JTextField();
	private JTextField faxField = new JTextField();
	private JTextArea notesField = new JTextArea();
	
	private JLabel nameLab = new JLabel("Ονοματεπώνυμο");
	private JLabel idLab = new JLabel("ΑΦΜ");
	private JLabel cityLab = new JLabel("Έδρα/Πόλη");
	private JLabel addressLab = new JLabel("Διεύθυνση");
	private JLabel phoneNumberLab = new JLabel("Τηλέφωνο");
	private JLabel emailLab = new JLabel("E-mail");
	private JLabel zipCodeLab = new JLabel("Τ.Κ.");
	private JLabel faxLab = new JLabel("ΦΑΞ");
	private JLabel notesLab = new JLabel("Σημειώσεις");
	private JLabel clientDetailsLabel = new JLabel("IMAGE PLACEHOLDER");
	
	private JButton saveButton = new JButton("Αποθήκευση");
	private JButton ordersRecordbutton = new JButton("Ιστορικό Παραγγελιών");
	
	private JCheckBox editCheckBox = new JCheckBox("Επεξεργασία");
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	private JScrollPane notesFieldScrollPane = new JScrollPane();
	
	public ClientInfoFrame(Client client, JTabbedPane aTabbedPane, RecordTableModel rtm, ThreadManagement threadManager)
	{
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			
		idField.setText(client.getId());
		nameField.setText(client.getName());
		cityField.setText(client.getCity());
		addressField.setText(client.getAddress());
		phoneNumberField.setText(client.getPhoneNumber());
		emailField.setText(client.getEmail());
		zipCodeField.setText(client.getZipCode());
		faxField.setText(client.getFax());
		notesField.setText(client.getNotes());	
		
		nameField.setEditable(false);
		nameField.setPreferredSize(new Dimension(150, 20));
		
		idField.setEditable(false);
		idField.setPreferredSize(new Dimension(70, 20));
		
		cityField.setEditable(false);
		cityField.setPreferredSize(new Dimension(150, 20));
		
		zipCodeField.setEditable(false);
		zipCodeField.setPreferredSize(new Dimension(70, 20));
		
		phoneNumberField.setEditable(false);
		phoneNumberField.setPreferredSize(new Dimension(150, 20));
		
		emailField.setEditable(false);
		emailField.setPreferredSize(new Dimension(150, 20));
		
		faxField.setEditable(false);
		faxField.setPreferredSize(new Dimension(70, 20));
		
		addressField.setEditable(false);
		addressField.setPreferredSize(new Dimension(150, 20));

		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("230px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("144px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("max(146dlu;default):grow"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("60px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("33px"),
				FormSpecs.LINE_GAP_ROWSPEC,}));
		
		panel.add(clientDetailsLabel, "2, 2, 7, 1, center, center");
		panel.add(nameLab, "2, 4, fill, center");
		panel.add(nameField, "4, 4, fill, fill");
		panel.add(idLab, "6, 4, fill, center");
		panel.add(idField, "8, 4, fill, fill");
		panel.add(cityLab, "2, 6, fill, center");
		panel.add(cityField, "4, 6, fill, fill");
		panel.add(addressLab, "2, 8, left, center");
		panel.add(addressField, "4, 8, fill, fill");
		panel.add(zipCodeLab, "6, 8, fill, center");
		panel.add(zipCodeField, "8, 8, fill, fill");
		panel.add(phoneNumberLab, "2, 10, left, center");
		panel.add(phoneNumberField, "4, 10, fill, fill");
		panel.add(faxLab, "6, 10, fill, center");
		panel.add(faxField, "8, 10, fill, fill");
		panel.add(emailLab, "2, 12, left, center");
		panel.add(emailField, "4, 12, fill, fill");
		panel.add(notesLab, "2, 14, fill, top");
		
		notesFieldScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(notesFieldScrollPane, "4, 14, fill, fill");
		notesField.setBackground(UIManager.getColor("TextField.disabledBackground"));
		notesField.setEditable(false);
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		notesFieldScrollPane.setViewportView(notesField);
		notesField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		panel.add(ordersRecordbutton, "2, 18, 2, 1, left, fill");
		
		ordersRecordbutton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				//select record tab
				aTabbedPane.setSelectedIndex(3);
				
				//stop auto refresh
				threadManager.stopTicking();
				
				//lock on target client
				rtm.lockOnClient(client);
				
				//close window
				dispose();
			}
		});
		
		saveButton.setEnabled(false);
		panel.add(saveButton, "8, 18, right, fill");
		panel.add(editCheckBox, "8, 16, right, fill");
		
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String id = idField.getText();
				String name = nameField.getText();
				String city = cityField.getText();
				String phoneNumber = phoneNumberField.getText();
				String email = emailField.getText();
				String address = addressField.getText();
				String fax = faxField.getText();
				String zipCode = zipCodeField.getText();
				String notes = notesField.getText();
				
				if(!(name.trim().equals("")))
				{
					if((id.length() == 9) & (name.length() <= 30) & (city.length() <= 30) & (phoneNumber.length() <= 15) & (email.length() <= 30) & (address.length() <= 30) & (fax.length() <= 15) & (zipCode.length() <= 10) & (notes.length() <= 120))
					{
						db.connect();
						try
						{
							String query = "UPDATE client SET name = " + "'" + name + "', " + "city = " + "'" + city + "', " + "phoneNumber = " + "'" + phoneNumber + "', " + "email = " + "'" + email + "', " +  "address = " + "'" + address + "', " + "fax = " + "'" + fax + "', " + "zipCode = " + "'" + zipCode + "', " + "notes = " + "'" + notes + "' WHERE client.id = " + "'" + id + "'";
							int rs = db.getStatement().executeUpdate(query);
							dispose();
							JOptionPane.showMessageDialog(null, "Οι αλλαγές αποθηκεύτηκαν.");
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						db.closeConnection();
					
						nameField.setEditable(false);
						cityField.setEditable(false);
						phoneNumberField.setEditable(false);
						emailField.setEditable(false);
						addressField.setEditable(false);
						faxField.setEditable(false);
						zipCodeField.setEditable(false);
						notesField.setEditable(false);

						saveButton.setEnabled(false);
					
						editCheckBox.setSelected(false);
					}
					else
						JOptionPane.showMessageDialog(null, "Γράφε καλά.");	
				}
				else
					JOptionPane.showMessageDialog(null, "Συμπληρώστε τα απαραίτητα στοιχεία");
			}
		});
		
		editCheckBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(editCheckBox.isSelected())
				{
					nameField.setEditable(true);
					cityField.setEditable(true);
					phoneNumberField.setEditable(true);
					emailField.setEditable(true);
					addressField.setEditable(true);
					faxField.setEditable(true);
					zipCodeField.setEditable(true);
					notesField.setEditable(true);
					notesField.setBackground(Color.WHITE);
					
					saveButton.setEnabled(true);
				}
				else
				{
					nameField.setEditable(false);
					cityField.setEditable(false);
					phoneNumberField.setEditable(false);
					emailField.setEditable(false);
					addressField.setEditable(false);
					faxField.setEditable(false);
					zipCodeField.setEditable(false);
					notesField.setEditable(false);
					notesField.setBackground(UIManager.getColor("TextField.disabledBackground"));

					saveButton.setEnabled(false);
				}
			}
		});
		
		this.setContentPane(panel);
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent we)
			{
				//initialize prompt default option
				int PromptResult = JOptionPane.YES_OPTION;
				
				//we care to ask only if there is an edit
				if(editCheckBox.isSelected())
				{
					String ObjButtons[] = {"Ναι", "Όχι"};			
					PromptResult = JOptionPane.showOptionDialog(null, "Έξοδος;", "Easy Orders 1.0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);
				}
				
				if(PromptResult == JOptionPane.YES_OPTION)
					dispose();
			}
		});

		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(450, 100);
		this.setSize(595, 636);
		this.setResizable(false);
		this.setTitle("Στοιχεία Πελάτη");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
}