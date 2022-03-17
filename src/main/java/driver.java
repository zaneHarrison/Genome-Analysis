import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.*;

//FILE 1: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\wh8103-cogdata.txt"
//FILE 2: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\kordi52-cogdata.txt"
//FILE 3: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\wh8101-cogdata.txt"
//FILE 4: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\rcc307-cogdata.txt"
//FILE 5: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\cc9902-cogdata.txt"
//FILE 6: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\ns01-cogdata.txt"
//FILE 7: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\pcc6307-cogdata.txt"
//FILE 8: "C:\\Users\\zaneh\\IdeaProjects\\Genome Analysis Test\\src\\main\\resources\\syn20-cogdata.txt"

public class driver {
    public static void main(String[] args) {

        //TO READ INPUT FROM USER
        Scanner scan = new Scanner(System.in);

        //ASK USER WHICH FUNCTION
        System.out.println("Enter 1 to print COG names of a given genome.\n" +
                "Enter 2 to calculate unique COGs for a given genome.\n" +
                "Enter 3 to generate a union of COGs for a set of genomes.");
        int numToDetermineFunction = Integer.parseInt(scan.nextLine());

        if (numToDetermineFunction == 1) {
            System.out.println("Enter file name:");
            String fName = scan.nextLine();
            System.out.println("Print COG names? (y/n):");
            String printNames = scan.nextLine();
            ArrayList<String> cogNames = dataExtraction.getCogList(fName, printNames);
        } else if (numToDetermineFunction == 2) {
            ArrayList<String> uniqueCogs = dataExtraction.getUniqueCogs();
        } else if (numToDetermineFunction == 3) {
            ArrayList<String> union = dataExtraction.getCogUnion();
        }
    }
}
