public interface Employee {
  void arriveAtWork();
  void leaveWork();
  void beginTimebox(String type);
  void endTimeBox();
  void doWork(int nextTimebox);
  boolean inTimebox();
  boolean inTheBuilding();
  void askQuestion();
  boolean answerQuestion();
  Team myTeam();
  void setTeam(Team team);
  boolean isTeamLead();
}
