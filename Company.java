import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Company {

  public int numberOfManagers;
  ArrayList<Team> teams;

  public Company (String numberOfManagersString) {
    numberOfManagers = Integer.parseInt(numberOfManagersString);
    teams = new ArrayList<>();
  }

  public void createDay() {
    // using a latch to await for all developers to arrive
    final CountDownLatch arrivalLatch = new CountDownLatch(12);

    for(int i = 0; i < numberOfManagers; i++) {
      Employee manager = new Manager(i, arrivalLatch);
      for(int teamNumber = 0; teamNumber < 3; teamNumber++) {
        Team team = new Team(teamNumber);
        team.addEmployee(manager);

        // four developers - one lead
        for(int employeeID = 0; employeeID < 4; employeeID++) {

          // Semi-random lead creation
          if (employeeID == teamNumber) {
            Employee teamLead = new  Developer(teamNumber, true, arrivalLatch);
            team.addEmployee(teamLead);
          } else {
            Employee normalDeveloper = new  Developer(employeeID, false, arrivalLatch);
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
  }

}
