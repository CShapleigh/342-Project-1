public class CompanyClock extends Thread {
  protected static int currentTime;

  public void run() {
    while(true) {
      CompanyClock.currentTime++;
      try {
        sleep(1);
      } catch (Exception e) {
        System.err.println("Error in company clock");
      }
    }
  }

  public static int getCurrentTime() {
    return currentTime;
  }

}
