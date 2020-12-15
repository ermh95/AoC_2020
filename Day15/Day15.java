import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;

public class Day15 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day15\\input15.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static long part1(File input) throws FileNotFoundException{
		return findNumber(2020, input);
	}
	
	private static long part2(File input) throws FileNotFoundException{
		return findNumber(30000000, input);
	}
	
	private static int findNumber (int endpoint, File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		int[] numbersSaid = new int[endpoint];
		for(int i = 0; i < endpoint; i++) {
			numbersSaid[i] = -1;
		}
		int i = 0;
		int lastNumber = 0;
		int tmp, lastOccurance;
		while(sc.hasNextInt()) {
			lastNumber = sc.nextInt();
			if(sc.hasNextInt()) {
				numbersSaid[lastNumber] = i;
			}
			i++;
		}
		sc.close();
		while(i < endpoint) {
			lastOccurance = numbersSaid[lastNumber];
			if(lastOccurance == -1) {
				numbersSaid[lastNumber] = i-1;
				lastNumber = 0;
			} else {
				tmp = i - 1 - lastOccurance;
				numbersSaid[lastNumber] = i-1;
				lastNumber = tmp;
			}
			i++;
		}
		return lastNumber;
	}

}
