import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day8 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day8\\input8.txt");
		System.out.println(part1(input));
        System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String totalInput = "";
		while(sc.hasNextLine()) {
			totalInput = totalInput + "\n" + sc.nextLine();
		}
		totalInput = totalInput.substring(1);
		sc.close();
		String[] code = totalInput.split("\n");
		String[][] commands = new String[code.length][];
		for(int i = 0; i < code.length; i++) {
			commands[i] = code[i].split(" ");
		}
		
		int pos = 0, acc = 0, finalAcc =0;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		boolean inf = false;
		while(!inf) {
			if(visited.contains(pos)) {
				inf = true;
				finalAcc = acc;
			}
			visited.add(pos);
			if(commands[pos][0].equals("acc")) {
				acc += Integer.parseInt(commands[pos][1]);
				pos++;
			} else if(commands[pos][0].equals("jmp")) {
				pos += Integer.parseInt(commands[pos][1]);
			} else {
				pos++;
			}
		}
		return finalAcc;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String totalInput = "";
		while(sc.hasNextLine()) {
			totalInput = totalInput + "\n" + sc.nextLine();
		}
		totalInput = totalInput.substring(1);
		sc.close();
		String[] code = totalInput.split("\n");
		String[][] commands = new String[code.length][];
		for(int i = 0; i < code.length; i++) {
			commands[i] = code[i].split(" ");
		}
		
		int pos = 0, acc = 0, finalAcc = 0, index = 0;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		boolean inf = false, solutionFound = false, changeFound = false;
		inf = false;
		while(!solutionFound && index < code.length) { //Looping through attempts at switching out different lines
			changeFound = false;
			while(!changeFound) { //This loop switches out a line
				if(commands[index][0].equals("jmp")) {
					commands[index][0] = "nop";
					changeFound = true;
				} else if(commands[index][0].equals("nop")) {
					commands[index][0] = "jmp";
					changeFound = true;
				} else {
					index++;
				}
			}
			acc = 0;
			pos = 0;
			inf = false;
			visited.clear();
			while(pos < code.length && !inf) { //Testing the code with the current changed line
				if(visited.contains(pos)) {
					inf = true;
				}
				visited.add(pos);
				if(commands[pos][0].equals("acc")) {
					acc += Integer.parseInt(commands[pos][1]);
					pos++;
				} else if(commands[pos][0].equals("jmp")) {
					pos += Integer.parseInt(commands[pos][1]);
				} else {
					pos++;
				}
			}
			if(inf) { // reverting the change
				if(commands[index][0].equals("jmp")) {
					commands[index][0] = "nop";
					changeFound = true;
				} else if(commands[index][0].equals("nop")) {
					commands[index][0] = "jmp";
					changeFound = true;
				}
				index++;
			} else {
				solutionFound = true;
			}
		}
		finalAcc = acc;
		return finalAcc;
	}

}
