public class Manager implements Employee extends Thread {

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

  }

  public void begnTimeBox(String type) {

  }

  public void endTimeBox() {

  }

  public void askQuestion() {

  }

  public void answerQuestion() {

  }

  public void doWork(int nextTimebox) {
    //do nothing, unless a question is asked, then answer it

  }

  public boolean inTimebox(){

  }

  public boolean inTheBuilding() {
    return arrived;
  }

}
