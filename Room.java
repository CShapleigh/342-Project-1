public class Room {
	public static boolean isLocked;
	public static Team team;
	
	public Room(){
		isLocked= false;
	}
	
	
  public static void addTeam(Team team){
	  if(!isLocked){
		  lockRoom();
		  this.team = team;
		  System.out.println("Team " + team.teamID + " arrives in room");
		  for(Employee teamMember : team.teamMembers){
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
		  System.out.println("Room is free");
		  notifyAll();
	  }
  }
}
