import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class NewClientFrame extends JDialog
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
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public NewClientFrame()
	{
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		db.connect();
		
		nameField.setBounds(112, 5, 150, 20);
		nameField.setPreferredSize(new Dimension(150, 20));
		
		idField.setBounds(359, 5, 89, 20);
		idField.setPreferredSize(new Dimension(70, 20));
		
		cityField.setBounds(112, 50, 150, 20);
		cityField.setPreferredSize(new Dimension(150, 20));
		
		zipCodeField.setBounds(359, 81, 89, 20);
		zipCodeField.setPreferredSize(new Dimension(70, 20));
		
		phoneNumberField.setBounds(112, 152, 150, 20);
		phoneNumberField.setPreferredSize(new Dimension(150, 20));
		
		emailField.setBounds(112, 183, 150, 20);
		emailField.setPreferredSize(new Dimension(150, 20));
		
		notesField.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		notesField.setBounds(112, 231, 150, 60);
		notesField.setPreferredSize(new Dimension(150, 60));
		
		faxField.setBounds(359, 152, 89, 20);
		faxField.setPreferredSize(new Dimension(70, 20));
		
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
		
		saveButton.setBounds(340, 252, 108, 39);
		panel.add(saveButton);
		
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
				
				if(!id.trim().equals("") & !(name.trim().equals("")))
				{
					try
					{
						String query = "INSERT INTO client (id, name, city, phoneNumber, email, address, fax, zipCode, notes) VALUES ('" + id + "', " + "'" + name + "', " + "'" + city + "', " + "'" + phoneNumber + "', " + "'" + email + "', " + "'" + address + "', " + "'" + fax + "', " + "'" + zipCode + "', " + "'" + notes + "')";
						int rs = db.getStatement().executeUpdate(query);
						
						JOptionPane.showMessageDialog(null, "Ο νέος χρήστης καταχωρήθηκε.");
						
						idField.setText("");
						nameField.setText("");
						cityField.setText("");
						phoneNumberField.setText("");
						emailField.setText("");
						addressField.setText("");
						faxField.setText("");
						zipCodeField.setText("");
						notesField.setText("");
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Συμπληρώστε τα απαραίτητα στοιχεία");
			}
		});
		
		this.setContentPane(panel);
		
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
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setSize(502, 337);
		this.setResizable(false);
		this.setTitle("Νέος Πελάτης");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}
}