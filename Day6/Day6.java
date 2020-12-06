import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Day6 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\git\\AoC_2020\\Day6\\input6.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		int ans = 0, currentGroup;
		Scanner sc = new Scanner(input);
		String list = "";
		while(sc.hasNextLine()) {
			list = list + "\n" + sc.next();
		}
		list = list.substring(1,list.length());
		String[] answers = list.split("\n\n");
		for(int i = 0; i < answers.length; i++) {
			currentGroup = 0;
			for (int j = 97; j < 123; j++) {
				if(answers[i].indexOf(j) >= 0) {
					currentGroup++;
				}
			}
			ans += currentGroup;
		}
		sc.close();
		return ans;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		int ans = 0,  currentGroup;
		Scanner sc = new Scanner(input);
		String list = "";
		boolean charIsCommon;
		while(sc.hasNextLine()) {
			list = list + "\n" + sc.next();
		}
		sc.close();
		list = list.substring(1,list.length());
		String[] answers = list.split("\n\n");
		String[][] currentPerson = new String[answers.length][26];
		for(int i = 0; i < answers.length; i++) {
			currentGroup = 0;
			currentPerson[i] = answers[i].split("\n");
			for (int j = 97; j < 123; j++) {
				charIsCommon = true;
				for(int k = 0; k < currentPerson[i].length; k++) {
					if(currentPerson[i][k].indexOf(j) < 0) {
						charIsCommon = false;
					}
				}
				if(charIsCommon) {
					currentGroup++;
				}
			}
			ans += currentGroup;
		}
		return ans;
	}
	
}