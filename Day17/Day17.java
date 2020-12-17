import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day17 {
	
	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\sterho\\eclipse-workspace\\AoC_2020_01\\Day17\\input17.txt");
		System.out.println(part1(input));
		System.out.println(part2(input));
	}
	
	public static int part1(File input) throws FileNotFoundException{
		int NUM_OF_CYCLES = 6;
		Scanner sc = new Scanner(input);
		String currentLine = sc.nextLine();
		int initialSize = currentLine.length();
		int systemSize = initialSize + NUM_OF_CYCLES*2 + 1;
		char state[][][] = new char[systemSize][systemSize][systemSize];
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					state[i][j][k] = '.';
				}
			}
		}
		for(int i = 0; i < initialSize; i++) {
			state[systemSize/2+i-initialSize/2+1][systemSize/2+initialSize/2-1][systemSize/2] = currentLine.charAt(i);
		}
		for(int i = 1; i < initialSize && sc.hasNextLine(); i++) {
			currentLine = sc.nextLine();
			for(int j = 0; j < initialSize; j++) {
				state[systemSize/2+j-initialSize/2+1][systemSize/2+initialSize/2-i-1][systemSize/2] = currentLine.charAt(j);
			}
		}
		sc.close();
		for(int i = 0; i < NUM_OF_CYCLES; i++) {
			state = takeStep3(state);
		}
		int activeCount = 0;
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					if(state[i][j][k] == '#') {
						activeCount++;
					}
				}
			}
		}
		return activeCount;
		
	}
	
	public static int part2(File input) throws FileNotFoundException{
		int NUM_OF_CYCLES = 6;
		Scanner sc = new Scanner(input);
		String currentLine = sc.nextLine();
		int initialSize = currentLine.length();
		int systemSize = initialSize + NUM_OF_CYCLES*2 + 1;
		char state[][][][] = new char[systemSize][systemSize][systemSize][systemSize];
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					for(int l = 0; l < systemSize; l++) {
						state[i][j][k][l] = '.';
					}
				}
			}
		}
		for(int i = 0; i < initialSize; i++) {
			state[systemSize/2+i-initialSize/2+1][systemSize/2+initialSize/2-1][systemSize/2][systemSize/2] = currentLine.charAt(i);
		}
		for(int i = 1; i < initialSize && sc.hasNextLine(); i++) {
			currentLine = sc.nextLine();
			for(int j = 0; j < initialSize; j++) {
				state[systemSize/2+j-initialSize/2+1][systemSize/2+initialSize/2-i-1][systemSize/2][systemSize/2] = currentLine.charAt(j);
			}
		}
		sc.close();
		for(int i = 0; i < NUM_OF_CYCLES; i++) {
			state = takeStep4(state);
		}
		int activeCount = 0;
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					for(int l = 0; l < systemSize; l++) {
						if(state[i][j][k][l] == '#') {
							activeCount++;
						}
					}
				}
			}
		}
		return activeCount;
		
	}
	
	private static char[][][] takeStep3(char[][][] currentState){
		int systemSize = currentState.length;
		int  neighbourCount;
		char newState[][][] = new char[systemSize][systemSize][systemSize];
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					newState[i][j][k] = currentState[i][j][k];
					neighbourCount = 0;
					for(int di = -1; di <= 1; di++) {
						for(int dj = -1; dj <= 1; dj++) {
							for(int dk = -1; dk <= 1; dk++) {
								if(!(di==0&&dj==0&&dk==0)&&i+di>=0&&i+di<systemSize&&j+dj>=0&&j+dj<systemSize&&k+dk>=0&&k+dk<systemSize) {
									if(currentState[i+di][j+dj][k+dk] == '#') {
										neighbourCount++;
									}
								}
							}
						}
					}
					if(currentState[i][j][k] == '#' && !(neighbourCount==2||neighbourCount==3)) {
						newState[i][j][k] = '.';
					} else if(currentState[i][j][k] == '.' && neighbourCount==3) {
						newState[i][j][k] = '#';
					}
				}
			}
		}
		return newState;
	}
	
	private static char[][][][] takeStep4(char[][][][] currentState){
		int systemSize = currentState.length;
		int  neighbourCount;
		char newState[][][][] = new char[systemSize][systemSize][systemSize][systemSize];
		for(int i = 0; i < systemSize; i++) {
			for(int j = 0; j < systemSize; j++) {
				for(int k = 0; k < systemSize; k++) {
					for(int l = 0; l < systemSize; l++) {
						newState[i][j][k][l] = currentState[i][j][k][l];
						neighbourCount = 0;
						for(int di = -1; di <= 1; di++) {
							for(int dj = -1; dj <= 1; dj++) {
								for(int dk = -1; dk <= 1; dk++) {
									for(int dl = -1; dl <= 1; dl++) {
										if(!(di==0&&dj==0&&dk==0&&dl==0)&&i+di>=0&&i+di<systemSize&&j+dj>=0&&j+dj<systemSize&&k+dk>=0&&k+dk<systemSize&&l+dl>=0&&l+dl<systemSize) {
											if(currentState[i+di][j+dj][k+dk][l+dl] == '#') {
												neighbourCount++;
											}
										}
									}
								}
							}
						}
						if(currentState[i][j][k][l] == '#' && !(neighbourCount==2||neighbourCount==3)) {
							newState[i][j][k][l] = '.';
						} else if(currentState[i][j][k][l] == '.' && neighbourCount==3) {
							newState[i][j][k][l] = '#';
						}
					}
				}
			}
		}
		return newState;
	}
	
}
