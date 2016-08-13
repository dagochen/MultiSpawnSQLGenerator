
public class Position 
{
	private String x;
	private String y;
	private String z;
	private int map;
	
	public Position(int map2, String x2, String y2, String z2) 
	{
		this.map = map2;
		this.x = x2;
		this.y = y2;
		this.z = z2;
	}
	
	public String getString()
	{
		return "X: " + x + " Y: " + y + " Z: " + z + " Map: " + map;
		
	}

}
