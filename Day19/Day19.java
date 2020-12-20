import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Day19 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day19\\example19part2modified.txt");
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
		String message = new String();
		int index42 = 0, index31 = 0;
		while(sc.hasNextLine()) {
			message = sc.nextLine();
			boolean tryAgain = true;
			while(tryAgain) {
				tryAgain = false;
			if(validMessages.contains(message)) {
				validSum++;
			} else {
				for(int i = 0; i < messages42.size() && !tryAgain; i++) {
					index42 = message.indexOf(messages42.get(i));
					if(index42 >= 0) {
						for(int j = 0; j < messages42.size() && !tryAgain; j++)
						if(message.indexOf(messages42.get(j), index42 + 1) == index42 + messages42.get(i).length()) {
							message = message.substring(0, index42) + message.substring(index42 + messages42.get(i).length());
							tryAgain = true;
						}
					}
				}
				if(!tryAgain) {
					for(int i = 0; i < messages31.size() && !tryAgain; i++) {
						index31 = message.indexOf(messages31.get(i));
						if(index31 >= 0) {
							for(int j = 0; j < messages31.size() && !tryAgain; j++)
							if(message.indexOf(messages31.get(j), index31 + 1) == index31 + messages31.get(i).length()) {
								message = message.substring(0, index31) + message.substring(index31 + messages31.get(i).length());
								tryAgain = true;
							}
						}
					}
				}
				
			}
			}
		}
		sc.close();
		return validSum;
	}
	
	private static boolean checkValidity(String message, HashMap<Integer, String> rules, int ruleKey) {
		//System.out.println(message);
		if(rules.get(ruleKey).charAt(0) == '"') {
			if(message.length() == 1 && message.charAt(0) == rules.get(ruleKey).charAt(1)) {
				//System.out.println("yes");
				return true;
			} else {
				//System.out.println("no");
				return false;
			}
		}
		String currentRule[] = rules.get(ruleKey).split(" \\| ");
		String rulesToCheck[] = new String[3];
		String message1 = new String();
		String message2 = new String();
		String message3 = new String();
		boolean valid = false;
		for(int i = 0; i < currentRule.length; i++) {
			rulesToCheck = currentRule[i].split(" ");
			//System.out.println(rulesToCheck.length);
			if(rulesToCheck.length == 1) {
				if(checkValidity(message, rules, Integer.parseInt(rulesToCheck[0]))) {
					valid = true;
				}
			} else if(rulesToCheck.length == 2) {
				for(int j = 1; j < message.length() && !valid; j++) {
					message1 = message.substring(0, j);
					if(checkValidity(message1, rules, Integer.parseInt(rulesToCheck[0]))) {
						message2 = message.substring(j, message.length());
						if(checkValidity(message2, rules, Integer.parseInt(rulesToCheck[1]))) {
							valid = true;
						}
					}
				}
			} else if(rulesToCheck.length == 3) {
				for(int j = 1; j < message.length()-1 && !valid; j++) {
					message1 = message.substring(0, j);
					if(checkValidity(message1, rules, Integer.parseInt(rulesToCheck[0]))) {
						for(int k = j+1; k < message.length() && !valid; k++) {
							message2 = message.substring(j, k);
							if(checkValidity(message2, rules, Integer.parseInt(rulesToCheck[1]))) {
								message3 = message.substring(k, message.length());
								if(checkValidity(message3, rules, Integer.parseInt(rulesToCheck[2]))) {
									valid = true;
								}
							}
						}
					}
				}
			}
		}
		return valid;		
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
					boolean helping = true;
					while(helping) {
						helping = false;
						for(int j = 0; j < infList.size(); j++) {
							for(int k = 0; k < startingPieces.size(); k++) {
								tmp = infList.get(j) + startingPieces.get(k);
								if(tmp.length() < 30) {
									helping = true;
									infList.add(tmp);
								}
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
					boolean helping = true;
					while(helping) {
						helping = false;
						for(int j = 0; j < startingPieces.size(); j++) {
							for(int k = 0; k < endPieces.size(); k++) {
								for(int l = 0; l < infList.size(); l++) {
									tmp = startingPieces.get(j) + infList.get(l) + endPieces.get(k);
									if(tmp.length() < 50) {
										helping = true;
										ans.add(tmp);
									}
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
