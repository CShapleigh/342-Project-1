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
    if (!teamMembers.contains(teamMember)) {
      teamMember.setTeam();
      teamMembers.add(teamMember);
    }
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

  public boolean developersGone() {
    for(Employee teamMember : teamMembers) {
      if (!teamMember.isLead || teamMember instanceof Manager) {
        break;
      }
      if (teamMember.inTheBuilding()) {
        return false;
      }
    }
    return true;
  }

  public Employee teamManager() {
    for(Employee teamMember : teamMembers) {
      if (teamMember instanceof Manager) {
        return teamMember;
      }
    }
    return null;
  }

  public Employee teamLead() {
    for (Employee teamMember : teamMembers) {
      if (teamMember instanceof Manager) {
        break;
      }
      if (teamMember.isLead) {
        return teamMember;
      }
    }
    return null;
  }

}
