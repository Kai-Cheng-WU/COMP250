
public class HotelReservation extends Reservation{
	private Hotel reserved;
	private String roomtype;
	private int nmbnight;
	private int pricepernight;
	
	
	public HotelReservation (String name, Hotel reserved, String roomtype, int nmbnight) {
		super(name);
		this.reserved = reserved;
		this.roomtype = roomtype;
		this.nmbnight = nmbnight;
		
		reserved.reserveRoom(roomtype);
		
	}
	
	public int getNumOfNights() {
		return nmbnight;
	}
	
	@Override
	public int getCost() {
		Room r1 = new Room(roomtype);
		return nmbnight*(r1.getPrice());
		
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		
		HotelReservation Hr = (HotelReservation) object;

		return ((this.reserved.equals(Hr.reserved)) && (this.roomtype.contentEquals(Hr.roomtype)) && (this.nmbnight == Hr.nmbnight) && (this.getCost() == Hr.getCost()));
	}

}
