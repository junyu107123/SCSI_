package scsi.demo.scsi;
import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        File getCSVFiles = new File("/test/example.csv");
        Scanner sc = new Scanner(getCSVFiles);
        sc.useDelimiter(",");
        while (sc.hasNext())
        {
            System.out.print(sc.next() + " | ");
        }
        sc.close();  
    }
}