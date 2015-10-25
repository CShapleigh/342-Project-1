import java.util.concurrent.locks.*;
public class Room {
	public static Team team;
	public final Lock roomLock;

	public Room() {
		roomLock = new ReentrantLock();
	}

	//When addTeam is called, attempts to lock the room
  public void addTeam(Team team) {
	  roomLock.lock();
	  try{
		  System.out.println("Room is locked");
		  Room.team = team;
		  System.out.println("Team " + team.teamID + " arrives in room");
		  for (Employee teamMember : team.teamMembers) {
				//I think we should move the print statement here, so it's each team
				//member arrives in the room?? Not sure though. He does say they all
				//arrive at once.
			  if(!(teamMember instanceof Manager)){
				  teamMember.beginTimebox("LEAD_TEAM_STANDUP");
			  }
		  }
	  } finally {
		  roomLock.unlock();
		  System.out.println("Team " + team.teamID + " has finished its standup");
		  System.out.println("Room is open");
		  //notifyAll();
	  }
  }
}
