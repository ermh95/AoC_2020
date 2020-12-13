import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day13 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day13\\input13.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		int currentTime = Integer.parseInt(sc.nextLine()), currentBus, rightBus = 0;
		int minTime = 1000000;
		ArrayList<Integer> busList = new ArrayList<Integer>();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		String[] table = sc.nextLine().split(",");
		sc.close();
		for(int i = 0; i < table.length; i++) {
			if(!table[i].equals("x")) {
				busList.add(Integer.parseInt(table[i]));
				indexList.add(i);
			}
		}
		for(int i = 0; i < busList.size(); i++) {
			currentBus = busList.get(i)-currentTime%busList.get(i);
			if(currentBus < minTime) {
				minTime = currentBus;
				rightBus = busList.get(i);
			}
		}
		
		return minTime*rightBus;
	}
	
	private static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		int throwAway = Integer.parseInt(sc.nextLine());
		ArrayList<Integer> busList = new ArrayList<Integer>();
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		String[] table = sc.nextLine().split(",");
		sc.close();
		for(int i = 0; i < table.length; i++) {
			if(!table[i].equals("x")) {
				busList.add(Integer.parseInt(table[i]));
				indexList.add(i);
			}
		}
		int j;
		boolean foundInterval;
		long time[] = new long[busList.size()-1];
		long interval[] = new long[busList.size()-1];
		for(int i = 0; i < time.length; i++) { //Find all solutions for individual buses
			foundInterval = false;
			j = 1;
			time[i] = 0;
			interval[i] = 0;
			while(!foundInterval) {
				if((j*busList.get(i+1))%busList.get(0)==indexList.get(i+1)%busList.get(0)) {
					if(time[i] == 0) {
						if(j*busList.get(i+1)-indexList.get(i+1) > 0) {
						time[i] = j*busList.get(i+1)-indexList.get(i+1);
						}
					} else {
						interval[i] = j*busList.get(i+1)-indexList.get(i+1)-time[i];
						foundInterval = true;
					}
				}
				j++;
			}
		}
		long candidateTime = time[0];
		long candidateInterval = interval[0];
		for(int i = 1; i < interval.length; i++) { //combine bus solutions one at a time
			candidateTime = findCommonTime(candidateTime, time[i], candidateInterval, interval[i]);
			candidateInterval = lcm(candidateInterval, interval[i]);
		}
		return candidateTime;
	}
	
	private static long findCommonTime(long time1, long time2, long interval1, long interval2) {
		boolean found = false;
		long i = 1, ans = 0;
		while(!found && i < 1000000) {
			if((time1+interval1*i)%interval2 == time2) {
				found = true;
				ans = time1+interval1*i;
			}
			i++;
		}
		return ans;
	}
	
	private static long gcd(long a, long b)
	{
	    while (b > 0)
	    {
	        long temp = b;
	        b = a % b;
	        a = temp;
	    }
	    return a;
	}
	
	private static long lcm(long a, long b)
	{
	    return a * (b / gcd(a, b));
	}

}
