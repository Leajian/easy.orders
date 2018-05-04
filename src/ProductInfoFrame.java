import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.border.EtchedBorder;

public class ProductInfoFrame extends JDialog
{
	private JTextField idField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextField qualityField = new JTextField();
	private JTextField locationField = new JTextField();
	private JTextField producerField = new JTextField();
	private JTextField packagingField = new JTextField();
	private JTextField priceField = new JTextField();
	
	private JLabel idLab = new JLabel("Κωδικός");
	private JLabel nameLab = new JLabel("Όνομα");
	private JLabel qualityLab = new JLabel("Ποιότητα");
	private JLabel locationLab = new JLabel("Προέλευση");
	private JLabel producerLab = new JLabel("Προμηθευτής");
	private JLabel priceLab = new JLabel("Τιμή (€)");
	private JLabel packagingLab = new JLabel("Συσκευασία");
	private JLabel stockLab = new JLabel("Απόθεμα");
	
	private JButton saveButton = new JButton("Αποθήκευση");
	
	private JCheckBox editCheckBox = new JCheckBox("Επεξεργασία");
	
	private JSpinner stockSpinner = new JSpinner();
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public ProductInfoFrame(String id, ProductsTableModel ptm)
	{
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		db.connect();
		
		try
		{
			String query = "SELECT * FROM product WHERE id = " + "'" + id + "'";
			ResultSet rs = db.getStatement().executeQuery(query);
			
			rs.next();
			
			idField.setText(rs.getString("id"));
			nameField.setText(rs.getString("name"));
			qualityField.setText(rs.getString("quality"));
			locationField.setText(rs.getString("location"));
			producerField.setText(rs.getString("producer"));
			packagingField.setText(rs.getString("packaging"));
			priceField.setText(rs.getString("price"));
			stockSpinner.setValue(rs.getObject("stock"));
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		panel.setLayout(null);
		
		idLab.setBounds(19, 25, 93, 14);
		panel.add(idLab);
		idField.setEditable(false);
		idField.setBounds(101, 22, 65, 20);
		panel.add(idField);
		
		nameLab.setBounds(19, 50, 93, 14);
		panel.add(nameLab);
		nameField.setEditable(false);
		nameField.setBounds(101, 47, 129, 20);
		panel.add(nameField);
		
		qualityLab.setBounds(286, 25, 93, 14);
		panel.add(qualityLab);
		qualityField.setEditable(false);
		qualityField.setBounds(371, 22, 65, 20);
		panel.add(qualityField);
		
		locationLab.setBounds(19, 75, 93, 14);
		panel.add(locationLab);
		locationField.setEditable(false);
		locationField.setBounds(101, 72, 129, 20);
		panel.add(locationField);
		
		producerLab.setBounds(19, 100, 93, 14);
		panel.add(producerLab);
		producerField.setEditable(false);
		producerField.setBounds(101, 97, 129, 20);
		panel.add(producerField);
		
		packagingLab.setBounds(286, 50, 93, 14);
		panel.add(packagingLab);
		packagingField.setEditable(false);
		packagingField.setBounds(371, 47, 65, 20);
		panel.add(packagingField);
		
		priceLab.setBounds(286, 100, 93, 14);
		panel.add(priceLab);
		priceField.setEditable(false);
		priceField.setBounds(371, 97, 65, 20);
		panel.add(priceField);
		
		stockLab.setBounds(286, 75, 93, 14);
		panel.add(stockLab);
		stockSpinner.setEnabled(false);
		stockSpinner.setBounds(371, 72, 65, 20);
		panel.add(stockSpinner);
		saveButton.setEnabled(false);
		
		editCheckBox.setBounds(331, 142, 97, 23);
		panel.add(editCheckBox);
		
		saveButton.setBounds(331, 172, 111, 31);
		panel.add(saveButton);
		
		saveButton.addActionListener(new ActionListener()
		{	
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String id = idField.getText();
				String name = nameField.getText();
				String quality = qualityField.getText();
				String location = locationField.getText();
				String producer = producerField.getText();
				String packaging = packagingField.getText();
				String price = priceField.getText();
				int stock = (Integer)stockSpinner.getValue();
				
				if(!(name.trim().equals("")))
				{
					if((id.length() <= 5) & (name.length() <= 30) & (quality.length() <= 2) & (location.length() <= 30) & (producer.length() <= 30) & (packaging.length() <= 2) & (price.length() <= 10) & (stock <= 99999))
					{
						try
						{
							String query = "UPDATE product SET name = " + "'" + name + "', " + "quality = " + "'" + quality + "', " + "location = " + "'" + location + "', " + "producer = " + "'" + producer + "', " +  "packaging = " + "'" + packaging + "', " + "price = " + "'" + price + "', " + "stock = " + "'" + stock + "' WHERE product.id = " + "'" + id + "'";
							int rs = db.getStatement().executeUpdate(query);
							
							//refresh the table after save
							ptm.refresh();
							dispose();
							JOptionPane.showMessageDialog(null, "Οι αλλαγές αποθηκεύτηκαν.");
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						
						nameField.setEditable(false);
						qualityField.setEditable(false);
						locationField.setEditable(false);
						producerField.setEditable(false);
						packagingField.setEditable(false);
						priceField.setEditable(false);
						stockSpinner.setEnabled(false);
						
						saveButton.setEnabled(false);
						
						editCheckBox.setSelected(false);
					}
					else
						JOptionPane.showMessageDialog(null, "Γράφε καλά.");		
				}
				else
					JOptionPane.showMessageDialog(null, "Συμπληρώστε τα απαραίτητα στοιχεία.");
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
					qualityField.setEditable(true);
					locationField.setEditable(true);
					producerField.setEditable(true);
					packagingField.setEditable(true);
					priceField.setEditable(true);
					stockSpinner.setEnabled(true);
					
					saveButton.setEnabled(true);
				}
				else
				{
					nameField.setEditable(false);
					qualityField.setEditable(false);
					locationField.setEditable(false);
					producerField.setEditable(false);
					packagingField.setEditable(false);
					priceField.setEditable(false);
					stockSpinner.setEnabled(false);
					
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
		this.setSize(458, 242);
		this.setResizable(false);
		this.setTitle("Στοιχεία Προιόντος");
		this.setVisible(true);
	}
}