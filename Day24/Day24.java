import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Day24 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day24\\input24.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<int[]> blackTiles = new ArrayList<int[]>();
		String currentLine = new String();
		int position[] = new int[2];
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			position[0] = 0;
			position[1] = 0;
			for(int i = 0; i < currentLine.length(); i++) {
				if(currentLine.charAt(i) == 'e') {
					position[0] += 2;
				} else if(currentLine.charAt(i) == 'w'){
					position[0] -= 2;
				} else if(currentLine.charAt(i) == 'n') {
					i++;
					position[1]++;
					if(currentLine.charAt(i) == 'e') {
						position[0]++;
					} else {
						position[0]--;
					}
				} else {
					i++;
					position[1]--;
					if(currentLine.charAt(i) == 'e') {
						position[0]++;
					} else {
						position[0]--;
					}
				}
			}
			boolean newTile = true;
			for(int i = 0; i < blackTiles.size(); i++) {
				if(position[0] == blackTiles.get(i)[0] && position[1] == blackTiles.get(i)[1]) {
					blackTiles.remove(i);
					newTile= false;
				}
			}
			if(newTile) {
				int tmp[] = {position[0], position[1]};
				blackTiles.add(tmp);
			}
		}
		sc.close();
		return blackTiles.size();
	}
	
	public static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		int days = 100, maxX = 0, minX = 0, maxY = 0, minY = 0;
		ArrayList<int[]> blackTiles = new ArrayList<int[]>();
		String currentLine = new String();
		int position[] = new int[2];
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			position[0] = 0;
			position[1] = 0;
			for(int i = 0; i < currentLine.length(); i++) {
				if(currentLine.charAt(i) == 'e') {
					position[0] += 2;
				} else if(currentLine.charAt(i) == 'w'){
					position[0] -= 2;
				} else if(currentLine.charAt(i) == 'n') {
					i++;
					position[1]++;
					if(currentLine.charAt(i) == 'e') {
						position[0]++;
					} else {
						position[0]--;
					}
				} else {
					i++;
					position[1]--;
					if(currentLine.charAt(i) == 'e') {
						position[0]++;
					} else {
						position[0]--;
					}
				}
			}
			boolean newTile = true;
			for(int i = 0; i < blackTiles.size(); i++) {
				if(position[0] == blackTiles.get(i)[0] && position[1] == blackTiles.get(i)[1]) {
					blackTiles.remove(i);
					newTile= false;
				}
			}
			if(newTile) {
				maxX = Math.max(maxX, position[0]);
				minX = Math.min(minX, position[0]);
				maxY = Math.max(maxY, position[1]);
				minY = Math.min(minY, position[1]);
				int tmp[] = {position[0], position[1]};
				blackTiles.add(tmp);
			}
		}
		sc.close();
		int neighbours;
		for(int i = 0; i < days; i++) {
			ArrayList<int[]> tmp = new ArrayList<int[]>();
			for(int x = minX-2; x < maxX+3; x++) {
				for(int y = minY-1; y < maxY+2; y++) {
					neighbours = 0;
					for(int j = 0; j < blackTiles.size(); j++) {
						if(x+2 == blackTiles.get(j)[0] && y == blackTiles.get(j)[1]) {
							neighbours++;
						}
						if(x-2 == blackTiles.get(j)[0] && y == blackTiles.get(j)[1]) {
							neighbours++;
						}
						if(x+1 == blackTiles.get(j)[0] && y+1 == blackTiles.get(j)[1]) {
							neighbours++;
						}
						if(x-1 == blackTiles.get(j)[0] && y+1 == blackTiles.get(j)[1]) {
							neighbours++;
						}
						if(x+1 == blackTiles.get(j)[0] && y-1 == blackTiles.get(j)[1]) {
							neighbours++;
						}
						if(x-1 == blackTiles.get(j)[0] && y-1 == blackTiles.get(j)[1]) {
							neighbours++;
						}
					}
					boolean isBlack = false;
					for(int j = 0; j < blackTiles.size(); j++) {
						if(x == blackTiles.get(j)[0] && y == blackTiles.get(j)[1]) {
							isBlack = true;
						}
					}
					if(isBlack) {
						if(neighbours > 0 && neighbours < 3) {
							int[] tmpPosition = {x,y};
							tmp.add(tmpPosition);
						}
					} else {
						if(neighbours == 2) {
							int[] tmpPosition = {x,y};
							tmp.add(tmpPosition);
							maxX = Math.max(maxX, x);
							minX = Math.min(minX, x);
							maxY = Math.max(maxY, y);
							minY = Math.min(minY, y);
						}
					}
				}
			}
			blackTiles.clear();
			blackTiles.addAll(tmp);
		}
		return blackTiles.size();
	}
}
