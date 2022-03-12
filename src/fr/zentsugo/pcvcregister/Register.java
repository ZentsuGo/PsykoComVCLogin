package fr.zentsugo.pcvcregister;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.alee.laf.WebLookAndFeel;

import fr.zentsugo.login.LoginMain;
import fr.zentsugo.mail.Mail;
import fr.zentsugo.pcvc.LoginWindow;
import fr.zentsugo.pcvc.SqliteConnection;

public class Register {

	private JFrame frame;
	private static JTextField lblname;
	private static JTextField lblusername;
	private static JTextField lblpseudo;
	private static JPasswordField passwordField;
	private static JTextField lblAge;
	private static JLabel lblRank;
	private static JTextField emailField;
	private static Choice choice_1;
	private static JEditorPane dtrpnNotObligateMake;
	private static Choice rankchoice;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WebLookAndFeel.install(true);
					WebLookAndFeel.setDecorateAllWindows(true);
					Register window = new Register();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.scrollbar);
		frame.setBackground(Color.WHITE);
		frame.setBounds(100, 100, 700, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblname = new JTextField();
		lblname.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblname.setForeground(Color.WHITE);
		lblname.setBackground(Color.DARK_GRAY);
		lblname.setBounds(136, 101, 155, 20);
		frame.getContentPane().add(lblname);
		lblname.setColumns(10);
		
		lblusername = new JTextField();
		lblusername.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblusername.setForeground(Color.WHITE);
		lblusername.setBackground(Color.DARK_GRAY);
		lblusername.setBounds(136, 132, 155, 20);
		frame.getContentPane().add(lblusername);
		lblusername.setColumns(10);
		
		lblpseudo = new JTextField();
		lblpseudo.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblpseudo.setForeground(Color.WHITE);
		lblpseudo.setBackground(Color.DARK_GRAY);
		lblpseudo.setBounds(136, 163, 155, 20);
		frame.getContentPane().add(lblpseudo);
		lblpseudo.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		passwordField.setForeground(Color.WHITE);
		passwordField.setBackground(Color.DARK_GRAY);
		passwordField.setBounds(136, 194, 155, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblName = new JLabel("Name *");
		lblName.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblName.setBounds(44, 104, 70, 14);
		frame.getContentPane().add(lblName);
		
		JLabel lblUsername = new JLabel("Username *");
		lblUsername.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblUsername.setBounds(44, 135, 70, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPseudo = new JLabel("Pseudo *");
		lblPseudo.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblPseudo.setBounds(44, 166, 70, 14);
		frame.getContentPane().add(lblPseudo);
		
		JLabel lblPassword = new JLabel("Password *");
		lblPassword.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblPassword.setBounds(44, 197, 70, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblNewLabel = new JLabel("Age *");
		lblNewLabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblNewLabel.setBounds(44, 320, 70, 14);
		frame.getContentPane().add(lblNewLabel);
		
		lblAge = new JTextField();
		lblAge.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblAge.setBackground(Color.DARK_GRAY);
		lblAge.setToolTipText("");
		lblAge.setBounds(136, 317, 155, 20);
		frame.getContentPane().add(lblAge);
		lblAge.setColumns(10);
		
		rankchoice = new Choice();
		rankchoice.setForeground(Color.WHITE);
		rankchoice.setBackground(Color.DARK_GRAY);
		rankchoice.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		rankchoice.setBounds(136, 347, 155, 20);
		rankchoice.add("GFX");
		rankchoice.add("Programmer");
		rankchoice.add("MapMaker");
		rankchoice.add("SoundDesigner");
		rankchoice.add("StoryMaker");
		frame.getContentPane().add(rankchoice);
		
		lblRank = new JLabel("Rank *");
		lblRank.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblRank.setBounds(44, 347, 46, 14);
		frame.getContentPane().add(lblRank);
		
		JLabel lblEmail = new JLabel("Email *");
		lblEmail.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblEmail.setBounds(44, 232, 46, 14);
		frame.getContentPane().add(lblEmail);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		emailField.setForeground(Color.WHITE);
		emailField.setBackground(Color.DARK_GRAY);
		emailField.setBounds(136, 229, 155, 20);
		frame.getContentPane().add(emailField);
		emailField.setColumns(10);
		
		JButton btnNewButton = new JButton("Register");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!emailField.getText().isEmpty() && !lblname.getText().isEmpty() && !lblusername.getText().isEmpty() && !passwordField.getText().isEmpty() && !choice_1.getSelectedItem().isEmpty() && !rankchoice.getSelectedItem().isEmpty()) {
					
					if(!textField.getText().equals("")) {
						
						//faire ces memes identifications avec plus d'informations sur la base SQL pour user en bas :)
						
						String AdminKey1 = textField.getText();
						String AdminKey = AdminKey1;
							
							Connection connection = SqliteConnection.dbConnector();
							
							String query2 = "select * from AdminsKeys where Key = ?";
							PreparedStatement ppstt2 = null;
							try {
								
								ppstt2 = connection.prepareStatement(query2);
								
							} catch (SQLException e4) {
								// TODO Auto-generated catch block
								e4.printStackTrace();
							};
							try {
								
								ppstt2.setLong(1, Long.parseLong(AdminKey));
								
							} catch (SQLException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							
							ResultSet rs2 = null;
							
							try {
								
								
								//ppstt2.setString(1, textField.getText());
								
								rs2 = ppstt2.executeQuery();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							int count2 = 0;
							
							try {
								while(rs2.next()) {
									
									count2 = count2 + 1;
									
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
							if (count2 == 1) {
								
								try {
									LoginWindow.addnewadmin(lblname.getText(), lblusername.getText(), lblpseudo.getText(), passwordField.getText(), rankchoice.getSelectedItem());
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								JOptionPane.showMessageDialog(null, "Welcome Admin to\nPsykoCom Videos Conferences " + LoginMain.version + " !", "Welcome Admin !", JOptionPane.INFORMATION_MESSAGE);
								
								frame.show(false);
								new LoginWindow().setVisible(true);
								
							} else if (count2 > 1) {
								
								JOptionPane.showMessageDialog(null, "Account already exists !", "Error register", JOptionPane.ERROR_MESSAGE);
								
								passwordField.setText("");
								
								textField.setText("");
								
							} else {
								
								JOptionPane.showMessageDialog(null, "Are you trying to hack ?", "Error register", JOptionPane.ERROR_MESSAGE);
								
								passwordField.setText("");
								
								textField.setText("");
								
							}
							
						} else {
					
							LoginWindow.addnewuser(lblname.getText(), lblusername.getText(), lblpseudo.getText(), passwordField.getText(), rankchoice.getSelectedItem());
							
							JOptionPane.showMessageDialog(null, "You have successfully register your account !", "Successfully register !", JOptionPane.INFORMATION_MESSAGE);
							
							frame.show(false);
							new LoginWindow().setVisible(true);
							
						}
				} else {
					
					JOptionPane.showMessageDialog(null, "You have to fill out\nthe required fields !", "Error Required Fields", JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));
		btnNewButton.setBounds(136, 415, 155, 34);
		frame.getContentPane().add(btnNewButton);
		
		dtrpnNotObligateMake = new JEditorPane();
		dtrpnNotObligateMake.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		dtrpnNotObligateMake.setForeground(Color.WHITE);
		dtrpnNotObligateMake.setBackground(Color.DARK_GRAY);
		dtrpnNotObligateMake.setBounds(352, 101, 192, 145);
		dtrpnNotObligateMake.setText("Not required\nMake a little\ndescription about you.");
		frame.getContentPane().add(dtrpnNotObligateMake);
		
		JEditorPane editorPane_1 = new JEditorPane();
		editorPane_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		editorPane_1.setForeground(Color.WHITE);
		editorPane_1.setBackground(Color.DARK_GRAY);
		editorPane_1.setBounds(352, 397, 192, 52);
		frame.getContentPane().add(editorPane_1);
		
		choice_1 = new Choice();
		choice_1.setForeground(Color.WHITE);
		choice_1.setBackground(Color.DARK_GRAY);
		choice_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		choice_1.setBounds(352, 347, 155, 25);
		choice_1.add("By a friend");
		choice_1.add("By a search");
		choice_1.add("By a software (specify down)");
		choice_1.add("By a website (specify down)");
		choice_1.add("Other (specify down)");
		frame.getContentPane().add(choice_1);
		
		JLabel lblNewLabel_1 = new JLabel("Description about you");
		lblNewLabel_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(352, 76, 138, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblFromWhereDo = new JLabel("From where do you know PsykoCom ?");
		lblFromWhereDo.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblFromWhereDo.setBounds(352, 283, 215, 34);
		frame.getContentPane().add(lblFromWhereDo);
		
		JLabel lblSpecifyDownIf = new JLabel("Specify down if proposals do not concern you");
		lblSpecifyDownIf.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblSpecifyDownIf.setBounds(352, 320, 228, 14);
		frame.getContentPane().add(lblSpecifyDownIf);
		
		JLabel lblPsykocomRegisterV = new JLabel("PsykoCom Register v.1.0.0 BETA");
		lblPsykocomRegisterV.setFont(new Font("Yu Gothic Light", Font.BOLD, 15));
		lblPsykocomRegisterV.setBounds(44, 25, 247, 14);
		frame.getContentPane().add(lblPsykocomRegisterV);
		
		JLabel lblWeWillControl = new JLabel("We will control your inscription if it looks correct ");
		lblWeWillControl.setFont(new Font("Yu Gothic Light", Font.BOLD, 13));
		lblWeWillControl.setBounds(44, 50, 334, 14);
		frame.getContentPane().add(lblWeWillControl);
		
		JLabel lblRequired = new JLabel("* = Required Fields");
		lblRequired.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		lblRequired.setBounds(44, 76, 138, 14);
		frame.getContentPane().add(lblRequired);
		
		JButton btnLogin = new JButton("Cancel");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				new LoginWindow().setVisible(true);
				
			}
		});
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		btnLogin.setBounds(491, 494, 89, 23);
		frame.getContentPane().add(btnLogin);
		
		JLabel lblYouAreAn = new JLabel("You are an admin ? Put an register admin key :");
		lblYouAreAn.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		lblYouAreAn.setBounds(10, 472, 281, 14);
		frame.getContentPane().add(lblYouAreAn);
		
		textField = new JTextField();
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.DARK_GRAY);
		textField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		textField.setBounds(10, 495, 228, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
	}

	public void setVisible(boolean b) {
		
		frame.setVisible(b);
		
	}
	
	public static String getUserMail() {
		
		return emailField.getText();
		
	}

	public static String getUserName() {
		
		return lblname.getText();
		
	}
	
	public static String getUserPseudo() {
		
		return lblpseudo.getText();
		
	}

	public static String getUserPassword() {
		
		return passwordField.getText();
		
	}
	
	public static String getUserAge() {
		
		return choice_1.getSelectedItem();
		
	}

	public static String getUserRank() {
		
		return rankchoice.getSelectedItem();
		
	}
	
	public static String getUserDescription() {
		
		return dtrpnNotObligateMake.getText();
		
	}

	public static String getUserUsername() {
		
		return lblusername.getText();
		
	}
}
