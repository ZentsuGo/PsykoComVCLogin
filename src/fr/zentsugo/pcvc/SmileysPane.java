package fr.zentsugo.pcvc;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class SmileysPane extends JFrame {



	    //autoreplacing :) with picture
	    JTextPane p = new JTextPane();
	    
	    public SmileysPane() throws Exception {
	        p.setEditorKit(new StyledEditorKit());
	        getContentPane().add(p);
	        SimpleAttributeSet attrs = new SimpleAttributeSet();
	        StyleConstants.setIcon(attrs, getImage());
	        p.addCaretListener(new CaretListener() {
	            public void caretUpdate(CaretEvent e) {
	                SwingUtilities.invokeLater(new Runnable() {
	                    public void run() {
	                        try {
	                            StyledDocument doc = (StyledDocument) p.getDocument();
	                            String text = doc.getText(0, p.getDocument().getLength());
	                            int index = text.indexOf(":l");
	                            int start = 0;
	                            while (index > -1) {
	                                Element el = doc.getCharacterElement(index);
	                                if (StyleConstants.getIcon(el.getAttributes()) == null) {
	                                    doc.remove(index, 2);
	                                    SimpleAttributeSet attrs = new SimpleAttributeSet();
	                                    StyleConstants.setIcon(attrs, getImage());
	                                    doc.insertString(index, ":l", attrs);
	                                }
	                                start = index + 2;
	                                index = text.indexOf(":l", start);
	                            }
	                        }
	                        catch (Exception ex) {
	                            ex.printStackTrace();
	                        }
	                    }
	                });
	            }
	        });
	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        this.setSize(522, 438);
	    }

	    @SuppressWarnings("deprecation")
		public void main(String[] args) throws Exception {
	    	
	        try {
	        	SmileysPane test11 = new SmileysPane();
				test11.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	    	initialize();
	        
	    }
	    
	    @SuppressWarnings("deprecation")
		public void initialize() {
			
	    	show();
	    	
	    }

	    protected ImageIcon getImage() {
	        BufferedImage bi = new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB);
	        Graphics g = bi.getGraphics();
	        
	        Font f = new Font("Helvetica", Font.BOLD,20);
	        g.setFont(f);
	        g.drawString("Keep Smiling!!!", 50, 30);
	        g.drawOval(60, 60, 200, 200);
	        g.fillOval(90, 120, 50, 20);
	        g.fillOval(190, 120, 50, 20);
	        g.drawLine(165, 125, 165, 175);
	        g.drawArc(110, 130, 95, 95, 0, -180);
	        return new ImageIcon(bi);
	    }
	}
