package fr.zentsugo.notifications;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class TranslucentWindowDemo extends JFrame implements ActionListener {
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private final Timer timer = new Timer(1000, this);
    private final Date now = new Date();
    private final JLabel text = new JLabel();
    private static TranslucentWindowDemo tw = null;

	    public TranslucentWindowDemo() {
	    	
	        super("New window");
	        setLayout(new GridBagLayout());
	        
	        setUndecorated( true );
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBackground(new Color(0f, 0f, 0f, 1f / 3f));
	        setSize(300,200);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add a sample button.
	        add(new JButton("I am a Button"));
	        
	        timer.start();
	        
	        while (timer.getDelay() > 0) {
	        	
	        	//tw.dispose();
	        	System.out.println("not work lol");
	        	
	        }
	        
	        tw.dispose();
	        
	    }

	    public static void main(String[] args) {
	    	
	        // Determine if the GraphicsDevice supports translucency.
	        GraphicsEnvironment ge = 
	            GraphicsEnvironment.getLocalGraphicsEnvironment();
	        GraphicsDevice gd = ge.getDefaultScreenDevice();

	        //If translucent windows aren't supported, exit.
	        if (!gd.isWindowTranslucencySupported(TRANSLUCENT)) {
	            System.err.println(
	                "Translucency is not supported");
	                System.exit(0);
	        }
	        
	        JFrame.setDefaultLookAndFeelDecorated(true);

	        // Create the GUI on the event-dispatching thread
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                tw = new TranslucentWindowDemo();

	                // Set the window to 55% opaque (45% translucent).
	                tw.setOpacity(0.55f);

	                // Display the window.
	                tw.setVisible(true);
	            }
	        });
	    }

		@Override
		public void actionPerformed(ActionEvent e) {
			
	        now.setTime(System.currentTimeMillis());
	        text.setText(String.format("<html><body><font size='50'>%s</font></body></html>",sdf.format(now)));
			
		}
	}
