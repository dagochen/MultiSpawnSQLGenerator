
public class GameObject {

	private String name;
	private int guid;
	private int id;
	private Position position;
	

	
	public GameObject(String name, int guid, int id, int map, String x, String y, String z) 
	{
		this.name = name;
		this.guid = guid;
		this.id = id;
		this.position = new Position(map,x,y,z);
		
	}


	public Position getPosition() {
		// TODO Auto-generated method stub
		return position;
	}


	public int getGuid() {
		return guid;
	}


	public String getString() {

		return "" + name + " - " + position.getString() + " - multinode";
	}



	
	

}
