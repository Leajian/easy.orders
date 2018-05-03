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
	
	private JButton saveButton = new JButton("Αποθήκευση");
	private JButton ordersRecordbutton = new JButton("Ιστορικό Παρραγελιών");
	
	private JCheckBox editCheckBox = new JCheckBox("Επεξεργασία");
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public ClientInfoFrame(String id)
	{
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		db.connect();
		
		try
		{
			String query = "SELECT * FROM client WHERE id = " + "'" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			idField.setText(rs.getString("id"));
			nameField.setText(rs.getString("name"));
			cityField.setText(rs.getString("city"));
			phoneNumberField.setText(rs.getString("phoneNumber"));
			emailField.setText(rs.getString("email"));
			addressField.setText(rs.getString("address"));
			faxField.setText(rs.getString("fax"));
			zipCodeField.setText(rs.getString("zipCode"));
			notesField.setText(rs.getString("notes"));
			
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		
		nameField.setEditable(false);
		nameField.setBounds(112, 5, 150, 20);
		nameField.setPreferredSize(new Dimension(150, 20));
		
		idField.setEditable(false);
		idField.setBounds(359, 5, 89, 20);
		idField.setPreferredSize(new Dimension(70, 20));
		
		cityField.setEditable(false);
		cityField.setBounds(112, 50, 150, 20);
		cityField.setPreferredSize(new Dimension(150, 20));
		
		zipCodeField.setEditable(false);
		zipCodeField.setBounds(359, 81, 89, 20);
		zipCodeField.setPreferredSize(new Dimension(70, 20));
		
		phoneNumberField.setEditable(false);
		phoneNumberField.setBounds(112, 152, 150, 20);
		phoneNumberField.setPreferredSize(new Dimension(150, 20));
		
		emailField.setEditable(false);
		emailField.setBounds(112, 183, 150, 20);
		emailField.setPreferredSize(new Dimension(150, 20));
		
		notesField.setEditable(false);
		notesField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		notesField.setBounds(112, 231, 150, 60);
		notesField.setPreferredSize(new Dimension(150, 60));
		
		faxField.setEditable(false);
		faxField.setBounds(359, 152, 89, 20);
		faxField.setPreferredSize(new Dimension(70, 20));
		
		addressField.setEditable(false);
		addressField.setBounds(112, 81, 150, 20);
		addressField.setPreferredSize(new Dimension(150, 20));
		
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		
		nameLab.setBounds(14, 8, 219, 14);
		panel.add(nameLab);
		panel.add(nameField);
		
		idLab.setBounds(326, 8, 80, 14);
		panel.add(idLab);
		panel.add(idField);
		
		cityLab.setBounds(14, 53, 163, 14);
		panel.add(cityLab);
		panel.add(cityField);
		
		addressLab.setBounds(14, 84, 108, 14);
		panel.add(addressLab);
		panel.add(addressField);
		
		zipCodeLab.setBounds(326, 84, 80, 14);
		panel.add(zipCodeLab);
		panel.add(zipCodeField);
		
		phoneNumberLab.setBounds(14, 155, 108, 14);
		panel.add(phoneNumberLab);
		panel.add(phoneNumberField);
		
		faxLab.setBounds(326, 155, 70, 14);
		panel.add(faxLab);
		panel.add(faxField);
		
		emailLab.setBounds(14, 186, 108, 14);
		panel.add(emailLab);
		panel.add(emailField);
		
		notesLab.setBounds(14, 231, 120, 14);
		panel.add(notesLab);
		panel.add(notesField);
		saveButton.setEnabled(false);
		
		saveButton.setBounds(359, 332, 111, 31);
		panel.add(saveButton);
		
		ordersRecordbutton.setBounds(14, 332, 163, 33);
		panel.add(ordersRecordbutton);
		
		editCheckBox.setBounds(359, 298, 97, 23);
		panel.add(editCheckBox);
		
		ordersRecordbutton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}
		});
		
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
					try
					{
						String query = "UPDATE client SET name = " + "'" + name + "', " + "city = " + "'" + city + "', " + "phoneNumber = " + "'" + phoneNumber + "', " + "email = " + "'" + email + "', " +  "address = " + "'" + address + "', " + "fax = " + "'" + fax + "', " + "zipCode = " + "'" + zipCode + "', " + "notes = " + "'" + notes + "' WHERE customer.id = " + "'" + id + "'";
						int rs = db.getStatement().executeUpdate(query);
						
						JOptionPane.showMessageDialog(null, "Οι αλλαγές αποθηκεύτηκαν.");
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					
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
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setSize(494, 408);
		this.setResizable(false);
		this.setTitle("Στοιχεία Πελάτη");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}