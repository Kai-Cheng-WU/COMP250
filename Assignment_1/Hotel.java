
public class Hotel {
	private String name;
	private Room[] Rooms;
	 
	public Hotel (String name, Room[] Rooms) {
		this.name = name;
		this.Rooms = new Room[Rooms.length];
		for (int i=0; i < (Rooms.length); i++) {
			Room myroom = new Room(Rooms[i].getType());
			this.Rooms[i]=myroom;
		}
		
	}
	
	public int reserveRoom (String type) {
		Room room = null;
		room = Room.findAvailableRoom(Rooms, type);
		if (room != null) {
			room.changeAvailability();
			return room.getPrice();
		}
		else {
			throw new IllegalArgumentException ("No room of such type can be created");
		}

	}
	
	public boolean cancelRoom (String type) {
		return Room.makeRoomAvailable(Rooms, type);
	}
	
	



}