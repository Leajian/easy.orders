import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class LoginFrame extends JFrame
{	
	private JLabel usernameLabel = new JLabel("Όνομα Χρήστη");
	private JLabel passwordLabel = new JLabel("Κωδικός");
	
	private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	
	private JButton loginButton = new JButton("Είσοδος");
	
	private JPanel panel = new JPanel();
	
	private DBConnect db = new DBConnect();
	
	public LoginFrame()
	{
		usernameField.setPreferredSize(new Dimension(110, 20));
		passwordField.setPreferredSize(new Dimension(110, 20));
		loginButton.setAlignmentY(0.0f);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		this.setContentPane(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.UNRELATED_GAP_COLSPEC,
				ColumnSpec.decode("193px:grow"),
				FormSpecs.UNRELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("27px"),}));
		panel.add(usernameLabel, "2, 2, fill, center");
		panel.add(usernameField, "2, 2, right, top");
		panel.add(passwordLabel, "2, 4, fill, fill");
		panel.add(passwordField, "2, 4, right, top");
		panel.add(loginButton, "2, 6, center, fill");
		
		db.connect();
		
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String username = usernameField.getText();
				String password = md5(new String(passwordField.getPassword()));
	
				try
				{
					//not safe
					String query = "SELECT * FROM employee WHERE username = " + "'" + username + "'" + " AND password = " + "'" + password + "'";
					ResultSet rs = db.getStatement().executeQuery(query);
					
					if(rs.next())
					{
						new SellerMainFrame(new Employee(username));
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Λάθος όνομα χρήστη ή κωδικός.", "Easy Orders 1.0", JOptionPane.ERROR_MESSAGE);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(600, 300);		
		this.setSize(257, 128);
		this.setResizable(false);
		this.setTitle("Είσοδος Χρήστη");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	static String md5(String password)
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