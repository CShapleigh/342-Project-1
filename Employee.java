import java.util.ArrayList;
public interface Employee {
  void arriveAtWork();
  void leaveWork();
  void beginTimebox(String type);
  void endTimeBox();
  void doWork(int nextTimebox);
  boolean inTimebox();
  boolean inTheBuilding();
  boolean answerQuestion();
  ArrayList<Team> myTeam();
  void setTeam(Team team);
  boolean isTeamLead();
  void threadSleep(long time);
  void threadRun();
}
