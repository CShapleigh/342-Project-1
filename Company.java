import java.util.ArrayList;
public class Company {

  public int numberOfManagers;
  ArrayList<Team> teams;

  public Company (String numberOfManagersString) {
    numberOfManagers = Integer.parseInt(numberOfManagersString);
    teams = new ArrayList<Team>();
  }

  public void createDay() {
    for(int i = 0; i < numberOfManagers; i++) {
      Employee manager = new Manager(i);
      for(int teamNumber = 0; teamNumber < 2; teamNumber++) {
        Team team = new Team(teamNumber);
        team.addEmployee(manager);
        for(int employeeID = 0; employeeID < 3; employeeID++) {
          Employee teamLead = new  Developer(teamNumber, true);
          Employee normalDeveloper = new  Developer(employeeID, false);
          team.addEmployee(normalDeveloper);
        }
      }
    }
  }

  public void beginDay() {
    for(Team team : teams) {
      team.beginDay();
    }
  }

}
