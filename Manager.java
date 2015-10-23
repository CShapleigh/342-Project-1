import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Manager extends Thread implements Employee {

  public boolean atWork;
  public ArrayList<Team> teams;

  public int managerID;
  public CountDownLatch arrivalLatch;

  public Manager(int managerID, CountDownLatch arrivalLatch) {
    teams = new ArrayList<>();
    this.managerID  = managerID;
    atWork = false;
    this.arrivalLatch = arrivalLatch;
  }

  public ArrayList<Team> myTeam() {
    return this.teams;
  }

  public void setTeam(Team team) {
    teams.add(team);
  }

  public void run() {
    arriveAtWork();

    try {
      waitForTeamLeadsAtWork();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

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
    System.out.println("Manager " + managerID + " begins " + type); //TODO: add time
    obligation.startTimebox(this, type);
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


  private void waitForTeamLeadsAtWork() throws InterruptedException {
    // using a latch to await for all developers to arrive
    System.out.println("Manager " + managerID + " waits for team leads."); //TODO: add time
    try {
      arrivalLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Entire team has arrived.");
    beginTimebox("Standup");
  }
}
