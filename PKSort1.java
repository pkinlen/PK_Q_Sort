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
// -could it be made generic?
// -if the arr were put into it's own class, then it need not be passed as a parameter

// Author: Philip Kinlen
public class PKSort1 {
	
    public static void main(String[] args) {
         System.out.println("About to run a sorting algorithm by Philip Kinlen.");
         System.out.println("It is based on q-sort and aims to be memory efficient.");
         
         Random randGen = new Random(); // have an optional arg, which is the rand seed, 
                                        // default seed is the timer.
                  
         int numElms = 200;
         double[] arr = generateRandArr(numElms, randGen);
                        // = generateOrderedArr(numElms, true);
         System.out.println("Here's the array before sorting:");
         printArr(arr);
         
         pkSort(arr);
         
         System.out.println("After sorting it looks like this:");
         printArr(arr);
    }
    
    // The following sorting algorithm is based on q-sort.
    // It tries to minimise the amount of extra space required
    public static void pkSort(double[] arr){
       pkSortSub(arr, 0, arr.length - 1 );
    }
    //////////////////////////////////////////////////////////////////////////////
    private static void pkSortSub(double[] arr, int left, int right){    
       int available;
    
       if ( left >= right) // when there are 0 or 1 elements, there is nothing to be done.
          return;
       else { // the reason we use this 'else' here is because we want the 'double pivot'
              // to have been released before we recursively call this function.

	       int     mid             = ( right + left ) / 2;
	       double  pivot;  // we set the pivot to be the median of the left, mid and right elements.
	       
	       if ( (arr[right] > arr[left]) == (arr[left] > arr[mid])) {
	    	   pivot       = arr[left];
	       } else if ( (arr[left] > arr[mid]) == (arr[mid] > arr[right])) {		    	   
		       pivot       = arr[mid];
		       arr[mid]    = arr[left];
		   } else { 
	    	   pivot       = arr[right]; 
	    	   arr[right]  = arr[left];		       
           }
	       available = left;
	    	
	       boolean workingFromLeft = false;
	       int     leftIdx         = left;
	       int     rightIdx        = right + 1;

	       while ( leftIdx < rightIdx){
  	           int     current;
  	           
  	           boolean doSwitch;
	    	   
	           if (workingFromLeft){
	              leftIdx++;
	              current  = leftIdx;
	              doSwitch = (arr[current] > pivot);
	           } else {
	              rightIdx--;
	              current  = rightIdx;
	              doSwitch = (pivot > arr[current]);
	           }
	           
	           if( doSwitch){	        	   
	               workingFromLeft  = !workingFromLeft; // toggle
	               arr[available]   = arr[current];
	               available        = current;
	           }
	        }    
	        arr[available] = pivot;    	        
         }
         // now we have a recursive call, to sort the sub-arrays
         pkSortSub(arr, left, available - 1);
         pkSortSub(arr, available + 1, right);
    }
    ///////////////////////////////////////////////////////////////////////////
    private static double[] generateRandArr(int numElms, Random randGen){
    	double[] res = new double[numElms];
    	
    	for(int i = 0; i < numElms; i++)
    		res[i] = randGen.nextDouble();
    	
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////
    private static double[] generateOrderedArr(int numElms, boolean increasing){
    	double[] res = new double[numElms];
    	
    	for( int i = 0; i < numElms; i++)
    		res[i] = ( increasing ? i : numElms - i);
    	    	
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////    
    private static void printArr(double[] arr){
    	String str = (arr.length > 0 ? Double.toString(arr[0]) : "");
    	
    	for ( int i = 1; i < arr.length; i++){
    		str += ", " + Double.toString(arr[i]);
    	}
    	
    	System.out.println(str);
    }  
    ///////////////////////////////////////////////////////////////////////////
    private static int medianOfThree( double[] arr, int i1, int i2, int i3){
    	if ( (arr[i2] > arr[i1]) == (arr[i1] > arr[i3]))
    		return i1;
    	else if ( (arr[i1] > arr[i2]) == (arr[i2] > arr[i3]))
    		return i2;
    	else
    		return i3;
    }
}    

