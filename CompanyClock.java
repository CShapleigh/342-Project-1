public class CompanyClock extends Thread {
  protected int currentTime;

  public void run() {
    while(true) {
      currentTime++;
      try {
        sleep(1);
      } catch (Exception e) {
        System.err.println("Error in company clock");
      }
    }
  }

}
