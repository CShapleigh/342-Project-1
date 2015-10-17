
//Main driver should take in number of Managers and create teams based on that
/*
* createDay(int numManagers)
* for (numManager)
*  new manager
*  new developer(name, true)
*  new team
*  team.add(manager)
*  team.add(developer)
*  for(numManager*3)
*   new dev(name, false)
*   team.add(developer)
*   teamList.add(team)
*
*/

/*
* beginDay()
* foreach(team : teamList)
* team.beginDay();
*/

/*
* main()
* createDay(args[0])
* beginDay();
*
*/

public class Main {
    public ArrayList<Team> teams;

    public static void main(String[] args) {
        System.out.println("Starting work day");
    }

    private void createDay(Int numberOfManagers) {
      for(int i = 0; i < numberOfManagers; i++) {
        Employee manager = new Manager(i);
        for(int teamNumber = 0; teamNumber < 2, teamNumber++) {
          Team team = new Team(teamNumber, true);
          Employee teamLead = new  Developer(teamNumber, false);
          //check if manager is not in here first
          team.add(manager);
          team.add(teamLead);
          for(int employeeID = 0; employeeID < 3, employeeID++) {
            Employee normalDeveloper = new  Developer(employeeID, false);
            team.add(normalDeveloper);
          }
        }
      }
    }

    private void beginDay() {

    }

}
