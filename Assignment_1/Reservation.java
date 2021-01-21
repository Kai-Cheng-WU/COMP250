
public abstract class Reservation {
	
	private String name;
	
	public Reservation(String name) {
		this.name = name;
	}

	public final String reservationName(){
		return name;
	}
	
	public abstract int getCost();
	
	public abstract boolean equals(Object object);
	
}
