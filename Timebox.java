import java.util.Random;

public class Timebox {
  public static final int STANDUP_MS = 150;
  public static final int MANAGER_LUNCH_AND_MEETING_MS = 600;
  public static final int ANSWER_TIME_MS = 100;
  public static final int TEN_AM_MEET = 1200;
  public static final int TWO_PM_MEET = 3600;
  public static final int FOUR_PM_MEET = 4800;
  public static final int LUNCH = 2400;
  public static final int FIVE_PM = 5400;


  public static int fuzzTime(int min, int max) {
    Random r = new Random();
    //inclusive
    return r.nextInt((max-min) + 1) + min;
  }

  public static String timeToString(int time){
	//Asssume its morning
	String amOrPM = "AM";
	//finds minutes from remainder when divided by intervals of an hour
    String minutes_string = String.format("%02d", time % 600);
    //find hour associated with time
    String hours_string = String.format("%02d", (Math.floor((double)time/600) + 8));

    int hours = Integer.parseInt(hours_string);
    //if past noon, format to proper time and changes to PM
    if (hours > 12){
    	hours = hours - 12;
    	amOrPM = "PM";
        hours_string = Integer.toString(hours);
    }
    String timeString = hours_string + ":" + minutes_string + amOrPM;
    return timeString;
  }

  public void startTimebox(Employee employee, String type) {
    switch (type) {
      case "MANAGER_LEAD_STANDUP":
        managerLeadStandupMeeting(employee);
        break;
      case "LEAD_TEAM_STANDUP":
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
      case "Question_Answer":
        answerQuestion(employee);
        break;
    }
  }

  private void managerLeadStandupMeeting(Employee employee) {
    try {
      employee.threadSleep(Long.valueOf(STANDUP_MS));
    } catch (Exception e) {
      System.err.println("Error waiting during standup");
    }
  }

  private void standupMeeting(Employee employee) {
    if ((employee instanceof Developer) && employee.isTeamLead()) {
      System.out.println("Lead starting standup");
    } else if (employee instanceof Manager) {
      System.out.println("Manager starting standup");
    } else {
      System.out.println("Regular dev starting standup");
    }

    try {
      employee.threadSleep(Long.valueOf(STANDUP_MS));
    } catch (Exception e) {
      System.err.println("Error waiting during standup");
    }

    System.out.println("Finished timebox");
  }

  private void answerQuestion(Employee employee) {
    try {
      employee.threadSleep(Long.valueOf(ANSWER_TIME_MS));
    } catch (Exception e) {
      System.err.println("Error waiting during standup");
    }

  }

  private void lunchTime(Employee employee) {
    int lunchTime = 0;
    if(employee instanceof Manager) {
      lunchTime = MANAGER_LUNCH_AND_MEETING_MS;
    } else {
      lunchTime = fuzzTime(300, 600);
    }
    try {
      employee.threadSleep(Long.valueOf(lunchTime));
    } catch (Exception e) {
      System.err.println("Error waiting during lunch");
    }
  }

  private void normalMeeting(Employee employee) {
    try {
      employee.threadSleep(Long.valueOf(MANAGER_LUNCH_AND_MEETING_MS));
    } catch (Exception e) {
      System.err.println("Error waiting during meeting");
    }

  }

}
