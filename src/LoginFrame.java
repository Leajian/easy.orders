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
import javax.swing.SwingConstants;

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
		usernameField.setBounds(101, 5, 113, 20);
		usernameField.setPreferredSize(new Dimension(100, 20));
		passwordField.setBounds(101, 33, 113, 20);
		passwordField.setPreferredSize(new Dimension(100, 20));
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		usernameLabel.setBounds(10, 8, 125, 14);
		
		panel.add(usernameLabel);
		panel.add(usernameField);
		passwordLabel.setBounds(10, 39, 125, 14);
		panel.add(passwordLabel);
		panel.add(passwordField);
		loginButton.setAlignmentY(0.0f);
		loginButton.setBounds(84, 62, 81, 27);
		panel.add(loginButton);
		
		this.setContentPane(panel);
		
		db.connect();
		
		loginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String username = usernameField.getText();
				String password = md5(passwordField.getText());
	
				try
				{
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
			}
		});
		
		this.setIconImage(new ImageIcon(getClass().getResource("/favicon-32x32.png")).getImage());
		this.setLocation(650, 300);
		this.setVisible(true);
		this.setSize(257, 128);
		this.setResizable(false);
		this.setTitle("Είσοδος Χρήστη");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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