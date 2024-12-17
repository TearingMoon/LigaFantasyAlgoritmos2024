package entities;

public class Team 
{
	private String name;
	private int score;
	private int goalsFor;
	private int goalsAgainst;

	public Team(String name, int score, int goalsFor, int goalsAgainst)
	{
		this.name = name;
		this.score = score;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsAgainst;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) throws Exception {
		if(score >= 0) this.score = score;
		else throw new Exception("Se ha intentado poner un score negativo");
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) {
		this.goalsFor = goalsFor;
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}
}
