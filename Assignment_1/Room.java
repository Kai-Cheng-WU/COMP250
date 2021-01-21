
public class Room {
	private String type;
	private int price;
	private boolean availability;
	
	public Room (String type){
		this.type = type;
		this.availability = true;

		
		if (type == "double") {
			price = 9000;
		}
		else if (type == "queen") {
			price = 11000;
		}
		else if (type == "king") {
			price = 15000;
		}
		else {
			throw new IllegalArgumentException ("No room of such type can be created");
		}
		
	}
	
	public Room (Room roomoo) {
		this.type = roomoo.getType();
		this.price = roomoo.getPrice();
		this.availability = roomoo.availability;
	}
	
	public String getType() {
		return type;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void changeAvailability() {
		if (availability == true) {
			availability = false;
		}
		else {
			availability = true;
		}
	}
	
	public static Room findAvailableRoom(Room[] rooms, String type){
		for (int i = 0; i < rooms.length; i++){
			if (rooms[i].type == type){
				if (rooms[i].availability == true){
					return rooms[i];
				}
			}
		}
		return null;
	}
	
	public static boolean makeRoomAvailable(Room[] rooms, String type) {
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i].type == type) {
				if (rooms[i].availability == false) {
					rooms[i].availability = true;
					return true;
				}
			}
		}
		return false;
	}
	
/////////////////////////Tester below  >:(

	public static void main(String[] args)
    {
        Room room = new Room("king");
        room.getType();
        room.getPrice();
        
        System.out.println(room.type);
        System.out.println(room.getType());
        System.out.println(room.getPrice());
        
        Room roomooo = new Room(room);
        room.type = "queen";
        System.out.println(roomooo.getType());
        System.out.println(room.getType());
        System.out.println(room == roomooo);
        System.out.println(room);
        System.out.println(room.price);
        Room[] rooms = {room,roomooo};
        Room myroom = findAvailableRoom(rooms, "king");

        System.out.println(myroom.price);
        
    }
	
}
