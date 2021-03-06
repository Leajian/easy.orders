import java.awt.Dialog;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class ProductInfoFrame extends JDialog
{
	private JTextField idField = new JTextField();
	private JTextField nameField = new JTextField();
	private JTextField qualityField = new JTextField();
	private JTextField locationField = new JTextField();
	private JTextField producerField = new JTextField();
	private JTextField packagingField = new JTextField();
	private JTextField priceField = new JTextField();
	
	private JLabel ProductDetailsLabel = new JLabel("IMAGE PLACEHOLDER");
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
	
	public ProductInfoFrame(Product product)
	{	
		//this blocks other windows unless this is closed.
		//also it must be here and not the bottom for some reason
		this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		//TODO: create object instead

		idField.setText(product.getId());
		nameField.setText(product.getName());
		qualityField.setText(product.getQuality());
		locationField.setText(product.getLocation());
		producerField.setText(product.getProducer());
		packagingField.setText(product.getPackaging());
		priceField.setText(product.getPrice());
		stockSpinner.setValue(Integer.parseInt(product.getStock()));
			
		panel.setBorder(null);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("183px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(60dlu;default):grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:23px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:max(19dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		//panel.add(ProductDetailsLabel, "2, 1, 7, 1, center, center");
		panel.add(idLab, "2, 2, left, fill");
		
		idField.setEditable(false);
		panel.add(idField, "4, 2, fill, fill");

		qualityField.setEditable(false);
		panel.add(qualityField, "8, 2, fill, fill");
		panel.add(nameLab, "2, 4, left, fill");
		panel.add(qualityLab, "6, 2, left, center");

		nameField.setEditable(false);
		panel.add(nameField, "4, 4, fill, fill");

		packagingField.setEditable(false);
		panel.add(packagingField, "8, 4, fill, fill");
		panel.add(locationLab, "2, 6, fill, fill");

		locationField.setEditable(false);
		panel.add(locationField, "4, 6, fill, fill");

		stockSpinner.setEnabled(false);
		panel.add(stockSpinner, "8, 6, fill, fill");
		panel.add(producerLab, "2, 8, left, center");
		panel.add(packagingLab, "6, 4, left, center");

		producerField.setEditable(false);
		panel.add(producerField, "4, 8, fill, fill");
		panel.add(priceLab, "6, 8, left, center");
		panel.add(stockLab, "6, 6, left, center");

		priceField.setEditable(false);
		panel.add(priceField, "8, 8, fill, fill");
		panel.add(editCheckBox, "6, 10, 3, 1, right, fill");
		
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
		saveButton.setEnabled(false);
		panel.add(saveButton, "6, 12, 3, 1, right, fill");
		
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
					if((id.length() <= 5) & (name.length() <= 30) & (quality.length() <= 2) & (location.length() <= 30) & (producer.length() <= 30) & (packaging.length() <= 2) & (price.length() <= 10) & ((stock <= 99999) & (stock >= 0)))
					{
						db.connect();
						try
						{
							String query = "UPDATE product SET name = " + "'" + name + "', " + "quality = " + "'" + quality + "', " + "location = " + "'" + location + "', " + "producer = " + "'" + producer + "', " +  "packaging = " + "'" + packaging + "', " + "price = " + "'" + price + "', " + "stock = " + "'" + stock + "' WHERE product.id = " + "'" + id + "'";
							int rs = db.getStatement().executeUpdate(query);
							dispose();
							JOptionPane.showMessageDialog(null, "Οι αλλαγές αποθηκεύτηκαν.");
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						db.connect();
						
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
				{
					dispose();
				}
			}
		});

		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(480, 120);
		this.setSize(496, 225);
		this.setResizable(false);
		this.setTitle("Στοιχεία Προιόντος");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}
}