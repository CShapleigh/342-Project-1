public class Developer extends Thread implements Employee {

  public boolean arrived;
  public String name;
  public boolean isLead;

  public Developer(String name, boolean isLead) {
    this.name = name;
    this.isLead = isLead;
    arrived = false;
  }

  public void run() {

  }

  public void leaveWork() {

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
        wait();
      } catch (InterruptedException e) {
        System.out.println(e);
      }
    }

    // TODO: enter room
  }
}
