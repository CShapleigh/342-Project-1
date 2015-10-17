public class Room {
	public boolean isLocked;
	public Team team;
	
	public Room(){
		isLocked= false;
	}
	
	
  public static void addTeam(Team team){
	  if(!isLocked){
		  lockRoom();
		  this.team = team;
		  System.out.println("Team " + team.teamID + " arrives in room");
		  foreach(Employee teamMember : team.teamMembers){
			  teamMember.beginTimebox("Standup");
		  }
	  }
  }
  
  public static void lockRoom(){
	  if(!isLocked){
		  isLocked = true;
		  System.out.println("Room is locked");
	  }
  }
  
  public static void freeRoom(){
	  if(isLocked){
		  isLocked = false;
		  system.out.println("Room is free");
		  notifyAll();
	  }
  }
  
}
