import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.BrokenBarrierException;

public class Developer extends Thread implements Employee {

  private boolean atWork;
  private int developerID;
  private boolean isLead;
  private boolean questionAnswered;
  private Team team;
  private CountDownLatch leadArrivalLatch;
  private CountDownLatch teamMemberArrivalLatch;

  public Developer(int developerID, boolean isLead, CountDownLatch leadArrivalLatch, CountDownLatch teamMemberArrivalLatch) {
    this.questionAnswered = true;
    this.developerID = developerID;
    this.isLead = isLead;
    this.leadArrivalLatch = leadArrivalLatch;
    this.teamMemberArrivalLatch = teamMemberArrivalLatch;
  }

  // Utility functions
  public ArrayList<Team> myTeam() {
    ArrayList<Team> teamList = new ArrayList<Team>();
    teamList.add(this.team);
    return teamList;
  }
  public void setTeam(Team team) {
    this.team = team;
  }
  public boolean isTeamLead() {
    return isLead;
  }
  public boolean inTimebox() {
    return false;
  }
  public boolean inTheBuilding() {
    return atWork;
  }

  // Thread utilities
  public void threadSleep(long time) {
    try {
      sleep(time);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error in developer threadSleep");
    }
  }
  public void threadRun() {
    start();
  }

  public void run() {
    arriveAtWork();
    standUpWait();
    if (isLead) {
      try {
        waitForEmployees();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    beginTimebox("Standup");
    endTimeBox("Standup");
    if(isLead) {
      endStandUp();
    }


    // asks questions
    //askQuestion();
    //doWork(Timebox.LUNCH);

    // eat lunch
    //Timebox.startTimebox(this, "LUNCH");

    // grabs rom


    // leaveWork
    //leaveWork();

  }

  public void arriveAtWork() {
    // arrives at 8am every day
    int arrivalTime = Timebox.fuzzTime(0, 30);
    threadSleep(30);
    this.atWork = true;
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " arrives at work."); //TODO: add time

    // manager is awaiting for all developers to arrive
    if(isTeamLead()) {
      this.leadArrivalLatch.countDown();
    }
    this.teamMemberArrivalLatch.countDown();
  }

  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " begins " + type); //TODO: add time
    obligation.startTimebox(this, type);
  }

  public void endTimeBox(String type) {
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " ends " + type); //TODO: add time
  }

  public void askQuestion() {
    // TODO: finish
    // any point during day a dev (and leads) can ask a question
    this.questionAnswered = false;

    if (this.isLead) {
      // lead has to go to manager -- going to wait 10 minutes
      this.team.teamManager().answerQuestion();
    } else {
      // dev has to ask lead the question
      this.team.teamLead().answerQuestion();
      try {
        while (this.team.teamLead().answerQuestion() == false) {
          wait();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void leaveWork() {
    // start leaving 4:30/5. leads have to wait for their devs to leave first
    if (this.isLead && this.team.developersGone() || !this.isLead) {
        this.atWork = false;
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " leaves work."); //TODO: add time
    }
  }

  public boolean answerQuestion() {
    // TODO: finish

    // only lead and mangers
    if (this.isLead) {
      // 50% chance that leads can
      Random random = new Random(2);
      if (random.nextInt() == 0) {
        // answer immediately
        return true;
      } else {
        // goes to team manager with current question
        try {
          while (this.team.teamManager().answerQuestion() == false) {
            wait();
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      return false;
    }

    this.questionAnswered = true;
    return true;
  }

  public void doWork(int nextTimebox) {
    //wait around, ask a question, if lead, answer if possible

    // call startTimebox
    try {
      currentThread().sleep(1000);
//    currentThread().start();
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error in employee doWork");
    }
  }

  public void waitForEmployees() {
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " waits for other members."); //TODO: add time
    while (!team.everyoneArrived()) {
      try {
        // queue up members of team at the meeting room
        myTeam().get(0).roomEntryBarrier.await();
        wait();
      } catch (InterruptedException e) {
        System.out.println(e);
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }


  public void grabRoom(Room room) {
    if (!this.isLead) {
      return;
    }

    waitForEmployees();

    // now employees are together; wait for unlocked room
      while (Room.isLocked) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      // TODO: enter room
  }

  public void callStandup() {
    ArrayList<Employee> developers = team.normalDevelopers();
    for (Employee developer : developers) {
      developer.beginTimebox("Standup");
    }
  }

  public synchronized void endStandUp() {
    ArrayList<Employee> developers = team.normalDevelopers();
    for (Employee developer : developers) {
      developer.notify();
    }
  }

  private void waitForTeamMembersWork() throws InterruptedException {
    // using a latch to await for all developers to arrive
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " awaits team members."); //TODO: add time
    try {
      teamMemberArrivalLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    callStandup();
  }

  private synchronized void standUpWait() {
    try {
      wait();
    } catch (Exception e) {

    }
  }


}
