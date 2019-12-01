package phonebook;

import phonebook.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HashTable {
	int         size;
	Person[]    table;
	int         amountToFind;
	int         found;
	long        startTime;
	long        finishTime;
	Scanner     fileScanner;

	HashTable(int size) {
		this.size = size;
		table = new Person[size];
	}

	HashTable(ArrayList<Person> unsortedArray, File searchFile) {
		try {
			fileScanner = new Scanner(searchFile);
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			fileScanner = null;
		}
		this.size = unsortedArray.size();
		table = new Person[size];
		for (Person i : unsortedArray) {
			put(i);
		}
	}

	int findKey(long key) {
		int hash = (int) key % size;

		while (!(table[hash] == null || table[hash].getNumber() == key)) {
			hash = (hash + 1) % size;

			if (hash == key % size) {
				return -1;
			}
		}

		return hash;
	}

	boolean put(long key, String name, String surname) {
		int idx = findKey(key);

		if (idx == -1) {
			return false;
		}

		table[idx] = new Person(name, surname, key);
		return true;
	}

	boolean put(Person person) {
		int idx = findKey(person.getNumber());

		if (idx == -1) {
			return false;
		}

		table[idx] = new Person(person);
		return true;
	}

	public String get(long key) {
		int idx = findKey(key);

		if (idx == -1 || table[idx] == null) {
			return null;
		}

		return table[idx].getNamePlusSurname();
	}

	void searchHashTable() {
		String  personToFind;

		found = 0;
		amountToFind = 0;
		if (fileScanner == null)
			return;

		lable: while (fileScanner.hasNext()) {
			amountToFind++;
			personToFind = fileScanner.nextLine();
			for (int i = 0; i < size; i++)
				if (table[i].getNamePlusSurname().equals(personToFind)) {
					found++;
					continue lable;
				}
		}
	}
}
