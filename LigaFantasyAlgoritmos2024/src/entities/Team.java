package entities;

import structures.DoubleLinkedCircularList;

public class Team implements Comparable<Team> {
	String name;
	int score;
	int goalsFor;
	int goalsAgainst;
	DoubleLinkedCircularList<Player> players;
	int budget;
	int winStreak;
	int loseStreak;
	int highestWinStreak;
	int highestLoseStreak;
	int numberOfWinsAsLocal;
	int numberOfLosesAsLocal;
	int numberOfWinsAsVisitant;
	int numberOfLosesAsVisitant;
	int numberOfTies;

	public Team(String name, int score, int goalsFor, int goalsAgainst) {
		this.name = name;
		this.score = score;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsAgainst;
		players = new DoubleLinkedCircularList<Player>();
		budget = 0;
		winStreak = 0;
		loseStreak = 0;
		highestWinStreak = 0;
		highestLoseStreak = 0;
		numberOfWinsAsLocal = 0;
		numberOfLosesAsLocal = 0;
		numberOfWinsAsVisitant = 0;
		numberOfLosesAsVisitant = 0;
		numberOfTies = 0;
	}
	
	public Team(String name, int score, int goalsFor, int goalsAgainst, Player[] players) throws Exception {
		this.name = name;
		this.score = score;
		this.goalsFor = goalsFor;
		this.goalsAgainst = goalsAgainst;
		this.players = new DoubleLinkedCircularList<Player>();
		budget = 0;
		winStreak = 0;
		loseStreak = 0;
		highestWinStreak = 0;
		highestLoseStreak = 0;
		numberOfWinsAsLocal = 0;
		numberOfLosesAsLocal = 0;
		numberOfWinsAsVisitant = 0;
		numberOfLosesAsVisitant = 0;
		numberOfTies = 0;
		if (players.length != 11) throw new Exception("El equipo necesita 11 jugadores, ni mas ni menos");
		for(Player p : players)
		{
			this.players.Insert(p);
			budget += p.getCost();
		}
	}

	public int getWinStreak() {
		return winStreak;
	}

	public void setWinStreak(int winStreak) {
		this.winStreak = winStreak;
	}

	public int getLoseStreak() {
		return loseStreak;
	}

	public void setLoseStreak(int loseStreak) {
		this.loseStreak = loseStreak;
	}

	public int getHighestWinStreak() {
		return highestWinStreak;
	}

	public void setHighestWinStreak(int highestWinStreak) {
		this.highestWinStreak = highestWinStreak;
	}

	public int getHighestLoseStreak() {
		return highestLoseStreak;
	}

	public void setHighestLoseStreak(int highestLoseStreak) {
		this.highestLoseStreak = highestLoseStreak;
	}

	public int getNumberOfWinsAsLocal() {
		return numberOfWinsAsLocal;
	}

	public void setNumberOfWinsAsLocal(int numberOfWinsAsLocal) {
		this.numberOfWinsAsLocal = numberOfWinsAsLocal;
	}

	public int getNumberOfLosesAsLocal() {
		return numberOfLosesAsLocal;
	}

	public void setNumberOfLosesAsLocal(int numberOfLosesAsLocal) {
		this.numberOfLosesAsLocal = numberOfLosesAsLocal;
	}

	public int getNumberOfWinsAsVisitant() {
		return numberOfWinsAsVisitant;
	}

	public void setNumberOfWinsAsVisitant(int numberOfWinsAsVisitant) {
		this.numberOfWinsAsVisitant = numberOfWinsAsVisitant;
	}

	public int getNumberOfLosesAsVisitant() {
		return numberOfLosesAsVisitant;
	}

	public void setNumberOfLosesAsVisitant(int numberOfLosesAsVisitant) {
		this.numberOfLosesAsVisitant = numberOfLosesAsVisitant;
	}

	public int getNumberOfTies() {
		return numberOfTies;
	}

	public void setNumberOfTies(int numberOfTies) {
		this.numberOfTies = numberOfTies;
	}

	public DoubleLinkedCircularList<Player> getPlayers() {
		return players;
	}
	
	public int getBudget() {
		return budget;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) throws Exception {
		if (score >= 0)
			this.score = score;
		else
			throw new Exception("variable score in Team " + name + "cannot be set to a negative value.");
	}

	public void addScore(int amount) throws Exception {
		if (amount >= 0)
			score += amount;
		else
			throw new Exception("Cannot substract points from Team" + name + ".");
	}

	public int getGoalsFor() {
		return goalsFor;
	}

	public void setGoalsFor(int goalsFor) throws Exception {
		if (goalsFor >= 0)
			this.goalsFor = goalsFor;
		else
			throw new Exception("variable goalsFor in Team " + name + "cannot be set to a negative value.");
	}

	public void addGoalsFor(int amount) throws Exception {
		if (amount >= 0)
			goalsFor += amount;
		else
			throw new Exception("Cannot substract goalsFor from Team" + name + ".");
	}

	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	public void setGoalsAgainst(int goalsAgainst) throws Exception {
		if (goalsAgainst >= 0)
			this.goalsAgainst = goalsAgainst;
		else
			throw new Exception("variable goalsAgainst in Team " + name + "cannot be set to a negative value.");
	}

	public void addGoalsAgainst(int amount) throws Exception {
		if (amount >= 0)
			goalsAgainst += amount;
		else
			throw new Exception("Cannot substract goalsAgainst from Team" + name + ".");
	}
	
	public int getGoalsDifference()
	{
		return (goalsFor - goalsAgainst);
	}

	public void restoreGameData() {
		this.goalsAgainst = 0;
		this.goalsFor = 0;
		this.score = 0;
		this.loseStreak = 0;
		this.winStreak = 0;
		this.highestWinStreak = 0;
		this.highestLoseStreak = 0;
		this.numberOfWinsAsLocal = 0;
		this.numberOfLosesAsLocal = 0;
		numberOfWinsAsVisitant = 0;
		numberOfLosesAsVisitant = 0;
		this.numberOfTies = 0;
	}

	@Override

	public String toString() {
		String s = name;
		if (name.length() < 16) s += "\t\t\t" + score + "\t\t\t" + (goalsFor - goalsAgainst);
		else s += "\t\t" + score + "\t\t\t" + (goalsFor - goalsAgainst);
		return s;
	}

	public int compareTo(Team other) {
		if (this.score > other.getScore())
			return 1;
		else if (this.score < other.getScore())
			return -1;
		else {
			if ((this.getGoalsFor() - this.getGoalsAgainst()) > (other.getGoalsFor() - other.getGoalsAgainst()))
				return 1;
			else if ((this.getGoalsFor() - this.getGoalsAgainst()) < (other.getGoalsFor() - other.getGoalsAgainst()))
				return -1;
			else {
				if (this.getGoalsFor() > other.getGoalsFor())
					return 1;
				else if (this.getGoalsFor() < other.getGoalsFor())
					return -1;
				else
					return 0;
			}
		}
	}
}
