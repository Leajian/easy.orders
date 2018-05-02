import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

public class NewCustomerFrame extends JFrame
{
	private JTextField nameField = new JTextField();
	private JTextField idField = new JTextField();
	private JTextField cityField = new JTextField();
	private JTextField addressField = new JTextField();
	private JTextField phone_numberField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField zip_codeField = new JTextField();
	private JTextField faxField = new JTextField();
	private JTextArea notesField = new JTextArea();
	
	private JLabel nameLab = new JLabel("Ονοματεπώνυμο");
	private JLabel idLab = new JLabel("ΑΦΜ");
	private JLabel cityLab = new JLabel("Έδρα/Πόλη");
	private JLabel addressLab = new JLabel("Διεύθυνση");
	private JLabel phone_numberLab = new JLabel("Τηλέφωνο");
	private JLabel emailLab = new JLabel("E-mail");
	private JLabel zip_codeLab = new JLabel("Τ.Κ.");
	private JLabel faxLab = new JLabel("ΦΑΞ");
	private JLabel notesLab = new JLabel("Σημειώσεις");
	
	private JButton saveButton = new JButton("Αποθήκευση");
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public NewCustomerFrame()
	{
		db.connect();
		
		nameField.setBounds(112, 5, 150, 20);
		nameField.setPreferredSize(new Dimension(150, 20));
		
		idField.setBounds(359, 5, 89, 20);
		idField.setPreferredSize(new Dimension(70, 20));
		
		cityField.setBounds(112, 50, 150, 20);
		cityField.setPreferredSize(new Dimension(150, 20));
		
		zip_codeField.setBounds(359, 81, 89, 20);
		zip_codeField.setPreferredSize(new Dimension(70, 20));
		
		phone_numberField.setBounds(112, 152, 150, 20);
		phone_numberField.setPreferredSize(new Dimension(150, 20));
		
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
		
		zip_codeLab.setBounds(326, 84, 80, 14);
		panel.add(zip_codeLab);
		panel.add(zip_codeField);
		
		phone_numberLab.setBounds(14, 155, 108, 14);
		panel.add(phone_numberLab);
		panel.add(phone_numberField);
		
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
				String phone_number = phone_numberField.getText();
				String email = emailField.getText();
				String address = addressField.getText();
				String fax = faxField.getText();
				String zip_code = zip_codeField.getText();
				String notes = notesField.getText();
				
				if(!id.trim().equals("") & !(name.trim().equals("")))
				{
					try
					{
						String query = "INSERT INTO customer (id, name, city, phone_number, email, address, fax, zip_code, notes) VALUES ('" + id + "', " + "'" + name + "', " + "'" + city + "', " + "'" + phone_number + "', " + "'" + email + "', " + "'" + address + "', " + "'" + fax + "', " + "'" + zip_code + "', " + "'" + notes + "')";
						int rs = db.getStatement().executeUpdate(query);
						
						JOptionPane.showMessageDialog(null, "Ο νέος χρήστης καταχωρήθηκε.");
						
						idField.setText("");
						nameField.setText("");
						cityField.setText("");
						phone_numberField.setText("");
						emailField.setText("");
						addressField.setText("");
						faxField.setText("");
						zip_codeField.setText("");
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
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(500, 200);
		this.setVisible(true);
		this.setSize(502, 337);
		this.setResizable(false);
		this.setTitle("Νέος Πελάτης");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
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
}