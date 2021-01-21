
public class Customer {
	private String name;
	private int balance;
	private Basket reservations;
	
	
	public Customer(String name, int balance) {
		this.name = name;
		this.balance = balance;
		reservations = new Basket();
	}
	
	public String getName() {
		return name;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public Basket getBasket() {
		return reservations;
	}
	
	public int addFunds(int amount) {
		if (amount<0) {
			throw new IllegalArgumentException ("negative funds entered");		
		}
		balance += amount;
		return balance;
	}
	
	public int addToBasket(Reservation toadd) {
		if (toadd.reservationName()== name) {
			reservations.add(toadd);
			return reservations.getNumOfReservations();
		}
		else {
			throw new IllegalArgumentException ("wrong name");
		}
	}
	
	public int addToBasket(Hotel hotel, String roomtype, int numnights, boolean breakfast) {
		if (breakfast== true) {
			BnBReservation BB = new BnBReservation(name, hotel, roomtype, numnights);
			reservations.add(BB);
		}
		else {
			HotelReservation HH = new HotelReservation(name, hotel, roomtype, numnights);
			reservations.add(HH);
		}
		return reservations.getNumOfReservations();
	}
	
	public int addToBasket(Airport a1, Airport a2) {
		FlightReservation FF = new FlightReservation(name, a1, a2);
		reservations.add(FF);
		return reservations.getNumOfReservations();
	}
	
	public boolean removeFromBasket(Reservation reserved) {
		for(int i=0; i<reservations.getNumOfReservations(); i++) {
			if (reserved == reservations.getProducts()[i]) {
				reservations.remove(reserved);
				return true;
			}
			
		}
		return false;
	}
	
	public int checkOut() {
		int totalcost = 0;
		for(int i=0; i<reservations.getNumOfReservations(); i++) {
			totalcost += reservations.getProducts()[i].getCost();
			}
			
		if (balance < totalcost) {
			throw new IllegalArgumentException ("insufficient funds");
		}
		else {
			reservations = new Basket();
			return (balance - totalcost);
		}
	}

}
