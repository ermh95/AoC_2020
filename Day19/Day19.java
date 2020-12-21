import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Day19 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day19\\example19part2.txt");
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
		ArrayList<String> validMessages = listValid(rules, 0);
		ArrayList<String> messages42 = listValid(rules, 42);
		ArrayList<String> messages31 = listValid(rules, 31);
		ArrayList<String> messages4231 = new ArrayList<String>();
		for(int i = 0; i < messages42.size(); i++) {
			for(int j = 0; j < messages31.size(); j++) {
				messages4231.add(messages42.get(i) + messages31.get(j));
			}
		}
		String message = new String();
		int index42 = 0, index4231 = 0;
		while(sc.hasNextLine()) {
			message = sc.nextLine();
			/*String oldMessage = message;
			boolean infFound = true;
			while(infFound) {
				infFound = false;
				for(int i = 0; i < messages4231.size() && !infFound; i++) {
					index4231 = message.indexOf(messages4231.get(i));
					if(index4231 >= 0) {
						for(int j = 0; j < messages31.size() && !infFound; j++)
						if(message.indexOf(messages31.get(j), index4231 + messages4231.get(i).length()) == index4231 + messages4231.get(i).length()) {
							message = message.substring(0, index4231) + message.substring(index4231 + messages4231.get(i).length());
							//infFound = true;
						}
					}
				}
				for(int i = 0; i < messages42.size() && !infFound; i++) {
					index42 = message.indexOf(messages42.get(i));
					if(index42 >= 0) {
						for(int j = 0; j < messages42.size() && !infFound; j++)
						if(message.indexOf(messages42.get(j), index42 + messages42.get(i).length()) == index42 + messages42.get(i).length()) {
							message = message.substring(0, index42) + message.substring(index42 + messages42.get(i).length());
							//infFound = true;
						}
					}
				}
			}*/
			//System.out.println(message);
			if(validMessages.contains(message) /*|| validMessages.contains(oldMessage)*/) {
				validSum++;
			}
		}
		sc.close();
		return validSum;
	}
	
	private static ArrayList<String> listValid(HashMap<Integer, String> rules, int ruleKey){
		ArrayList<String> ans = new ArrayList<String>();
		if(rules.get(ruleKey).charAt(0) == '"') {
			//System.out.println("yes");
			ans.add(rules.get(ruleKey).substring(1,2));
			return ans;
		}
		String currentRule[] = rules.get(ruleKey).split(" \\| ");
		String rulesToCheck[] = new String[3];
		String tmp = new String();
		int loops = 1;
		for(int i = 0; i < currentRule.length; i++) {
			rulesToCheck = currentRule[i].split(" ");
			ArrayList<String> startingPieces = listValid(rules, Integer.parseInt(rulesToCheck[0]));
			if(rulesToCheck.length == 1) {
				ans.addAll(startingPieces);
			}
			else if(rulesToCheck.length == 2) {
				if(Integer.parseInt(rulesToCheck[1]) == ruleKey) {
					ArrayList<String> infList = new ArrayList<String>();
					infList.addAll(startingPieces);
					for(int a = 0; a < loops; a++) {
						for(int j = 0; j < infList.size(); j++) {
							for(int k = 0; k < startingPieces.size(); k++) {
								tmp = infList.get(j) + startingPieces.get(k);
								infList.add(tmp);
							}
						}
					}
					ans.addAll(infList);
				} else {
					ArrayList<String> endPieces = listValid(rules, Integer.parseInt(rulesToCheck[1]));
					for(int j = 0; j < startingPieces.size(); j++) {
						for(int k = 0; k < endPieces.size(); k++) {
							ans.add(startingPieces.get(j) + endPieces.get(k));
						}
					}
				}
			}
			else if(rulesToCheck.length == 3) {
				if(Integer.parseInt(rulesToCheck[1]) == ruleKey) {
					ArrayList<String> infList = new ArrayList<String>();
					ArrayList<String> endPieces = listValid(rules, Integer.parseInt(rulesToCheck[2]));
					for(int a = 0; a < loops; a++) {
						for(int j = 0; j < startingPieces.size(); j++) {
							for(int k = 0; k < endPieces.size(); k++) {
								for(int l = 0; l < infList.size(); l++) {
									tmp = startingPieces.get(j) + infList.get(l) + endPieces.get(k);
									infList.add(tmp);
								}
							}
						}
					}
				} else {
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
		}
		return ans;
	}

}
