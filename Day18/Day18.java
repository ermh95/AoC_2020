import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Day18 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day18\\input18.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static long part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String currentLine[] = new String[50];
		long sum = 0;
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" ");
			sum += calculate(currentLine);
		}
		sc.close();
		return sum;
	}
	public static long part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String currentLine[] = new String[50];
		long sum = 0;
		while(sc.hasNextLine()) {
			currentLine = modifyLine(sc.nextLine().split(" "));
			sum += calculate(currentLine);
		}
		sc.close();
		return sum;
	}
	
	private static long calculate(String[] line) {
		long ans = 0;
		int parenthesis = 0;
		int checkingIndex = 1;
		char operation = '+';
		for(int i = 0; i < line.length; i++) {
			if(i%2 == 0) {
				if(line[i].charAt(0) == '(') {
					line[i] = line[i].substring(1);
					if(operation == '+' && parenthesis == 0) {
						ans += calculate(Arrays.copyOfRange(line, i, line.length));
					}
					if(operation == '*' && parenthesis == 0) {
						ans *= calculate(Arrays.copyOfRange(line, i, line.length));
					}
					parenthesis++;
					checkingIndex = 0;
					while(checkingIndex < line[i].length() && line[i].charAt(checkingIndex) == '(') {
						parenthesis++;
						checkingIndex++;
					}
				} else {
					if(operation == '+' && parenthesis == 0) {
						ans += Character.getNumericValue(line[i].charAt(0));
					} else if(operation == '*' && parenthesis == 0) {
						ans *= Character.getNumericValue(line[i].charAt(0));
					}
					checkingIndex = 1;
					while(checkingIndex < line[i].length()) {
						if(line[i].charAt(checkingIndex) == ')') {
							parenthesis--;
							if(parenthesis < 0) {
								return ans;
							}
						}
						checkingIndex++;
					}
				}
			} else {
				operation = line[i].charAt(0);
			}
		}
		return ans;
	}
	
	private static String[] modifyLine(String[] line) { //Adding parenthesis around all additions
		int index = 0, parenthesis = 0;
		boolean endFound = false;
		for(int i = 0; i < line.length; i++) {
			if(line[i].charAt(0) == '+') {
				index = i-1;
				endFound = false;
				parenthesis = 0;
				while(!endFound) {
					parenthesis += countParenthesis(line[index]);
					if(parenthesis >= 0) {
						line[index] = "(" + line[index];
						endFound = true;
					}
					index--;
				}
				index = i+1;
				endFound = false;
				parenthesis = 0;
				while(!endFound) {
					parenthesis += countParenthesis(line[index]);
					if(parenthesis <= 0) {
						line[index] = line[index] + ")";
						endFound = true;
					}
					index++;
				}
			}
		}
		return line;
		
	}
	
	private static int countParenthesis(String a) {
		int count = a.length() - a.replace("(", "").length();
		count -= a.length() - a.replace(")", "").length();
		return count;
	}

}
