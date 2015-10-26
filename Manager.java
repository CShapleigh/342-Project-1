import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;

public class Manager extends Thread implements Employee {

  public boolean atWork;
  public ArrayList<Team> teams;

  public int managerID;
  public CountDownLatch arrivalLatch;
  public CyclicBarrier allDeveloperStandupBarrier;
  public Lock questionLock;
  public Condition hasQuestion;
  public ArrayList<Employee> employeesWithQuestions;
  public int currentTime;


  public Manager(int managerID, CountDownLatch arrivalLatch, Lock questionLock, Condition hasQuestion, CyclicBarrier allDeveloperStandupBarrier) {
    teams = new ArrayList<>();
    this.managerID  = managerID;
    atWork = false;
    this.arrivalLatch = arrivalLatch;
    this.questionLock = questionLock;
    this.hasQuestion = hasQuestion;
    this.employeesWithQuestions = new ArrayList<>();
    this.allDeveloperStandupBarrier = allDeveloperStandupBarrier;
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
  public void addQuestioningEmployee(Employee employee) {
    employeesWithQuestions.add(employee);
  }
  public void addToCurrentTime(int time) {
    currentTime += time;
  }
  public int getCurrentTime() {
    return currentTime;
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

  public void run() {
    arriveAtWork();
    try {
      waitForTeamLeadsAtWork();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
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
    endOfDayMeeting();
    endTimeBox("FOUR_PM_Meeting");
    doWork(Timebox.FIVE_PM);
    leaveWork();
  }

  public void arriveAtWork() {
    currentTime = 0;
    atWork = true;
    System.out.println("Manager " + managerID + " arrives at work. Time: + " Timebox.timeToString(getCurrentTime()) ); //TODO: add time
  }
  public void leaveWork() {
    atWork = false;
    System.out.println("Manager " + managerID + " leaves at work. Time: + " Timebox.timeToString(getCurrentTime())); //TODO: add time
  }
  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();
    System.out.println("Manager " + managerID + " begins " + type + " Time: + " Timebox.timeToString(getCurrentTime())); //TODO: add time
    obligation.startTimebox(this, type);
  }
  public void endTimeBox(String type) {
    System.out.println("Manager " + managerID + " ends " + type); //TODO: add time
  }

  public void answerQuestion(Employee employee, boolean skipChance) {
    employee.beginTimebox("Question_Answer");
    beginTimebox("Question_Answer");
    synchronized (this) {
      Iterator<Employee> iter = employeesWithQuestions.iterator();
      while (iter.hasNext()) {
        Employee emp = iter.next();
        if (emp.equals(employee)) {
          iter.remove();
          break;
        }
      }
    }
  }

  public void callStandup() {
    System.out.println("All leads ready. Manager " + managerID + " calling standup...");
  }

  public void doWork(int nextTimebox) {
    questionLock.lock();
    try {
      hasQuestion.await(nextTimebox, TimeUnit.MILLISECONDS);
      for (Employee developer : employeesWithQuestions) {
        answerQuestion(developer, false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Error in manager doWork");
    } finally {
      addToCurrentTime(nextTimebox);
      questionLock.unlock();
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

  public void endOfDayMeeting(){
	  try {
			allDeveloperStandupBarrier.await();
	  } catch (InterruptedException e) {
			e.printStackTrace();
	  } catch (BrokenBarrierException e) {
			e.printStackTrace();
	  }
	  beginTimebox("Four_PM_Meeting");

  }

}
