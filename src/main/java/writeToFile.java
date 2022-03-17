import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.lang.reflect.Array;
import java.util.*;

public class writeToFile {

    public static void main(String[] args) {
        try {

            //CALL UNION METHOD TO GENERATE UNION OF COGS AND LIST OF GENOME NAMES
            ArrayList<String> cogs = dataExtraction.getCogUnion();

            //CREATE WRITER, FORMAT TABLE
            FileWriter myWriter = new FileWriter("test.txt");
            myWriter.write("       ");

            //PRINT COG NAMES FOR COLUMNS
            for (int i = 0; i < cogs.size(); i++) {
                myWriter.write(cogs.get(i) + "\t");
            }

            myWriter.write("\n");

            //PRINT ROWS
            for (String genome : dataExtraction.presenceMap.keySet()) {
                myWriter.write(genome);
                int extraSpace = 7 - genome.length();
                if (extraSpace > 0) {
                    for (int i = 0; i < extraSpace; i++) {
                        myWriter.write(" ");
                    }
                }
                for (int num : dataExtraction.presenceMap.get(genome)) {
                    myWriter.write("   " + num + "   " + "\t");
                }
                myWriter.write("\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}