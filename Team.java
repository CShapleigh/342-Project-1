import java.util.ArrayList;

public class Team {
  public ArrayList<Employee> teamMembers;
  public int teamID;

  public Team() {
     this.teamMembers = new ArrayList<Employee>();
  }

  public void addEmployee(Employee teamMember) {
    teamMembers.add(teamMember.setTeam());
  }

  public void beginDay() {
    for(Employee teamMember : teamMembers) {
//      teamMember.run();
    }
  }

  public boolean everyoneArrived() {
    for(Employee teamMember : teamMembers) {
      if (teamMember.inTheBuilding() == false) {
        return false;
      }
    }
    return true;
  }

}
