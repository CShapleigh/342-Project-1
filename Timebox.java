import java.util.Random;


public class Timebox {
  public static final int STANDUP_MINS = 15;
  public static final int MANAGER_MEETING_MINS = 60;
  public static final int ANSWER_TIME_MINS = 10;
  public static final int TEN_AM_MEET = 1200;
  public static final int TWO_PM_MEET = 3600;
  public static final int FOUR_PM_MEET = 4800;
  public static final int LUNCH = 2400;
  public static final int FIVE_PM = 5400;
  public static final int MANAGER_LUNCH_AND_MEETING_LENGTH = 600;

  public static int fuzzTime(int min, int max) {
    Random r = new Random();
    //inclusive
    return r.nextInt((max-min) + 1) + min;
  }

  public static String timeToString(long time){
    //uhh
  }

  public static void startTimebox(Employee employee, String type) {
    switch (type) {
      case "Standup":
        standupMeeting(employee);
        break;
      case "Lunch":
        lunchTime(employee);
        break;
      case "Ten_AM_Meeting":
        normalMeeting(employee);
        break;
      case "TWO_PM_Meeting":
        normalMeeting(employee);
        break;
      case "Four_PM_Meeting":
        standupMeeting(employee);
        break;
    }
  }

  private void standupMeeting(Employee employee) {
    try {
      employee.wait(STANDUP_MINS * 100);
    } catch (Exception e) {
      System.err.println("Error waiting during standup");
    }
  }

  private void lunchTime(Employee employee) {
    int lunchTime = 0;
    if(employee instanceof Manager) {
      lunchTime = MANAGER_LUNCH_AND_MEETING_LENGTH;
    } else {
      lunchTime = fuzzTime(300, 600);
    }
    try {
      employee.wait(lunchTime);
    } catch (Exception e) {
      System.err.println("Error waiting during lunch");
    }
  }

  private void normalMeeting(Employee employee) {
    try {
      employee.wait(MANAGER_LUNCH_AND_MEETING_LENGTH);
    } catch (Exception e) {
      System.err.println("Error waiting during meeting");
    }

  }

}
