// In the following class we have a sorting algorithm which was based on the q-sort algorithm.
// It is rather memory efficient in that when sorting say Doubles,
// it only requires one extra temp Double to be stored, which is the pivot

// For a bit of back-ground reading: https://en.wikipedia.org/wiki/Quicksort
// In this implementation we start by setting the start element to be the pivot.
// We make a copy of that element and then start at the end of the array and
// come back through the array until we find an element that is less than the pivot. 
// We then put that element into the place previously occupied by the pivot.
// Now we have an available element near the end where we can write to,
// so we then continue at the element just after the original pivot until we find an element 
// greater than the pivot, we then copy that into the available space near the end.
// we then continue from near the end

// In the standard QSort algorithm, all the elements that are less than the pivot will be
// swapped, and each swap involves 3 assignments.
// In this algorithm, the number of assignments of the object being sorted will be 3 times less. 
// And on each pass, the first element and pivot may be moved twice, 
// all the others will be moved a maximum of once.
// However there is some extra overhead.

// Author: Philip Kinlen, (June 2014)
public class QSort<T extends Comparable<T>> {
	private T[]      m_arr;
	
	// The reason we have the following variables here 
	// rather than as local variables inside the sortSub(.) method is because 
	// we don't want to keep on having to reallocate them.
	// the recursive call has been set-up so that the different calls won't interfere with 
	// each-other. It is also thread safe.
	private T        m_pivot;
    private int      m_initialPivotIdx;
	private int      m_curLow;
	private int      m_curHigh;
	private boolean  m_workingFromLow;
	    
    // The following sorting algorithm is based on q-sort.
    // It tries to minimise the amount of runtime memory usage
	// The elements of arr will be rearranged.
    public  static <S extends Comparable<S>> void sort(S[] arr, boolean ascending){
    	
    	if ( arr == null)
    		return; // possibly could give a warning.
    	
        Ordering ordering = getOrdering(arr);
        
        if (ordering == Ordering.MIXED) {
            QSort<S> sorter = new QSort<S>(arr);                	
        	sorter.sortSub(0, arr.length-1);       // The main sorting is done here.
        	
        	if ( ! ascending)
        		reverseArr(arr);
        	
        }  else if (  (!ascending && ordering == Ordering.NON_STRICT_ASCENDING )
        		    ||( ascending && ordering == Ordering.NON_STRICT_DESCENDING) )   
        	reverseArr(arr);
        
        // else nothing more to do
        	
    }
    //////////////////////////////////////////////////////////////////////////////
    private static  <S extends Comparable<S>> Ordering getOrdering(S[] arr){
    	int equalCounter = 0;
    	int ascCounter   = 0;
    	int descCounter  = 0;   	
    	int i            = 1;
    	    	
    	while (  (i < arr.length)  &&  (ascCounter * descCounter == 0) ){
    		int cmp = arr[i-1].compareTo(arr[i]);
    		
    		if( cmp == 0)
    			equalCounter++;
    		else if( cmp < 0)
    		    ascCounter++;
    		else 
    			descCounter++;
    		
    		i++;
    	}

		if ( equalCounter == (arr.length-1))
			return Ordering.ALL_EQUAL;
		else if ( ascCounter == 0)
			return Ordering.NON_STRICT_DESCENDING;
		else if ( descCounter == 0)
			return Ordering.NON_STRICT_ASCENDING;
		else 
			return Ordering.MIXED;
    
    }
    //////////////////////////////////////////////////////////////////////////////
    // will sort m_arr's elements between low and high inclusive.
    private void sortSub(int low, int high){    
       int available; // at run-time we can have up to Order(log(N)) instances of this int
                      // on the stack concurrently.
    	
       if ( high <=  low ) // when there are 0 or 1 elements, there is nothing to be done.
          return;
       else { // we use this else branch to ensure that as many temporary variables as possible
    	      // are removed from the stack before the recursive call sortSub(..) below.

		   available         = low;	   
	       m_workingFromLow  = false;
	       m_curLow          = low;   // The current low  index. 
	       m_curHigh         = high;  // The current high index.
    	       	   
    	   m_initialPivotIdx = choosePivotIdx();    	   
		   m_pivot           = m_arr[m_initialPivotIdx];
		   
		   if ( m_initialPivotIdx != low)
			    m_arr[m_initialPivotIdx] = m_arr[low];
		   	    	
	       while ( m_curLow   < m_curHigh){

	           if (m_workingFromLow && m_arr[++m_curLow].compareTo(m_pivot) > 0){
		           m_arr[available]  = m_arr[m_curLow];
		           available         = m_curLow;
		           m_workingFromLow  = false;
	
	           } else if (!m_workingFromLow && m_pivot.compareTo(m_arr[m_curHigh--]) > 0){
		           m_arr[available]  = m_arr[m_curHigh + 1];
		           available         = m_curHigh + 1;
		           m_workingFromLow  = true;   		           
	           }
	        }  // end of while  
	        m_arr[available] = m_pivot;  // the pivot is now in the correct position. 
       } 
       // now we have a recursive call, to sort the sub-arrays
       sortSub(low,           available -1);  // sorting the elms from low to (available-1)
       sortSub(available + 1, high);          // sorting the elements from (available+1) to (high)
    }
    ///////////////////////////////////////////////////////////////////////////
    private int choosePivotIdx(){
    	
    	if ( m_curHigh - m_curLow < 5) // for very small sub arrays we just use m_curLow as the pivot index.
    		return m_curLow;
    	
    	int mid = (m_curLow + m_curHigh ) / 2;
    	
    	if ( m_arr[mid].compareTo(m_arr[m_curLow]) * m_arr[m_curLow].compareTo(m_arr[m_curHigh]) >= 0)
    		return m_curLow;
    	else if ( m_arr[m_curLow].compareTo(m_arr[mid]) * m_arr[mid].compareTo(m_arr[m_curHigh]) >= 0)
    		return mid;
    	else
    		return m_curHigh;
    }
    ///////////////////////////////////////////////////////////////////////////
    private static <S> void reverseArr(S[] arr){
    	
    	int halfN     = arr.length / 2;
    	int NminusOne = arr.length - 1;
    	
    	for( int i = 0; i < halfN; i++ ) {
    		
    		S   temp  = arr[i];
    		int j     = NminusOne - i;
    		
    		arr[i]    = arr[j];
    		arr[j]    = temp;
    	}    		
    }
    /////////////////////////////////////////////////////////////////////////////
    // We have a constructor which is private! 
    // It is only called from within the static sort(..) method.
    private QSort(T[] arr){
    	m_arr = arr;
    }
    /////////////////////////////////////////////////////////////////////////////
    private enum Ordering {
    	ALL_EQUAL,
    	NON_STRICT_ASCENDING,
    	NON_STRICT_DESCENDING,
    	MIXED   	
    }
}    

