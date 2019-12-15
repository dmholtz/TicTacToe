package model;

import java.util.Optional;

public class Tile {

	private Optional<Player> marker;
	
	public Tile()
	{
		this.marker = Optional.empty();
	}
	
	public boolean isEmpty()
	{
		if (marker.isEmpty())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Player getMarker()
	{
		return 	this.marker.orElseThrow(IllegalStateException::new);
	}
	
	public void setMarker(Player marker)
	{
		this.marker = Optional.of(marker);
	}
	
	public void removeMarker()
	{
		this.marker = Optional.empty();
	}
	
	@Override
	public String toString()
	{
		if (this.isEmpty())
		{
			return "-";
		}
		else {
			return this.marker.get().getSymbol().toString();
		}
		
	}
	
}
