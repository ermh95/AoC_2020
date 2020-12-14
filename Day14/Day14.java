import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day14 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day14\\input14.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static long part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String[] currentLine = new String[2];
		char[] currentMask = new char[36];
		char[] inSeq = new char[36];
		long[] memory = new long[100000];
		int currentMemory = 0;
		long inValue = 0, maskedValue = 0;
		for(int i = 0; i < memory.length; i++) {
			memory[i] = 0;
		}
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" = ");
			if(currentLine[0].equals("mask")) {
				for(int i = 0; i < currentMask.length; i++) {
					currentMask[i] = currentLine[1].charAt(i);
				}
			} else {
				currentMemory = Integer.parseInt(currentLine[0].substring(4, currentLine[0].length()-1));
				inValue = Long.parseLong(currentLine[1]);
				inSeq = longToSeq(inValue);
				for(int i = 0; i < inSeq.length; i++) {
					if(currentMask[i] != 'X') {
						inSeq[i] = currentMask[i];
					}
				}
				maskedValue = seqToLong(inSeq);
				memory[currentMemory] = maskedValue;
			}
		}
		sc.close();
		long memorySum = 0;
		for(int i = 0; i < memory.length; i++) {
			memorySum += memory[i];
		}
		return memorySum;
	}
	
	private static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String[] currentLine = new String[2];
		char[] currentMask = new char[36];
		char[] inSeq = new char[36];
		ArrayList<Long> filled = new ArrayList<Long>();
		ArrayList<Long> values = new ArrayList<Long>();
		ArrayList<Long> destinations = new ArrayList<Long>();
		long inMemory = 0;
		long inValue = 0;
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" = ");
			if(currentLine[0].equals("mask")) {
				for(int i = 0; i < currentMask.length; i++) {
					currentMask[i] = currentLine[1].charAt(i);
				}
			} else {
				inMemory = Long.parseLong(currentLine[0].substring(4, currentLine[0].length()-1));
				inValue = Long.parseLong(currentLine[1]);
				inSeq = longToSeq(inMemory);
				for(int i = 0; i < inSeq.length; i++) {
					if(currentMask[i] != '0') {
						inSeq[i] = currentMask[i];
					}
				}
				destinations = determineDestinations(inSeq);
				for(int i = 0; i < destinations.size(); i++) {
					if(!filled.contains(destinations.get(i))) {
						filled.add(destinations.get(i));
						values.add(inValue);
					} else {
						values.set(filled.indexOf(destinations.get(i)), inValue);
					}
				}
			}
		}
		sc.close();
		long memorySum = 0;
		for(int i = 0; i < values.size(); i++) {
			memorySum += values.get(i);
		}
		return memorySum;
	}
	
	private static ArrayList<Long> determineDestinations(char[] seq){
		ArrayList<Long> destinations = new ArrayList<Long>();
		long memory = 0;
		boolean xFound = false;
		char[] split1 = new char[36];
		char[] split2 = new char[36];
		for(int i = 0; i < seq.length; i++) {
			split1[i] = seq[i];
			split2[i] = seq[i];
		}
		for(int i = 0; i < seq.length && !xFound; i++) {
			if(seq[i] == 'X'){
				xFound = true;
				split1[i] = '0';
				split2[i] = '1';
				destinations.addAll(determineDestinations(split1));
				destinations.addAll(determineDestinations(split2));
			}
		}
		if(!xFound) {
			for(int i = 0; i < seq.length; i++) {
				if(seq[i] == '1') {
					memory += power(2,35-i);
				}
			}
			destinations.add(memory);
		}
		return destinations;
	}
	
	private static char[] longToSeq(long a) {
		char[] out = new char[36];
		for(int i = 0; i < out.length; i++) {
			if(a >= power(2, 35-i)) {
				out[i] = '1';
				a -= power(2, 35-i);
			} else {
				out[i] = '0';
			}
		}
		return out;
	}
	
	private static long seqToLong(char[] seq) {
		long out = 0;
		for(int i = 0; i < seq.length; i++) {
			if(seq[i] == '1') {
				out += power(2,35-i);
			}
		}
		return out;
	}
	
	private static long power(long a, long b) {
		long ans = 1;
		for(int i = 0; i < b; i++) {
			ans *= a;
		}
		return ans;
	}

}
