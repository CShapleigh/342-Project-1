public interface Employee {
  void arriveAtWork();
  void leaveWork();
  void begnTimeBox(String type);
  void endTimeBox();
  void askQuestion();
  void answerQuestion();
  void doWork(int nextTimebox);
  boolean inTimebox();
  boolean inTheBuilding();
  Team myTeam();
  void setTeam(Team team);
}
