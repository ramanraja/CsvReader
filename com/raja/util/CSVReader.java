/***
Utility class to read a CSV file and return it as a list of lists.
Each row is a list, which in turn contains the cells as an inner list.
This is a customized version of MKYong's 2016 code at
https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
Another great inspiration has been
https://agiletribe.wordpress.com/2012/11/23/the-only-class-you-need-for-csv-files/
***/

package com.raja.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
    private char separator;
    private char quote;
    
    public static void main(String[] args) {
        System.out.println ("Adapted from https://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/");
    }
    
    public ArrayList readCSV (String csvFileName)  throws Exception 
    {
        ArrayList<ArrayList> table = new ArrayList<ArrayList>();
        Scanner scanner = new Scanner(new File(csvFileName));
        while (scanner.hasNext()) {
            ArrayList<String> row = parseLine(scanner.nextLine());
            if (row != null && row.size() > 0)  // ignore blank lines
                table.add(row);
        }
        scanner.close();     
        return (table);   
    }
    
    public CSVReader()
    {
        this.separator = DEFAULT_SEPARATOR;
        this.quote = DEFAULT_QUOTE;        
    }

    public CSVReader(char separatorChar)
    {
        this.separator = separatorChar;
        this.quote = DEFAULT_QUOTE;    
    }
    
    public CSVReader(char separatorChar, char quoteChar)
    {
        this.separator = separatorChar;
        this.quote = quoteChar;
    }
        
    protected ArrayList<String> parseLine(String cvsLine) {

        ArrayList<String> result = new ArrayList<String>();

        //if empty, return!
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        if (quote == ' ') {
            quote = DEFAULT_QUOTE;
        }

        if (separator == ' ') {
            separator = DEFAULT_SEPARATOR;
        }

        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] chars = cvsLine.toCharArray();

        for (char ch : chars) {

            if (inQuotes) {
                startCollectChar = true;
                if (ch == quote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {

                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }

                }
            } else {
                if (ch == quote) {

                    inQuotes = true;

                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && quote == '\"') {
                        curVal.append('"');
                    }

                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }

                } else if (ch == separator) {

                    result.add(curVal.toString());

                    curVal = new StringBuffer();
                    startCollectChar = false;

                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }

}