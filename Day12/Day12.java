import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day12 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day12\\input12.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		ArrayList<String> lines = parseInput(input);
		char[] instruction = new char[lines.size()];
		int[] value = new int[lines.size()];
		int[] position = {0, 0};
		int direction = 0;
		for(int i = 0; i < lines.size(); i++) {
			instruction[i] = lines.get(i).charAt(0);
			value[i] = Integer.parseInt(lines.get(i).substring(1));
		}
		for(int i = 0; i < instruction.length; i++) {
			if(instruction[i] == 'N') {
				position[0] += value[i];
			}
			else if(instruction[i] == 'E') {
				position[1] += value[i];
			}
			else if(instruction[i] == 'S') {
				position[0] -= value[i];
			}
			else if(instruction[i] == 'W') {
				position[1] -= value[i];
			}
			else if(instruction[i] == 'L') {
				direction += value[i]/90;
				direction = direction%4;
			}
			else if(instruction[i] == 'R') {
				direction -= value[i]/90;
				if(direction < 0) {
					direction += 4;
				}
			}
			else if(instruction[i] == 'F') {
				if(direction == 0) {
					position[1] += value[i];
				} else if(direction == 1) {
					position[0] += value[i];
				} else if(direction == 2) {
					position[1] -= value[i];
				} else if(direction == 3) {
					position[0] -= value[i];
				}
			}
		}
		return Math.abs(position[0]) + Math.abs(position[1]);
	}

	private static int part2(File input) throws FileNotFoundException{
		ArrayList<String> lines = parseInput(input);
		char[] instruction = new char[lines.size()];
		int[] value = new int[lines.size()];
		int[] position = {0, 0};
		int[] waypoint = {1, 10};
		int[] oldWaypoint = {0, 0};
		for(int i = 0; i < lines.size(); i++) {
			instruction[i] = lines.get(i).charAt(0);
			value[i] = Integer.parseInt(lines.get(i).substring(1));
		}
		for(int i = 0; i < instruction.length; i++) {
			if(instruction[i] == 'N') {
				waypoint[0] += value[i];
			}
			else if(instruction[i] == 'E') {
				waypoint[1] += value[i];
			}
			else if(instruction[i] == 'S') {
				waypoint[0] -= value[i];
			}
			else if(instruction[i] == 'W') {
				waypoint[1] -= value[i];
			}
			else if(instruction[i] == 'R') {
				instruction[i] = 'L'; 
				value[i] = 360-value[i];
			}
			if(instruction[i] == 'L') { // Trigonometry, yay!
				oldWaypoint[0] = waypoint[0];
				oldWaypoint[1] = waypoint[1];
				if(value[i] == 90) {
					waypoint[0] = oldWaypoint[1];
					waypoint[1] = -oldWaypoint[0];
				} else if(value[i] == 180) {
					waypoint[0] = -oldWaypoint[0];
					waypoint[1] = -oldWaypoint[1];
				} else if(value[i] == 270) {
					waypoint[0] = -oldWaypoint[1];
					waypoint[1] = oldWaypoint[0];
				}
			}
			else if(instruction[i] == 'F') {
				position[0] += value[i]*waypoint[0];
				position[1] += value[i]*waypoint[1];
			}
		}
		return Math.abs(position[0]) + Math.abs(position[1]);
	}
	
	private static ArrayList<String> parseInput(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<String> lines = new ArrayList<String>();
		while(sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		sc.close();
		return lines;
	}
}
