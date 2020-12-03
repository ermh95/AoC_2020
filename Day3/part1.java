import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class part1 {

	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day3\\input.txt");
        Scanner sc = new Scanner(input);
        String tmp = new String();
        int numberOfLines = 0, x = 0, rowLength, treeCount = 0;
        char tree = '#';
        while(sc.hasNextLine()) {
        	tmp = sc.nextLine();
        	numberOfLines++;
        }
        rowLength = tmp.length();
        sc.close();
        sc = new Scanner(input);
        String[] rows = new String[numberOfLines];
        for(int i = 0; i < numberOfLines; i++) {
        	rows[i] = sc.nextLine();
        	if(rows[i].charAt(x) == tree) {
        		treeCount++;
        	}
        	x = (x+3)%rowLength;
        }
        sc.close();
        System.out.println(treeCount);
	}

}
