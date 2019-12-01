package phonebook;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class    Person {
	private String  NamePlusSurname;
	private long    number;

	Person(String name, String surname, long number) {
		this.number = number;
		if (surname != null)
			this.NamePlusSurname = name + " " + surname;
		else
			this.NamePlusSurname = name;
	}

	Person(Person newPerson) {
		NamePlusSurname = newPerson.getNamePlusSurname();
		number = newPerson.getNumber();
	}

	String   getNamePlusSurname() { return NamePlusSurname; }
	long     getNumber()          { return number; }
}

class   printInfo {
	private static int min;
	private static int sec;
	private static int ms;

	private static void setData(long time) {
		min = (int)time / 60000;
		sec = (int)time / 1000 % 60;
		ms = (int)time % 1000;
	}

	static void printAll(searchAlgorithms search) {
		System.out.print("Found " + search.getFound() + " / " + search.getAmountToFind() + " entries. ");
		setData(search.getFinishTime());
		System.out.println("Time taken: " + min + " min. " + sec + " sec. " + ms + " ms.");
	}

	static void printAll(searchAlgorithms search, sortAlgorithms sort) {
		System.out.print("Found " + search.getFound() + " / " + search.getAmountToFind() + " entries. ");
		setData(search.getFinishTime() + sort.getFinishTime());
		System.out.println("Time taken: " + min + " min. " + sec + " sec. " + ms + " ms.");

		setData(sort.getFinishTime());
		System.out.print("Sorting time: " + min + " min. " + sec + " sec. " + ms + " ms."
						+ (sort.isBubbleSortBreak() ? " - STOPPED, moved to linear search\n" : "\n"));

		setData(search.getFinishTime());
		System.out.println("Searching time: " + min + " min. " + sec + " sec. " + ms + " ms.");
	}

	static void printData(sortAlgorithms sort) {
		for (Person person : sort.getArray())
			System.out.println(person.getNamePlusSurname());
	}

	static void printData(searchAlgorithms search) {
		for (Person person : search.getArray())
			System.out.println(person.getNamePlusSurname());
	}
}


public class Main {

	private static ArrayList<Person>    parseFile(File book){
		ArrayList<Person>   array = new ArrayList<>();
		Scanner             bookScan;
		String[]            data;

		try {
			bookScan = new Scanner(book);
		} catch (FileNotFoundException e) {
			System.out.println("There is no such file");
			return null;
		}
		while (bookScan.hasNextLine()) {
			data = bookScan.nextLine().split(" ");
			array.add(new Person(data[1], data.length == 2 ? null : data[2], Long.parseLong(data[0])));
		}
		System.out.println();
		return array;
	}

	private static searchAlgorithms  linearSearch(ArrayList<Person> unsortedArray, File searchFile) {
		searchAlgorithms    linearSearch;

		System.out.println("Start searching (linear search)...");
		linearSearch = new searchAlgorithms(unsortedArray, searchFile);
		linearSearch.linearSearch();
		printInfo.printAll(linearSearch);
		System.out.println();
		return linearSearch;
	}

	private static void  bubblePlusJump (ArrayList<Person> unsortedArray,
	                                              searchAlgorithms linearSearch,
	                                              File searchFile)
	{
		sortAlgorithms      bubbleSort;
		searchAlgorithms    jumpSearch;

		bubbleSort = new sortAlgorithms(new ArrayList<>(unsortedArray));
		System.out.println("Start searching (bubble sort + jump search)...");
		bubbleSort.bubbleSort(linearSearch.getFinishTime());
		jumpSearch = new searchAlgorithms(bubbleSort.getArray(), searchFile);
		if (!bubbleSort.isBubbleSortBreak())
			jumpSearch.jumpSearch();
		else {
			linearSearch.zeroVariables();
			linearSearch.setFileScanner(searchFile);
			linearSearch.setArray(bubbleSort.getArray());
			linearSearch.linearSearch();
		}
		printInfo.printAll((bubbleSort.isBubbleSortBreak() ? linearSearch : jumpSearch), bubbleSort);
		System.out.println();
	}

	private static void quickPlusBinary(ArrayList<Person> unsortedArray, File searchFile) {
		searchAlgorithms    binarySearch;
		sortAlgorithms      quickSort;

		quickSort = new sortAlgorithms(new ArrayList<>(unsortedArray));
		System.out.println("Start searching (quick sort + binary search)...");
		quickSort.initializeQuickSort();
		binarySearch = new searchAlgorithms(quickSort.getArray(), searchFile);
		binarySearch.binarySearch();
		printInfo.printAll(binarySearch, quickSort);
	}

    public static void main(String[] args) {
	    File                bookFile;
	    File                searchFile;
	    ArrayList<Person>   unsortedArray;

	    bookFile = new File("/Users/qmebble/Downloads/directory.txt");
	    searchFile = new File("/Users/qmebble/Downloads/find.txt");
	    unsortedArray = parseFile(bookFile);
	    if (unsortedArray == null)
	    	return;
	    bubblePlusJump(unsortedArray,
		                linearSearch(unsortedArray, searchFile),
		                searchFile);
	    quickPlusBinary(unsortedArray, searchFile);
	}
}
