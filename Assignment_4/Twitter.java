
import java.util.ArrayList;

public class Twitter {
	
	//ADD YOUR CODE BELOW HERE
	private ArrayList<String> stopWords;
	private ArrayList<Tweet> tweets;
	private ArrayList<String> allWords;
	private MyHashTable<String,Tweet> lastWhisper;
	private MyHashTable<String,ArrayList<Tweet>> tweetAtTime;
	private MyHashTable<String,Integer> Trends;
	
	
	
	//ADD CODE ABOVE HERE 
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {
		//ADD YOUR CODE BELOW HERE
		this.stopWords = stopWords;
		this.tweets = tweets;
		allWords = new ArrayList<String>();
		
		
		int initialCapacity = (int) (tweets.size()/0.75);
		tweetAtTime = new MyHashTable<String,ArrayList<Tweet>>(initialCapacity);
		lastWhisper = new MyHashTable<String,Tweet>(initialCapacity);
		
		
		for(int i=0; i<tweets.size(); i++) {
			addTweet(tweets.get(i));
		}
		
		for(int i=0; i<tweets.size(); i++) {
			
			ArrayList<String> temp = new ArrayList<String>();
			for(int j=0; j<getWords(tweets.get(i).getMessage().toLowerCase()).size(); j++) {
				ArrayList<String> tempstr = getWords(tweets.get(i).getMessage().toLowerCase());
				
				for(int k=0; k<tempstr.size(); k++) {
					if (!temp.contains(tempstr.get(k)))
							temp.add(tempstr.get(k));
				}
					
			}
			allWords.addAll(temp);
		}
		
		ArrayList<String> processedStopWords = new ArrayList<String>();
		
		for(int i=0; i<stopWords.size(); i++) {
			processedStopWords.add(stopWords.get(i).toLowerCase());
		}
		
		allWords.removeAll(processedStopWords);
		
		
		
		//ADD CODE ABOVE HERE 
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {
		//ADD CODE BELOW HERE
		addTweetAtTime (t);
		addTweetAuthorLatest (t);
		//ADD CODE ABOVE HERE 
	}
	
	private void addTweetAtTime (Tweet t) {
		
		//adding to tweetAtTime
		if(tweetAtTime.get(t.getDateAndTime().substring(0, 10))==null) {
			ArrayList<Tweet> list = new ArrayList<Tweet>();
			list.add(t);
			tweetAtTime.put(t.getDateAndTime().substring(0, 10), list);
		}
		else {
			tweetAtTime.get(t.getDateAndTime().substring(0, 10)).add(t);
		}		
		
		
	}
	
	private void addTweetAuthorLatest (Tweet t) {
		//adding to lastWhisper
		if(lastWhisper.get(t.getAuthor())==null) {
			lastWhisper.put(t.getAuthor(), t);
		}
		
		else {
			String tempAuthor = t.getAuthor();
			String temptime = lastWhisper.get(tempAuthor).getDateAndTime();
			if(t.getDateAndTime().compareTo(temptime)>=0) {
				lastWhisper.put(t.getAuthor(), t);
			}
		}
		
	}

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
        //ADD CODE BELOW HERE

    	return lastWhisper.get(author);
    	
        //ADD CODE ABOVE HERE 
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
        //ADD CODE BELOW HERE
    	
    	return tweetAtTime.get(date);
    	
        //ADD CODE ABOVE HERE
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
        //ADD CODE BELOW HERE
    	int initialCapacity = (int) (tweets.size()/0.75);
    	MyHashTable<String,Integer> topics = new MyHashTable<String,Integer>(initialCapacity);
    	
    	for(int i=0; i<allWords.size(); i++) {
    		if (topics.get(allWords.get(i))==null) {
    			topics.put(allWords.get(i), 1);
    		}
    		else {
    			int j = topics.get(allWords.get(i));
    			topics.put(allWords.get(i), j+1);
    		}
    	}
    	
    	
    	return MyHashTable.fastSort(topics);
    	
        //ADD CODE ABOVE HERE    	
    }
    
    
    
    /**
     * An helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w);
    	}
    	return wordsList;
    }

    

}
