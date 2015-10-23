import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Team {
  public ArrayList<Employee> teamMembers;
  public int teamID;
  public CyclicBarrier roomEntryBarrier;

  public Team(int teamID) {
    this.teamID = teamID;
    this.teamMembers = new ArrayList<Employee>();
  }

  public void addEmployee(Employee teamMember) {
    if (!teamMembers.contains(teamMember)) {
      teamMember.setTeam(this);
      teamMembers.add(teamMember);
    }
  }


  public void beginDay() {
    // check to see if manager has already arrived (alive thread)
    // before starting manager.
    Manager manager = (Manager) teamManager();
    if (!manager.isAlive()) {
      manager.threadRun();
    }

    for(Employee teamMember : teamMembers) {
        if (teamMember instanceof Developer) {
          teamMember.threadRun();
        }
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
      if (!teamMember.isTeamLead() || teamMember instanceof Manager) {
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
      if (teamMember.isTeamLead()) {
        return teamMember;
      }
    }
    return null;
  }

  public String getTeamID() {
    return Integer.toString(teamID);
  }

}
