import java.util.Random;

// Author: Philip Kinlen
public class PK_QSortEG {

    public static void main(String[] args) {    	    	
    	runExamples();
    }
    ///////////////////////////////////////////////////////////////////////
    private static void runExamples(){
        //Random randGen = new Random(); // have an optional arg, which is the rand seed, 
        //                                // default seed is the timer.                  
        int      numElms            = 6;
        boolean  initiallyAscending = true;
        boolean  sortAscending      = false;
                  	
    	System.out.println("\nAbout to test with non-generic double version.");
    	double[] arrd               = generateOrderedArr(numElms, initiallyAscending);
    	callNonGenericQSort(arrd, sortAscending);  	

    	System.out.println("\nAbout to test generic QSort with Strings");
        String[] arrS               = {"Tom", "Dick", "Harry", "Mary", "Jane", "Sue"};
    	callPK_QSortWithTestCase(arrS, sortAscending);

        System.out.println("\nAbout to test generic QSort with Longs");
        Long[]   arrL               = generateOrderedArr(numElms, initiallyAscending, (Long)null);
    	callPK_QSortWithTestCase(arrL, sortAscending);
    	
        System.out.println("\nAbout to test generic QSort with Doubles");
        Double[] arrD               = generateOrderedArr(numElms, initiallyAscending, (Double)null);
    	callPK_QSortWithTestCase(arrD, sortAscending);
    	
    	System.out.println("\nAbout to test non-generic QSort with a random array.");
    	callNonGenericQSortWithRandomArr(numElms, sortAscending);    	
    }
    ///////////////////////////////////////////////////////////////////////////////
    private static <T extends Comparable<T>>void callPK_QSortWithTestCase(T[] arr, boolean ascending){
                           
         System.out.println("Here's the array before sorting:");
         printArr(arr);
         
         QSort.sort(arr, ascending);
         
         System.out.println("After sorting, it looks like this:");
         printArr(arr);
    }
    ///////////////////////////////////////////////////////////////////////////////
    private static void callNonGenericQSort(double[] arrd, boolean ascending){
                           
         System.out.println("Here's the array before sorting:");
         printArr(arrd);
         
         QSortDouble.sort(arrd, ascending);
         
         System.out.println("After sorting it looks like this:");
         printArr(arrd);
    }
    ////////////////////////////////////////////////////////////////////////////////////
    private static void callNonGenericQSortWithRandomArr(int numElms, boolean sortAsc){
    	Random   randGen = new Random();    	
    	double[] arrd    = generateRandomArr(numElms, randGen);
    	callNonGenericQSort(arrd, sortAsc);  	
    }
    ///////////////////////////////////////////////////////////////////////////
    public static double[] generateRandomArr(int numElms, Random randGen){
    	double[] res = new double[numElms];
    	
    	for(int i = 0; i < numElms; i++)
    		res[i] = randGen.nextDouble();
    	
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////
    private static double[] generateOrderedArr(int numElms, boolean ascending){
    	double[] res = new double[numElms];
    	
    	for( int i = 0; i < numElms; i++)
    		res[i] = ( ascending ? i : numElms - i);
    	    	
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////
    // You'd think that the following method should be templatized.
    // But how do you initialize a <T extends Number> with an int?
    private static Long[] generateOrderedArr(int numElms, boolean ascending, Long nullLong){

    	Long[] res = new Long[numElms];
    	
    	for( int i = 0; i < numElms; i++)   		
    		 res[i] = new Long( ascending ? i : numElms - i);
    	    
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////
    private static Double[] generateOrderedArr(int numElms, boolean ascending, Double nullDouble){

    	Double[] res = new Double[numElms];
    	
    	for( int i = 0; i < numElms; i++)   		
    		 res[i] = new Double( ascending ? i : numElms - i);
    	    
    	return res;
    }
    ///////////////////////////////////////////////////////////////////////////    
    public static void printArr(double[] arr){
    	String str = (arr.length > 0 ? Double.toString(arr[0]) : "");
    	
    	for ( int i = 1; i < arr.length; i++)
    		str += ", " + Double.toString(arr[i]);
    	
    	System.out.println(str);
    }  
    ///////////////////////////////////////////////////////////////////////////    
    public static <T> void printArr(T[] arr){
    	String str = (arr.length > 0 ? arr[0].toString() : "");
    	
    	for ( int i = 1; i < arr.length; i++)
    		str += ", " + arr[i].toString();
    	
    	System.out.println(str);
    }  
    ////////////////////////////////////////////////////////////////////////////
}    

