package fr.zentsugo.pcvc;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

import com.alee.laf.WebLookAndFeel;

import fr.zentsugo.login.LoginMain;
import fr.zentsugo.pcvcregister.Register;
import fr.zentsugo.userprofil.ServerGetImage;

import java.awt.SystemColor;

public class LoginWindow {

	private JFrame frmPsykocomVideosConferences;
	public static JTextField textField;
	private JPasswordField passwordField;
	public static JLabel lblServerConnection;
	public static boolean isAdmin;
	public static boolean cansetprofilimage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install(true);
					WebLookAndFeel.setDecorateAllWindows(true);
					LoginWindow window = new LoginWindow();
					window.frmPsykocomVideosConferences.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	static Connection connection = null;
	
	/**
	 * Create the application.
	 */
	public LoginWindow() {
		
		initialize();
		
		connection = SqliteConnection.dbConnector();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPsykocomVideosConferences = new JFrame();
		frmPsykocomVideosConferences.setTitle("PsykoCom Videos Conferences v.1.0.0 BETA");
		frmPsykocomVideosConferences.setResizable(false);
		frmPsykocomVideosConferences.getContentPane().setBackground(SystemColor.scrollbar);
		frmPsykocomVideosConferences.setBounds(100, 100, 777, 484);
		frmPsykocomVideosConferences.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPsykocomVideosConferences.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.DARK_GRAY);
		textField.setBounds(265, 106, 196, 28);
		frmPsykocomVideosConferences.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		passwordField.setForeground(Color.WHITE);
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setBounds(265, 203, 196, 28);
		frmPsykocomVideosConferences.getContentPane().add(passwordField);
		
		JLabel lblPsykocomVideosConferences = new JLabel("PsykoCom Videos Conferences");
		lblPsykocomVideosConferences.setFont(new Font("Yu Gothic Light", Font.BOLD, 18));
		lblPsykocomVideosConferences.setBounds(233, 27, 268, 35);
		frmPsykocomVideosConferences.getContentPane().add(lblPsykocomVideosConferences);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Yu Gothic Light", Font.PLAIN, 15));
		lblUsername.setBounds(135, 106, 120, 35);
		frmPsykocomVideosConferences.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Yu Gothic Light", Font.PLAIN, 15));
		lblPassword.setBounds(135, 202, 120, 28);
		frmPsykocomVideosConferences.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Connection");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					String query = "select * from UsersInfos where username=? and password=?";
					PreparedStatement ppstt = connection.prepareStatement(query);
					ppstt.setString(1, textField.getText());
					ppstt.setString(2, passwordField.getText());
					
					ResultSet rs = ppstt.executeQuery();
					
					int count = 0;
					
					while(rs.next()) {
						
						count = count + 1;
						
					}
					
					if (count == 1) {
						
						String pseudo1 = null;
						String rank1 = null;
						
						JOptionPane.showMessageDialog(null, "Username and password are correct, connection !", "Connection Sucessful !", JOptionPane.INFORMATION_MESSAGE);
						
						/*String searchnamepseudo = "select * from UsersInfos where pseudo=?";
						PreparedStatement namepseudo = connection.prepareStatement(searchnamepseudo);
						
						
						
						ResultSet rsname = namepseudo.executeQuery();;
						
						while (rsname.next())
					      {
							
							pseudo1 = rsname.getString("Pseudo");
							rank1 = rsname.getString("Rank");
							
					      }
						
						*/
						
						frmPsykocomVideosConferences.setVisible(false);
				        new LoginMain().setVisible(true);
				        
						frmPsykocomVideosConferences.setVisible(false);
				        new LoginMain().setVisible(true);
				        
				        isAdmin = true;
				        if (isAdmin = true) {
				        	
				        	LoginMain.ranklabel.setText("Rank : Admin");
				        	
				        	isAdmin = false;
				        
				        }
				        
				        	ServerGetImage.getProfilImage(LoginMain.profilimagelabel, textField);
				        
				        LoginMain.usernameField.setText(textField.getText());
				        
				        isAdmin = false;
				        LoginMain.ranklabel.setText("Rank : User");
						
					} else if (count > 1) {
						
						JOptionPane.showMessageDialog(null, "Duplicate Username and password !", "Connection error !", JOptionPane.ERROR_MESSAGE);
						
					} else {
						
						try {
							
							String query2 = "select * from AdminsInfos where username=? and password=?";
							PreparedStatement ppstt2 = connection.prepareStatement(query2);
							ppstt2.setString(1, textField.getText());
							ppstt2.setString(2, passwordField.getText());
							
							ResultSet rs2 = ppstt2.executeQuery();
							
							int count2 = 0;
							
							while(rs2.next()) {
								
								count2 = count2 + 1;
								
							}
							
							if (count2 == 1) {
								
								String pseudo2 = null;
								String rank2 = null;
								
								try {
								
								//String searchname2 = "select * from AdminsInfos where pseudo=? or (pseudo IS NULL AND ? IS NULL)";
								
								//System.out.println(pseudo2);
								
								//name2.setString(1, pseudo2);
								
								//ResultSet rspseudo2 = name2.executeQuery();;
								
								//while (rspseudo2.next())
							      //{
									
									//pseudo2 = rspseudo2.getString(3);
									//rank2 = rspseudo2.getString("pseudo");
									
							      //}
									
									JOptionPane.showMessageDialog(null, "Username and Password are correct, connection Admin !", "Connection sucessful !", JOptionPane.INFORMATION_MESSAGE);
									
									frmPsykocomVideosConferences.setVisible(false);
							        new LoginMain().setVisible(true);
									
							        LoginMain.usernameField.setText(textField.getText());
							        LoginMain.ranklabel.setText("Rank : " + rank2);
							        
							        isAdmin = true;
							        if (isAdmin = true) {
							        	
							        	LoginMain.ranklabel.setText("Rank : Admin");
							        	
							        	isAdmin = false;
							        
							        }
							        
							        	ServerGetImage.getProfilImage(LoginMain.profilimagelabel, textField);
							        
								} catch (Exception e2) {
									
									e2.printStackTrace();
									
								}
								
							} else if (count2 > 1) {
								
								JOptionPane.showMessageDialog(null, "Duplicate Username and Password !", "Connection error !", JOptionPane.ERROR_MESSAGE);
								
							} else {
								
								JOptionPane.showMessageDialog(null, "Wrong Username and or Password !", "Connection error !", JOptionPane.ERROR_MESSAGE);
								
							}
							
//							rs.close();
//							ppstt.close();
							
						} catch (Exception e2) {
							
							e2.printStackTrace();
							
						}
						
					}
					
//					rs.close();
//					ppstt.close();
					
				} catch (Exception e2) {
					
					e2.printStackTrace();
					
				}
				
			}
		});
		btnNewButton.setBounds(283, 271, 161, 35);
		frmPsykocomVideosConferences.getContentPane().add(btnNewButton);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frmPsykocomVideosConferences.setVisible(false);
				new Register().setVisible(true);
				
			}
		});
		btnRegister.setForeground(Color.WHITE);
		btnRegister.setBackground(Color.DARK_GRAY);
		btnRegister.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		btnRegister.setBounds(632, 381, 89, 23);
		frmPsykocomVideosConferences.getContentPane().add(btnRegister);
		
		JLabel lblYouHa = new JLabel("No account ? Register !");
		lblYouHa.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblYouHa.setBounds(491, 385, 131, 14);
		frmPsykocomVideosConferences.getContentPane().add(lblYouHa);
		
		lblServerConnection = new JLabel("Server Connection :");
		lblServerConnection.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblServerConnection.setBounds(10, 385, 168, 14);
		frmPsykocomVideosConferences.getContentPane().add(lblServerConnection);
		
	}
	
	public static void addnewuser(String Name, String Username, String Pseudo, String Password, String Rank) {
		
		String query = ("INSERT INTO UsersInfos (Name, Username, Pseudo, Password, Rank) " + "VALUES ('"  + Name +  "' , '"  + Username +  "' , '"  + Pseudo +  "' , '"  + Password +  "' , '"  + Rank +  "')");
		
		connection = SqliteConnection.dbConnector();
		
		try {
			
			PreparedStatement name2 = connection.prepareStatement(query);
			name2.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void addnewadmin(String Name, String Username, String Pseudo, String Password, String Rank) throws SQLException {
		
		String query = ("INSERT INTO AdminsInfos (Name, Username, Pseudo, Password, Rank) " + "VALUES ('"  + Name +  "' , '"  + Username +  "' , '"  + Pseudo +  "' , '"  + Password +  "' , '"  + Rank +  "')");
		
		connection = SqliteConnection.dbConnector();
			
			PreparedStatement name2 = connection.prepareStatement(query);
			name2.executeUpdate();
		
	}

	public void setVisible(boolean b) {
		
		frmPsykocomVideosConferences.setVisible(b);
		
	}
}
