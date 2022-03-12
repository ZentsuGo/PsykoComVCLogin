package fr.zentsugo.login;


import java.net.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;

import com.alee.laf.WebLookAndFeel;
import com.sun.corba.se.pept.transport.Connection;

import fr.zentsugo.pcvc.LoginWindow;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.SystemColor;


@SuppressWarnings("serial")
public class LoginMain extends javax.swing.JFrame {

	private static String username;

	private String ServerIP = "127.0.0.1";

	public static String prefix = "[PsykoCom] ";

	private int Port = 1805;

	private static Socket sock;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ArrayList<String> userslist = new ArrayList();

	private BufferedReader reader;

	private static PrintWriter writer;

	private static Boolean Connected = false;

	public static String version = "v.1.0.0 BETA";
	
	public static JLabel profilimagelabel; 



	public LoginMain() {
		getContentPane().setBackground(SystemColor.scrollbar);
		setResizable(false);
		initialize();

		Smiley(chatTextArea, "=l", getImage());

		Smiley(chatTextArea, "=)", getImage()); 
	}

	public class IncomingReader implements Runnable{

		public void run() {

			String[] data;

			String stream;

			String done = "Done";

			String connect = "Connect";

			String disconnect = "Disconnect";

			String chat = "Chat";

			try {
				while ((stream = reader.readLine()) != null) {
					
					data = stream.split(":");
						
						if (data[2].equals(chat)) {

							chattextareaappend(prefix + data[0] + ": " + data[1] + "\n");

							chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());

						} else if (data[2].equals(connect)){

							chatTextArea.removeAll();

							userAdd(data[0]);

						} else if (data[2].equals(disconnect)) {

							userRemove(data[0]);

						} else if (data[2].equals(done)) {

							usersList.setText("");

							writeUsers();

							userslist.clear();

						}
						
					}
				
				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");

				if (isActive() == false || isShowing() == false) sock.close();

			}catch(Exception ex) {
			}
		}
	}

	public void ListenThread() {

		Thread IncomingReader = new Thread(new IncomingReader());
		IncomingReader.start();

	}

	public void userAdd(String data) {
		
		String ranktext = ranklabel.getText();
		
		if (ranktext.contains("Admin")) {
		
			userslist.add("-Admin- " + data);
		
		} else if (ranktext.contains("User")) {
		
			userslist.add("-User- " + data);
		
		}

	}

	public void userRemove(String data) {

		chattextareaappend(prefix + data + " has disconnected.\n");

	}

	public void writeUsers() {

		String[] tempList = new String[(userslist.size())];

		userslist.toArray(tempList);

		for (String token:tempList) {

			usersList.append(token + "\n");

		}

	}

	public void sendDisconnect() {

		if (Connected == true) {

			String disconnection = (username + ": :Disconnect");

			try{
				writer.println(disconnection);
				writer.flush();
			} catch (Exception e) {
				chattextareaappend(prefix + "Cannot send the Disconnection message !\n");
			}
		} else {

			chattextareaappend(prefix + "Are you sure you want\nto you disconnect ?\n");

			clearChat();

		}
	}

	public void Disconnect() {

		if (Connected == true) {

			Object[] options1 = {"Yes",
			"No"};
			int n = JOptionPane.showOptionDialog(this,
					"Are you sure you want to you disconnect ? ",
					"Confirmation Disconnection",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options1,
					options1[1]);

			if (n == JOptionPane.YES_OPTION) {

				try {

					chattextareaappend(prefix + "You've been disconnected.\n");
					sendDisconnect();
					sock.close();

				} catch(Exception ex) {

					chattextareaappend(prefix + "An error has interrupted the disconnection. \n");
					chattextareaappend(prefix + "Try again later....");

				}

				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");

				setVisible(false);
				new LoginWindow().setVisible(true);

			} else if (n == JOptionPane.NO_OPTION) {

				chattextareaappend(prefix + "Disconnection cancelled.\n");

				return;

			}

		} else {




			Object[] options2 = {"Yes",
			"No"};
			int n = JOptionPane.showOptionDialog(this,
					"Are you sure you want to you disconnect ? ",
					"Confirmation Disconnection",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options2,
					options2[1]);

			if (n == JOptionPane.YES_OPTION) {

				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");

				setVisible(false);
				new LoginWindow().setVisible(true);

			} else if (n == JOptionPane.NO_OPTION) {

				return;

			}

		}

		if (Connected == true) {

			lblConnected.setText("Connection : Yes");

		} else if (Connected == false) {

			lblConnected.setText("Connection : No");

		} else {

			lblConnected.setText("Connection : Null");

		}

	}




	public void initialize() {
		jLabel1 = new javax.swing.JLabel();
		jLabel1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));
		usernameField = new javax.swing.JTextField();
		usernameField.setBackground(Color.LIGHT_GRAY);
		usernameField.setForeground(Color.WHITE);
		usernameField.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		/*connectButton = new javax.swing.JButton();
		connectButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));*/
		disconnectButton = new javax.swing.JButton();
		disconnectButton.setForeground(Color.WHITE);
		disconnectButton.setBackground(Color.DARK_GRAY);
		disconnectButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		sendButton = new javax.swing.JButton();
		sendButton.setForeground(Color.WHITE);
		sendButton.setBackground(Color.DARK_GRAY);
		sendButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		jScrollPane3 = new javax.swing.JScrollPane();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenuBar1.setForeground(Color.WHITE);
		jMenuBar1.setBackground(Color.DARK_GRAY);
		jMenu1 = new javax.swing.JMenu();
		jMenu1.setBackground(Color.DARK_GRAY);
		jMenu1.setForeground(Color.WHITE);
		jMenu1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem1.setBackground(Color.DARK_GRAY);
		jMenuItem1.setForeground(Color.WHITE);
		jMenuItem1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));

		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				//playSound

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				close();

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

		});
		setTitle("PsykoCom Video Conference Client " + version);

		/*textPane.setColumns(20);
		textPane.setRows(5)*/;

		usernameField.setEditable(false);
		usernameField.setText(username);

		jLabel1.setText("Username :");

		usernameField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				usernameFieldActionPerformed(evt);
			}
		});

		/*connectButton.setText("Connection");
		connectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				connectButtonActionPerformed(evt);
			}
		});*/

		disconnectButton.setText("Disconnection");
		disconnectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				disconnectButtonActionPerformed(evt);
			}
		});

		sendButton.setText("Send Message");
		sendButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				sendButtonActionPerformed(evt);
			}
		});

		jMenu1.setText("Menu");

		jMenuItem1.setText("Options");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem1ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuBar1.add(jMenu1);

		setJMenuBar(jMenuBar1);

		lblPsykocomVideoConference = new JLabel("PsykoCom Video Conference Client v1.0.0 BETA");
		lblPsykocomVideoConference.setFont(new Font("Yu Gothic Light", Font.PLAIN, 14));

		lblConnected = new JLabel("Connected :");
		lblConnected.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));

		JButton btnNewButton = new JButton("Smiley");
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.DARK_GRAY);
		btnNewButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		ranklabel = new JLabel("Rank :");
		ranklabel.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));

		JLabel lblOnlineUsers = new JLabel("Online users");
		lblOnlineUsers.setFont(new Font("Yu Gothic Light", Font.PLAIN, 13));

		JScrollPane scrollPane = new JScrollPane();

		scrollPane_1 = new JScrollPane();
		
		profilimagelabel = new JLabel("");
		
		JButton btnNewButton_1 = new JButton("Profil Image");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
		btnNewButton_1.setBackground(Color.DARK_GRAY);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(lblPsykocomVideoConference)
									.addPreferredGap(ComponentPlacement.RELATED, 338, Short.MAX_VALUE)
									.addComponent(lblOnlineUsers)
									.addGap(95))
								.addGroup(layout.createSequentialGroup()
									.addGroup(layout.createParallelGroup(Alignment.LEADING)
										.addGroup(layout.createSequentialGroup()
											.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
												.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
									.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(profilimagelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
									.addGap(16)))
							.addContainerGap())
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
									.addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)
									.addGap(36)
									.addComponent(lblConnected))
								.addComponent(ranklabel))
							.addGap(207)
							.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(disconnectButton, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
							.addGap(16))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPsykocomVideoConference)
						.addComponent(lblOnlineUsers))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(layout.createParallelGroup(Alignment.LEADING)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
							.addComponent(sendButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
							.addComponent(profilimagelabel, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addGroup(layout.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnNewButton)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)))
					.addGap(106)
					.addGroup(layout.createParallelGroup(Alignment.TRAILING)
						.addGroup(layout.createSequentialGroup()
							.addComponent(ranklabel)
							.addGap(1)
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
								.addComponent(usernameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblConnected)))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(disconnectButton)
								.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
							.addGap(8)))
					.addContainerGap())
		);
		usersList = new javax.swing.JTextArea();
		usersList.setBackground(Color.DARK_GRAY);
		usersList.setForeground(Color.WHITE);
		jScrollPane3.setViewportView(usersList);
		usersList.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));

		usersList.setEditable(false);
		usersList.setColumns(20);
		usersList.setRows(5);

		textPane = new JTextPane();
		textPane.setBackground(Color.DARK_GRAY);
		textPane.setForeground(Color.WHITE);
		scrollPane_1.setViewportView(textPane);
		textPane.addKeyListener(new Keychecker());

		chatTextArea = new JTextPane();
		chatTextArea.setBackground(Color.DARK_GRAY);
		chatTextArea.setForeground(Color.WHITE);
		chatTextArea.setEditable(false);
		scrollPane.setViewportView(chatTextArea);
		getContentPane().setLayout(layout);

		//usernameField.setText(LoginWindow.textField.getText());
		usernameField.setText("zentsugo");

		pack();

		connection();

	}// </editor-fold>                        

	private void connection() {                                              
		// TODO add your handling code here:
		if (Connected == false) {

			if (!usernameField.getText().isEmpty()) {

				username = usernameField.getText();

				try {

					sock = new Socket(ServerIP, Port);

					InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());

					reader = new BufferedReader(streamreader);

					writer = new PrintWriter(sock.getOutputStream());

					writer.println(username + ":has connected.:Connect");
					
					writer.println(profilimagelabel.getIcon() + ": : Image");

					chattextareaappend(prefix + "Welcome " + username + " to PsykoCom Video Conference " + version + " !\n");

					writer.flush();

					usernameField.setEditable(false);

					Connected = true;

				} catch (Exception ex) {
					
					chattextareaappend(prefix + "A problem has interrupted the connection. \n");
					chattextareaappend(prefix + "The Server might be busy, contact an Admin for help.");
					
				}
				
				ListenThread();
				
			} else {
				
				JOptionPane.showMessageDialog(null, "You have to specify an Username !", "Connection Error", JOptionPane.ERROR_MESSAGE);
				
			}
		} else if (Connected == true) {
			
			chattextareaappend(prefix + "You are already connected. \n");
			
		}
		
		if (Connected == true) {
			
			lblConnected.setText("Connection : Yes");
			
		} else if (Connected == false) {
			
			lblConnected.setText("Connection : No");
			
		} else {
			
			lblConnected.setText("Connection : Null");
			
		}
		
	}                                             
	
	private void disconnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
		
		Disconnect();
		
		if (Connected == true) {
			
			lblConnected.setText("Connection : Yes");
			
		} else if (Connected == false) {
			
			lblConnected.setText("Connection : No");
			
		} else {
			
			lblConnected.setText("Connection : Null");
			
		}
		
	}                                                
	
	private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {

		String nothing = "";
		String command = textPane.getText();
		String[] message = command.split(" ");

		if (textPane.getText().contains("/")) {

			textPane.setText("");
			textPane.requestFocus();

		}

		if (textPane.getText().equals(nothing) || textPane.getText().contains("\n") || textPane.getText().contains("\r")) {

			textPane.setText("");
			textPane.requestFocus();

		} else {

			try {

				String text;

				text = textPane.getText().replaceAll("\n", "").replaceAll("\r", "").replaceAll(":", "=");

				writer.println(username + ":" + text + ":" + "Chat");
				writer.flush();

			} catch (Exception ex) {

				chattextareaappend("The message was not sent. \n");

			}

			textPane.setText("");
			textPane.requestFocus();

		}

		textPane.setText("");
		textPane.requestFocus();

		if (Connected == true) {

			lblConnected.setText("Connection : Yes");

		} else if (Connected == false) {

			lblConnected.setText("Connection : No");

		} else {

			lblConnected.setText("Connection : Null");

		}
		
		if (message[0].equalsIgnoreCase("/clearmychat")) {
			
			clearChat();
			
		}

		if(message[0].equalsIgnoreCase("/me")) {

			if(message.length == 1) {

				chattextareaappend(prefix + "Error, try /spam <message>. \n");

				textPane.setText("");
				textPane.requestFocus();

			}

			if (message.length >= 2) {

				try {

					String text = "";

					for (int i = 1; i < message.length; i++) {
						text += message[i] + " ";
					}

					String text2;

					text2 = text.replaceAll("\n", "").replaceAll("\r", "").replaceAll(":", "=");

					writer.println("ME - " + username.toUpperCase() + ":" + text2 + ":" + "Chat");
					writer.flush();

					textPane.setText("");
					textPane.requestFocus();

				} catch (Exception e) {

					chattextareaappend(prefix + "The message was not sent.");

					textPane.setText("");
					textPane.requestFocus();

				}

			}

		}

	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {



	}                                          

	private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {

		//texthelpusernamefield.show(true);

	}                                             

	public static void main(String args[]) {
		final LoginMain q = new LoginMain();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				q.setVisible(true);
			}
		});
		q.connection();
	}

	protected static ImageIcon getImage() {
		BufferedImage bi = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();

		g.setColor(Color.yellow);
		g.fillOval(0, 0, 14, 14);

		g.setColor(Color.black);
		g.drawOval(0, 0, 14, 14);
		g.drawLine(4, 9, 9, 9);
		g.drawOval(4, 4, 1, 1);
		g.drawOval(10, 4, 1, 1);
		return new ImageIcon(bi);
	}

	protected static ImageIcon getSmile() {

		BufferedImage bi = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();

		g.setColor(Color.yellow);
		g.fillOval(0, 0, 14, 14);

		g.setColor(Color.black);
		g.drawOval(0, 0, 14, 14);
		g.drawLine(4, 9, 9, 9);
		g.drawOval(4, 4, 1, 1);
		g.drawOval(10, 4, 10, 4);

		return new ImageIcon(bi);

	}

	public static void chattextareaappend(String message) {

		chatTextArea.setText(chatTextArea.getText() + message);

	}

	public void clearChat() {

		chatTextArea.setText(prefix + "The chat will be cleared.");

		chatTextArea.setText("");

		chatTextArea.setText(prefix + "The chat has been cleared.");

	}

	public static void Smiley(final JTextPane showto, final String form, final ImageIcon smiley) {

		showto.setEditorKit(new StyledEditorKit());
		SimpleAttributeSet attrs = new SimpleAttributeSet();
		StyleConstants.setIcon(attrs, smiley);
		showto.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						try {
							StyledDocument doc = (StyledDocument) showto.getDocument();
							String text = doc.getText(0, showto.getDocument().getLength());
							int index = text.indexOf(form);
							int start = 0;
							while (index > -1) {
								Element el = doc.getCharacterElement(index);
								if (StyleConstants.getIcon(el.getAttributes()) == null) {
									doc.remove(index, 2);
									SimpleAttributeSet attrs = new SimpleAttributeSet();
									StyleConstants.setIcon(attrs, smiley);
									doc.insertString(index, form, attrs);
								}
								start = index + 2;
								index = text.indexOf(form, start);
							}
						}
						catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				});
			}
		});

	}
	
	public void close() {
		
		if (Connected == true) {

			Object[] options1 = {"Yes",
			"No", "Close all"};
			int n = JOptionPane.showOptionDialog(this,
					"Are you sure you want to you disconnect ? ",
					"Confirmation Disconnection",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options1,
					options1[1]);

			if (n == JOptionPane.YES_OPTION) {

				try {

					chattextareaappend(prefix + "You've been disconnected.\n");
					sendDisconnect();
					sock.close();

				} catch(Exception ex) {

					chattextareaappend(prefix + "An error has interrupted the disconnection. \n");
					chattextareaappend(prefix + "Try again later....");

				}

				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");

				setVisible(false);
				new LoginWindow().setVisible(true);

			} else if (n == JOptionPane.NO_OPTION) {

				chattextareaappend(prefix + "Disconnection cancelled.\n");

				return;

			} else if (n == JOptionPane.CANCEL_OPTION) {
				
				try {

					chattextareaappend(prefix + "You've been disconnected.\n");
					sendDisconnect();
					sock.close();

				} catch(Exception ex) {

					chattextareaappend(prefix + "An error has interrupted the disconnection. \n");
					chattextareaappend(prefix + "Try again later....");

				}

				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");
				
				System.exit(1);
				
			}

		} else {




			Object[] options2 = {"Yes",
			"No", "Cancel"};
			int n = JOptionPane.showOptionDialog(this,
					"Are you sure you want to close ? ",
					"Confirmation Close",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options2,
					options2[1]);

			if (n == JOptionPane.YES_OPTION) {

				Connected = false;

				usernameField.setEditable(false);

				usersList.setText("");

				setVisible(false);
				new LoginWindow().setVisible(true);

			} else if (n == JOptionPane.NO_OPTION) {
				
				chattextareaappend(prefix + "Close cancelled.");
				
				return;

			} else if (n == JOptionPane.CANCEL_OPTION) {
				
				chattextareaappend(prefix + "Close cancelled.");
				
				return;
				
			}

		}

		if (Connected == true) {

			lblConnected.setText("Connection : Yes");

		} else if (Connected == false) {

			lblConnected.setText("Connection : No");

		} else {

			lblConnected.setText("Connection : Null");

		}
		
	}
	
    public void drawImage() {
		
		LoginMain.Smiley(LoginMain.chatTextArea, "profilimage", (ImageIcon) LoginMain.profilimagelabel.getIcon());
                        
}
	
	class Keychecker extends KeyAdapter {

		@SuppressWarnings("unused")
		@Override
		public void keyPressed(KeyEvent event) {

			String nothing = "";

			char ch = event.getKeyChar();

			if (event.getKeyCode() == KeyEvent.VK_ENTER) {

				if (!textPane.getText().equals(nothing) || !textPane.getText().equals("\n") || !textPane.getText().equals("\r") || !textPane.getText().equals("\n\r")) {

					String text;

					text = textPane.getText().replaceAll("\n", "").replaceAll("\r", "").replaceAll(":", "=");

					String text2;

					try {

						writer.println(username + ":" + text + ":" + "Chat");
						writer.flush();

					} catch (Exception e) {

						e.printStackTrace();

					}

					textPane.setText("");
					textPane.requestFocus();

					//}

				}

			}

		}
	}
	//private javax.swing.JButton connectButton;
	public static javax.swing.JTextPane chatTextArea;
	private javax.swing.JButton disconnectButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JButton sendButton;
	public static javax.swing.JTextField usernameField;
	public static javax.swing.JLabel ranklabel;
	private static javax.swing.JTextArea usersList;
	private JLabel lblPsykocomVideoConference;
	private static JLabel lblConnected;
	private static JTextPane textPane;
	private JScrollPane scrollPane_1;
}