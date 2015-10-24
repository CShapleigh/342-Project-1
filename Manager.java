import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
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

  // Utility functions
  public ArrayList<Team> myTeam() {
    return this.teams;
  }
  public void setTeam(Team team) {
    teams.add(team);
  }
  public boolean inTimebox() {
      return false;
  }
  public boolean inTheBuilding() {
    return atWork;
  }
  public boolean isTeamLead() {
    return false;
  }
  public Lock getStandUpLock() {
    return null;
  }

  // Thread utilities
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
  public void threadUnlock() {

  }

  public void run() {
    arriveAtWork();
    try {
      waitForTeamLeadsAtWork();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    beginTimebox("Standup");
    endTimeBox("Standup");
    endStandUp();
    doWork(Timebox.TEN_AM_MEET);
    beginTimebox("Ten_AM_Meeting");
    endTimeBox("Ten_AM_Meeting");
    doWork(Timebox.LUNCH);
    beginTimebox("Lunch");
    endTimeBox("Lunch");
    doWork(Timebox.TWO_PM_MEET);
    beginTimebox("TWO_PM_Meeting");
    endTimeBox("TWO_PM_Meeting");
    doWork(Timebox.FOUR_PM_MEET);
    beginTimebox("FOUR_PM_Meeting");
    endTimeBox("FOUR_PM_Meeting");
    doWork(Timebox.FIVE_PM);
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

  public void endTimeBox(String type) {
    System.out.println("Manager " + managerID + " ends " + type); //TODO: add time
  }

  public boolean answerQuestion() {
    return false;
  }

  public void doWork(int nextTimebox) {
    try {
      currentThread().sleep(1000);
//    currentThread().start();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error in manager doWork");
    }
  }

  // Manager needs to have a morning stand up with leads before anything else happens
  // Developers just wait around during this morning standup, until leads are finished
  public void callStandup() {
    System.out.println("Manager calling standup");
    for (Team team : teams) {
      team.teamLead().beginTimebox("Standup");
    }
  }

  public void endStandUp() {
    for (Team team : teams) {
      team.teamLead().threadUnlock();
    }
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
    callStandup();
  }



}
