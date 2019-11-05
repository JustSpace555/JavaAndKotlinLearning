import  java.io.File;
import  java.io.FileNotFoundException;
import  java.io.FileWriter;
import  java.io.IOException;
import  java.util.*;
import  java.util.Map.Entry;

public class                            FlashCards {

	private static ArrayList<String>        log;
	private static Map<String[], Integer>   cards;
	private static Scanner                  globalScanner;

	private static void                 storeToLog(String str, boolean print) {
		if (print) {
			System.out.println(str);
		}
		log.add(str);
	}

	private static boolean              findKey(String value) {
		for (Entry<String[], Integer> entry : cards.entrySet()){
			if (entry.getKey()[0].equals(value)) {
				return true;
			}
		}
		return false;
	}

	private static boolean              findValue(String value) {
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			if (entry.getKey()[1].equals(value)) {
				return true;
			}
		}
		return false;
	}

	private static String               findKeyByValue(String value) {
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			if (entry.getKey()[1].equals(value)) {
				return entry.getKey()[0];
			}
		}
		return null;
	}

	private static boolean              checkExist(String value, String param) {
		if ("key".equals(param) && findKey(value)) {
			storeToLog("The card \"" + value + "\" already exists.", true);
			return true;
		}
		else if ("value".equals(param) && findValue(value)) {
			storeToLog("The definition \"" + value + "\" already exists.", true);
			return true;
		}
		return false;
	}

	private static void                 addCard() {
		String      inputKey;
		String      inputVal;
		String[]    temp;

		storeToLog("The card:", true);
		inputKey = globalScanner.nextLine();
		storeToLog(inputKey, false);
		if (checkExist(inputKey, "key")) {
			return ;
		}
		storeToLog("The definition of the card:", true);
		inputVal = globalScanner.nextLine();
		storeToLog(inputVal, false);
		if (checkExist(inputVal, "value")) {
			return;
		}
		temp = new String[2];
		temp[0] = inputKey;
		temp[1] = inputVal;
		cards.put(temp, 0);
		storeToLog("The pair (\"" + inputKey + "\":\"" + inputVal + "\") has been added.", true);
	}

	private static void                 removeCard() {
		String  inputKey;

		storeToLog("The card:", true);
		inputKey = globalScanner.nextLine();
		storeToLog(inputKey, false);
		if (findKey(inputKey)) {
			for (Entry<String[], Integer> loop : cards.entrySet()) {
				if (loop.getKey()[0].equals(inputKey)) {
					cards.remove(loop.getKey(), loop.getValue());
					storeToLog("The card has been removed.", true);
					break;
				}
			}
		}
		else {
			storeToLog("Can't remove \"" + inputKey + "\": there is no such card.", true);
		}
	}

	private static void                 importCards() {
		String                  filePath;
		File                    file;
		Scanner                 fileScan;
		int                     amountAddedCards;
		String                  inputKey;
		String                  inputVal;
		int                     errors;
		String[]                temp;

		storeToLog("File name:", true);
		filePath = globalScanner.nextLine();
		storeToLog(filePath, false);
		file = new File(filePath);
		try {
			fileScan = new Scanner(file);
		}
		catch (FileNotFoundException e) {
			storeToLog("File not found.", true);
			return ;
		}
		amountAddedCards = 0;
		while (fileScan.hasNextLine()) {
			inputKey = fileScan.nextLine();
			inputVal = fileScan.nextLine();
			errors = Integer.parseInt(fileScan.nextLine());
			temp = new String[2];
			temp[0] = inputKey;
			temp[1] = inputVal;
			if (findKey(inputKey)) {
				for (Entry<String[], Integer> entry : cards.entrySet()) {
					if (entry.getKey()[0].equals(inputKey)) {
						cards.remove(entry.getKey(), entry.getValue());
						cards.put(temp, errors);
					}
				}
			}
			else {
				cards.put(temp, errors);
			}
			amountAddedCards++;
		}
		storeToLog(amountAddedCards + " cards have been loaded.", true);
	}

	private static void                 importCards(String filePath) throws IOException {
		File                    file;
		Scanner                 fileScan;
		int                     amountAddedCards;
		String                  inputKey;
		String                  inputVal;
		int                     errors;
		String[]                temp;

		file = new File(filePath);
		fileScan = new Scanner(file);
		amountAddedCards = 0;
		while (fileScan.hasNextLine()) {
			inputKey = fileScan.nextLine();
			inputVal = fileScan.nextLine();
			errors = Integer.parseInt(fileScan.nextLine());
			temp = new String[2];
			temp[0] = inputKey;
			temp[1] = inputVal;
			if (findKey(inputKey)) {
				for (Entry<String[], Integer> entry : cards.entrySet()) {
					if (entry.getKey()[0].equals(inputKey)) {
						cards.remove(entry.getKey(), entry.getValue());
						cards.put(temp, errors);
					}
				}
			}
			else {
				cards.put(temp, errors);
			}
			amountAddedCards++;
		}
		storeToLog(amountAddedCards + " cards have been loaded.", true);
	}

	private static void                 exportCards() throws IOException {
		String      filePath;
		FileWriter  writer;
		int         amountExport;

		storeToLog("File name:", true);
		filePath = globalScanner.nextLine();
		storeToLog(filePath, false);
		writer = new FileWriter(filePath);
		amountExport = 0;
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			writer.write(entry.getKey()[0] + "\n");
			writer.write(entry.getKey()[1] + "\n");
			writer.write(entry.getValue().toString() + "\n");
			amountExport++;
		}
		writer.close();
		storeToLog(amountExport + " cards have been saved.", true);
	}

	private static void                 exportCards(String filePath) throws IOException {
		FileWriter  writer;
		int         amountExport;

		writer = new FileWriter(filePath);
		amountExport = 0;
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			writer.write(entry.getKey()[0] + "\n");
			writer.write(entry.getKey()[1] + "\n");
			writer.write(entry.getValue().toString() + "\n");
			amountExport++;
		}
		writer.close();
		storeToLog(amountExport + " cards have been saved.", true);
	}

	private static void                 ask() {
		int     n;
		int     j;
		Random  rand;
		int     randInt;
		String  inputStr;

		storeToLog("How many times to ask?", true);
		inputStr = globalScanner.nextLine();
		storeToLog(inputStr, false);
		n = Integer.parseInt(inputStr);
		rand = new Random(555);
		for (int i = 0; i < n; i++) {
			randInt = rand.nextInt(cards.size());
			j = 0;
			for (Entry<String[], Integer> loop : cards.entrySet()) {
				if (j == randInt) {
					storeToLog("Print the definition of \"" + loop.getKey()[0] + "\":", true);
					inputStr = globalScanner.nextLine();
					storeToLog(inputStr, false);
					if (loop.getKey()[1].equals(inputStr)) {
						storeToLog("Correct answer.", true);
					}
					else if (findValue(inputStr)){
						storeToLog("Wrong answer. The correct one is \"" + loop.getKey()[1] + "\"" +
							" you've just written the definition of \"" + findKeyByValue(inputStr) + "\".", true);
						cards.computeIfPresent(loop.getKey(), (k, v) -> v + 1);
					}
					else {
						storeToLog("Wrong answer. The correct one is \"" + loop.getKey()[1] +"\".", true);
						cards.computeIfPresent(loop.getKey(), (k, v) -> v + 1);
					}
				}
				if (j > randInt) {
					break;
				}
				j++;
			}
		}
	}

	private static void                 exportLog() throws IOException {
		FileWriter  writer;
		String      filePath;

		storeToLog("File name:", true);
		filePath = globalScanner.nextLine();
		storeToLog(filePath, false);
		writer = new FileWriter(filePath);
		for (String str : log) {
			writer.write(str + "\n");
		}
		storeToLog("The log has been saved.", true);
		writer.close();
	}

	private static void                 hardestCard() {
		int             max;
		int             amountOfMaxes;
		StringBuilder   output;
		int             iterator;

		max = 0;
		amountOfMaxes = 0;
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		if (max == 0) {
			storeToLog("There are no cards with errors.", true);
			return;
		}
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			if (entry.getValue() == max) {
				amountOfMaxes++;
			}
		}
		output = new StringBuilder("The hardest card" + (amountOfMaxes > 1 ? "s are " : " is "));
		iterator = 0;
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			if (entry.getValue() == max) {
				iterator++;
				output.append("\"").append(entry.getKey()[0]).append("\"");
				if (iterator < amountOfMaxes) {
					output.append(", ");
				}
			}
		}
		output.append(". You have ").append(max).append(" errors answering ").append(amountOfMaxes > 1 ? "them." : "it.");
		storeToLog(output.toString(), true);
	}

	private static void                 resetStats() {
		for (Entry<String[], Integer> entry : cards.entrySet()) {
			cards.computeIfPresent(entry.getKey(), (k, v) -> 0);
		}
		storeToLog("Card statistics has been reset.", true);
	}

	private static void                 printAllCards() {
		StringBuilder   output;

		if (cards.isEmpty()) {
			storeToLog("Card's array is empty", true);
			return ;
		}
		output = new StringBuilder();
		for (Entry<String[], Integer> entry : cards.entrySet()){
			output.append("Card: ");
			output.append(entry.getKey()[0]);
			output.append(". Definition: ");
			output.append(entry.getKey()[1]);
			output.append(". Amount of errors: ");
			output.append(entry.getValue());
			output.append("\n");
			storeToLog(output.toString(), true);
			output.delete(0, output.length());
		}
	}


	public static void                  main(String[] args) throws IOException {
		String              inputStr;
		String              importFile;
		String              exportFile;

		globalScanner = new Scanner(System.in);
		cards = new HashMap<>();
		log = new ArrayList<>();
		importFile = null;
		exportFile = null;
		if (args.length != 0) {
			for (int i = 0; i < args.length; i++) {
				if (i + 1 < args.length) {
					if ("-import".equals(args[i])) {
						importFile = args[i + 1];
					}
					if ("-export".equals(args[i])) {
						exportFile = args[i + 1];
					}
				}
			}
		}
		if (importFile != null) {
			importCards(importFile);
		}
		for (;;) {
			storeToLog("Input the action (print cards, add, remove, import, export, ask, exit, log, hardest card," +
							" reset stats):", true);
			inputStr = globalScanner.nextLine();
			storeToLog(inputStr, false);
			if ("exit".equals(inputStr)) {
				storeToLog("Bye bye!", true);
				if (exportFile != null) {
					exportCards(exportFile);
				}
				log.clear();
				break;
			}
			switch (inputStr) {
				case "print cards":
					printAllCards();
					break;
				case "add" :
					addCard();
					break;
				case "remove":
					removeCard();
					break;
				case "import":
					importCards();
					break;
				case "export":
					exportCards();
					break;
				case "ask":
					ask();
					break;
				case "log":
					exportLog();
					break;
				case "hardest card":
					hardestCard();
					break;
				case "reset stats":
					resetStats();
					break;
				default:
					storeToLog("Wrong command", true);
					break;
			}
		}
	}
}
