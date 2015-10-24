import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;
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
  public Lock questionLock;
  public Condition hasQuestion;

  public Developer(int developerID, boolean isLead,
                   CountDownLatch leadArrivalLatch,
                   CountDownLatch teamMemberArrivalLatch,
                   CyclicBarrier managerLeadStandupBarrier,
                   Lock questionLock,
                   Condition hasQuestion) {
    this.questionAnswered = true;
    this.developerID = developerID;
    this.isLead = isLead;
    this.leadArrivalLatch = leadArrivalLatch;
    this.teamMemberArrivalLatch = teamMemberArrivalLatch;
    this.standUpLock = new ReentrantLock();
    this.managerLeadStandupBarrier = managerLeadStandupBarrier;
    this.questionLock = questionLock;
    this.hasQuestion = hasQuestion;
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
  public void threadUnlock() {
    standUpLock.unlock();
  }

  public void run() {
    arriveAtWork();
    //standUpWait();
    // if (isLead) {
    //   try {
    //     waitForEmployees();
    //   } catch (Exception e) {
    //     e.printStackTrace();
    //   }
    // }
    //beginTimebox("Standup");
    //endTimeBox("Standup");
    // if (isLead) {
    //   endStandUp();
    // }

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
    threadSleep(arrivalTime);
    this.atWork = true;
    standUpLock.lock();
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " arrives at work."); //TODO: add time

    // manager is awaiting for all developers to arrive
    if(isTeamLead()) {
      this.leadArrivalLatch.countDown();
    }
    this.teamMemberArrivalLatch.countDown();
  }

  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();

    // if (type == "Standup" && this.isLead) {
    //   try {
    //     System.out.println("Awaiting lead manager barrier...");
    //     this.managerLeadStandupBarrier.await();
    //   } catch (InterruptedException e) {
    //     e.printStackTrace();
    //   } catch (BrokenBarrierException e) {
    //     e.printStackTrace();
    //   }
    //}

    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " begins " + type); //TODO: add time
    obligation.startTimebox(this, type);
  }

  public void endTimeBox(String type) {
    System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " ends " + type); //TODO: add time
  }

  public void askQuestion() {
    if (isTeamLead()) {
      team.teamManager().answerQuestion(this);
    } else {
      team.teamLead().answerQuestion(this);
    }
  }

  public void leaveWork() {
    // start leaving 4:30/5. leads have to wait for their devs to leave first
    if (this.isLead && this.team.developersGone() || !this.isLead) {
        this.atWork = false;
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " leaves work."); //TODO: add time
    }
  }

  public void answerQuestion(Employee employee) {
    // TODO: finish

    // only lead and mangers
    if (this.isLead) {
      Random r = new Random();
      int choice = r.nextInt(2);
      if(choice==0) {
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " immediately answers question."); //TODO: add time
        return;
      }
    }
    questionLock.lock();
    try {
      hasQuestion.notify();
    } catch (Exception e) {
      System.err.println("Error in developer answerquestion.");
    } finally {
      questionLock.unlock();
    }
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
      // while (Room.isLocked) {
      //   try {
      //     wait();
      //   } catch (InterruptedException e) {
      //     e.printStackTrace();
      //   }
      // }

      // TODO: enter room
  }

  public void callStandup() {
    // Leads meet - get unlocked - start a standup with team
    ArrayList<Employee> developers = team.normalDevelopers();
    for (Employee developer : developers) {
      developer.beginTimebox("Standup");
    }
  }

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

  // For both standups
  // Lead
  private synchronized void standUpWait() {
    try {
      while (true) {
        standUpLock.tryLock();
      }
    } catch (Exception e) {

    }
  }


}
