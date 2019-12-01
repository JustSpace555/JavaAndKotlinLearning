package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class searchAlgorithms {
	private long              startTime;
	private long              finishTime;
	private Scanner           fileScanner;
	private ArrayList<Person> array;
	private int               amountToFind;
	private int               found;

	void    setFileScanner(File searchFile) {
		try {
			fileScanner = new Scanner(searchFile);
		} catch (FileNotFoundException e) {
			System.out.println("There is no such file");
			fileScanner = null;
		}
	}

	searchAlgorithms(ArrayList<Person> array, File searchFile) {
		this.array = array;
		fileScanner = null;
		setFileScanner(searchFile);
		amountToFind = 0;
		found = 0;
	}

	long                getFinishTime()                     { return finishTime; }
	int                 getAmountToFind()                   { return amountToFind; }
	int                 getFound()                          { return found; }
	void                setArray(ArrayList<Person> array)   { this.array = array; }
	ArrayList<Person>   getArray()                          { return array; }

	void     zeroVariables() {
		startTime = 0;
		finishTime = 0;
		if (fileScanner != null) {
			fileScanner.close();
		}
		fileScanner = null;
		amountToFind = 0;
		found = 0;
	}

	private int   backwardSearch(String find, int prevRight, int currentRight) {
		for (int i = prevRight; i < currentRight; i++) {
			if (this.array.get(i).getNamePlusSurname().equals(find)) {
				return i;
			}
		}
		return -1;
	}

	void jumpSearch() {
		int     prevRight;
		int     currentRight;
		int     jumpLength;
		String  personToFind;

		if (fileScanner == null) {
			return;
		}
		startTime = System.currentTimeMillis();
		while (fileScanner.hasNext()) {
			personToFind = fileScanner.nextLine();
			amountToFind++;
			prevRight = 0;
			currentRight = 0;
			if (array.get(prevRight).getNamePlusSurname().equals(personToFind)) {
				found++;
				continue;
			}
			jumpLength = (int) Math.sqrt(array.size());
			while (currentRight < array.size() - 1) {
				currentRight = Math.min(array.size() - 1, currentRight + jumpLength);
				if (array.get(currentRight).getNamePlusSurname().compareTo(personToFind) >= 0) {
					break;
				}
				prevRight = currentRight;
			}
			if (currentRight == array.size() - 1
				&& personToFind.compareTo(array.get(currentRight)
											.getNamePlusSurname()) > 0)
			{
				continue;
			}
			if (backwardSearch(personToFind, prevRight, currentRight) != -1) {
				found++;
			}
		}
		finishTime = System.currentTimeMillis() - startTime;
	}

	void linearSearch() {
		String  personToFind;

		startTime = System.currentTimeMillis();
		while (fileScanner.hasNextLine()) {
			amountToFind++;
			personToFind = fileScanner.nextLine();
			for (Person person : array) {
				if (person.getNamePlusSurname().equals(personToFind)) {
					found++;
					break;
				}
			}
		}
		finishTime = System.currentTimeMillis() - startTime;
	}

	void binarySearch() {
		String  personToFind;
		int     left;
		int     right;
		int     mid;

		startTime = System.currentTimeMillis();
		label: while (fileScanner.hasNext()) {
			personToFind = fileScanner.nextLine();
			amountToFind++;
			left = 0;
			right = array.size() - 1;
			while (left <= right) {
				mid = left + (right - left) / 2;
				if (array.get(mid).getNamePlusSurname().equals(personToFind)) {
					found++;
					continue label;
				}
				else if (personToFind.compareTo(array.get(mid).getNamePlusSurname()) > 0)
					left = mid + 1;
				else
					right = mid - 1;
			}
		}
		finishTime = System.currentTimeMillis() - startTime;
	}
}
