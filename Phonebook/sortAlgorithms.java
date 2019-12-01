package phonebook;

import java.util.ArrayList;

class sortAlgorithms {

	private boolean           bubbleSortBreak;
	private long              startTime;
	private long              finishTime;
	private ArrayList<Person> array;

	sortAlgorithms(ArrayList<Person> array) {
		this.array = array;
		startTime = 0;
		finishTime = 0;
		bubbleSortBreak = false;
	}

	boolean              isBubbleSortBreak()                   { return bubbleSortBreak; }
	long                 getFinishTime()                       { return finishTime; }
	ArrayList<Person>    getArray()                            { return array; }

	private void swap(int i, int j) {
		Person  temp = array.get(i);

		array.set(i, array.get(j));
		array.set(j, temp);
	}

	void bubbleSort(long linearSearch) {
		startTime = System.currentTimeMillis();
		label: for (int i = 0; i < array.size() - 1; i++) {
			for (int j = 0; j < array.size() - i - 1; j++) {
				if (System.currentTimeMillis() - startTime >= linearSearch * 10) {
					bubbleSortBreak = true;
					break label;
				}
				if (array.get(j).getNamePlusSurname().equals("Zonnya Sasha"))
					System.out.println(1);
				if (array.get(j).getNamePlusSurname().compareTo(array.get(j + 1).getNamePlusSurname()) > 0)
					if (j + 1 < array.size())
						swap(j, j + 1);
			}
		}
		finishTime = System.currentTimeMillis() - startTime;
	}

	private int quickSortPartition(int left, int right) {
		Person  pivot = array.get(right);
		int partitionIndex = left - 1;

		for (int j = left; j < right; j++) {
			if (array.get(j).getNamePlusSurname().compareTo(pivot.getNamePlusSurname()) < 0) {
				partitionIndex++;
				swap(partitionIndex, j);
			}
		}
		swap(partitionIndex + 1, right);
		return partitionIndex + 1;
	}

	private void quickSort(int left, int right) {
		if (left < right) {
			int partition = quickSortPartition(left, right);
			quickSort(left, partition - 1);
			quickSort(partition + 1, right);
		}
	}

	void initializeQuickSort() {
		startTime = System.currentTimeMillis();
		quickSort(0, array.size() - 1);
		finishTime = System.currentTimeMillis() - startTime;
	}
}
