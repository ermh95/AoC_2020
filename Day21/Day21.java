import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class Day21 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day21\\input21.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String currentLine[] = new String[2];
		String ingredients[] = new String[2];
		String allergens[] = new String[2];
		HashMap<String, ArrayList<String>> candidates = new HashMap<String, ArrayList<String>>();
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" \\(contains ");
			allergens = currentLine[1].split("(, )|(\\))");
			ingredients = currentLine[0].split(" ");
			for(int i = 0; i < allergens.length; i++) {
				if(candidates.get(allergens[i]) == null) {
					ArrayList<String> ingredientList = new ArrayList<String>();
					for(int j = 0; j < ingredients.length; j++) {
						ingredientList.add(ingredients[j]);
					}
					candidates.put(allergens[i], ingredientList);
				} else {
					ArrayList<String> filteredList = new ArrayList<String>();
					for(int j = 0; j < ingredients.length; j++) {
						if(candidates.get(allergens[i]).contains(ingredients[j])) {
							filteredList.add(ingredients[j]);
						}
					}
					candidates.put(allergens[i], filteredList);
				}
			}
		}
		sc.close();
		ArrayList<String> dangerousList = new ArrayList<String>();
		for(String key: candidates.keySet()) {
			for(int i = 0; i < candidates.get(key).size(); i++) {
				if(!dangerousList.contains(candidates.get(key).get(i))) {
					dangerousList.add(candidates.get(key).get(i));
				}
			}
		}
		sc = new Scanner(input);
		int safeSum = 0;
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" \\(contains ");
			allergens = currentLine[1].split("(, )|(\\))");
			ingredients = currentLine[0].split(" ");
			for(int i = 0; i < ingredients.length; i++) {
				if(!dangerousList.contains(ingredients[i])) {
					safeSum++;
				}
			}
		}
		sc.close();
		return safeSum;
	}
	
	public static String part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		String currentLine[] = new String[2];
		String ingredients[] = new String[2];
		String allergens[] = new String[2];
		HashMap<String, ArrayList<String>> candidates = new HashMap<String, ArrayList<String>>();
		while(sc.hasNextLine()) {
			currentLine = sc.nextLine().split(" \\(contains ");
			allergens = currentLine[1].split("(, )|(\\))");
			ingredients = currentLine[0].split(" ");
			for(int i = 0; i < allergens.length; i++) {
				if(candidates.get(allergens[i]) == null) {
					ArrayList<String> ingredientList = new ArrayList<String>();
					for(int j = 0; j < ingredients.length; j++) {
						ingredientList.add(ingredients[j]);
					}
					candidates.put(allergens[i], ingredientList);
				} else {
					ArrayList<String> filteredList = new ArrayList<String>();
					for(int j = 0; j < ingredients.length; j++) {
						if(candidates.get(allergens[i]).contains(ingredients[j])) {
							filteredList.add(ingredients[j]);
						}
					}
					candidates.put(allergens[i], filteredList);
				}
			}
		}
		sc.close();
		HashMap<String, String> truth = new HashMap<String, String>();
		boolean helping = true;
		String trueIngredient = new String();
		String toRemove = new String();
		int startingSize = 0;
		while(helping) {
			startingSize = candidates.size();
			for(String key: candidates.keySet()) {
				if(candidates.get(key).size() == 1) {
					trueIngredient = candidates.get(key).get(0);
					truth.put(key, trueIngredient);
					toRemove = key;
					for(String key2: candidates.keySet()) {
						candidates.get(key2).remove(trueIngredient);
					}
				}
			}
			candidates.remove(toRemove);
			if(candidates.size() == startingSize || candidates.size()==0) {
				helping = false;
			}
		}
		ArrayList<String> sortedKeys=new ArrayList<String>(truth.keySet());
		Collections.sort(sortedKeys);
		String ans = "";
		for(int i = 0; i < sortedKeys.size(); i++) {
			ans = ans + truth.get(sortedKeys.get(i));
			if(i != sortedKeys.size()-1) {
				ans = ans + ",";
			}
		}
		return ans;
	}
}
