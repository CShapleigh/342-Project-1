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

  public void startTimebox(Employee employee, String type) {
    switch (type) {
      case "Standup":
        break;
      case "Lunch":
        break;
      case "Ten_AM_Meeting":
        break;
      case "TWO_PM_Meeting":
        break;
      case "Four_PM_Meeting":
        break;
    }
  }

}
