import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Day20 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day20\\input20.txt");
		File monsterFile = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day20\\monster.txt");
		System.out.println(part1(input));
		System.out.println(part2(input, monsterFile));
	}
	
	public static long part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		HashMap<Integer, char[][]> images = new HashMap<Integer, char[][]>();
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		String currentLine = new String();
		int imageSize = 10;
		int tileID = 0;
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			char currentImage[][] = new char[imageSize][imageSize];
			tileID = Integer.parseInt(currentLine.substring(5, 9));
			IDs.add(tileID);
			for(int i = 0; i < imageSize; i++) {
				currentLine = sc.nextLine();
				for(int j = 0; j < imageSize; j++) {
					currentImage[i][j] = currentLine.charAt(j);
				}
			}
			images.put(tileID, currentImage);
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
		}
		sc.close();
		int numOfBorders = 0;
		int i = 0;
		long ans = 1;
		while(i < IDs.size()) {
			numOfBorders = 0;
			for(int j = 0; j < IDs.size(); j++) {
				if(findMatchingBorder(images, IDs.get(i), IDs.get(j)) >= 0 && i!=j) {
					numOfBorders++;
				}
			}
			if(numOfBorders == 2) {
				ans *= IDs.get(i);
			}
			i++;
		}
		return ans;
	}
	
	public static int part2(File input, File monsterFile) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		HashMap<Integer, char[][]> images = new HashMap<Integer, char[][]>();
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		HashMap<Integer, ArrayList<Integer>> neighbours = new HashMap<Integer, ArrayList<Integer>>();
		String currentLine = new String();
		int imageSize = 10;
		int tileID = 0;
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			char currentImage[][] = new char[imageSize][imageSize];
			tileID = Integer.parseInt(currentLine.substring(5, 9));
			IDs.add(tileID);
			for(int i = 0; i < imageSize; i++) {
				currentLine = sc.nextLine();
				for(int j = 0; j < imageSize; j++) {
					currentImage[i][j] = currentLine.charAt(j);
				}
			}
			images.put(tileID, currentImage);
			if(sc.hasNextLine()) {
				sc.nextLine();
			}
		}
		sc.close();
		sc = new Scanner(monsterFile);
		char[][] monster = new char[3][20];
		int row = 0;
		while(sc.hasNextLine()) {//Modeling the monster
			currentLine = sc.nextLine();
			for(int i = 0; i < currentLine.length(); i++) {
				monster[row][i] = currentLine.charAt(i);
			}
			row++;
		}
		sc.close();
		int numOfBorders = 0;
		int puzzleSize = sqrt(IDs.size());
		int solution[][] = new int[puzzleSize][puzzleSize];
		int cornerID = 0;
		ArrayList<Integer> matchingBorders = new ArrayList<Integer>();
		for(int i = 0; i < IDs.size(); i++) {
			matchingBorders.clear();
			numOfBorders = 0;
			ArrayList<Integer> neighbourList = new ArrayList<Integer>();
			for(int j = 0; j < IDs.size(); j++) {
				if(findMatchingBorder(images, IDs.get(i), IDs.get(j)) >= 0 && i!=j) {
					numOfBorders++;
					matchingBorders.add(findMatchingBorder(images, IDs.get(i), IDs.get(j)));
					neighbourList.add(IDs.get(j));
				}
			}
			neighbours.put(IDs.get(i), neighbourList);
			if(numOfBorders == 2) { //Rotating the corner piece to the corrrect orientation.
				if(matchingBorders.contains(0) && (matchingBorders.contains(1) || matchingBorders.contains(4))) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				} else if((matchingBorders.contains(2) || matchingBorders.contains(5)) && matchingBorders.contains(3)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(3) && matchingBorders.contains(0)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				}
				cornerID = IDs.get(i);
			}
		}
		solution[0][0] = cornerID;
		int latestID = cornerID, nextID = 0, rightBorder = 0, perfectBorder = 0;
		int matchingBorder = 0;
		for(int i = 0; i < puzzleSize; i++) { //Solving the puzzle from left to right, top to bottom.
			for(int j = 0; j < puzzleSize; j++) {
				if(i == 0 && j == 0) {
					j++;
				}
				if(j == 0) {
					rightBorder = 2;
					perfectBorder = 5;
				} else {
					rightBorder = 1;
					perfectBorder = 4;
				}
				for(int k = 0; k < neighbours.get(latestID).size(); k++) {
					matchingBorder = findMatchingBorder(images, latestID, neighbours.get(latestID).get(k));
					if(matchingBorder == rightBorder || matchingBorder == perfectBorder) {
						nextID = neighbours.get(latestID).get(k);
						int tries = 0;
						while(matchingBorder != perfectBorder) {
							char tmpImage[][] = new char[imageSize][imageSize];
							if(tries == 4) {
								tmpImage = flipTile(images.get(nextID));
							} else {
								tmpImage = rotateTile(images.get(nextID));
							}
							images.put(nextID, tmpImage);
							matchingBorder = findMatchingBorder(images, latestID, nextID);
							tries++;
						}
					}
				}
				solution[i][j] = nextID;
				latestID = nextID;
			}
			latestID = solution[i][0];
		}
		int fusedPuzzleSize = puzzleSize*(imageSize-2);
		char[][] fusedPuzzle = new char[fusedPuzzleSize][fusedPuzzleSize];
		for(int i = 0; i < puzzleSize; i++) { //Combining the tiles and removing the borders.
			for(int j = 0; j < puzzleSize; j++) {
				for(int k = 0; k < imageSize-2; k++) {
					for(int l = 0; l < imageSize-2; l++) {
						fusedPuzzle[i*(imageSize-2) + k][j*(imageSize-2) + l] = images.get(solution[i][j])[k+1][l+1];
					}
				}
			}
		}
		int monstersFound = 0;
		int monsterSquares[][] = new int[fusedPuzzleSize][fusedPuzzleSize];
		int tries = 0;
		while(monstersFound == 0 && tries < 8) { //Searching for the right orientation and marking monsters
			if(tries == 3) {
				fusedPuzzle = flipTile(fusedPuzzle);
			} else {
				fusedPuzzle = rotateTile(fusedPuzzle);
			}
			tries++;
			for(int i = 0; i < fusedPuzzleSize; i++) {
				for(int j = 0; j < fusedPuzzleSize; j++) {
					if(checkForMonster(fusedPuzzle, monster, i, j)) {
						monstersFound++;
						for(int k = 0; k < monster.length; k++) {
							for(int l = 0; l < monster[0].length; l++) {
								if(monster[k][l] == '#') {
									monsterSquares[i+k][j+l] = 1;
								}
							}
						}
					}
				}
			}
		}
		int roughness = 0;
		for(int i = 0; i < fusedPuzzleSize; i++) {
			for(int j = 0; j < fusedPuzzleSize; j++) {
				if(fusedPuzzle[i][j] == '#' && monsterSquares[i][j] != 1) {
					roughness++;
				}
			}	
		}
		return roughness;
	}
	
	private static boolean checkForMonster(char[][] image, char[][] monster, int starti, int startj) {
		int imageSize = image.length;
		if(startj + monster[0].length > imageSize || starti + monster.length > imageSize) {
			return false;
		}
		boolean monsterFound = true;
		for(int i = 0; i < monster.length; i++) {
			for(int j = 0; j < monster[0].length; j++) {
				if(monster[i][j] == '#' && image[starti + i][startj + j] != '#') {
					monsterFound = false;
				}
			}
		}
		return monsterFound;
	}
	
	private static char[][] rotateTile(char[][] tile){
		int tileSize = tile.length;
		char[][] newTile = new char[tileSize][tileSize];
		for(int i = 0; i < tileSize; i++) {
			for(int j = 0; j < tileSize; j++) {
				newTile[i][j] = tile[j][tileSize-1-i];
			}
		}
		return newTile;
	}
	
	private static char[][] flipTile(char[][] tile){
		int tileSize = tile.length;
		char[][] newTile = new char[tileSize][tileSize];
		for(int i = 0; i < tileSize; i++) {
			for(int j = 0; j < tileSize; j++) {
				newTile[i][j] = tile[tileSize-1-i][j];
			}
		}
		return newTile;
	}
	
	private static int findMatchingBorder(HashMap<Integer, char[][]> images, int ID1, int ID2){
		int imageSize = images.get(ID1).length;
		int matchingBorder  = -1;
		String border1[] = new String[4];
		String border2[] = new String[4];
		String border1r[] = new String[4];
		for(int i = 0; i < 4; i++) {
			border1[i] = "";
			border2[i] = "";
			border1r[i] = "";
		}
		for(int i = 0; i < imageSize; i++) {
			border1[0] = border1[0] + Character.toString(images.get(ID1)[0][i]);
			border1[1] = border1[1] + Character.toString(images.get(ID1)[i][imageSize-1]);
			border1[2] = border1[2] + Character.toString(images.get(ID1)[imageSize-1][imageSize-1-i]);
			border1[3] = border1[3] + Character.toString(images.get(ID1)[imageSize-1-i][0]);
			border2[0] = Character.toString(images.get(ID2)[0][i]) + border2[0];
			border2[1] = Character.toString(images.get(ID2)[i][imageSize-1]) + border2[1];
			border2[2] = Character.toString(images.get(ID2)[imageSize-1][imageSize-1-i]) + border2[2];
			border2[3] = Character.toString(images.get(ID2)[imageSize-1-i][0]) + border2[3];
			border1r[0] = Character.toString(images.get(ID1)[0][i]) + border1r[0];
			border1r[1] = Character.toString(images.get(ID1)[i][imageSize-1]) + border1r[1];
			border1r[2] = Character.toString(images.get(ID1)[imageSize-1][imageSize-1-i]) + border1r[2];
			border1r[3] = Character.toString(images.get(ID1)[imageSize-1-i][0]) + border1r[3];
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(border1[i].equals(border2[j]) || border1r[i].equals(border2[j])) {
					matchingBorder = i;
				}
			}
		}
		if(border1[1].equals(border2[3])) {
			matchingBorder = 4;
		}else if(border1[2].equals(border2[0])) {
			matchingBorder = 5;
		}
		return matchingBorder;
	}
	
	private static int sqrt(int a) {
		int b = 1;
		int ans = 0;
		while(b < 100 && ans == 0) {
			if(b*b == a) {
				ans = b;
			}
			b++;
		}
		return ans;
	}
}
