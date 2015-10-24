import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.BrokenBarrierException;

public class Developer extends Thread implements Employee {

  public final Lock standUpLock;
  private boolean atWork;
  private int developerID;
  private boolean isLead;
  private boolean questionAnswered;
  private Team team;
  private CountDownLatch leadArrivalLatch;
  private CountDownLatch teamMemberArrivalLatch;
  private CyclicBarrier managerLeadStandupBarrier;
  private CyclicBarrier allDeveloperStandupBarrier;

  public Developer(int developerID, boolean isLead,
                   CountDownLatch leadArrivalLatch,
                   CountDownLatch teamMemberArrivalLatch,
                   CyclicBarrier managerLeadStandupBarrier,
                   CyclicBarrier allDeveloperStandupBarrier) {
    this.questionAnswered = true;
    this.developerID = developerID;
    this.isLead = isLead;
    this.leadArrivalLatch = leadArrivalLatch;
    this.teamMemberArrivalLatch = teamMemberArrivalLatch;
    this.standUpLock = new ReentrantLock();
    this.managerLeadStandupBarrier = managerLeadStandupBarrier;

    // TODO: testing this - not sure if right yet
    this.allDeveloperStandupBarrier = allDeveloperStandupBarrier;
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
  public Lock getStandUpLock() {
    return standUpLock;
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

  @Override
  public void callStandup() {

  }

  public void threadUnlock() {
    standUpLock.unlock();
  }

  public void run() {
    arriveAtWork();

    if (isLead) {
      try {
        // TODO:
        beginTimebox("MANAGER_LEAD_STANDUP");

//        waitForEmployees();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      // 1. Dev has arrived at work. Doesn't do anything until leads are
      //    done with standup with manager
      try {
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " ready for standup. Waiting..."); //TODO: add time
        this.allDeveloperStandupBarrier.await();
        beginTimebox("LEAD_DEVELOPER_STANDUP");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }

      System.out.println("After standup");
      try {
        currentThread().wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

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
    standUpLock.lock();

    if (this.isLead) {
      System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " arrives at work."); //TODO: add time
    } else {
      System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " arrives at work."); //TODO: add time
    }

    // manager is awaiting for all developers to arrive
    if(isTeamLead()) {
      this.leadArrivalLatch.countDown();
    }
    this.teamMemberArrivalLatch.countDown();
  }

  public void leadAwaitsDevelopersForStandup() {
    System.out.println("Lead awaits devs for standup");
    try {
      this.allDeveloperStandupBarrier.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (BrokenBarrierException e) {
      e.printStackTrace();
    }
  }

  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();

    if (type == "MANAGER_LEAD_STANDUP" && this.isLead) {
      System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " begins " + type); //TODO: add time
      obligation.startTimebox(this, type);
    } else if (type == "LEAD_TEAM_STANDUP") {
      if (this.isLead) {
        System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " begins " + type); //TODO: add time
        obligation.startTimebox(this, type);

        try {
          System.out.println("Awaiting lead manager barrier...");
          this.managerLeadStandupBarrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }


      } else {
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " begins " + type); //TODO: add time
        obligation.startTimebox(this, type);
      }

    }


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

    if (this.isLead) {
      System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " waits for other members."); //TODO: add time
    } else {
      System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " waits for other members."); //TODO: add time
    }

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
      // while (Room.isLocked) {
      //   try {
      //     wait();
      //   } catch (InterruptedException e) {
      //     e.printStackTrace();
      //   }
      // }

      // TODO: enter room
  }

  // TODO: old -- now using a barrier for the lead + developers standup
//  public void callStandup() {
//    // Leads meet - get unlocked - start a standup with team
//    ArrayList<Employee> developers = team.normalDevelopers();
//    for (Employee developer : developers) {
//      developer.beginTimebox("Standup");
//    }
//  }

  public void endStandUp() {
    ArrayList<Employee> developers = team.normalDevelopers();
    for (Employee developer : developers) {
      developer.threadUnlock();
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

    // Developers should wait until leads have had their standup with the manager
    if (this.isLead) {
      System.out.println("Have morning stand up with manager ");
    } else {
      // wait around until that stand up is over
      System.out.printf("Not lead... waiting around");
    }

  }


}
