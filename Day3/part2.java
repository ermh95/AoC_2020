import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class part2 {

	public static void main(String[] args) throws FileNotFoundException{
		// TODO Auto-generated method stub
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day3\\input.txt");
        Scanner sc = new Scanner(input);
        String tmp = new String();
        int numberOfLines = 0, x = 0, rowLength;
        long multipliedTreeCount = 1;
        int slope[] = {1, 3, 5, 7, 1};
        long treeCount[] = new long[slope.length];
        char tree = '#';
        while(sc.hasNextLine()) {
        	tmp = sc.nextLine();
        	numberOfLines++;
        }
        rowLength = tmp.length();
        sc.close();
        sc = new Scanner(input);
        String[] rows = new String[numberOfLines];
        for(int i = 0; i < slope.length; i++) {
        	x = 0;
        	treeCount[i] = 0;
        	for(int j = 0; j < numberOfLines; j++) {
        		if(i == 0) {
        			rows[j] = sc.nextLine();
        		}
        		if(rows[j].charAt(x) == tree) {
        			treeCount[i]++;
        		}
        		x = (x+slope[i])%rowLength;
        		if(i == 4) {
        			j++;
        		}
        	}
        	multipliedTreeCount = multipliedTreeCount*treeCount[i];
        	System.out.println(treeCount[i]);
        }
        sc.close();
        System.out.println(multipliedTreeCount);
	}

}