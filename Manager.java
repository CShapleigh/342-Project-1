public class Manager extends Thread implements Employee {

  public boolean arrived;

  public Manager() {
    arrived = false;

  }

  public void run() {

  }

  public void arriveAtWork() {
    arrived = true;
    System.out.println("Whatever format");
  }

  public void leaveWork() {
    arrived = false;
    System.out.println("Whatever format");

  }

  public void begnTimeBox(String type) {
    Timebox obligation = new Timebox();
    System.out.println("Whatever format");
//    obligation.begnTimeBox(this, type);
  }

  public void endTimeBox() {

  }

  public void askQuestion() {

  }

  public void answerQuestion() {

  }

    public void doWork(int nextTimebox) {

    }

  public boolean inTimebox(){
      return false;

  }

  public boolean inTheBuilding() {
    return arrived;
  }

}
