import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

public class Team {
  public ArrayList<Employee> teamMembers;
  public int teamID;
  public CyclicBarrier roomEntryBarrier;
  public CyclicBarrier developerStandupBarrier;

  public Team(int teamID, CyclicBarrier developerStandupBarrier) {
    this.teamID = teamID;
    this.teamMembers = new ArrayList<Employee>();
    this.developerStandupBarrier = developerStandupBarrier;
  }

  // Utility functions
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
  public ArrayList<Employee> normalDevelopers() {
    ArrayList<Employee> developers = new ArrayList<Employee>();
    for(Employee teamMember : teamMembers) {
      if (!teamMember.isTeamLead() && !(teamMember instanceof Manager)) {
        developers.add(teamMember);
      }
    }
    return developers;
  }
  public String getTeamID() {
    return Integer.toString(teamID);
  }
  public void addEmployee(Employee teamMember) {
    if (!teamMembers.contains(teamMember)) {
      teamMember.setTeam(this);
      teamMembers.add(teamMember);
    }
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
  public boolean everyoneArrived() {
    for(Employee teamMember : teamMembers) {
      if (teamMember.inTheBuilding() == false) {
        return false;
      }
    }
    return true;
  }
  public CyclicBarrier getDeveloperStandupBarrier() {
    return developerStandupBarrier;
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

}
