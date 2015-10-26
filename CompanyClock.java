public class CompanyClock extends Thread {
  protected static int currentTime;

  public CompanyClock() {
    currentTime = 0;
  }

  public void run() {
    while(true) {
      CompanyClock.currentTime++;
      try {
        sleep(1);
        if (currentTime == 6000) {
          return;
        }
      } catch (Exception e) {
        System.err.println("Error in company clock");
      }
    }
  }

  public static int getCurrentTime() {
    return currentTime;
  }

}
