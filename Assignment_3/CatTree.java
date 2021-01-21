import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException; 


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {
        	if (this.data.monthHired < c.data.monthHired) {
        		if (this.junior == null) {
        			this.junior = c;
        		}
        		else {
        			this.junior.addCat(c);
        		}
        	}
        	
        	
        	else if (this.data.monthHired > c.data.monthHired) {
        		if (this.senior == null) {
        			this.senior = c;
        		}
        		else {
        			this.senior.addCat(c);
        		}
        	}
        	
        	
        	else {
        		if (this.same == null){
        			if (this.data.furThickness>=c.data.furThickness) {
        				this.same = c;
        			}
        			else if (this.data.furThickness<c.data.furThickness) {
        				CatInfo temp = c.data;
        				c.data = this.data;
        				this.data = temp;
        				this.same = c;
        			}
        		}
        		
        		else {
        			if (this.data.furThickness>=c.data.furThickness) {
        				this.same.addCat(c);
        			}
        			
        			else {     //need to be fixed later, someshit wrong here :(
        				CatInfo temp1 = c.data;
        				CatInfo temp2 = this.data;
        				
        				CatNode mycat = new CatNode(this.same.data);
        				
        				this.data = temp1;
        				this.same.data = temp2;
        				
        				this.same.addCat(mycat);
      
        				
        				
        			}
        		}
        		
        	}
        	
            // ADD YOUR CODE HERE
            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public CatNode removeCat(CatInfo c) {
        	if (this.data == c) {
        		if (this.same!= null) {
        			if (this == root) {
        				root = this.same;
        			}
        			this.data = this.same.data;
        		
        			if (this.same.same != null) {
        				this.same.removeCat(this.same.data);
        			}
        			else if (this.same.same == null) {
        				this.same = null;
        			}
        		}
        		
        		else if (this.senior != null){
        			if (this == root) {
        				root = this.senior;
        			}
        			this.data = this.senior.data;
        			
        			if (this.senior.same != null)
        			this.same = this.senior.same;
        			
        			if (this.junior != null)
        			this.junior = this.senior.addCat(this.junior);
        			
        			if (this.senior.senior != null){
        				this.senior = this.senior.senior;
        			}
        			else {
        				this.senior = null;
        			}
        				
        		
        		}
        		
        		else if (this.junior != null){
        			if (this == root) {
        				root = this.junior;
        			}
        			
        			this.data = this.junior.data;
        			
        			if (this.junior.same != null)
        			this.same = this.junior.same;
        			
         			if (this.junior.senior != null)
        			this.senior = this.junior.senior;       			
        			
        			if (this.junior.junior != null) {
        			    this.junior = this.junior.junior;	
        			}
        			else {
        				this.junior = null;  
        			}
        		}
        		
        		else {
        			return null;
        		}
        		
        	}
        	
        	else if ((this.data.monthHired == c.monthHired) && (this.same != null)) {
        		this.same.removeCat(c);
        	}
        	
        	else if ((this.data.monthHired < c.monthHired) && (this.junior != null)) {
        		this.junior.removeCat(c);
        	}
        	
        	else if ((this.data.monthHired >c.monthHired) && (this.senior != null)){
        		this.senior.removeCat(c);
        	}
            // ADD YOUR CODE HERE
            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int mostSenior() {
        	if (this.senior == null) {
        		return this.data.monthHired;
        	}
            // ADD YOUR CODE HERE
            return this.senior.mostSenior(); //CHANGE THIS
        }
        
        
        
        public int fluffiest() {
        	ArrayList<Integer> lengths = new ArrayList<Integer>();
        	lengths.add(this.data.furThickness);
        	
        	if(this.junior != null) {
        		lengths.add(this.junior.data.furThickness);
        		this.junior.fluffiest();
        	}
        	
        	if(this.senior != null) {
        		lengths.add(this.senior.data.furThickness);
        		this.senior.fluffiest();
        	}
        	
        	if (this.same != null) {
        		lengths.add(this.same.data.furThickness);
        		this.same.fluffiest();
        	}
        	
        	int max=0;
        	for (int i =0; i< lengths.size();i++) {
        		if (lengths.get(i)>max) {
        			max = lengths.get(i);
        		}
        	}
            // ADD YOUR CODE HERE
        	System.out.println(lengths);
        	System.out.println(max);
            return max; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        

        public int hiredFromMonths(int monthMin, int monthMax) {
        	int count = 0;

        	
        	if (monthMin > monthMax) {
        		return 0;
        	}
        	
        	if ((this.data.monthHired >= monthMin)&&(this.data.monthHired <= monthMax)) {
            	count +=1;
            }
        	if ((this.same!= null)) {
        		count += this.same.hiredFromMonths(monthMin, monthMax);
        	}
        	if ((this.data.monthHired<monthMax)&&(this.junior!= null)) {
        		count += this.junior.hiredFromMonths(monthMin, monthMax);
        	}   	
        	if ((this.data.monthHired>monthMin)&&(this.senior!= null)) {
        		count += this.senior.hiredFromMonths(monthMin, monthMax);
        	}
        	
        	
        	// ADD YOUR CODE HERE
            return count; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
            
        }
        
        public CatInfo fluffiestFromMonth(int month) {
        	if (this.data.monthHired == month) {
        		return this.data;
        	}
        	else if (this.data.monthHired < month) {
        		return this.junior.fluffiestFromMonth(month);
        	}
        	else if (this.data.monthHired > month){
        		return this.senior.fluffiestFromMonth(month);
        	}
            // ADD YOUR CODE HERE
            return null; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public int[] costPlanning(int nbMonths) {
        	CatNode myCat = new CatNode (this.data);
        	CatTree myTree = new CatTree(myCat);
        	myCat.junior = this.junior;
        	myCat.senior = this.senior;
        	myCat.same = this.same;
        	myTree.root = myCat;
        	
        	int[]cost = new int[nbMonths];
        	CatTreeIterator iter = new CatTreeIterator();
        	iter = (CatTreeIterator) myTree.iterator();
        	
        	while (iter.hasNext()) {
        		CatInfo nxtct = iter.next();
        		if(nxtct.nextGroomingAppointment-243<nbMonths)
        		cost[nxtct.nextGroomingAppointment-243] += nxtct.expectedGroomingCost;
        	}
            // ADD YOUR CODE HERE
            return cost; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
        ArrayList<CatInfo> treeinfo = new ArrayList<CatInfo>();
        private int curCat = 0;

    	
        public CatTreeIterator() {
        	this.TreeCreator(root);


            //YOUR CODE GOES HERE
        }
        
        public void TreeCreator (CatNode c) {
        	if (c.senior!= null) {
        		TreeCreator(c.senior);
        	}
        	if (c.same!= null) {
        		TreeCreator(c.same);
        	}
        	
        	treeinfo.add(c.data);
        	
        	if (c.junior!= null) {
        		TreeCreator(c.junior);
        	}
        }
        
        public CatInfo next(){
        	CatInfo temp;
        	temp = treeinfo.get(curCat);
        	curCat++;
            //YOUR CODE GOES HERE
            return temp; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public boolean hasNext() {
            //YOUR CODE GOES HERE
            return (curCat < treeinfo.size()); // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
    }
    
}

