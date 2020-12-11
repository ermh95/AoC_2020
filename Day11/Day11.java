import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day11 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day11\\input11.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		char[][] currentState = parseInput(input);
		char[][] lastState = new char[currentState.length][currentState[0].length];
		boolean changing = true;
		while(changing) {
			lastState = currentState;
			currentState = calculateStep(currentState, false);
			if(Arrays.deepEquals(currentState, lastState)) {
				changing = false;
			}
		}
		int freeCount = 0;
		for(int i = 0; i < currentState.length; i++) {
			for(int j = 0; j < currentState[0].length; j++) {
				if(currentState[i][j] == '#') {
					freeCount++;
				}
			}
		}
		return freeCount;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		char[][] currentState = parseInput(input);
		char[][] lastState = new char[currentState.length][currentState[0].length];
		boolean changing = true;
		while(changing) {
			lastState = currentState;
			currentState = calculateStep(currentState, true);
			if(Arrays.deepEquals(currentState, lastState)) {
				changing = false;
			}
		}
		int freeCount = 0;
		for(int i = 0; i < currentState.length; i++) {
			for(int j = 0; j < currentState[0].length; j++) {
				if(currentState[i][j] == '#') {
					freeCount++;
				}
			}
		}
		return freeCount;
	}
	
	private static char[][] parseInput(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<String> rows = new ArrayList<String>();
		while(sc.hasNextLine()) {
			rows.add(sc.nextLine());
		}
		sc.close();
		char[][] currentState = new char[rows.size()][rows.get(0).length()];
		for(int i = 0; i < currentState.length; i++) {
			for(int j = 0; j < currentState[0].length; j++) {
				currentState[i][j] = rows.get(i).charAt(j);
			}
		}
		return currentState;
	}
	
	private static char[][] calculateStep(char[][] currentState, boolean farsight){
		char[][] newState = new char[currentState.length][currentState[0].length];
		int occupied;
		int leaveLimit = 4 + (farsight? 1:0);
		for(int i = 0; i < currentState.length; i++) {
			for(int j = 0; j < currentState[0].length; j++) {
				if(currentState[i][j] == '.') {
					newState[i][j] = '.';
				} else {
					occupied = 0;
					for(int dx = -1; dx <= 1; dx++) {
						for(int dy = -1; dy <= 1; dy++) {
							if(!(dx==0 && dy==0)) {
								occupied += directionOccupied(currentState, i, j, dx, dy, farsight)? 1:0;
							}
						}
					}
					if(occupied == 0) {
						newState[i][j] = '#';
					}else if(occupied >= leaveLimit) {
						newState[i][j] = 'L';
					} else {
						newState[i][j] = currentState[i][j];
					}
				}
			}
		}
		return newState;
	}
	
	private static boolean directionOccupied(char[][] currentState, int x, int y, int dx, int dy, boolean farsight){
		int step = 1;
		boolean canSee = true;
		boolean isOccupied = false;
		while(canSee) {
			if(x+step*dx >= currentState.length || x+step*dx < 0 || y+step*dy >= currentState[x].length || y+step*dy < 0 ) {
				isOccupied = false;
				canSee = false;
			} else if(currentState[x+step*dx][y+step*dy] == '.') {
				if(farsight) {
					step++;
				} else {
					return false;
				}
			} else if(currentState[x+step*dx][y+step*dy] == '#') {
				isOccupied = true;
				canSee = false;
			} else {
				isOccupied = false;
				canSee = false;
			}
		}
		return isOccupied;
	}

}
