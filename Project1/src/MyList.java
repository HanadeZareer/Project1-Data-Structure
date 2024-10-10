
public class MyList<T extends Comparable<T>> implements Listable<T> {

	// Array of T elements
	private T[] arr;
	private int n = 0; // the size of arr

	// Arg-constructor
	public MyList(int capacity) {
		arr = (T[]) new Comparable[capacity];
	}
	
	// Implement the Listable's methods 
	
	/*
	 * add T data to arr
	 */
	@Override
	public void insert(T data) {
		if (n < arr.length) {
			arr[n] = data;
			n++;
		} else
			System.out.println("Oopps!! No size left :(");

	}

	/*
	 * delete a specified data
	 */
	@Override
	public boolean delete(T data) {
		int index = search(data);
		
		if (index == -1)
			return false;
		
		for (int i = index; i < n-1; i++) {
			arr[i] = arr[i + 1];
		}
		n--;
		return true;
	}

	@Override
	public int search(T data) {

		for (int i = 0; i < n; i++)
			if (data.compareTo(arr[i]) == 0)
				return i;
		return -1;
	}

	/*
	 * delete all data in arr
	 */
	@Override
	public void clear() {
		n = 0;
	}

	/*
	 * display the arr 
	 */
	@Override
	public void print() {
		for (int i = 0; i < n; i++)
			System.out.println(i + ": \t" + arr[i]);
	}

	/*
	 * return T data at specified index  
	 */
	@Override
	public T getAt(int index) {
		if(index >= 0 && index < n)
			return arr[index];
		return null;
	}
	
	/*
	 * return the size of arr
	 */
	@Override
	public int size() {
		return n;
	}

}
