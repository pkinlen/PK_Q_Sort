import java.util.Arrays;
import java.util.Random;

//////////////////////////////////////////////////////////////
/*
 *  Found that the PK sort uses about 25% extra calls to the compareTo(.) method
 *  compared to the inbuilt sort algorithm when the array had a uniform random distribution.
 *  
 *  If the array is pre-sorted then the inbuilt sorter is very efficient,
 *  i.e. there are just N-1 calls to the method compareTo(.)
 *  In that case PK sort is much worse ( when N = 100, found it to be 7 times worse!)
 *  
 *  Here's a little summary of number of calls to compareTo(.) when we had 10,000 elements
 *                          Built-in           PK_Sort
 *  Random Arr              123,600            154,025
 *  Already Sorted            9,999              9,999
 *  Sorted in wrong dir      77,231              9,999
 *  
 *  
 */
///////////////////////////////////////////////////////////////
public class SortBenchmarking {
	private static int   m_numElms = 10000;
	
    private static long  m_compareCounter;
	private static long  m_startTime;
	private static long  m_milliSecs;
	
	private static TestObj[] m_originalArr;
	private static TestObj[] m_workingArr;
	
    ///////////////////////////////////////////////////////	
	public static void run(){
		
		boolean ascending = true;
		
		m_originalArr = getRandArr(m_numElms);
	    // QSort.sort(m_originalArr, !ascending); // Can work with a pre-sorted array
							
	    System.out.println("\nAbout to start the test.\n");
	    
        /////////////////////////////////////////////////////////
		// Java inbuilt sort:
		resetAndStartTimer();		
		Arrays.sort(m_workingArr);
		stopTimer();
		printResults("Inbuilt Array.sort");
		
		////////////////////////////////////////////////////////
		// PK sort:
		resetAndStartTimer();
		QSort.sort(m_workingArr, ascending);
		stopTimer();
		printResults("PK QSort");

		/////////////////////////////////////////////
		//printArrays();		
	}
    ///////////////////////////////////////////////////////
	public static void stopTimer(){
		m_milliSecs = System.currentTimeMillis() - m_startTime;
	}
	///////////////////////////////////////////////////////
    public static void resetAndStartTimer(){
    	// we can't do: m_workingArr = m_originalArr
    	// but then again, also we don't need a deep copy.
    	m_workingArr      = standardCopy( m_originalArr);

    	m_compareCounter  = 0;    	
    	m_milliSecs       = 0;
    	m_startTime       = System.currentTimeMillis(); 
    }    
    ///////////////////////////////////////////////////////
    private static TestObj[] getRandArr(int numElms){
    	TestObj[] arr  = new TestObj[numElms];
    	
    	Random randGen = new Random();
    	int    maxRand = numElms * 2;
    	
    	for(int i  = 0; i < numElms; i++)
    		arr[i] = new TestObj(randGen.nextInt(maxRand));     	

    	return arr;
    }
    ///////////////////////////////////////////////////////	
    public static TestObj[] deepCopy(TestObj[] arr){
    	TestObj[] res = new TestObj[arr.length];
    	
    	for( int i = 0; i < arr.length; i++)
    		res[i] = new TestObj(arr[i]);
    	
    	return res;
    }
    ////////////////////////////////////////////////////
    public static TestObj[] standardCopy(TestObj[] arr){
    	TestObj[] res = new TestObj[arr.length];
    	
    	for( int i = 0; i < arr.length; i++)
    		res[i] = arr[i];
    	
    	return res;
    }
    ////////////////////////////////////////////////////
	public static void printArrays(){
		
		if ( m_originalArr != null){
			System.out.println("The original array looked like: ");
			PK_QSortEG.printArr(m_originalArr);
		}
		
		if ( m_workingArr != null){
			System.out.println("and the sorted array: ");
			PK_QSortEG.printArr(m_workingArr);
		}
	}
	/////////////////////////////////////////////////////
	private static void printResults(String sortName){
		System.out.println("\nResults for " + sortName);		
		System.out.format("With num elms:        %,12d%n", m_numElms);
		System.out.format("Comparison counter:   %,12d%n", m_compareCounter);
		System.out.format("Milli secs counter:   %,12d%n", m_milliSecs);
	}
    ///////////////////////////////////////////////////////
    public static void incrementCompareCounter(){
    	m_compareCounter++;
    }
    ///////////////////////////////////////////////////////	
}
