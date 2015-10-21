public class Room {
	public static boolean isLocked;
	public static Team team;

	public Room() {
		isLocked= false;
	}


  public void addTeam(Team team) {
	  if (!isLocked) {
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
	  if (!isLocked) {
		  isLocked = true;
		  System.out.println("Room is locked");
	  }
  }

  public void freeRoom() {
	  if(isLocked) {
		  isLocked = false;
		  System.out.println("Room is free");
		  notifyAll();
	  }
  }
}
