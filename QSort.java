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

// Author: Philip Kinlen
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
    	
        QSort<S> sorter = new QSort<S>(arr);
        sorter.sortSub(0, arr.length);
                
        if ( !ascending)
        	reverseArr( arr);        
    }
    //////////////////////////////////////////////////////////////////////////////
    // will sort m_arr's elements between low inclusive and high exclusive.
    private void sortSub(int low, int high){    
       int available; // at run-time we can have up to Order(log(N)) instances of this int
                      // on the stack concurrently.
    	
       if ( high <= ( low + 1)) // when there are 0 or 1 elements, there is nothing to be done.
          return;
       else { // we use this else branch to ensure that as many temporary variables as possible
    	      // are removed from the stack before the recursive call sortSub(..) below.
		   m_initialPivotIdx = choosePivotIdx( low, (low + high)/2, high-1 );    	   
		   m_pivot           = m_arr[m_initialPivotIdx];
		   
		   if ( m_initialPivotIdx != low)
			    m_arr[m_initialPivotIdx] = m_arr[low];
		   	    	
		   available         = low;	   
	       m_workingFromLow  = false;
	       m_curLow          = low;
	       m_curHigh         = high;
	
	       while ( m_curLow  < m_curHigh){
	    	   
	           if (m_workingFromLow && greaterThan(m_arr[++m_curLow], m_pivot)){
		           m_arr[available]  = m_arr[m_curLow];
		           available         = m_curLow;
		           m_workingFromLow  = false;
	
	           } else if ((!m_workingFromLow) && greaterThan(m_pivot, m_arr[--m_curHigh])){
		           m_arr[available]  = m_arr[m_curHigh];
		           available         = m_curHigh;
		           m_workingFromLow  = true;   		           
	           }
	        }  // end of while  
	        m_arr[available] = m_pivot;  // the pivot is now in the correct position. 
       } 
       // now we have a recursive call, to sort the sub-arrays
       sortSub(low,           available);  // we now sort elms from low to (available-1)
       sortSub(available + 1, high);       // we sort elements from (available+1) to (high-1)
    }
    ///////////////////////////////////////////////////////////////////////////
    private int choosePivotIdx( int i1, int i2, int i3){
    	if ( m_arr[i2].compareTo(m_arr[i1]) * m_arr[i1].compareTo(m_arr[i3]) >= 0)
    		return i1;
    	else if ( m_arr[i1].compareTo(m_arr[i2]) * m_arr[i2].compareTo(m_arr[i3]) >= 0)
    		return i2;
    	else
    		return i3;
    }
    //////////////////////////////////////////////////////////////////////////////////
    private boolean  greaterThan(T a, T b){
    	return ( a.compareTo(b) > 0);
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
}    

