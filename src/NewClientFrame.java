import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

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

	private JScrollPane notesFieldScrollPane = new JScrollPane();
	
	public NewClientFrame()
	{
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		nameField.setPreferredSize(new Dimension(150, 20));
		idField.setPreferredSize(new Dimension(70, 20));
		cityField.setPreferredSize(new Dimension(150, 20));
		zipCodeField.setPreferredSize(new Dimension(70, 20));
		phoneNumberField.setPreferredSize(new Dimension(150, 20));
		emailField.setPreferredSize(new Dimension(150, 20));
		faxField.setPreferredSize(new Dimension(70, 20));
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
				RowSpec.decode("33px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,}));
		
		panel.add(nameLab, "2, 2, fill, center");
		panel.add(nameField, "4, 2, fill, fill");
		panel.add(idLab, "6, 2, fill, center");
		panel.add(idField, "8, 2, fill, fill");
		panel.add(cityLab, "2, 4, fill, center");
		panel.add(cityField, "4, 4, fill, fill");
		panel.add(addressLab, "2, 6, left, center");
		panel.add(addressField, "4, 6, fill, fill");
		panel.add(zipCodeLab, "6, 6, fill, center");
		panel.add(zipCodeField, "8, 6, fill, fill");
		panel.add(phoneNumberLab, "2, 8, left, center");
		panel.add(phoneNumberField, "4, 8, fill, fill");
		panel.add(faxLab, "6, 8, fill, center");
		panel.add(faxField, "8, 8, fill, fill");
		panel.add(emailLab, "2, 10, left, center");
		panel.add(emailField, "4, 10, fill, fill");
		panel.add(notesLab, "2, 12, fill, top");
		panel.add(saveButton, "8, 14, right, fill");
		
		notesFieldScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		panel.add(notesFieldScrollPane, "4, 12, fill, fill");
		notesField.setLineWrap(true);
		notesField.setWrapStyleWord(true);
		notesFieldScrollPane.setViewportView(notesField);
		notesField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
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
					if((id.length() == 9) & (name.length() <= 30) & (city.length() <= 30) & (phoneNumber.length() <= 15) & (email.length() <= 30) & (address.length() <= 30) & (fax.length() <= 15) & (zipCode.length() <= 10) & (notes.length() <= 120))
					{
						db.connect();
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
						db.closeConnection();
					}		
					else
						JOptionPane.showMessageDialog(null, "Γράφε καλά.");
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
		this.setLocation(450, 100);
		this.setSize(595, 315);
		this.setResizable(false);
		this.setTitle("Νέος Πελάτης");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
}