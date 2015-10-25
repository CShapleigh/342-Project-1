import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;
import java.util.concurrent.BrokenBarrierException;

public class Developer extends Thread implements Employee {

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
  private CyclicBarrier allDeveloperStandupBarrier;

  public Developer(int developerID, boolean isLead,
                   CountDownLatch leadArrivalLatch,
                   CountDownLatch teamMemberArrivalLatch,
                   CyclicBarrier managerLeadStandupBarrier,
                   Lock questionLock,
                   Condition hasQuestion,
                   CyclicBarrier allDeveloperStandupBarrier) {
    this.questionAnswered = true;
    this.developerID = developerID;
    this.isLead = isLead;
    this.leadArrivalLatch = leadArrivalLatch;
    this.teamMemberArrivalLatch = teamMemberArrivalLatch;
    this.managerLeadStandupBarrier = managerLeadStandupBarrier;
    this.questionLock = questionLock;
    this.hasQuestion = hasQuestion;

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

  public void run() {
    arriveAtWork();

    // 1. Lead immediately goes to standup with manager
    if (isLead) {
      try {
        this.managerLeadStandupBarrier.await();
        beginTimebox("MANAGER_LEAD_STANDUP");

        // 2. Lead can now proceed to his own team's standup
        System.out.println("Lead "  + team.getTeamID() + Integer.toString(developerID) +  " has finished standup with manager.");
        System.out.println("Lead "  + team.getTeamID() + Integer.toString(developerID) +  " goes to standup with team.");

        try {
          this.team.getDeveloperStandupBarrier().await();
          beginTimebox("LEAD_DEVELOPER_STANDUP");
          System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " has finished standup with team.");
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }

      } catch (Exception e) {
        e.printStackTrace();
      }




      // Regular developer
    } else {

      // 1. Dev has arrived at work. Immediately go to standup barrier
      //    and await for leads to finish with manager
      try {
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " ready for standup. Waiting..."); //TODO: add time
        this.team.getDeveloperStandupBarrier().await();
        beginTimebox("LEAD_DEVELOPER_STANDUP");
        System.out.println("Developer " + team.getTeamID() + Integer.toString(developerID) + " finished standup");
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        System.out.println("Broken barrier");
      }



      // TODO: proceed with work day
    }
  }

  public void arriveAtWork() {
    // arrives at 8am every day
    int arrivalTime = Timebox.fuzzTime(0, 30);
    threadSleep(arrivalTime);
    this.atWork = true;
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
    System.out.println("Lead " + team.getTeamID() + Integer.toString(developerID) + " awaits devs for standup");
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




}
