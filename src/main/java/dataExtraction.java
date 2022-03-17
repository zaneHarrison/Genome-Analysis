import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class dataExtraction {

    public static ArrayList<String> genomeNames = new ArrayList<>();
    public static ArrayList<String> genomeFiles = new ArrayList<>();
    public static HashMap<String, ArrayList<Integer>> presenceMap = new HashMap<>();

    //GET COG NAME
    public static String getCogName(String line) {
        String[] parsedData = line.split("\t");
        return parsedData[9];
    }

    //GET COG LIST
    public static ArrayList<String> getCogList(String fileName, String printNames) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Reading data from " + fileName + "...\n");

        //Create list to hold cog names
        ArrayList<String> cogList = new ArrayList<>();

        //OPEN FILE FOR READING
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            myReader.nextLine();
            //Create list to add COGs to
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                //CALL HELPER TO EXTRACT COG NAME
                String cogName = getCogName(line);
                cogList.add(cogName);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        //REMOVE DUPLICATES BY ADDING TO SET
        HashSet<String> set = new HashSet<>();
        for (String cogName : cogList) {
            set.add(cogName);
        }
        cogList.clear();
        for (String cogName : set) {
            cogList.add(cogName);
        }

        //CHECK IF NAMES SHOULD BE PRINTED
        if (printNames.equals("y")) {
            for (String cogName : cogList) {
                System.out.println(cogName);
            }
        }

        if (printNames.equals("y")) {
            System.out.println("There are " + cogList.size() + " COGs in this genome.");
        }

        return cogList;
    }

    //GET UNIQUE COGS
    public static ArrayList<String> getUniqueCogs() {

        //GET BASE FILE FROM USER
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter file path for base genome:");
        String baseFile = scan.nextLine();  // Read user input
        ArrayList<String> baseList = getCogList(baseFile, "n");
        System.out.println("Enter number of genomes to compare against:");
        String toCompareAgainst = scan.nextLine();
        int numToCompareAgainst = Integer.valueOf(toCompareAgainst);

        //LIST TO HOLD GENOMES TO COMPARE AGAINST
        ArrayList<ArrayList<String>> genomesToCompareAgainst = new ArrayList<>();
        for (int i = 1; i <= numToCompareAgainst; i++) {
            System.out.println("Enter comparator genome #" + i);
            ArrayList<String> cogList = getCogList(scan.nextLine(), "n");
            genomesToCompareAgainst.add(cogList);
        }

        //CREATE SETS AND FIND SYMMETRIC DIFFERENCE
        HashSet<String> uniqueSet = new HashSet<>(baseList);
        for (ArrayList<String> list : genomesToCompareAgainst) {
            HashSet<String> currSet = new HashSet<>(list);
            //HashSet<String> uniqueSet = new HashSet<>(baseList);
            uniqueSet.removeAll(currSet);
        }
        baseList.clear();
        for (String cog : uniqueSet) {
            baseList.add(cog);
        }

        //PROMPT USER TO PRINT UNIQUE COG NAMES
        System.out.println("Print unique COG names? (y/n)");
        Scanner scanIn = new Scanner(System.in);
        String print = scanIn.nextLine();
        if (print.equals("y")) {
            for (String cogName : baseList) {
                if (genomesToCompareAgainst.get(0).contains(cogName)) {
                    System.out.println("This should not be printed.");
                }
                System.out.println(cogName);
            }
        }

        System.out.println("Number of unique COGs: " + baseList.size());

        return baseList;
    }

    //GENERATE UNION OF COGS
    public static ArrayList<String> getCogUnion() {

        //CREATE SET TO HOLD UNION OF GENOMES
        HashSet<String> set = new HashSet<>();

        //GET NUMBER OF GENOMES FROM USER
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter number of genomes from which to generate list:");
        int numGenomes = Integer.valueOf(scan.nextLine());  // Read user input

        //ADD GENOMES TO SET
        for (int i = 0; i < numGenomes; i++) {
            System.out.println("Enter genome #" + (i+1) + " file:");
            String genomeFile = scan.nextLine();
            //ADD FILE PATH TO LIST OF FILE PATHS
            genomeFiles.add(genomeFile);

            //ADD GENOME TO GLOBAL LIST
            int hyphenIdx = genomeFile.indexOf('-');
            genomeNames.add(genomeFile.substring(76, hyphenIdx));

            ArrayList<String> cogList = getCogList(genomeFile, "n");
            for (String cog : cogList) {
                set.add(cog);
            }
        }

        //ITERATE OVER INPUTTED GENOMES TO CREATE MAP
        for (int i = 0; i < genomeNames.size(); i++) {

            String name = genomeNames.get(i);
            ArrayList<String> genomeCogs = getCogList(genomeFiles.get(i), "n");
            ArrayList<Integer> presenceAbsenceList = new ArrayList<>();

            //ADD 0s OR 1s AS NEEDED
            for (String cog : set) {
                if (genomeCogs.contains(cog)) {
                    presenceAbsenceList.add(1);
                } else {
                    presenceAbsenceList.add(0);
                }
            }

            //ADD TO MAP
            presenceMap.put(name, presenceAbsenceList);
        }

        for (String key : presenceMap.keySet()) {
            System.out.println("\n");
            for (int num : presenceMap.get(key)) {
                System.out.print(num + " ");
            }
        }

        //RETURN LIST OF COGS
        System.out.println("\n");
        ArrayList<String> returnList = new ArrayList<>();
        for (String cog : set) {
            System.out.println(cog);
            returnList.add(cog);
        }
        System.out.println("Number of COGs in union: " + returnList.size() + ".");
        return returnList;
    }
}
