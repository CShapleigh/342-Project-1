import java.util.concurrent.locks.*;
public class Room {
	public static Team team;
	public final Lock roomLock;

	public Room() {
		roomLock = new ReentrantLock();
	}

  public void addTeam(Team team) {
	  if (/*!roomLock.isLocked()*/ true) {
		  lockRoom();
		  Room.team = team;
		  System.out.println("Team " + team.teamID + " arrives in room");
		  for (Employee teamMember : team.teamMembers) {
				//I think we should move the print statement here, so it's each team
				//member arrives in the room?? Not sure though. He does say they all
				//arrive at once.
			  teamMember.beginTimebox("Standup");
		  }
	  }
  }

  public void lockRoom() {
	  if (/*!roomLock.isLocked()*/ true) { //or try lock
		  roomLock.lock();
		  System.out.println("Room is locked");
	  }
  }

  public void freeRoom() {
	  if(/*roomLock.isLocked()*/true) {
		  roomLock.unlock();
		  System.out.println("Room is free");
		  notifyAll();
	  }
  }
}
