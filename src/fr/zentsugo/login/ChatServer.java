//Alternative pour avoir getString SQL
//Le supprimer dans le Login
//Le rajouter dans le LoginMain
//A partir du usernameField
//Le set à partir d'un query et d'un Statement, prepareStatement(query)

package fr.zentsugo.login;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.alee.laf.WebLookAndFeel;


public class ChatServer extends javax.swing.JFrame {
	ArrayList clientOutputStreams;
        ArrayList<String> onlineUsers;
        private LoginMain Loginmain;

	public class ClientHandler implements Runnable	{
		BufferedReader reader;
		Socket sock;
        PrintWriter client;


		public ClientHandler(Socket clientSocket, PrintWriter user) {
		// new inputStreamReader and then add it to a BufferedReader
                        client = user;
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} // end try
			catch (Exception ex) {
				outputPane.append("Error beginning StreamReader. \n");
			} // end catch

		} // end ClientHandler()

		public void run() {
			
			WebLookAndFeel.install(true);
			WebLookAndFeel.setDecorateAllWindows(true);
			
                        String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat", image = "Image" ;
			String[] data;

			try {
				while ((message = reader.readLine()) != null) {

					outputPane.append("Received : " + message + "\n");
					
					data = message.split(":");
                                        for (String token:data) {

                                        outputPane.append(token + "\n");

                                        }

                     if (data[2].equals(connect)) {

                                                tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                                                userAdd(data[0]);

					} else if (data[2].equals(disconnect)) {

                                            tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                                            userRemove(data[0]);

					} else if (data[2].equals(chat)) {

                                            tellEveryone(message);

					} else if (data[2].equals(image)) {
						
						drawImage();
						
					} else {
                                            outputPane.append("No option in data argument 2 was put. \n");
                                        }


			     }
				
			}
			catch (Exception ex) {
				outputPane.append("Lost a connection. \n");
                                ex.printStackTrace();
                                clientOutputStreams.remove(client);
			}
		}
	}
    /** Creates new form ServerWindow */
    public ChatServer() {
    	WebLookAndFeel.install(true);
		WebLookAndFeel.setDecorateAllWindows(true);
    	getContentPane().setBackground(SystemColor.scrollbar);
        initComponents();
    }



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    
    private void initComponents() {
    	
    	WebLookAndFeel.install(true);
		WebLookAndFeel.setDecorateAllWindows(true);

        jScrollPane1 = new javax.swing.JScrollPane();
        outputPane = new javax.swing.JTextArea();
        outputPane.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
        outputPane.setForeground(Color.WHITE);
        outputPane.setBackground(Color.DARK_GRAY);
        startButton = new javax.swing.JButton();
        startButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(Color.DARK_GRAY);
        stopButton = new javax.swing.JButton();
        stopButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
        stopButton.setForeground(Color.WHITE);
        stopButton.setBackground(Color.DARK_GRAY);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PsykoCom Video Conference Server v.1.0.0 BETA");

        outputPane.setColumns(20);
        outputPane.setEditable(false);
        outputPane.setLineWrap(true);
        outputPane.setRows(5);
        outputPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(outputPane);

        startButton.setText("Start");
        startButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startButtonActionPerformed(evt);
            }
        });

        stopButton.setText("Stop");
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed(evt);
            }
        });
        
        JLabel lblOptions = new JLabel("Options");
        lblOptions.setFont(new Font("Yu Gothic Light", Font.PLAIN, 15));
        
        JLabel lblConsole = new JLabel("Console :");
        lblConsole.setFont(new Font("Yu Gothic Light", Font.PLAIN, 11));
        
        btnNewButton = new JButton("Kick all");
        btnNewButton.setFont(new Font("Yu Gothic Light", Font.PLAIN, 12));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(Color.DARK_GRAY);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
        					.addGroup(layout.createSequentialGroup()
        						.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(lblConsole))
        			.addGap(18)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblOptions)
        				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(129, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
        						.addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
        					.addGap(3)
        					.addComponent(lblConsole)
        					.addGap(1)
        					.addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
        				.addGroup(layout.createSequentialGroup()
        					.addComponent(lblOptions)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(btnNewButton)))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);
        
        pack();
        
    }                      

    private void startButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
    	
        Thread starter = new Thread(new ServerStart());
        starter.start();

        outputPane.append("Server started. \n");
    }                                           

    private void stopButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
    	
        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        outputPane.append("Server stopping... \n");
    	
    }                                          

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatServer().setVisible(true);
            }
        });
    }


    public class ServerStart implements Runnable {
        public void run() {
                    clientOutputStreams = new ArrayList();
                    onlineUsers = new ArrayList();  

                    try {
                    	ServerSocket serverSock = new ServerSocket(1805);

                    	while (true) {
                    		
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);


				
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				outputPane.append("New connection. \n");
			}
		}
		catch (Exception ex)
		{
			outputPane.append("Error making a connection. \n");
			outputPane.append(ex.toString() + "\n");
		}

	}
    }

    	public void userAdd (String data) {
            String message, add = ": :Connect", done = "Server: :Done", name = data;
            outputPane.append("Before " + name + " added. \n");
            onlineUsers.add(name);
            outputPane.append("After " + name + " added. \n");
            String[] tempList = new String[(onlineUsers.size())];
            onlineUsers.toArray(tempList);

                for (String token:tempList) {

                    message = (token + add);
                    tellEveryone(message);
                }
                tellEveryone(done);
	}

	public void userRemove (String data) {
                String message, add = ": :Connect", done = "Server: :Done", name = data;
                onlineUsers.remove(name);
                String[] tempList = new String[(onlineUsers.size())];
		onlineUsers.toArray(tempList);

                for (String token:tempList) {

                    message = (token + add);
                    tellEveryone(message);
                }
                tellEveryone(done);
	}

        public void tellEveryone(String message) {
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				outputPane.append("Sending: " + message + "\n");
                                writer.flush();
                                outputPane.setCaretPosition(outputPane.getDocument().getLength());

			}
			catch (Exception ex) {
				outputPane.append("Error telling everyone. \n");
			}
		}
	}
        
        public void drawImage() {
        		
        		LoginMain.Smiley(Loginmain.chatTextArea, "profilimage", (ImageIcon) LoginMain.profilimagelabel.getIcon());
				outputPane.append("Sending: " + "image" + "\n");
                                
	}


        
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outputPane;
    private javax.swing.JButton startButton;
    private javax.swing.JButton stopButton;
    private JButton btnNewButton;
}
