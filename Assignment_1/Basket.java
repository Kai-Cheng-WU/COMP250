
public class Basket {
	
	private Reservation[] Reservations;
	
	public Basket(){
		Reservations = new Reservation[0];
	}

	public Reservation[] getProducts() {
		Reservation[] ReservationsCopy = Reservations;
		return ReservationsCopy;
		
	}
	
	public int add(Reservation newReserv) {
		int size = Reservations.length;
		Reservation[] Copy = new Reservation [size];
		for(int i=0; i<size; i++) {
			Copy[i] = Reservations[i];		
		}
		Reservations = new Reservation[size+1];
		for (int i=0; i<size; i++) {
			Reservations[i] = Copy [i];
		}
		Reservations[Reservations.length -1] = newReserv;
		return Reservations.length;
		
	}
	
	public boolean remove (Reservation toberemoved) {
		for(int i=0; i<Reservations.length; i++) {
			if (toberemoved.equals(Reservations[i])){
				Reservations[i]=null;
				for(int j=i+1; j<Reservations.length; j++) {
					Reservation temp = Reservations[j];
					Reservations[j-1]=temp;
				}
				int size = Reservations.length;
				Reservation[] Copy = new Reservation[size-1];
				for(int k=0; k<size-1; k++) {
					Copy[k] = Reservations[k];		
				}
				Reservations = new Reservation[size-1];
				for(int k=0; k<size-1; k++) {
					Reservations[k] = Copy[k];		
				}
				return true;
				
			}
		}
		return false;
	}
	
	
	public void clear() {
		Reservations = new Reservation[0];
	}
	
	
	public int getNumOfReservations() {
		int counter=0;
		for (int i=0; i<Reservations.length; i++) {
			if (Reservations[i]!= null) {
				counter++;
			}
		}
		return counter;		
	}
	
	public int getTotalCost() {
		int total=0;
		for (int i=0; i<Reservations.length; i++) {
			if (Reservations[i]!= null) {
				total += Reservations[i].getCost();
			}
		}
		return total;
	}
	
	
	/////////////////////Tester below     >:(
	public static void main(String[] args)
    {
        
    }
	
}
