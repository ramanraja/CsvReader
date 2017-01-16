import com.raja.util.CSVReader;
import java.util.ArrayList;
import java.util.Iterator;

public class CSVTester
{
    public static void main(String[] args) throws Exception 
    {
        // NOTE: any of the file operations below can fail, so deal with the exception suitably
        
        CSVReader reader = new CSVReader();
        ArrayList<ArrayList> outerList = reader.readCSV ("test1.csv");
        for (ArrayList<String> innerList : outerList) 
            for (String str : innerList)
                System.out.println (str);
        System.out.println("---------------------------"); 
              
        reader = new CSVReader('|');
        outerList = reader.readCSV ("test2.tsv");
        for (ArrayList<String> innerList : outerList) 
            for (String str : innerList)
                System.out.println (str);     
        System.out.println("---------------------------"); 
              
        reader = new CSVReader('|', '#');
        outerList = reader.readCSV ("test3.tsv");
        for (ArrayList<String> innerList : outerList) 
            for (String str : innerList)
                System.out.println (str); 
    }
}
