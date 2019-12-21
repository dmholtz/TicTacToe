package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import datatypes.Coordinate;
import model.Symbol;
import ui.TilePanel;
import ui.markers.Marker;
import ui.markers.OMarker;
import ui.markers.XMarker;

public class UITester implements MouseListener{
	
	TilePanel boxes[] = new TilePanel[9];
	
	int n = 0;
	
	public UITester()
	{
		/*
		 * Erzeugung eines neuen Frames mit dem Titel "Mein JFrame Beispiel"
		 */
		JFrame meinFrame = new JFrame("Some stupid name here");
		meinFrame.setSize(800, 500);
		meinFrame.setResizable(false);
		/*
		 * Hinzufügen einer einfachen Komponente (hier: JLabel)
		 */
		//meinFrame.add(new JLabel("Beispiel JLabel"));
		//JLabel label = new JLabel("Beispiel");
		//label.setLocation(0, 0);
		//meinFrame.add(label);
		//label.setText("Was anderes");

		// Erzeugung zweier JPanel-Objekte
		GridLayout gl = new GridLayout(3,3);
		gl.setHgap(7);
		gl.setVgap(7);
        JPanel gamePanel = new JPanel(gl);
        JPanel controlPanel = new JPanel();
        gamePanel.setBackground(Color.black);
        //gamePanel.setSize(400,600);
        controlPanel.setBackground(Color.yellow);
        //controlPanel.setSize(200, 400);
        //Beschriftungen für die beiden Seiten werden erstellt
        JLabel labelRot = new JLabel("Ich bin auf der roten Seite");
        JLabel labelGelb = new JLabel("Ich bin auf der gelben Seite");
        //Labels werden unseren Panels hinzugefügt
        //gamePanel.add(labelRot);
        controlPanel.add(labelGelb);
        
        for (int i = 0; i < 9; i++)
        {
        	boxes[i] = new TilePanel(new Coordinate(i/3, i%3));
        	boxes[i].setMinimumSize(new Dimension(80,80));
        	boxes[i].setMaximumSize(new Dimension(80,80));
        	// boxes[i].setBackground(Color.WHITE);
        	gamePanel.add(boxes[i]);
        	boxes[i].addMouseListener(this);
        }
        
//        JPanel or = new JPanel();
//        or.setPreferredSize(new Dimension (80,80));
//        or.setBackground(Color.black);
//        JPanel om = new JPanel();
//        om.setPreferredSize(new Dimension (80,80));
//        om.setBackground(Color.GRAY);
//        JPanel ol = new JPanel();
//        ol.setPreferredSize(new Dimension (80,80));
//        ol.setBackground(Color.DARK_GRAY);
//        gamePanel.add(ol);
//        gamePanel.add(om);
//        gamePanel.add(or);
 
        // Erzeugung eines JSplitPane-Objektes mit horizontaler Trennung
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);
        // Hier setzen wir links unser rotes JPanel und rechts das gelbe
        
        splitpane.setResizeWeight(0.8);
        splitpane.setLeftComponent(gamePanel);
        splitpane.setRightComponent(controlPanel);
        
        splitpane.setEnabled(false);
        
        //c.setSize(400, 400);
        //boxes[0].addMouseListener(this);
        //gamePanel.add(c);
 
        // Hier fügen wir unserem Dialog unser JSplitPane hinzu
        meinFrame.add(splitpane);
        // Wir lassen unseren Dialog anzeigen
        meinFrame.setVisible(true);
	}
	
	public Marker getCurrentMarker()
	{	
		if (n%2 != 0)
		{
			return new XMarker(Color.BLUE);
		}
		else
		{
			return new OMarker(Color.RED);
		}
	}

	public static void main(String[] args) {
		UITester u = new UITester();
        
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse clicked");
		TilePanel t = (TilePanel)e.getComponent();
		Marker cross = new XMarker(Color.BLUE);
		t.paintMarker(getCurrentMarker());
		n++;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getSource());
		TilePanel t = (TilePanel)e.getComponent();
		if(t.isPreviewEnabled())
		{
			t.previewMarker(getCurrentMarker());
		}
			
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		TilePanel t = (TilePanel)e.getComponent();
		if(t.isPreviewEnabled())
		{
			t.removePreview();
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
