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

  public static String timeToString(int time){
	//Asssume its morning
	String amOrPM = "AM";
	//finds minutes from remainder when divided by intervals of an hour
    int minutes = String.format("%02d", time%600);
    //find hour associated with time
    int hours = String.format("%02d", (Math.floor((double)time/600) + 8));
    //if past noon, format to proper time and changes to PM
    if(hours > 12){
    	hours = hours - 12;
    	amOrPM = "PM";
    }
    String timeString = hours.toString() + ":" + minutes.toString() + amOrPM;
    return timeString;
  }

  public static void startTimebox(Employee employee, String type) {
    switch (type) {
      case "Standup":
//        standupMeeting(employee);
        break;
      case "Lunch":
//        lunchTime(employee);
        break;
      case "Ten_AM_Meeting":
//        normalMeeting(employee);
        break;
      case "TWO_PM_Meeting":
//        normalMeeting(employee);
        break;
      case "Four_PM_Meeting":
//        standupMeeting(employee);
        break;
    }
  }

  private void standupMeeting(Employee employee) {
    try {
      employee.sleep(STANDUP_MINS * 100);
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
      employee.sleep(lunchTime);
    } catch (Exception e) {
      System.err.println("Error waiting during lunch");
    }
  }

  private void normalMeeting(Employee employee) {
    try {
      employee.sleep(MANAGER_LUNCH_AND_MEETING_LENGTH);
    } catch (Exception e) {
      System.err.println("Error waiting during meeting");
    }

  }

}
