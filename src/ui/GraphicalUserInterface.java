package ui;

import java.awt.Color;
import java.awt.Font;
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
	
	private JLabel statusLabel;
	
	private UserRequestEventListener userRequestEventListeners;
	
	public GraphicalUserInterface()
	{
		this.setupWindow();
	}
	
	private void setupWindow()
	{
		applicationWindow = new JFrame("Tic-Tac-Toe Game");
		applicationWindow.setSize(600, 600);
		applicationWindow.setResizable(false);

		this.setupGamePanel();
		this.setupControlPanel();
		this.mergeAndAddPanels();
		
		this.addTilesToGamePanel();
		
		applicationWindow.setVisible(true);
		applicationWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		controlPanel.setBackground(Color.white);
        statusLabel = new JLabel("Tic Tac Toe");
        statusLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
        controlPanel.add(statusLabel);
	}
	
	private void mergeAndAddPanels()
	{
		JSplitPane splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false);        
        splitpane.setResizeWeight(0.05);
        splitpane.setRightComponent(this.gamePanel);
        splitpane.setLeftComponent(this.controlPanel);     
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
	
	public void setStatus(String text)
	{
		statusLabel.setText(text);
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
