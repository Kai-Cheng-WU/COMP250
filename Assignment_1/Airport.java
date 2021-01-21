
public class Airport {
	private int x;
	private int y;
	private int fees;

	public Airport(int x, int y, int fees) {
		this.x = x;
		this.y = y;
		this.fees = fees;
		
	}
	
	public int getFees() {
		return fees;
	}
	
	public static int getDistance(Airport a1, Airport a2) {
		double distance;
		distance =  Math.sqrt(Math.pow((a1.x - a2.x),2) + Math.pow((a1.y - a2.y),2)) ;
		int RoundedDistance = (int)distance;
		if ((double)RoundedDistance != distance) {
			RoundedDistance ++;
		}
		return RoundedDistance;
	}
	
	
	/////////////////////Tester below     >:(
	public static void main(String[] args)
    {
        Airport air1 = new Airport(-900,0,30);
        Airport air2 = new Airport (10,10,6);
        System.out.println(air1.getFees());
        System.out.println(Airport.getDistance(air1, air2));
        
		   Airport a1 = new Airport(4, 30000, 20000);
		   Airport a2 = new Airport(7, 80000, 30000);
		   FlightReservation f1 = new FlightReservation("myFlight", a1, a2);
		   System.out.println(a1.x-a2.x);
		   System.out.println(a1.y-a2.y);
		   System.out.println((a1.x-a2.x)*(a1.x-a2.x));
		   System.out.println((a1.y-a2.y)*(a1.y-a2.y));
		   System.out.println(50000*50000);
		   System.out.println(Airport.getDistance(a1, a2));
        
    }
}
