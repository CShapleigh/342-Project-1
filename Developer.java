import java.util.concurrent.BrokenBarrierException;

public class Developer extends Thread implements Employee {

  public boolean arrived;
  public int developerID;
  public boolean isLead;
  public Team team;

  public Developer(int developerID, boolean isLead) {
    this.developerID = developerID;
    this.isLead = isLead;
    arrived = false;
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

  }

  public void arriveAtWork() {
    // arrives at 8am every day
    arrived = true;
    System.out.println("Whatever format");
  }


  public void begnTimeBox(String type) {

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

  }

  public boolean inTimebox() {
    return false;
  }

  public boolean inTheBuilding() {
    return arrived;
  }

  public void grabRoom(Room room) {
    // if room is locked, developer will wait outside the room
    while (room.isLocked) {
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

    // TODO: enter room
  }
}
