
public interface Listable<T> {
	void insert(T data);     // to add data
	boolean delete(T data); // to delete data
	int search(T data);    // to search for data
	void clear();         // to delete all data
	void print();        // to print data
	T getAt(int index); // to return specific data
	int size();        // to return data's size 

}