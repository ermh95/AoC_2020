import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

public class Day20 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day20\\example20.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
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
					//System.out.println(IDs.get(i));
					//System.out.println(IDs.get(j));
					//System.out.println("\n");
				}
			}
			//System.out.println(numOfBorders);
			if(numOfBorders == 2) {
				ans *= IDs.get(i);
			}
			i++;
		}
		return ans;
	}
	
	public static long part2(File input) throws FileNotFoundException{
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
		int numOfBorders = 0;
		int puzzleSize = sqrt(IDs.size());
		int solution[][] = new int[puzzleSize][puzzleSize];
		System.out.println(puzzleSize);
		int i = 0;
		int cornerID = 0;
		long ans = 1;
		ArrayList<Integer> matchingBorders = new ArrayList<Integer>();
		while(i < IDs.size()) {
			matchingBorders.clear();
			numOfBorders = 0;
			ArrayList<Integer> neighbourList = new ArrayList<Integer>();
			for(int j = 0; j < IDs.size(); j++) {
				if(findMatchingBorder(images, IDs.get(i), IDs.get(j)) >= 0 && i!=j) {
					numOfBorders++;
					matchingBorders.add(findMatchingBorder(images, IDs.get(i), IDs.get(j)));
					//System.out.println(IDs.get(i));
					//System.out.println(IDs.get(j));
					//System.out.println("\n");
				}
			}
			//System.out.println(numOfBorders);
			if(numOfBorders == 2) {
				/*if(matchingBorders.contains(0) && matchingBorders.contains(1)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(2) && matchingBorders.contains(3)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(3) && matchingBorders.contains(0)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(4) && matchingBorders.contains(5)) {
					char tmpImage[][] = flipTile(images.get(IDs.get(i)));
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(5) && matchingBorders.contains(6)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					tmpImage = rotateTile(tmpImage);
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(6) && matchingBorders.contains(7)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				} else if(matchingBorders.contains(7) && matchingBorders.contains(4)) {
					char tmpImage[][] = rotateTile(images.get(IDs.get(i)));
					tmpImage = rotateTile(tmpImage);
					images.put(IDs.get(i), tmpImage);
				}*/
				cornerID = IDs.get(i);
			}
			i++;
		}
		System.out.println(solution[0][0]);
		for(i = 0; i < matchingBorders.size(); i++) {
			System.out.println(matchingBorders.get(i));
		}
		for(i = 0; i < imageSize; i++) {
			for(int j = 0; j < imageSize; j++) {
				System.out.print(images.get(solution[0][0])[i][j]);
			}
			System.out.println("\n");
		}
		return ans;
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
		//System.out.println("Border 3 of ID " + ID2 + ": " + border2[3]);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(border1[i].equals(border2[j])) {
					matchingBorder = i;
				}
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(border1r[i].equals(border2[j])) {
					matchingBorder = i+4;
				}
			}
		}
		//System.out.println(ID1);
		//System.out.println(ID2);
		//System.out.println(matchingBorder);
		//System.out.println("\n");
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
