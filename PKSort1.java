import java.util.Random;

// In the following class we have a sorting algorithm which was based on the q-sort algorithm.
// It is rather memory efficient in that when sorting say doubles,
// it only requires one extra temp double to be stored.

// For a bit of back-ground reading: https://en.wikipedia.org/wiki/Quicksort
// In this implementation we start by setting the start element to be the pivot.
// We make a copy of that element and then start at the end of the array and
// come back through the array until we find an element that is less than the pivot. 
// We then put that element into the place previously occupied by the pivot.
// Now we have an available element near the end where we can write to,
// so we then continue at the element just after the original pivot until we find an element 
// greater than the pivot, we then copy that into the available space near the end.
// we then continue from near the end

// Possible extensions:
// -allow sorting to be both ascending and descending
// -make a better choice of the pivot, look into efficiency when the array is already sorted
// -could it be made generic?
// -if the arr were put into it's own class, then it need not be passes as a parameter

// Author: Philip Kinlen
public class PKSort1 {

    public static void main(String[] args) {
         System.out.println("About to run sorter by Philip Kinlen.");
         System.out.println("It is based on q-sort and aims to be memory efficient.");
         
         Random randGen = new Random(); // have an optional arg, which is the rand seed, 
                                        // default seed is the timer.
                  
         int numElms = 7;
         double[] arr = generateRandArr(numElms, randGen);
         System.out.println("Here's the array before sorting:");
         printArr(arr);
         
         pkSort(arr);
         
         System.out.println("After sorting it looks like this:");
         printArr(arr);
    }
    
    // The following sorting algorithm is based on q-sort.
    // It tries to minimise the amount of extra space required
    public static void pkSort(double[] arr){
    	pkSortSub(arr, 0, arr.length);
    }

    private static void pkSortSub(double[] arr, int start, int end){
    
        // System.out.println("calling sub with start: " + Integer.toString(start) 
        //		            + " and end: " + Integer.toString(end));

    	int available = start;
    	
    	if ( start >= end)
    		return;    	
    	else { // the reason we use this 'else' here is because we want the 'double pivot' 
    		   // to have been released before we recursively call this function.

    		int     numElms          = end - start;
    		double  pivot            = arr[start];    		
    		boolean workingFromStart = false; // initially we'll work back from the end
    		int     leftIdx          = start;
    		int     rightIdx         = end;
    		int     current;
    		
    		for ( int i = 1; i < numElms; i++){
              
    			if (workingFromStart){
    				leftIdx++;
    				current = leftIdx;
    			} else {
    				rightIdx--;
    				current = rightIdx;
    			}
    			
    			// for an ascending sort we use a '>' in the following line of code
    			// ( a descending sort would use '<' )
    			if ( workingFromStart == (arr[current] > pivot)) { // we need to do a switch
    				
    			     workingFromStart = !workingFromStart; // toggle
    				 arr[available]   = arr[current];
    				 available        = current;
    			}
    		}
    		
    		arr[available] = pivot;
    		
    	}
		// now we have a recursive call, to sort the sub-arrays
        pkSortSub(arr, start, available);
        pkSortSub(arr, available + 1, end);   		
    }
    
    
    private static double[] generateRandArr(int numElms, Random randGen){
    	double[] res = new double[numElms];
    	for(int i = 0; i < numElms; i++){
    		res[i] = randGen.nextDouble();
    	}
    	return res;
    }
    
    private static void printArr(double[] arr){
    	String str = (arr.length > 0 ? Double.toString(arr[0]) : "");
    	
    	for ( int i = 1; i < arr.length; i++){
    		str += ", " + Double.toString(arr[i]);
    	}
    	
    	System.out.println(str);
    }    
}    

