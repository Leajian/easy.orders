import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Window.Type;

public class LoginFrame extends JFrame{

	private JPasswordField passwordField;
	DBConnect db = new DBConnect();

	public LoginFrame() {
		setType(Type.UTILITY);
		setResizable(false);
		
		this.setTitle("EasyOrders");
		this.setBounds(100, 100, 385, 400);;
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("144px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(100dlu;pref)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("28px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("150px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),
				RowSpec.decode("fill:30px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("23px"),
				RowSpec.decode("24px"),
				RowSpec.decode("29px:grow"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,}));
		
		JLabel loginImage = new JLabel();
		loginImage.setHorizontalAlignment(SwingConstants.CENTER);
		loginImage.setFont(new Font("Tahoma", Font.PLAIN, 93));
		loginImage.setIcon(new ImageIcon(LoginFrame.class.getResource("/images/user-symbol.png")));
		this.getContentPane().add(loginImage, "2, 2, 5, 1, fill, center");
		
		JTextField usernameField = new JTextField();
		usernameField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
				this.getContentPane().add(usernameField, "4, 4, fill, center");
				usernameField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.getContentPane().add(passwordField, "4, 5, fill, center");
		
		JLabel usernameLabel = new JLabel("Όνομα Χρήστη");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.getContentPane().add(usernameLabel, "2, 4, right, center");
		
		JLabel passwordLabel = new JLabel("Κωδικός");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		this.getContentPane().add(passwordLabel, "2, 5, right, center");
		
		JCheckBox passwordVisibilityCheckbox = new JCheckBox("Εμφάνιση κωδικού");
		passwordVisibilityCheckbox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (passwordVisibilityCheckbox.isSelected())
					passwordField.setEchoChar((char) 0);
				else
					passwordField.setEchoChar('●');
					
			}
		});
		this.getContentPane().add(passwordVisibilityCheckbox, "4, 7, left, top");
		
		JButton loginButton = new JButton("Είσοδος");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = usernameField.getText();
				String password = md5(passwordField.getPassword().toString());
				
				db.connect();
				try
				{
					//maybe "SELECT DISTINCT" for extra security?
					String query = "SELECT * FROM employee WHERE username = " + "'" + username + "'" + " AND password = " + "'" + password + "'";
					ResultSet rs = db.getStatement().executeQuery(query);
					
					rs.next();
				    
					int privilege = rs.getInt("privilege");
					
					if(privilege == 1)
						JOptionPane.showMessageDialog(null, "You are Administrator.");
					else
						JOptionPane.showMessageDialog(null, "You are User.");
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Λάθος όνομα χρήστη ή κωδικός.");
				}
				db.closeConnection();
			}
		});
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 27));
		this.getContentPane().add(loginButton, "2, 9, fill, fill");
		
		JButton newUserButton = new JButton("Νέος Χρήστης");
		newUserButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		getContentPane().add(newUserButton, "4, 9, 3, 1, fill, fill");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.requestFocus();
	}
	
	public static String md5(String password)
	{
		try
		{
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(password.getBytes(), 0, password.length());
			
			return new BigInteger(1, md5.digest()).toString(16);
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return password;
	}
}
