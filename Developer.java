import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Developer extends Thread implements Employee {

  public boolean atWork;
  public int developerID;
  public boolean isLead;
  public Team team;

  public Developer(int developerID, boolean isLead) {
    this.developerID = developerID;
    this.isLead = isLead;
    atWork = false;
  }

  public void run() {

  }

  public Team myTeam() {
    return this.team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public void leaveWork() {
    // start leaving 4:30/5
    // project manager
    if (this.isLead) {
      if (this.team.developersGone()) {
        // all other developers on the team are gone -- lead can leave
        atWork = false;
      }
    } else {
      atWork = false;
    }
  }

  public void arriveAtWork() {
    // arrives at 8am every day
    atWork = true;
    System.out.println("Whatever format");
  }


  public void begnTimeBox(String type) {

    // lunchish

    // 4:15

  }

  public void endTimeBox() {
    // TODO: implement

  }

  public void askQuestion() {
    // TODO: finish
    // any point during day a team member can ask a question

    // leads can ask too
    if (this.isLead) {
      // lead has to go to manager -- going to wait 10 minutes
      this.team.teamManager().askQuestion();
    } else {
      // dev has to ask lead the question
      this.team.teamLead().answerQuestion();
    }
  }

  public void answerQuestion() {
    // TODO: finish

    // only lead and mangers
    if (this.isLead) {
      // 50% chance that leads can
      Random random = new Random(2);
      if (random.nextInt() == 0) {
        // answer immediately
      } else {
        // goes to team manager with current question
        this.team.teamManager().askQuestion();
      }
    }
  }

  public void doWork(int nextTimebox) {
    //wait around, ask a question, if lead, answer if possible

    // call startTimebox
    currentThread().sleep(1000);
    currentThread().start();
  }

  public boolean inTimebox() {
    return false;
  }

  public boolean inTheBuilding() {
    //
    return atWork;
  }

  public void waitForEmployees() {
    while (!myTeam().everyoneArrived()) {
      try {
        // queue up members of team at the meeting room
        this.myTeam().roomEntryBarrier.await();
        wait();
      } catch (InterruptedException e) {
        System.out.println(e);
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }

  public void grabRoom(Room room) {
    // if room is locked, developer will wait outside the room

    // if everyone arrives and trying to meet, team leader can enter the
    if (this.isLead) {
      waitForEmployees();
    }

    // now employees are together; wait for unlocked room
    if (this.isLead) {
      while (room.isLocked) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      // TODO: enter room
    }
  }
}
