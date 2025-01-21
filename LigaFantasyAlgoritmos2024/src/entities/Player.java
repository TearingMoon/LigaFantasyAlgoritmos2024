package entities;

public class Player
{
	private String name;
	private int age;
	private Position position;
	private int cost;
	
	public Player(String name, int age, String position, int cost)
	{
		this.name = name;
		this.age = age;
		this.cost = cost;
		
		switch(position)
		{
		case "POR":
			this.position = Position.Portero;
			break;
		case "LAT_IZQ":
			this.position = Position.Lateral_Izdo;
			break;
		case "LAT_DER":
			this.position = Position.Lateral_Dcho;
			break;
		case "CEN":
			this.position = Position.Central;
			break;
		case "PIV":
			this.position = Position.Pivote;
			break;
		case "MED_CEN":
			this.position = Position.Mediocentro;
			break;
		case "MED_PUN":
			this.position = Position.Mediapunta;
			break;
		case "EXT_IZQ":
			this.position = Position.Extremo_Izdo;
			break;
		case "EXT_DER":
			this.position = Position.Extremo_Dcho;
			break;
		case "DEL":
			this.position = Position.Delantero;
			break;
		default:
			this.position = Position.NULL_POSITION;
			break;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
}
