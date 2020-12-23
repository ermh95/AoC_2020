public class Day23 {

	public static void main(String[] args){
		String input = "135468729";
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static String part1(String input){
		int numberOfRounds = 100;
		int numberOfCups = input.length();
		int cups[] = new int[numberOfCups+1];
		int currentCup = Integer.parseInt(input.substring(0, 1));
		for(int i = 1; i < input.length(); i++) {
			cups[currentCup] = Integer.parseInt(input.substring(i, i+1));
			currentCup = cups[currentCup];
		}
		cups[currentCup] = Integer.parseInt(input.substring(0, 1));
		int startingCup = Integer.parseInt(input.substring(0, 1));
		cups = playGame(cups, numberOfRounds, startingCup);
		String ans = "";
		currentCup = 1;
		for(int i = 1; i < numberOfCups; i++) {
			currentCup = cups[currentCup];
			ans = ans + currentCup;
		}
		return ans;
	}
	
	public static long part2(String input){
		int numberOfRounds = 10000000;
		int numberOfCups = 1000000;
		int cups[] = new int[numberOfCups+1];
		int currentCup = Integer.parseInt(input.substring(0, 1));
		for(int i = 1; i < input.length(); i++) {
			cups[currentCup] = Integer.parseInt(input.substring(i, i+1));
			currentCup = cups[currentCup];
		}
		for(int i = input.length(); i < numberOfCups; i++) {
			cups[currentCup] = i+1;
			currentCup = i+1;
		}
		cups[currentCup] = Integer.parseInt(input.substring(0, 1));
		int startingCup = Integer.parseInt(input.substring(0, 1));
		cups = playGame(cups, numberOfRounds, startingCup);
		long ans = (long)cups[1]*(long)cups[cups[1]];
		return ans;
	}
	
	private static int[] playGame(int[] cups, int numberOfRounds, int startingCup) {
		int cup1, cup2, cup3, destinationCup, tmp;
		int currentCup = startingCup;
		int maxCup = cups.length-1;
		for(int i = 0; i < numberOfRounds; i++) {
			cup1 = cups[currentCup];
			cup2 = cups[cup1];
			cup3 = cups[cup2];
			cups[currentCup] = cups[cup3];
			destinationCup = currentCup - 1;
			while(destinationCup == cup1 || destinationCup == cup2 || destinationCup == cup3 || destinationCup < 1) {
				destinationCup--;
				if(destinationCup < 1) {
					destinationCup = maxCup;
				}
			}
			tmp = cups[destinationCup];
			cups[destinationCup] = cup1;
			cups[cup3] = tmp;
			currentCup = cups[currentCup];
		}
		return cups;
	}

}
