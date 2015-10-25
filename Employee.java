import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
public interface Employee {
  void arriveAtWork();
  void leaveWork();
  void beginTimebox(String type);
  void endTimeBox(String type);
  void doWork(int nextTimebox);
  boolean inTimebox();
  boolean inTheBuilding();
  void answerQuestion(Employee employee, boolean skipChance);
  ArrayList<Team> myTeam();
  void setTeam(Team team);
  boolean isTeamLead();
  void threadSleep(long time);
  void threadRun();
  void callStandup();
}
