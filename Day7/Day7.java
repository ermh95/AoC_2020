import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day7 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day7\\input7.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String totalInput = "";
		String currentBag = new String();
		ArrayList<String> outerCandidates = new ArrayList<String>();
		while(sc.hasNextLine()) {
			totalInput = totalInput + "\n" + sc.nextLine();
		}
		totalInput = totalInput.substring(1);
		sc.close();
		String[] rules = totalInput.split("\n");
		String[][] bags = new String[rules.length][];
		for(int i = 0; i < rules.length; i++) {
			bags[i] = rules[i].split("( contain )|(, )");
			bags[i][bags[i].length-1] = bags[i][bags[i].length-1].substring(0,bags[i][bags[i].length-1].length()-1);
		}
		int tmp = -1;
		while(outerCandidates.size() > tmp) {
			tmp = outerCandidates.size();
			for(int i = 0; i < bags.length; i++) {
				for(int j = 1; j < bags[i].length; j++) {
					if(bags[i][j].charAt(0) == '1') {
						currentBag = bags[i][j].substring(2) + "s";
					} else {
						currentBag = bags[i][j].substring(2);
					}
					if((currentBag.equals("shiny gold bags") || outerCandidates.contains(currentBag)) && !outerCandidates.contains(bags[i][0])) {
						outerCandidates.add(bags[i][0]);
					}
				}
			}
		}
		int ans = outerCandidates.size();
		return ans;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String totalInput = "";
		while(sc.hasNextLine()) {
			totalInput = totalInput + "\n" + sc.nextLine();
		}
		totalInput = totalInput.substring(1);
		sc.close();
		String[] rules = totalInput.split("\n");
		String[][] bags = new String[rules.length][];
		for(int i = 0; i < rules.length; i++) {
			bags[i] = rules[i].split("( contain )|(, )");
			bags[i][bags[i].length-1] = bags[i][bags[i].length-1].substring(0,bags[i][bags[i].length-1].length()-1);
		}
		int ans = howManyBags(bags,"shiny gold bags");
		return ans;
	}
	
	private static int howManyBags(String[][] bags, String inputBag) {
		int bagCount = 0;
		String currentBag = new String();
		for(int i = 0; i < bags.length; i++) {
			if(bags[i][0].equals(inputBag)) {
				for(int j = 1; j < bags[i].length; j++) {
					if(bags[i][j].equals("no other bags")) {
						return 1;
					} else {
						int numberOfBags = Integer.parseInt(bags[i][j].substring(0,1));
						if(numberOfBags == 1) {
							currentBag = bags[i][j].substring(2) + "s";
						} else {
							currentBag = bags[i][j].substring(2);
						}
						bagCount += numberOfBags*howManyBags(bags,currentBag);
					}
				}
			}
		}
		if(!inputBag.equals("shiny gold bags")) {
			bagCount++;
		}
		return bagCount;
	}
}