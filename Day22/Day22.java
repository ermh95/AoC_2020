import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Day22 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day22\\input22.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		sc.nextLine();
		String currentLine = sc.nextLine();
		ArrayList<Integer> deck1 = new ArrayList<Integer>();
		ArrayList<Integer> deck2 = new ArrayList<Integer>();
		while(!currentLine.equals("")) {
			deck1.add(Integer.parseInt(currentLine));
			currentLine = sc.nextLine();
		}
		sc.nextLine();
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			deck2.add(Integer.parseInt(currentLine));
		}
		sc.close();
		int score = combat(deck1, deck2);
		return Math.abs(score);
	}
	
	public static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		sc.nextLine();
		String currentLine = sc.nextLine();
		ArrayList<Integer> deck1 = new ArrayList<Integer>();
		ArrayList<Integer> deck2 = new ArrayList<Integer>();
		while(!currentLine.equals("")) {
			deck1.add(Integer.parseInt(currentLine));
			currentLine = sc.nextLine();
		}
		sc.nextLine();
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine();
			deck2.add(Integer.parseInt(currentLine));
		}
		sc.close();
		int score = recursiveCombat(deck1, deck2);
		return Math.abs(score);
	}
	
	private static int recursiveCombat(ArrayList<Integer> deck1, ArrayList<Integer> deck2) {
		ArrayList<ArrayList<Integer>> history1 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> history2 = new ArrayList<ArrayList<Integer>>();
		int card1 = 0, card2 = 0;
		boolean winner = true, repeat = true;
		while(deck1.size() > 0 && deck2.size() > 0) {
			for(int i = 0; i < history1.size(); i++) {
				repeat = history1.get(i).size() == deck1.size() && history2.get(i).size() == deck2.size();
				for(int k = 0; k < history1.get(i).size() && repeat; k++) {
					if(history1.get(i).get(k) != deck1.get(k)) {
						repeat = false;
					}
				}
				for(int k = 0; k < history2.get(i).size() && repeat; k++) {
					if(history2.get(i).get(k) != deck2.get(k)) {
						repeat = false;
					}
				}
				if(repeat) {
					int score = 0;
					for(int j = 0; j < deck1.size(); j++) {
						score += deck1.get(deck1.size()-1-j)*(j+1);
					}
					return score;
				}
			}
			ArrayList<Integer> tmp1 = new ArrayList<Integer>();
			ArrayList<Integer> tmp2 = new ArrayList<Integer>();
			for(int i = 0; i < deck1.size(); i++) {
				tmp1.add(deck1.get(i));
			}
			for(int i = 0; i < deck2.size(); i++) {
				tmp2.add(deck2.get(i));
			}
			history1.add(tmp1);
			history2.add(tmp2);
			card1 = deck1.get(0);
			card2 = deck2.get(0);
			deck1.remove(0);
			deck2.remove(0);
			if(deck1.size() >= card1 && deck2.size() >= card2) {
				ArrayList<Integer> newDeck1 = new ArrayList<Integer>(deck1.subList(0, card1));
				ArrayList<Integer> newDeck2 = new ArrayList<Integer>(deck2.subList(0, card2));
				winner = recursiveCombat(newDeck1, newDeck2) > 0;
			} else {
				winner = card1 > card2;
			}
			if(winner) {
				deck1.add(card1);
				deck1.add(card2);
			} else {
				deck2.add(card2);
				deck2.add(card1);
			}
		}
		int score = 0;
		if(winner) {
			for(int i = 0; i < deck1.size(); i++) {
				score += deck1.get(deck1.size()-1-i)*(i+1);
			}
		} else {
			for(int i = 0; i < deck2.size(); i++) {
				score -= deck2.get(deck2.size()-1-i)*(i+1);
			}
		}
		return score;
	}
	
	private static int combat(ArrayList<Integer> deck1, ArrayList<Integer> deck2) {
		int card1 = 0, card2 = 0;
		boolean winner = true;
		while(deck1.size() > 0 && deck2.size() > 0) {
			card1 = deck1.get(0);
			card2 = deck2.get(0);
			deck1.remove(0);
			deck2.remove(0);
			winner = card1 > card2;
			if(winner) {
				deck1.add(card1);
				deck1.add(card2);
			} else {
				deck2.add(card2);
				deck2.add(card1);
			}
		}
		int score = 0;
		if(winner) {
			for(int i = 0; i < deck1.size(); i++) {
				score += deck1.get(deck1.size()-1-i)*(i+1);
			}
		} else {
			for(int i = 0; i < deck2.size(); i++) {
				score -= deck2.get(deck2.size()-1-i)*(i+1);
			}
		}
		return score;
	}

}
