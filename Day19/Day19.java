import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Day19 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day19\\input19.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static long part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		HashMap<Integer, String> rules = new HashMap<Integer, String>();
		String currentLine[] = new String[2];
		boolean findingLines = true;
		while(findingLines) {
			currentLine = sc.nextLine().split(": ");
			if(currentLine.length == 1) {
				findingLines = false;
			} else {
				rules.put(Integer.parseInt(currentLine[0]), currentLine[1]);
			}
		}
		int validSum = 0;
		ArrayList<String> validMessages = listValid(rules, 0);
		while(sc.hasNextLine()) {
			if(validMessages.contains(sc.nextLine())) {
				validSum++;
			}
		}
		sc.close();
		return validSum;
	}
	
	public static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		HashMap<Integer, String> rules = new HashMap<Integer, String>();
		String currentLine[] = new String[2];
		boolean findingLines = true;
		while(findingLines) {
			currentLine = sc.nextLine().split(": ");
			if(currentLine.length == 1) {
				findingLines = false;
			} else {
				rules.put(Integer.parseInt(currentLine[0]), currentLine[1]);
			}
		}
		int validSum = 0;
		ArrayList<String> messages42 = listValid(rules, 42);
		ArrayList<String> messages31 = listValid(rules, 31);
		String message = new String();
		int index42 = 0, index31 = 0;
		while(sc.hasNextLine()) {
			message = sc.nextLine();
			int searchIndex = 0;
			int count42 = 0;
			int count31 = 0;
			boolean found42 = true;
			while(found42) {
				found42 = false;
				for(int i = 0; i < messages42.size() && !found42; i++) {
					index42 = message.indexOf(messages42.get(i), searchIndex);
					if(index42 == searchIndex) {
						searchIndex += messages42.get(i).length();
						found42 = true;
						count42++;
					}
				}
			}
			boolean found31 = true;
			while(found31 && searchIndex < message.length()) {
				found31 = false;
				for(int i = 0; i < messages31.size() && !found31; i++) {
					index31 = message.indexOf(messages31.get(i), searchIndex);
					if(index31 == searchIndex) {
						searchIndex += messages31.get(i).length();
						found31 = true;
						count31++;
					}
				}
			}
			if(found31 && count42 > count31 && count42 >= 2 && count31 > 0) {
				validSum++;
			}
		}
		sc.close();
		return validSum;
	}
	
	private static ArrayList<String> listValid(HashMap<Integer, String> rules, int ruleKey){
		ArrayList<String> ans = new ArrayList<String>();
		if(rules.get(ruleKey).charAt(0) == '"') {
			ans.add(rules.get(ruleKey).substring(1,2));
			return ans;
		}
		String currentRule[] = rules.get(ruleKey).split(" \\| ");
		String rulesToCheck[] = new String[3];
		for(int i = 0; i < currentRule.length; i++) {
			rulesToCheck = currentRule[i].split(" ");
			ArrayList<String> startingPieces = listValid(rules, Integer.parseInt(rulesToCheck[0]));
			if(rulesToCheck.length == 1) {
				ans.addAll(startingPieces);
			}
			else if(rulesToCheck.length == 2) {
				ArrayList<String> endPieces = listValid(rules, Integer.parseInt(rulesToCheck[1]));
				for(int j = 0; j < startingPieces.size(); j++) {
					for(int k = 0; k < endPieces.size(); k++) {
						ans.add(startingPieces.get(j) + endPieces.get(k));
					}
				}
			}
			else if(rulesToCheck.length == 3) {
				ArrayList<String> middlePieces = listValid(rules, Integer.parseInt(rulesToCheck[1]));
				ArrayList<String> endPieces = listValid(rules, Integer.parseInt(rulesToCheck[2]));
				for(int j = 0; j < startingPieces.size(); j++) {
					for(int k = 0; k < middlePieces.size(); k++) {
						for(int l = 0; l < endPieces.size(); l++) {
							ans.add(startingPieces.get(j) + middlePieces.get(k) + endPieces.get(l));
						}
					}
				}
			}
		}
		return ans;
	}

}
