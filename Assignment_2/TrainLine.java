import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	public int getSize() {
		TrainStation duh = leftTerminus;
		int count = 1;
		while (duh.getRight() != null) {
			duh = duh.getRight();
			count++;
		}
		// YOUR CODE GOES HERE
		return count; // change this!
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {
		
		if (current.getLine().equals(this) != true) {
			throw new StationNotFoundException("the station is not found on this line");
		}
		
		else if (previous!=null && current.hasConnection && current.getTransferStation().getName() != previous.getName()) {
			return current.getTransferStation();
		}
		
		else if (previous == null && current.hasConnection) {
			return current.getTransferStation();
		}
		
		else {
			return getNext(current);
		}

	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation getNext(TrainStation station) {
		if (this.goingRight == true && station.equals(rightTerminus)==false && station.getLine().equals(this)){
			return station.getRight();	
		}
		
		else if (this.goingRight == false && station.equals(leftTerminus)==false && station.getLine().equals(this)) {
			return station.getLeft();
		}
		
		else if (this.goingRight == true && station.equals(rightTerminus) && station.getLine().equals(this)){
			this.reverseDirection();
			return station.getLeft();	
		}

		else if (this.goingRight == false && station.equals(leftTerminus) && station.getLine().equals(this)){
			this.reverseDirection();
			return station.getRight();	
		}

		
		else {
			throw new StationNotFoundException("Station not found");
		}
		
	}

	// You can modify the header to this method to handle an exception. You cannot make any other change to the header.
	public TrainStation findStation(String name) {
		TrainStation duh = leftTerminus;
		while (true) {
			if (duh.getName() == name) {
				return duh;
			}
			duh = duh.getRight();
			if (duh == rightTerminus && duh.getName()!= name) {
				throw new StationNotFoundException("Station not found");	
			}
		}		
	}
	
	public void swap(TrainStation tl, TrainStation tr) {
		TrainStation tmp= new TrainStation("temp");
		tmp.setLeft(tl.getLeft());
		tmp.setRight(tl.getRight());
		if (tl.getLeft()!=null)
		tl.getLeft().setRight(tr);
		tl.setLeft(tr);
		tl.setRight(tr.getRight());
		tr.setLeft(tmp.getLeft());
		if (tr.getRight()!=null)
		tr.getRight().setLeft(tl);
		tr.setRight(tl);
		
	}

	public void sortLine() {
		int ct=0;
		for (int i = 0; i < lineMap.length; i++) {
			lineMap[i].setNonTerminal();
		}
			
		while(this.getSize()-ct>0) {

			TrainStation mystation = this.getLeftTerminus();
			TrainStation potentialLeftT = mystation.getRight();
			
			
			while (mystation.getRight()!= null) {
				if (mystation.getName().compareTo(mystation.getRight().getName())>0){
					swap(mystation,mystation.getRight());
					this.leftTerminus = potentialLeftT;

				}
				else {
					mystation = mystation.getRight();
				}
				
			}
			
			ct++;
			
			if (mystation.getRight()==null) {
				this.rightTerminus = mystation;

			}
								
		}
		this.leftTerminus.setLeft(null);
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRight(null);
		this.rightTerminus.setRightTerminal();
	
		this.lineMap = this.getLineArray();
	}

	public TrainStation[] getLineArray() {
		TrainStation[] LineArray = new TrainStation[this.getSize()];
		TrainStation currentstation = this.getLeftTerminus();
		LineArray[this.getSize()-1] = this.getRightTerminus();
		
		for(int i=0; i<this.getSize();i++) {
			LineArray[i] = currentstation;
			currentstation = currentstation.getRight();
		}
		return LineArray;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);
	
		for (int i = 0; i < shuffledArray.length; i++) {
			shuffledArray[i].setNonTerminal();
		}
		
		this.leftTerminus = shuffledArray[0];
		this.leftTerminus.setLeftTerminal();
		this.leftTerminus.setLeft(null);
		this.rightTerminus = shuffledArray[shuffledArray.length-1];
		this.rightTerminus.setRightTerminal();
		this.rightTerminus.setRight(null);
		
		TrainStation mystation = this.getLeftTerminus();
		for (int i=1; i<shuffledArray.length;i++) {
			mystation.setRight(shuffledArray[i]);
			if(mystation.getRight()!=null) {
			mystation.getRight().setLeft(mystation);
			mystation = mystation.getRight();
			}
			
			
		}

		// YOUR CODE GOES HERE

	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
	
	
	public static void main(String[] args) {
		TrainStation caonima = new TrainStation("gg");
		System.out.println(caonima.getLeft());
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
	
}
