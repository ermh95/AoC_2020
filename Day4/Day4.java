import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
public class Day4 {

	public static void main(String[] args) throws FileNotFoundException{
		File input = new File("C:\\Users\\Erik\\git\\AoC_2020\\Day4\\input.txt");
        System.out.println(part1(input));
        System.out.println(part2(input));
	}
	
	private static int part1(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
        String list = "";
        int numOfProp = 8, validCount = 0;
        while(sc.hasNextLine()) {
        	list = list + "\n" + sc.nextLine();
        }
        sc.close();
        list = list.substring(1,list.length());
        String[] passports = list.split("\n\n");
        String[][] properties = new String[passports.length][numOfProp];
        for(int i = 0; i < passports.length; i++) {
        	properties[i] = passports[i].split("(\s)|(\n)");
        	for(int j = 0; j < properties[i].length; j++) {
        		properties[i][j] = properties[i][j].substring(0, 3);
        	}
        	if(Arrays.asList(properties[i]).contains("byr") && Arrays.asList(properties[i]).contains("iyr") &&
        			Arrays.asList(properties[i]).contains("eyr") && Arrays.asList(properties[i]).contains("hgt") &&
        			Arrays.asList(properties[i]).contains("hcl") && Arrays.asList(properties[i]).contains("ecl") &&
        			Arrays.asList(properties[i]).contains("pid")) {
        		validCount++;
        	}
        }
        return validCount;
	}
	
	private static int part2(File input) throws FileNotFoundException{
		Scanner sc = new Scanner(input);
        String list = "";
        String unit = new String();
        int numOfProp = 8, validCount = 0, numVal;
        boolean byr, iyr, eyr, hgt, hcl, ecl, pid, approvedChar;
        while(sc.hasNextLine()) {
        	list = list + "\n" + sc.nextLine();
        }
        sc.close();
        list = list.substring(1,list.length());
        String[] passports = list.split("\n\n");
        String[][] properties = new String[passports.length][numOfProp];
        String[][][] value = new String[passports.length][numOfProp][2];
        for(int i = 0; i < passports.length; i++) {
        	properties[i] = passports[i].split("(\s)|(\n)");
        	byr = false;
        	iyr = false;
        	eyr = false;
        	hgt = false;
        	hcl = false;
        	ecl = false;
        	pid = false;
        	for(int j = 0; j < properties[i].length; j++) {
        		value[i][j] = properties[i][j].split(":");
        		if(value[i][j][0].equals("byr")) {
        			numVal = Integer.parseInt(value[i][j][1]);
        			if(numVal >= 1920 && numVal <= 2002) {
        				byr = true;
        			}
        		}
        		if(value[i][j][0].equals("iyr")) {
        			numVal = Integer.parseInt(value[i][j][1]);
        			if(numVal >= 2010 && numVal <= 2020) {
        				iyr = true;
        			}
        		}
        		if(value[i][j][0].equals("eyr")) {
        			numVal = Integer.parseInt(value[i][j][1]);
        			if(numVal >= 2020 && numVal <= 2030) {
        				eyr = true;
        			}
        		}
        		if(value[i][j][0].equals("hgt")) {
        			unit = value[i][j][1].substring(value[i][j][1].length()-2,value[i][j][1].length());
        			if(unit.equals("cm")) {
        				numVal = Integer.parseInt(value[i][j][1].substring(0, value[i][j][1].length()-2));
        				if(numVal >= 150 && numVal <= 193) {
        					hgt = true;
        				}
        			}
        			if(unit.equals("in")) {
        				numVal = Integer.parseInt(value[i][j][1].substring(0, value[i][j][1].length()-2));
        				if(numVal >= 59 && numVal <= 76) {
        					hgt = true;
        				}
        			}
        		}
        		if(value[i][j][0].equals("hcl")) {
        			if(value[i][j][1].length() == 7 && value[i][j][1].substring(0,1).equals("#")) {
        				approvedChar = true;
        				for(int k = 1; k < 7; k++) {
        					numVal = (int) value[i][j][1].charAt(k);
        					if(!((numVal > 47 && numVal < 58) || (numVal > 96 && numVal < 103))) {
        						approvedChar = false;
        					}
        				}
        				hcl = approvedChar;
        			}
        		}
        		if(value[i][j][0].equals("ecl")) {
        			if(value[i][j][1].equals("amb") || value[i][j][1].equals("blu") || value[i][j][1].equals("brn") ||
        					value[i][j][1].equals("gry") || value[i][j][1].equals("grn") ||
        					value[i][j][1].equals("hzl") || value[i][j][1].equals("oth")) {
        				ecl = true;
        			}
        		}
        		if(value[i][j][0].equals("pid")) {
        			if(value[i][j][1].length() == 9) {
        				approvedChar = true;
        				for(int k = 0; k < 9; k++) {
        					numVal = (int) value[i][j][1].charAt(k);
        					if(!(numVal > 47 && numVal < 58)) {
        						approvedChar = false;
        					}
        				}
        				pid = approvedChar;
        			}
        		}
        	}
        	if(byr && iyr && eyr && hgt && hcl && ecl && pid) {
    			validCount++;
    		}
        }
        return validCount;
	}
}
