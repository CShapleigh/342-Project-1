public class Developer implements Employee extends Thread {

  public boolean arrived;
  public string name;
  public boolean isLead;

  public Developer(String name, boolean isLead) {
    this.name = name;
    this.isLead = isLead;
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
    //wait around, ask a question, if lead, answer if possible

  }

  public boolean inTimebox() {

  }

}
