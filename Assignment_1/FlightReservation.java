
public class FlightReservation extends Reservation{
	private Airport arrival;
	private Airport departure;
	

	
	public FlightReservation(String name, Airport departure, Airport arrival) {
		super(name);
		this.arrival = arrival;
		this.departure = departure;
		if (departure == arrival) {
			throw new IllegalArgumentException ("The airports are the same");
		}
		
	}

	@Override
	public int getCost() {

		double dis = Airport.getDistance(arrival, departure);
		double costperkm = 124/167.52;
		double fuelcost = dis*costperkm;
		
		double fees = (fuelcost + arrival.getFees() + departure.getFees() + 5375);
		int roundedfees = (int)fees;
		if ((double)roundedfees != fees) {
			roundedfees++;
		}
		return roundedfees;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		
		FlightReservation Fr = (FlightReservation)object;
		
		return ((this.arrival == Fr.arrival) && (this.departure == Fr.departure) && (this.getCost()== Fr.getCost()) && (this.reservationName() == Fr.reservationName()));
	}
	
	public static void main(String[] args) {
		Airport c = new Airport (100000,0,4);
		Airport d = new Airport (0,10,5);
		
		FlightReservation Fr = new FlightReservation("a",c ,d);
		FlightReservation nima = new FlightReservation("a",c, d);
		FlightReservation sile = new FlightReservation("b",c, d);
		
		System.out.println(Fr.equals(nima));
		System.out.println(Fr.equals(sile)); 
		System.out.println(Fr == nima);
		System.out.println(Fr == sile);
		System.out.println(Fr.getCost());
		
		   Airport a1 = new Airport(4, 30000, 20000);
		   Airport a2 = new Airport(7, 80000, 30000);
		   FlightReservation f1 = new FlightReservation("myFlight", a1, a2);
		   System.out.println(Airport.getDistance(a1, a2));
		   int cost = f1.getCost(); 
		   
		   System.out.println(cost);
		
	}

}
