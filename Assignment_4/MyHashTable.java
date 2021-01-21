import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


public class MyHashTable<K,V> implements Iterable<HashPair<K,V>>{
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K,V>>> buckets; 
    
    // constructor
    public MyHashTable(int initialCapacity) {
    	// ADD YOUR CODE BELOW THIS
    	this.numBuckets = initialCapacity;

    	this.buckets = new ArrayList<LinkedList<HashPair<K,V>>>();
    	
    	for(int i=0; i<initialCapacity; i++) {
    		LinkedList<HashPair<K,V>> link = new LinkedList<HashPair<K,V>>();
    		buckets.add(link);
    	}
        
        this.numEntries = 0;
        //ADD YOUR CODE ABOVE THIS
    }
    
    public int size() {
        return this.numEntries;
    }
    
    public boolean isEmpty() {
        return this.numEntries == 0;
    }
    
    public int numBuckets() {
        return this.numBuckets;
    }
    
    /**
     * Returns the buckets variable. Useful for testing  purposes.
     */
    public ArrayList<LinkedList< HashPair<K,V> > > getBuckets(){
        return this.buckets;
    }
    
    /**
     * Given a key, return the bucket position for the key. 
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode())%this.numBuckets;
        return hashValue;
    }
    
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE   
    	

    	HashPair <K,V> mypair = new HashPair <K,V>(key, value);
    	int hash = hashFunction(key);
  
    	
    	for(int i=0; i<buckets.get(hash).size();i++) {
    		if (buckets.get(hash).get(i).getKey().equals(key)) {
    			V temp = buckets.get(hash).get(i).getValue();
    			buckets.get(hash).get(i).setValue(value);
    	    	double loadfac = (1.0*this.size())/numBuckets;
    	    	if (loadfac>MAX_LOAD_FACTOR) {
    	    		rehash();
    	    	}
    			return temp;
    		}
    	}

    	
    	buckets.get(hash).add(mypair);
    	numEntries++;
    	double loadfac = (1.0*this.size())/numBuckets;
    	if (loadfac>MAX_LOAD_FACTOR) {
    		rehash();
    	}
    	return null;
        
        //  ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Get the value corresponding to key. Expected average runtime O(1)
     */
    
    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
        
    	int hash = hashFunction(key);
    	for(int i=0; i<buckets.get(hash).size(); i++) {
    		if(key.equals(buckets.get(hash).get(i).getKey())) {
    			return buckets.get(hash).get(i).getValue();
    		}
    	}
    	return null;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Remove the HashPair corresponding to key . Expected average runtime O(1) 
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
        
    	int hash = hashFunction(key);
    	LinkedList<HashPair<K,V>> link = buckets.get(hash);
    	for (int i=0; i<link.size(); i++) {
    		if (link.get(i).getKey().equals(key)) {
    			V temp = link.get(i).getValue();
    			link.remove(link.get(i));
    			numEntries--;
    			return temp;
    		}
    	}
    	return null;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /** 
     * Method to double the size of the hashtable if load factor increases
     * beyond MAX_LOAD_FACTOR.
     * Made public for ease of testing.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    public void rehash() {
        //ADD YOUR CODE BELOW HERE
     	int oldsize = this.numBuckets;
    	int newsize = 2*oldsize;
    	this.numBuckets = newsize;   
    	
    	ArrayList<LinkedList<HashPair<K,V>>> newbuckets = new ArrayList<LinkedList<HashPair<K,V>>>();
    	
    	for(int i=0; i<newsize; i++) {
    		LinkedList <HashPair<K,V>> link = new LinkedList<HashPair<K,V>>();
    		newbuckets.add(link);    		
    	}
    	for(int i=0; i<oldsize; i++) {

    			for(int j=0; j<buckets.get(i).size();j++) {
    				HashPair<K, V> mypair = buckets.get(i).get(j);
    				newbuckets.get(hashFunction((K) mypair.getKey())).add(mypair);
    			}
    		
    	}
    	
    	this.buckets = newbuckets;
    	

        //ADD YOUR CODE ABOVE HERE
    }
    
    
    /**
     * Return a list of all the keys present in this hashtable.
     * Expected average runtime is O(m), where m is the number of buckets
     */
    
    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
    	ArrayList<K> mykeys = new ArrayList<K>();
        for (int i=0; i<buckets.size(); i++) {
        	for(int j=0; j<buckets.get(i).size(); j++) {
        		mykeys.add(buckets.get(i).get(j).getKey());
        	}	
        }
    	return mykeys;
    	
        //ADD YOUR CODE ABOVE HERE
    }
    
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(m) where m is the number of buckets
     */

    
    
	public ArrayList<V> values() {
        //ADD CODE BELOW HERE
    	ArrayList<V> myvalues = new ArrayList<V>();
    	

    	MyHashTable <K,V> mht = new MyHashTable <K,V>(this.numBuckets);
    	for(int i=0; i<buckets.size();i++) {
    		for(int j=0; j<buckets.get(i).size(); j++) {
    			mht.put((K)buckets.get(i).get(j).getValue(), buckets.get(i).get(j).getValue());
    		}
    	}
    	myvalues = (ArrayList<V>) mht.keys();
        
    	return myvalues;

    	
        //ADD CODE ABOVE HERE
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable<V>> ArrayList<K> slowSort (MyHashTable<K, V> results) {
        ArrayList<K> sortedResults = new ArrayList<>();
        for (HashPair<K, V> entry : results) {
			V element = entry.getValue();
			K toAdd = entry.getKey();
			int i = sortedResults.size() - 1;
			V toCompare = null;
        	while (i >= 0) {
        		toCompare = results.get(sortedResults.get(i));
        		if (element.compareTo(toCompare) <= 0 )
        			break;
        		i--;
        	}
        	sortedResults.add(i+1, toAdd);
        }
        return sortedResults;
    }
    
    
	/**
	 * This method takes as input an object of type MyHashTable with values that 
	 * are Comparable. It returns an ArrayList containing all the keys from the map, 
	 * ordered in descending order based on the values they mapped to.
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    
    private static <K, V extends Comparable<V>> ArrayList<K> merge (ArrayList<K> l1, ArrayList<K> l2, MyHashTable<K,V> results){
		ArrayList <K> list = new ArrayList<K>();

    	while (!l1.isEmpty() && !l2.isEmpty()) {
    		
			V comp1 = (V)results.get(l1.get(0));
			V comp2 = (V)results.get(l2.get(0));

		
			
			if(comp1.compareTo(comp2)<0) {
				list.add(l2.remove(0));
			}
			else {
				list.add(l1.remove(0));
			}
    	}
    	
			
		while(!l1.isEmpty()) {
			list.add(l1.remove(0));
		}
		while(!l2.isEmpty()) {
			list.add(l2.remove(0));
		}
				
		
    	return list; 
    }

    
    private static <K, V extends Comparable<V>> ArrayList<K> mergesort (ArrayList<K> list, MyHashTable<K, V> results){
    	
    	if(list.size()==1) {
    		return list;
    	}
    	
    	else {
    		int mid = (list.size()-1)/2;
    		ArrayList<K> l1 = new ArrayList<K>();
    		ArrayList<K> l2 = new ArrayList<K>();
        	for(int i=0; i<mid+1; i++) {
        		if(list.get(i)!=null)
        		l1.add(list.get(i));
        	}
        	for(int i=mid+1; i<list.size(); i++) {
        		if(list.get(i)!=null)
        		l2.add(list.get(i));

        	}
        	
    		l1 = mergesort(l1, results);
    		l2 = mergesort(l2, results);
    		return merge(l1,l2,results);
    	}
    }

    
    public static <K, V extends Comparable<V>> ArrayList<K> fastSort(MyHashTable<K, V> results) {
        //ADD CODE BELOW HERE
    	ArrayList<K> mykeys = results.keys();
    	return mergesort(mykeys,results);
    	
        //ADD CODE ABOVE HERE
    }

    
    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }   
    
    private class MyHashIterator implements Iterator<HashPair<K,V>> {
        //ADD YOUR CODE BELOW HERE
    	public ArrayList<HashPair<K,V>> myentries = new ArrayList<HashPair<K,V>>();
    	private int cur=0;
        //ADD YOUR CODE ABOVE HERE
    	
    	/**
    	 * Expected average runtime is O(m) where m is the number of buckets
    	 */
        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
        	for(int i=0; i<buckets.size(); i++) {
        		LinkedList<HashPair<K,V>> mylist = buckets.get(i);
        		if(mylist.isEmpty()== false) {
        			Iterator<HashPair<K,V>> pairiter = mylist.iterator();
        			while (pairiter.hasNext()) {
        				myentries.add(pairiter.next());
        			}
        		}
        	}
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
        	
        	return (cur < myentries.size());
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
        @Override
        /**
         * Expected average runtime is O(1)
         */
        public HashPair<K,V> next() {
            //ADD YOUR CODE BELOW HERE
        	
        	HashPair<K,V> temp;
        	temp = myentries.get(cur);
        	cur++;
        	return temp;
        	
            //ADD YOUR CODE ABOVE HERE
        }
        
    }
}
