import java.util.ArrayList;
public class Manager extends Thread implements Employee {

  public boolean atWork;
  public ArrayList<Team> teams;

  public int managerID;

  public Manager(int managerID) {
    teams = new ArrayList<Team>();
    this.managerID  = managerID;
    atWork = false;
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

  public void askQuestion() {
    System.out.println("I shouldn't be asking a question");
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
      System.err.println("Error");
    }
  }

  public void threadRun() {
    start();
  }

  private void waitForTeamLeadsAtWork() {
    for(Team team : teams) {
      while(!team.teamLead().inTheBuilding()) {
        try {
          wait();
        } catch (Exception e) {
          System.err.println("Error");
        }
      }
    }
  }

}
