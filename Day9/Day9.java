import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day9 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day9\\input9.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<Integer> previous = new ArrayList<Integer>();
		int currentNumber, firstInvalid = 0;
		boolean valid, invalidFound = false;
		while(sc.hasNextInt() && !invalidFound) {
			currentNumber = sc.nextInt();
			if(previous.size() >= 25) {
				valid = false;
				for(int i = 0; i < previous.size(); i++) {
					for(int j = i+1; j < previous.size(); j++) {
						if(previous.get(i)+previous.get(j) == currentNumber) {
							valid = true;
						}
					}
				}
				if(!valid) {
					invalidFound = true;
					firstInvalid = currentNumber;
				}
				previous.remove(0);
			}
			previous.add(currentNumber);
		}
		sc.close();
		return firstInvalid;
	}
	
	private static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<Long> previous = new ArrayList<Long>();
		ArrayList<Long> allNumbers = new ArrayList<Long>();
		long currentNumber, firstInvalid = 0;
		boolean valid, seriesFound = false, invalidFound = false;
		while(sc.hasNextLong()) { //Finding the first invalid case like in part 1.
			currentNumber = sc.nextLong();
			if(previous.size() >= 25 && !invalidFound) {
				valid = false;
				for(int i = 0; i < previous.size(); i++) {
					for(int j = i+1; j < previous.size(); j++) {
						if(previous.get(i)+previous.get(j) == currentNumber) {
							valid = true;
						}
					}
				}
				if(!valid && !invalidFound) {
					invalidFound = true;
					firstInvalid = currentNumber;
				}
				previous.remove(0);
			}
			previous.add(currentNumber);
			allNumbers.add(currentNumber);
		}
		sc.close();
		int i = 0, j;
		long currentSum, ans = 0;
		ArrayList<Long> currentSeries = new ArrayList<Long>();
		while(i < allNumbers.size() && !seriesFound) {
			currentSum = 0;
			currentSeries.clear();
			j = 0;
			while(currentSum < firstInvalid) {
				currentSum += allNumbers.get(i + j);
				currentSeries.add(allNumbers.get(i + j));
				j++;
			}
			if(currentSum == firstInvalid) {
				ans = Collections.max(currentSeries) + Collections.min(currentSeries);
				seriesFound = true;
			}
			i++;
		}
		return ans;
	}
	
	

}
