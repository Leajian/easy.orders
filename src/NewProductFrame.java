import java.awt.Dialog;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

public class NewProductFrame extends JDialog
{
	private JTextField idField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextField qualityField = new JTextField();
	private JTextField locationField = new JTextField();
	private JTextField supplierField = new JTextField();
	private JTextField packagingField = new JTextField();
	private JTextField priceField = new JTextField();
	
	private JLabel idLab = new JLabel("Κωδικός");
	private JLabel nameLab = new JLabel("Όνομα");
	private JLabel qualityLab = new JLabel("Ποιότητα");
	private JLabel locationLab = new JLabel("Προέλευση");
	private JLabel supplierLab = new JLabel("Προμηθευτής");
	private JLabel priceLab = new JLabel("Τιμή (€)");
	private JLabel packagingLab = new JLabel("Συσκευασία");
	private JLabel stockLab = new JLabel("Απόθεμα");
	
	private JButton saveButton = new JButton("Αποθήκευση");
	
	private JSpinner stockSpinner = new JSpinner();
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public NewProductFrame()
	{
		//this blocks other windows unless this is closed.
		//this is important because we don't want to add a new product with the same id
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		db.connect();
		
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panel.setLayout(null);
		
		idLab.setBounds(19, 25, 93, 14);
		panel.add(idLab);
		idField.setEditable(false);
		idField.setBounds(101, 22, 65, 20);
		panel.add(idField);
		
		nameLab.setBounds(19, 50, 93, 14);
		panel.add(nameLab);
		nameField.setBounds(101, 47, 129, 20);
		panel.add(nameField);
		
		qualityLab.setBounds(286, 25, 93, 14);
		panel.add(qualityLab);
		qualityField.setBounds(371, 22, 65, 20);
		panel.add(qualityField);
		
		locationLab.setBounds(19, 75, 93, 14);
		panel.add(locationLab);
		locationField.setBounds(101, 72, 129, 20);
		panel.add(locationField);
		
		supplierLab.setBounds(19, 100, 93, 14);
		panel.add(supplierLab);
		supplierField.setBounds(101, 97, 129, 20);
		panel.add(supplierField);
		
		packagingLab.setBounds(286, 50, 93, 14);
		panel.add(packagingLab);
		packagingField.setBounds(371, 47, 65, 20);
		panel.add(packagingField);
		
		priceLab.setBounds(286, 100, 93, 14);
		panel.add(priceLab);
		priceField.setBounds(371, 97, 65, 20);
		panel.add(priceField);
		
		stockLab.setBounds(286, 75, 93, 14);
		panel.add(stockLab);
		stockSpinner.setBounds(371, 72, 65, 20);
		panel.add(stockSpinner);
		
		saveButton.setBounds(325, 149, 111, 31);
		panel.add(saveButton);
		
		try
		{
			String query = "SELECT MAX(id) FROM product";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			int id = rs.getInt("MAX(id)");
			id++;
			
			idField.setText(Integer.toString(id));
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
		
		saveButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String id = idField.getText();
				String name = nameField.getText();
				String quality = qualityField.getText();
				String location = locationField.getText();
				String supplier = supplierField.getText();
				String packaging = packagingField.getText();
				String price = priceField.getText();
				int stock = (Integer)stockSpinner.getValue();
				
				if(!(name.trim().equals("")))
				{
					try
					{
						String query = "INSERT INTO product (id, name, quality, location, supplier, packaging, price, stock) VALUES ('" + id + "', " + "'" + name + "', " + "'" + quality + "', " + "'" + location + "', " + "'" + supplier + "', " + "'" + packaging + "', " + "'" + price + "', " + "'" + stock + "')";
						int rs = db.getStatement().executeUpdate(query);
						
						JOptionPane.showMessageDialog(null, "Το νέο προιόν καταχωρήθηκε.");
						
						idField.setText(Integer.toString(Integer.parseInt(id) + 1));
						nameField.setText("");
						qualityField.setText("");
						locationField.setText("");
						supplierField.setText("");
						packagingField.setText("");
						priceField.setText("");
						stockSpinner.setValue(0);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Συμπληρώστε τα απαραίτητα στοιχεία.");
			}
		});

		this.setContentPane(panel);

		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(500, 200);
		this.setSize(458, 219);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Νέο Προιόν");
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
}