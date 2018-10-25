package edu.smith.cs.csc212.p6;

import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

/**
 * This is an ArrayList
 * 
 * @author barba
 *
 * @param <T>
 */
public class GrowableList<T> implements P6List<T> {
	public static final int START_SIZE = 32;
	private Object[] array;
	private int fill;

	public GrowableList() {
		this.array = new Object[START_SIZE];
		this.fill = 0;
	}

	@Override
	public T removeFront() { // O(n)
		if (array.length == 0) {
			throw new EmptyListError();
		} else {
			// save the object at the front
			@SuppressWarnings("unchecked")
			T saved = (T) array[0];
			// for each slot in array
			for (int i = 0; i < array.length - 1; i++) {
				// take the thing in the next slot
				// insert that thing at the current slot
				array[i] = array[i + 1];
			}
			return saved;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T removeBack() {
		switch (array.length) {
		case 0:
			throw new EmptyListError();
		case 1:
			T saved = (T) array[0];
			array[0] = null;
			return saved;
		default:
			// If the last slot in our array is full, delete it and return
			if (array[array.length - 1] != null) {
				saved = (T) array[array.length - 1];
				array[array.length - 1] = null;
				return saved;
			}
			// if we're here, the last slot of our array is not full
			for (int i = 0; i < array.length - 2; i++) {
				// Loop over the list.
				if (array[i + 1] == null) {
					// copy the last item, delete it from the list, and return the copy
					saved = (T) array[i];
					array[i] = null;
					return saved;
				}
			}
		}
		// TODO why do I have to return null. Shouldn't this be an error?
		return null;
	}

	@Override
	public T removeIndex(int index) {
		throw new P6NotImplemented();
	}

	@Override
	public void addFront(T item) {
		throw new P6NotImplemented();
	}

	@Override
	public void addBack(T item) {
		// If we're at the end of our internal array, make a new, bigger one.
		if (fill >= this.array.length) {
			// make a new, bigger array
			Object[] array2 = new Object[fill * 2];
			// copy current array to new array
			for (int i = 0; i < array.length; i++) {
				array2[i] = array[i];
			}
			array = array2;
		}
		// slap the item in at the end of the array
		this.array[fill++] = item;
	}

	@Override
	public void addIndex(T item, int index) {
		// If we're at the end of our internal array, make a new, bigger one.
		if (fill >= this.array.length) {
			// make a new, bigger array
			Object[] array2 = new Object[fill * 2];
			// copy current array to new array
			for (int i = 0; i < array.length; i++) {
				array2[i] = this.getIndex(i);
			}
			array = array2;
		}
		// if our length is zero, throw error
		// if our length is one,
		throw new P6NotImplemented();
	}

	@Override
	public T getFront() {
		return this.getIndex(0);
	}

	@Override
	public T getBack() {
		return this.getIndex(this.fill - 1);
	}

	/**
	 * Do not allow unchecked warnings in any other method. Keep the "guessing" the objects are actually a T here. Do that by calling this method instead
	 * of using the array directly.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getIndex(int index) {
		return (T) this.array[index];
	}

	@Override
	public int size() {
		return fill;
	}

	@Override
	public boolean isEmpty() {
		return fill == 0;
	}

}
