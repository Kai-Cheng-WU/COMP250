

import java.io.PrintStream;
import java.util.Hashtable;

public class StressTestPut extends StressTest{
	//in-house hashtable
	private MyHashTable<String, Tweet> tweetTable;
	//hash table java
	private Hashtable<String, Tweet> javaTweetTable;
	//

	private Long refExecutionTime;
	private int multiplier;
	StressTestPut(Integer timeOut, int mult, PrintStream output, PrintStream err){
		super(timeOut, output, err);
		this.multiplier = mult;
	}
    /* 
     *  Provide implementation of this (tester) method  for each test. 
     */
	public void createHashTable(){
    /*Creates 2 hash tables using Java's inbuilt hash table, and
    inhouse hash table class using the data provided.
     */
		int bucketCount = (int)(data.size()/0.75) + 1;
		//bucketCount /= 4; //trigger rehash
		this.tweetTable = new MyHashTable<String,Tweet>(bucketCount);
		this.javaTweetTable = new Hashtable<String,Tweet>(bucketCount);
	}


	private void getReferenceExecutionTime()
	{
		try {
			Long refStartTime = System.currentTimeMillis();
			for (Tweet tweet : data) {
				String testKey = tweet.getAuthor();
				Tweet myTweet = javaTweetTable.put(testKey, tweet);
			}
			this.refExecutionTime = System.currentTimeMillis() - refStartTime;
			this.out.print("Approximate execution time (for reference) " + this.refExecutionTime+"ms");
		}catch (Exception e)
		{
			this.out.println("Failed to obtain a reference time.");
		}
	}


	Boolean tester(){
		try {
			getReferenceExecutionTime();
			Long startTime = System.currentTimeMillis();

			for (Tweet tweet : data) {
				String testKey = tweet.getAuthor();
				Tweet myTweet = tweetTable.put(testKey, tweet);
			}
			Long timeTaken = System.currentTimeMillis() - startTime;

			if (!Thread.interrupted()) {
				if (verbose) {
					if (tweetTable.size() == javaTweetTable.size()) {
						this.out.println(" | Execution time of solution code : " + timeTaken + "ms");

						if (timeTaken < this.multiplier * this.refExecutionTime) {
							this.out.println("[PASS] Code executed under acceptable time.");
							return true;
						} else
							this.out.println("[FAIL] Code is not optimized enough.");
					} else {
						this.out.println("[FAIL] The size of the tables created did not match!");
						return false;
					}
				}
			}
			return false;
		}catch(Exception e){
			this.err.println(e);
			return false;
		}
	}


}
