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

// Author: Philip Kinlen.
public class QSortDouble {
	private double[] m_arr;
	private double   m_pivot;
    private int      m_initialPivotIdx;
	private int      m_lowIdx;
	private int      m_highIdx;
	private boolean  m_workingFromLow;
	    
    // The following sorting algorithm is based on q-sort.
    // It tries to minimise the amount of runtime memory usage
	// The elements of arr will be rearranged.
    public static void sort(double[] arr, boolean ascending){
    	
    	if ( arr == null)
    		return; // possibly could give a warning.
    	
        QSortDouble sorter = new QSortDouble(arr);
        sorter.sortSub(0, arr.length);
                
        if ( !ascending)
        	reverseArr( arr);        
    }
    ///////////////////////////////////////////////////////////////////////////
    public static void reverseArr(double[] arr){
    	
    	int halfN     = arr.length / 2;
    	int NminusOne = arr.length - 1;
    	
    	for( int i = 0; i < halfN; i++ ) {
    		
    		double temp   = arr[i];
    		int    j      = NminusOne - i;
    		
    		arr[i]        = arr[j];
    		arr[j]        = temp;
    	}    		
    }
    /////////////////////////////////////////////////////////////////////////////
    // We have a constructor which is private! 
    // It is only called from within the static sort(..) method.
    private QSortDouble(double[] arr){
    	m_arr = arr;
    }
    //////////////////////////////////////////////////////////////////////////////
    // will sort m_arr's elements between low inclusive and high exclusive.
    private void sortSub(int low, int high){    
       int available;
    	
       if ( high <= (low + 1)) // when there are 0 or 1 elements, there is nothing to be done.
          return;
       else { // we use this else branch to ensure that as many temporary variables as possible
    	      // are removed from the stack before the recursive call sortSub(..) below.
		   m_initialPivotIdx = idxOfMedianOfThree( low, (low + high)/2, high - 1 );    	   
		   m_pivot           = m_arr[m_initialPivotIdx];
		   
		   if ( m_initialPivotIdx != low)
			    m_arr[m_initialPivotIdx] = m_arr[low];
		   	    	
		   available         = low;	   
	       m_workingFromLow  = false;
	       m_lowIdx          = low;
	       m_highIdx         = high;
	
	       while ( m_lowIdx  < m_highIdx){
	    	   
	           if (m_workingFromLow && (m_arr[++m_lowIdx] > m_pivot)){
		           m_arr[available]  = m_arr[m_lowIdx];
		           available         = m_lowIdx;
		           m_workingFromLow  = false;
	
	           } else if ((!m_workingFromLow) && (m_pivot > m_arr[--m_highIdx])){
		           m_arr[available]  = m_arr[m_highIdx];
		           available         = m_highIdx;
		           m_workingFromLow  = true;   		           
	           }
	        }  // end of while  
	        m_arr[available] = m_pivot;  // the pivot is now in the correct position. 
       } 
       // now we have a recursive call, to sort the sub-arrays
       sortSub(low, available);  
       sortSub(available + 1, high);
    }
    ///////////////////////////////////////////////////////////////////////////
    private int idxOfMedianOfThree( int i1, int i2, int i3){
    	if ( (m_arr[i2] > m_arr[i1]) == (m_arr[i1] > m_arr[i3]))
    		return i1;
    	else if ( (m_arr[i1] > m_arr[i2]) == (m_arr[i2] > m_arr[i3]))
    		return i2;
    	else
    		return i3;
    }
    //////////////////////////////////////////////////////////////////////////////////
}    

