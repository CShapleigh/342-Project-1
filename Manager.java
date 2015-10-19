public class Manager extends Thread implements Employee {

  public boolean atWork;
  public Team team;
  public int managerID;

  public Manager(int managerID) {
    this.managerID  = managerID;
    atWork = false;
  }

  public Team myTeam() {
    return this.team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public void run() {
    arriveAtWork();
    //wait for team leads
    beginTimebox("Standup");
    leaveWork();
  }

  public void arriveAtWork() {
    atWork = true;
    System.out.println("Whatever format");
  }

  public void leaveWork() {
    atWork = false;
    System.out.println("Whatever format");
  }

  public void beginTimebox(String type) {
    Timebox obligation = new Timebox();
    System.out.println("Whatever format");
//    obligation.begnTimeBox(this, type);
  }

  public void endTimeBox() {

  }

  public void askQuestion() {
    return null;
  }

  public boolean answerQuestion() {
    return false;
  }

  public void doWork(int nextTimebox) {

  }

  public boolean inTimebox(){
      return false;

  }

  public boolean inTheBuilding() {
    return atWork;
  }

  public boolean isTeamLead() {
    return false;
  }

}
