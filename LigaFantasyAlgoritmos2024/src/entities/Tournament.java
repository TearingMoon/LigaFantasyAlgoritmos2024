package entities;

public class Tournament {
	private Team team1;
	private Team team2;
	private int goalsTeam1;
	private int goalsTeam2;

	public Tournament(Team team1, Team team2, int goalsTeam1, int goalsTeam2) {
		this.team1 = team1;
		this.team2 = team2;
		this.goalsTeam1 = goalsTeam1;
		this.goalsTeam2 = goalsTeam2;
	}

	public Team getTeam1() {
		return team1;
	}

	/**
	 * @param team1
	 */
	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	/**
	 * @param team2
	 */
	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public int getGoalsTeam1() {
		return goalsTeam1;
	}

	/**
	 * @param goalsTeam1
	 */
	public void setGoalsTeam1(int goalsTeam1) {
		this.goalsTeam1 = goalsTeam1;
	}

	public int getGoalsTeam2() {
		return goalsTeam2;
	}

	/**
	 * @param goalsTeam2
	 */
	public void setGoalsTeam2(int goalsTeam2) {
		this.goalsTeam2 = goalsTeam2;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Tournament t = (Tournament) obj;

		return team1.equals(t.team1) && team2.equals(t.team2);
	}

	@Override
	public String toString() {
		return "Tournament [team1=" + team1 + ", team2=" + team2 + ", goalsTeam1=" + goalsTeam1 + ", goalsTeam2="
				+ goalsTeam2 + "]";
	}

}
