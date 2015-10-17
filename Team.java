import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Team {
  public ArrayList<Employee> teamMembers;
  public int teamID;
  public CyclicBarrier roomEntryBarrier;

  public Team() {
     this.teamMembers = new ArrayList<Employee>();
  }

  public void addEmployee(Employee teamMember) {
    teamMember.setTeam();
    teamMembers.add(teamMember);
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
