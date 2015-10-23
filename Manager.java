import java.util.ArrayList;
public class Manager extends Thread implements Employee {

  public boolean atWork;
  public ArrayList<Team> teams;

  public int managerID;
  public int teamLeadCount;

  public Manager(int managerID) {
    teams = new ArrayList<Team>();
    this.managerID  = managerID;
    atWork = false;
    teamLeadCount = 0;
  }

  public ArrayList<Team> myTeam() {
    return this.teams;
  }

  public void setTeam(Team team) {
    teams.add(team);
  }

  public void run() {
    arriveAtWork();
    waitForTeamLeadsAtWork();
    beginTimebox("Standup");
    leaveWork();
  }

  public void arriveAtWork() {
    atWork = true;
    System.out.println("Manager " + managerID + " arrives at work."); //TODO: add time
  }

  public void leadArrive() {
    this.teamLeadCount++;
  }

  public void leaveWork() {
    atWork = false;
    System.out.println("Manager " + managerID + " leaves at work."); //TODO: add time
  }

  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();
    System.out.println("Whatever format");
//  obligation.begnTimeBox(this, type);
  }

  public void endTimeBox() {

  }

  public boolean answerQuestion() {
    return false;
  }

  public void doWork(int nextTimebox) {

  }

  public boolean inTimebox(){
      return false;

  }

  public boolean inTheBuilding() {
    return atWork;
  }

  public boolean isTeamLead() {
    return false;
  }

  public void threadSleep(long time) {
    try {
      sleep(time);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error in manager threadSleep");
    }
  }


  public void threadRun() {
    start();
  }

  private synchronized void waitForTeamLeadsAtWork() {
    System.out.println("Manager " + managerID + " waits for team leads."); //TODO: add time
    for(Team team : teams) {
      while(teamLeadCount != 4) {
        try {
          sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
          System.err.println("Error in waitForTeamLeadsAtWork");
        }
      }
    }
  }

}
