import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Day16 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day16\\input16.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		boolean readingRules = true;
		int numOfRules = 20;
		String currentLine = new String();
		String currentArray[] = new String[numOfRules];
		String rules[][] = new String[numOfRules][];
		int myTicket[] = new int[numOfRules];
		int index = 0;
		while(readingRules) {
			currentLine = sc.nextLine();
			if(currentLine.equals("")) {
				readingRules = false;
			} else {
				rules[index] = currentLine.split("(: )|( or )|(-)");
			}
			index++;
		}
		sc.nextLine();
		currentArray = sc.nextLine().split(",");
		for(int i = 0; i < numOfRules; i++) {
			myTicket[i] = Integer.parseInt(currentArray[i]);
		}
		sc.nextLine();
		sc.nextLine();
		ArrayList<String> otherTickets = new ArrayList<String>();
		while(sc.hasNextLine()) {
			otherTickets.add(sc.nextLine());
		}
		int tickets[][] = new int[otherTickets.size()][numOfRules];
		for(int i = 0; i < otherTickets.size(); i++) {
			currentArray = otherTickets.get(i).split(",");
			for(int j = 0; j < numOfRules; j++) {
				tickets[i][j] = Integer.parseInt(currentArray[j]);
			}
		}
		sc.close();
		int errorSum = 0;
		for(int i = 0; i < tickets.length; i++) {
			for(int j = 0; j < tickets[i].length; j++) {
				if(!checkIfValid(tickets[i][j], rules)) {
					errorSum += tickets[i][j];
				}
			}
		}
		return errorSum;
	}
	
	public static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		boolean readingRules = true;
		int numOfRules = 20;
		String currentLine = new String();
		String currentArray[] = new String[numOfRules];
		String rules[][] = new String[numOfRules][];
		int myTicket[] = new int[numOfRules];
		int index = 0;
		while(readingRules) {
			currentLine = sc.nextLine();
			if(currentLine.equals("")) {
				readingRules = false;
			} else {
				rules[index] = currentLine.split("(: )|( or )|(-)");
			}
			index++;
		}
		sc.nextLine();
		currentArray = sc.nextLine().split(",");
		for(int i = 0; i < numOfRules; i++) {
			myTicket[i] = Integer.parseInt(currentArray[i]);
		}
		sc.nextLine();
		sc.nextLine();
		ArrayList<String> otherTickets = new ArrayList<String>();
		while(sc.hasNextLine()) {
			otherTickets.add(sc.nextLine());
		}
		int tickets[][] = new int[otherTickets.size()+1][numOfRules];
		tickets[0] = myTicket;
		for(int i = 1; i < tickets.length; i++) {
			currentArray = otherTickets.get(i-1).split(",");
			for(int j = 0; j < numOfRules; j++) {
				tickets[i][j] = Integer.parseInt(currentArray[j]);
			}
		}
		sc.close();
		ArrayList<Integer> invalidTickets = new ArrayList<Integer>();
		for(int i = 0; i < tickets.length; i++) {
			for(int j = 0; j < tickets[i].length; j++) {
				if(!checkIfValid(tickets[i][j], rules)) {
					invalidTickets.add(i);
				}
			}
		}
		ArrayList<Integer>[] possiblePositions = (ArrayList<Integer>[])new ArrayList[numOfRules];
		for(int i = 0; i < numOfRules; i++) {
			possiblePositions[i] = determinePosition(rules[i], tickets, invalidTickets);
		}
		boolean unique, firstFound;
		boolean changing = true;
		int trueRules[] = new int[numOfRules];
		int first = 0, startSize = 0;
		ArrayList<Integer> solvedRules = new ArrayList<Integer>();
		while(changing) {
			startSize = solvedRules.size();
			for(int i = 0; i < numOfRules; i++) {
				unique = false;
				firstFound = false;
				for(int j = 0; j < numOfRules; j++) {
					if(possiblePositions[j].contains(i) && !solvedRules.contains(j)) {
						if(!firstFound) {
							unique = true;
							firstFound = true;
							first = j;
						} else {
							unique = false;
						}
					}
				}
				if(unique) {
					trueRules[i] = first;
					solvedRules.add(first);
				}
			}
			if(solvedRules.size() <= startSize) {
				changing = false;
			}
		}
		long ans = 1;
		for(int i = 0; i < rules.length; i++) {
			if(trueRules[i] < 6) {
				ans *= myTicket[i];
			}
		}
		return ans;
	}

	private static boolean checkIfValid(int value, String[][] rules) {
		boolean valid = false;
		for(int i = 0; i < rules.length; i++) {
			for(int j = 1; j < rules[i].length; j+=2) {
				if(value >= Integer.parseInt(rules[i][j]) && value <= Integer.parseInt(rules[i][j+1])) {
					valid = true;
				}
			}
		}
		return valid;
	}
	
	private static ArrayList<Integer> determinePosition(String[] rule, int[][] tickets, ArrayList<Integer> invalid) {
		ArrayList<Integer> possiblePositions = new ArrayList<Integer>();
		int value;
		for(int j = 0; j < tickets[0].length; j++) {
			possiblePositions.add(j);
			for(int i = 0; i < tickets.length; i++) {
				if(!invalid.contains(i)) {
					value = tickets[i][j];
					if(!(value >= Integer.parseInt(rule[1]) && value <= Integer.parseInt(rule[2]) || value >= Integer.parseInt(rule[3]) && value <= Integer.parseInt(rule[4]))) {
						possiblePositions.remove(Integer.valueOf(j));
					}
				}
			}
		}
		return possiblePositions;
	}
}
