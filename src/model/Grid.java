package model;

public class Grid {
	
	public static final int gridSize = 3;
	
	private final Tile allTiles[][] = new Tile[gridSize][gridSize];
	
	public Grid()
	{
		for(int x = 0; x < allTiles.length; x++)
		{
			for (int y = 0; y < allTiles[x].length; y++)
			{
				allTiles[x][y] = new Tile();
			}
		}
	}
	
	public Tile getTileFrom(final int x, final int y)
	{
		if (x < 0 || x >= gridSize || y < 0 || y >= gridSize)
		{
			throw new IllegalArgumentException("Given tile is not within the boundries of this grid.");
		}
		return allTiles[x][y];
	}

}
