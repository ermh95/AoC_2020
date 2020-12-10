import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Day10 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day10\\input10.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<Integer> adapters = new ArrayList<Integer>();
		int count1 = 0, count3 = 0, lastAdapter = 0;
		while(sc.hasNextInt()) {
			adapters.add(sc.nextInt());
		}
		sc.close();
		Collections.sort(adapters);
		for(int i = 0; i < adapters.size(); i++) {
			if(adapters.get(i) - lastAdapter == 1) {
				count1++;
			} else if(adapters.get(i) - lastAdapter == 3){
				count3++;
			}
			lastAdapter = adapters.get(i);
		}
		count3++;
		int ans = count1*count3;
		return ans;
	}
	
	private static long part2(File input) throws FileNotFoundException{
		//Solution works based on the assumption that all gaps in the input are jumps of 1 or 3.
		//It is also assumed that no more than 4 gaps of 1 appear in a row, since this was the case for me.
		//Not the most general solution in other words.
		Scanner sc = new Scanner(input);
		ArrayList<Integer> adapters = new ArrayList<Integer>();
		ArrayList<Integer> oneStreaks = new ArrayList<Integer>();
		int lastAdapter = 0, currentStreak = 0;
		long combinations = 1;
		while(sc.hasNextInt()) {
			adapters.add(sc.nextInt());
		}
		sc.close();
		Collections.sort(adapters);
		for(int i = 0; i < adapters.size(); i++) {
			if(adapters.get(i) - lastAdapter == 1) {
				currentStreak++;
			} else if(adapters.get(i) - lastAdapter == 3){
				if(currentStreak > 0) {
					oneStreaks.add(currentStreak);
					currentStreak = 0;
				}
			}
			lastAdapter = adapters.get(i);
		}
		oneStreaks.add(currentStreak);
		for(int i = 0; i < oneStreaks.size(); i++) {
			if(oneStreaks.get(i) == 2) {
				combinations *= 2;
			}
			if(oneStreaks.get(i) == 3) {
				combinations *= 4;
			}
			if(oneStreaks.get(i) == 4) {
				combinations *= 7; // Total combinations 8 minus 1 invalid case.
			}
		}
		return combinations;
	}

}
