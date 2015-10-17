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
      } else {
        // can't leave work
      }
    } else {
      // leave work
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

  }

  public void askQuestion() {
    // only leads can ask question

  }

  public void answerQuestion() {
    // 50% chance that leads can

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
