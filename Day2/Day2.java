
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day2 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day2\\input.txt");
        Scanner sc = new Scanner(input);
        String currentBoundary = new String();
        String currentLetterString = new String();
        String[] split;
        String currentPassword = new String();
        int min, max, count, valid1 = 0, valid2 = 0;;
        char currentChar;
        boolean statement1, statement2;
        while (sc.hasNextLine()) {
        	currentBoundary = sc.next();
        	split = currentBoundary.split("-");
        	min = Integer.parseInt(split[0]);
        	max = Integer.parseInt(split[1]);
        	currentLetterString = sc.next();
        	currentChar = currentLetterString.charAt(0);
        	currentPassword = sc.next();
        	count = currentPassword.length() - currentPassword.replace(Character.toString(currentChar), "").length();
        	if(count>=min && count<=max) {
        		valid1++;
        	}
        	statement1 = currentPassword.charAt(min-1) == currentChar;
        	statement2 = currentPassword.charAt(max-1) == currentChar;
        	if((statement1 || statement2) && !(statement1 && statement2) ) {
        		valid2++;
        	}
        }
        sc.close();
        System.out.println(valid1);
        System.out.println(valid2);
	}

}
