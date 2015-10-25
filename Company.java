import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.*;

public class Company {

  public int numberOfManagers;
  ArrayList<Team> teams;
  public Room standUpRoom;

  public Company (String numberOfManagersString) {
    numberOfManagers = Integer.parseInt(numberOfManagersString);
    teams = new ArrayList<>();
    standUpRoom = new Room();
  }

  public void createDay() {
    // using a latch to await for all developers to arrive
    final CountDownLatch leadArrivalLatch = new CountDownLatch(3);
    final CountDownLatch teamMemberArrivalLatch = new CountDownLatch(4);
    final CountDownLatch developersDontCareLatch = new CountDownLatch(0);
    final Lock questionLock = new ReentrantLock();
    final Condition hasQuestion = questionLock.newCondition();
    final CompanyClock companyClock = new CompanyClock();

    // Use a CyclicBarrier to await for standup
    final CyclicBarrier leadsReadyForTeamStandupBarrier = new CyclicBarrier(3, new Runnable() {
      @Override
      public void run() {
        System.out.println("All leads are ready for the standup with the manager");

        // TODO: start countdown of standup time
      }
    });

    final CyclicBarrier allDeveloperStandupBarrier = new CyclicBarrier(12, new Runnable() {
      @Override
      public void run() {
        System.out.println("All developers are ready for the standup");


        // TODO: start countdown of standup time
      }
    });

    for(int i = 0; i < numberOfManagers; i++) {
      Employee manager = new Manager(i, leadArrivalLatch, questionLock, hasQuestion);
      for(int teamNumber = 0; teamNumber < 3; teamNumber++) {


        // Create a new morning standup barrier for each team
        CyclicBarrier leadDeveloperStandupBarrier = new CyclicBarrier(4, new Runnable() {
          @Override
          public void run() {
            System.out.println("Leads and developers can begin standup now");
          }
        });

        Team team = new Team(teamNumber, leadDeveloperStandupBarrier);
        team.addEmployee(manager);

        // four developers - one lead
        for(int employeeID = 0; employeeID < 4; employeeID++) {

          // Semi-random lead creation
          if (employeeID == teamNumber) {
            Employee teamLead = new  Developer(teamNumber, true, leadArrivalLatch, teamMemberArrivalLatch, leadsReadyForTeamStandupBarrier,  questionLock, hasQuestion, allDeveloperStandupBarrier, standUpRoom);
            team.addEmployee(teamLead);
          } else {
            Employee normalDeveloper = new  Developer(employeeID, false, developersDontCareLatch, teamMemberArrivalLatch, leadsReadyForTeamStandupBarrier, questionLock, hasQuestion, allDeveloperStandupBarrier, standUpRoom);
            team.addEmployee(normalDeveloper);
          }
        }
        teams.add(team);
      }
    }
  }

  public void beginDay() {
    for(Team team : teams) {
      team.beginDay();
    }
    new CompanyClock().start();
  }

}
