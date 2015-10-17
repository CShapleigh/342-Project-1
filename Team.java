import java.util.ArrayList;

public class Team {
  public ArrayList<Employee> teamMembers;
  public int teamID;

  public Team() {
     this.teamMembers = new ArrayList<Employee>();

  }

  public void addEmployee(Employee teamMember) {
    teamMembers.add(teamMember);
  }

  public void beginDay() {
    foreach(Employee teamMember : teamMembers) {
      teamMember.run();
    }
  }

}
