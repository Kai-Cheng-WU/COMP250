
public class BnBReservation extends HotelReservation{
	
	public BnBReservation(String name, Hotel hotel, String roomtype, int nmbnight) {
		super(name, hotel, roomtype, nmbnight);
		
		HotelReservation Hr = new HotelReservation(name, hotel, roomtype, nmbnight);
	}
	
	@Override
	public int getCost() {
		int nights = getNumOfNights();
		int cost = super.getCost();
		int bnbcost = cost + nights*1000;
		return bnbcost;
	
	}

}
