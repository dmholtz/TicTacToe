package ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import datatypes.Coordinate;

public class GraphicalUserInterface implements MouseListener {
	
	private TilePanel allTiles[][] = new TilePanel[3][3];
	private JFrame applicationWindow;
	
	private JPanel gamePanel;
	private JPanel controlPanel;
	
	private UserRequestEventListener userRequestEventListeners;
	
	public GraphicalUserInterface()
	{
		this.setupWindow();
	}
	
	private void setupWindow()
	{
		applicationWindow = new JFrame("Tic-Tac-Toe Game");
		applicationWindow.setSize(800, 500);
		applicationWindow.setResizable(false);

		this.setupGamePanel();
		this.setupControlPanel();
		this.mergeAndAddPanels();
		
		this.addTilesToGamePanel();
		
		applicationWindow.setVisible(true);        
	}
	
	private void setupGamePanel()
	{
		GridLayout gl = new GridLayout(3,3);
		gl.setHgap(7);
		gl.setVgap(7);
        this.gamePanel = new JPanel(gl);
        gamePanel.setBackground(Color.black);
	}
	
	private void setupControlPanel()
	{
		controlPanel = new JPanel();
		controlPanel.setBackground(Color.yellow);
        JLabel labelGelb = new JLabel("Ich bin auf der gelben Seite");
        controlPanel.add(labelGelb);
	}
	
	private void mergeAndAddPanels()
	{
		JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false);        
        splitpane.setResizeWeight(0.8);
        splitpane.setLeftComponent(this.gamePanel);
        splitpane.setRightComponent(this.controlPanel);     
        splitpane.setEnabled(false);

        applicationWindow.add(splitpane);
	}
	
	private void addTilesToGamePanel()
	{
		for (int i = 0; i < allTiles.length; i++)
        {
			for (int j = 0; j < allTiles[i].length; j++)
			{
				Coordinate c = new Coordinate(i,j);
				allTiles[i][j] = new TilePanel(c);
				allTiles[i][j].addMouseListener(this);
				this.gamePanel.add(allTiles[i][j]);
			}
        }
	}
	
	public void updateGame(TileUpdateTask task)
	{
		TilePanel updateableTilePanel = allTiles[task.getCoordinate().getX()][task.getCoordinate().getY()];
		switch(task.getRequestType())
		{
		case MARK_TILE:
			updateableTilePanel.paintMarker(task.getMarker());
			break;
		case PREVIEW_MARKER:
			updateableTilePanel.previewMarker(task.getMarker());
			break;
		case CLEAR_PREVIEW:
			updateableTilePanel.removePreview();
			break;
		default:
			break;
		}
	}
	
	public synchronized void setUserRequestEventListener(UserRequestEventListener listener)
	{
		this.userRequestEventListeners = listener;
	}
	
	private synchronized void fireUserRequestEvent(Object source, UserRequest requestType)
	{
		UserRequestEvent requestEvent = new UserRequestEvent(source, requestType);
		
		this.userRequestEventListeners.requestReceived(requestEvent);
	}
	
	private Coordinate retrieveSourceTileCoordinate(MouseEvent e)
	{
		TilePanel sourceTilePanel = (TilePanel) e.getSource();
		return sourceTilePanel.getCoordinate();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.MARK_TILE);	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// no action needed		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// no action needed
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.PREVIEW_MARKER);	
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.fireUserRequestEvent(this.retrieveSourceTileCoordinate(e), UserRequest.CLEAR_PREVIEW);				
	}
}
