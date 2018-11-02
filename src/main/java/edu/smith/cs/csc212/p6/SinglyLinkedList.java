package edu.smith.cs.csc212.p6;

import java.util.Iterator;

import edu.smith.cs.csc212.p6.errors.BadIndexError;
import edu.smith.cs.csc212.p6.errors.EmptyListError;
import edu.smith.cs.csc212.p6.errors.P6NotImplemented;

public class SinglyLinkedList<T> implements P6List<T>, Iterable<T> {
	/**
	 * The start of this list. Node is defined at the bottom of this file.
	 */
	Node<T> start;

	@Override
	public T removeFront() {
		checkNotEmpty();
		T before = start.value;
		start = start.next;
		return before;
	}

	@Override
	public T removeBack() {
		// if list is empty throw error
		checkNotEmpty();
		// if list is one, run removefront
		if (this.size() == 1) {
			return (removeFront());
		} else {
			Node<T> secondLast = start;
			for (Node<T> current = start; current.next != null; current = current.next) {
				secondLast = current;
			}
			T val = secondLast.next.value;
			secondLast.next = null;
			return val;
		}
	}

	@Override
	public T removeIndex(int index) {
		checkNotEmpty();
		throw new P6NotImplemented();
	}

	@Override
	public void addFront(T item) {
		this.start = new Node<T>(item, start);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addBack(T item) {
		// If there's nothing in the list, add the item to the front.
		if (isEmpty()) {
			addFront(item);
		} else {
			// Traverse the list
			Node<T> last = start; // TODO am I assigning last to be start twice if my size is 1?
			for (Node<T> current = start; current == null; current = current.next) {
				current = last;
			}
			// TODO fix the unchecked thing here. make something so that guessing if an item is a node stays in one place.
			last.next = new Node(item, null);
		}
	}

	@Override
	public void addIndex(T item, int index) {
		// if index is 0, call add front
		if (index == 0) {
			this.addFront(item);
		} else {
			// traverse the list
			int i = 0;
			for (Node<T> current = start; current.next != null; current = current.next) {
				// if we come to the end of the list
				if (current.next == null) {
					// and index is one more than i, add to back
					if (index == i + 1) {
						this.addBack(item);
					}
					// and index is
				}

				// otherwise have them make friends.
				i++;
			}

		}
	}

	@Override
	public T getFront() {
		checkNotEmpty();
		return start.value;
	}

	@Override
	public T getBack() {
		checkNotEmpty();
		Node<T> last = this.start;
		for (Node<T> current = start; current.next != null; current = current.next) {
			last = current;
		}
		return last.value;
	}

	@Override
	public T getIndex(int index) { // he means get the item at that index
		checkNotEmpty();
		int i = 0;
		T val = null;
		for (Node<T> current = start; current != null; current = current.next) {
			if (i == index) {
				val = current.value;
			}
			i++;
		}
		return val;
	}

	@Override
	public int size() {
		System.out.println("SIZE CALLED");
		int count = 0;
		for (Node<T> n = this.start; n != null; n = n.next) {
			System.out.println(count);
			count++;
		}
		return count;
	}

	@Override
	public boolean isEmpty() {
		if (start == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Helper method to throw the right error for an empty state.
	 */
	private void checkNotEmpty() {
		if (this.isEmpty()) {
			throw new EmptyListError();
		}
	}

	/**
	 * The node on any linked list should not be exposed. Static means we don't need a "this" of SinglyLinkedList to make a node.
	 * 
	 * @param <T> the type of the values stored.
	 */
	private static class Node<T> {
		/**
		 * What node comes after me?
		 */
		public Node<T> next;
		/**
		 * What value is stored in this node?
		 */
		public T value;

		/**
		 * Create a node with no friends.
		 * 
		 * @param value - the value to put in it.
		 */
		public Node(T value, Node<T> next) {
			this.value = value;
			this.next = next;
		}
	}

	/**
	 * I'm providing this class so that SinglyLinkedList can be used in a for loop for {@linkplain ChunkyLinkedList}. This
	 * Iterator type is what java uses for {@code for (T x : list) { }} loops.
	 * 
	 * @author jfoley
	 *
	 * @param <T>
	 */
	private static class Iter<T> implements Iterator<T> {
		/**
		 * This is the value that walks through the list.
		 */
		Node<T> current;

		/**
		 * This constructor details where to start, given a list.
		 * 
		 * @param list - the SinglyLinkedList to iterate or loop over.
		 */
		public Iter(SinglyLinkedList<T> list) {
			this.current = list.start;
		}

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T found = current.value;
			current = current.next;
			return found;
		}
	}

	/**
	 * Implement iterator() so that {@code SinglyLinkedList} can be used in a for loop.
	 * 
	 * @return an object that understands "next()" and "hasNext()".
	 */
	public Iterator<T> iterator() {
		return new Iter<>(this);
	}
}
