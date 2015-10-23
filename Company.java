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
      for(int teamNumber = 0; teamNumber < 3; teamNumber++) {
        System.out.println("Creating team " + teamNumber);
        Team team = new Team(teamNumber);
        team.addEmployee(manager);
        for(int employeeID = 0; employeeID < 3; employeeID++) {

          // Semi-random lead creation
          if (employeeID == teamNumber) {
            System.out.println("Create a lead");
            Employee teamLead = new  Developer(teamNumber, true);
            team.addEmployee(teamLead);
          } else {
            Employee normalDeveloper = new  Developer(employeeID, false);
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
