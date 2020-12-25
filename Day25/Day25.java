import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day25 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\Documents\\GitHub\\AoC_2020\\Day25\\input25.txt");
		System.out.println(part1(input));
	}
	
	public static long part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		int cardKey = sc.nextInt();
		int doorKey = sc.nextInt();
		sc.close();
		boolean loopNumberFound = false;
		int cardLoops = 0;
		int loop = 0;
		long subjectNumber = 7;
		long modulo = 20201227;
		long value = 1;
		while(!loopNumberFound) {
			value = (value*subjectNumber)%modulo;
			loop++;
			if(value == cardKey) {
				loopNumberFound = true;
				cardLoops = loop;
			}
		}
		value = 1;
		subjectNumber = doorKey;
		for(int i = 0; i < cardLoops; i++) {
			value = (value*subjectNumber)%modulo;
		}
		return value;
	}

}
