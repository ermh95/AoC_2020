
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
public class Day5 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\git\\AoC_2020\\Day5\\input.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<String> list = new ArrayList<String>();
		int highestSeatID = 0, row, seat, seatID;
		String boardingPass = new String();
		while(sc.hasNext()) {
        	list.add(sc.next());
        }
		sc.close();
		for(int i = 0; i < list.size(); i++) {
			boardingPass = list.get(i);
			row = 0;
			seat = 0;
			for(int j = 0; j < 7; j++) {
				if(boardingPass.charAt(j) == 'B') {
					row += power(2,6-j);
				}
			}
			for(int j = 0; j < 3; j++) {
				if(boardingPass.charAt(7 + j) == 'R') {
					seat += power(2,2-j);
				}
			}
			seatID = row*8 + seat;
			if(seatID > highestSeatID) {
				highestSeatID = seatID;
			}
		}
		return highestSeatID;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<Integer> occupied = new ArrayList<Integer>();
		int mySeatID = 0, row, seat, seatID, searchNmb = 0;
		String boardingPass = new String();
		while(sc.hasNext()) {
        	list.add(sc.next());
        }
		sc.close();
		for(int i = 0; i < list.size(); i++) {
			boardingPass = list.get(i);
			row = 0;
			seat = 0;
			for(int j = 0; j < 7; j++) {
				if(boardingPass.charAt(j) == 'B') {
					row += power(2,6-j);
				}
			}
			for(int j = 0; j < 3; j++) {
				if(boardingPass.charAt(7 + j) == 'R') {
					seat += power(2,2-j);
				}
			}
			seatID = row*8 + seat;
			occupied.add(seatID);
		}
		boolean seatFound = false;
		while(!seatFound) {
			if(!occupied.contains(searchNmb) && occupied.contains(searchNmb-1)) {
				seatFound = true;
				mySeatID = searchNmb;
			}
			searchNmb++;
		}
		return mySeatID;
	}
	
	private static int power(int a, int b) {
		int ans = 1;
		for(int i = 0; i < b; i++) {
			ans *= a;
		}
		return ans;
	}

}
