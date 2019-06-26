/******************************************************************************
 *  Compilation:  javac RedBlackBST.java
 *  Execution:    java RedBlackBST < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/33balanced/tinyST.txt
 *
 *  A symbol table implemented using a left-leaning red-black BST.
 *  This is the 2-3 version.
 *
 *  Note: commented out assertions because DrJava now enables assertions
 *        by default.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java RedBlackBST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;

import edu.ufp.esocial.api.util.DatabaseST;

/**
 * The {@code BST} class represents an ordered symbol table of generic key-value
 * pairs. It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 * <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods. It also
 * provides ordered methods for finding the <em>minimum</em>, <em>maximum</em>,
 * <em>floor</em>, and <em>ceiling</em>. It also provides a <em>keys</em> method
 * for iterating over all of the keys. A symbol table implements the
 * <em>associative array</em> abstraction: when associating a value with a key
 * that is already in the symbol table, the convention is to replace the old
 * value with the new value. Unlike {@link java.util.Map}, this class uses the
 * convention that values cannot be {@code null}—setting the value associated
 * with a key to {@code null} is equivalent to deleting the key from the symbol
 * table.
 * <p>
 * This implementation uses a left-leaning red-black BST. It requires that the
 * key type implements the {@code Comparable} interface and calls the
 * {@code compareTo()} and method to compare two keys. It does not call either
 * {@code equals()} or {@code hashCode()}. The <em>put</em>, <em>contains</em>,
 * <em>remove</em>, <em>minimum</em>, <em>maximum</em>, <em>ceiling</em>, and
 * <em>floor</em> operations each take logarithmic time in the worst case, if
 * the tree becomes unbalanced. The <em>size</em>, and <em>is-empty</em>
 * operations take constant time. Construction takes constant time.
 * <p>
 * For additional documentation, see
 * <a href="https://algs4.cs.princeton.edu/33balanced">Section 3.3</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne. For other
 * implementations of the same API, see {@link ST}, {@link BinarySearchST},
 * {@link SequentialSearchST}, {@link BST}, {@link SeparateChainingHashST},
 * {@link LinearProbingHashST}, and {@link AVLTreeST}.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */

public class RedBlackBST<Key extends Comparable<Key>, Value> implements DatabaseST<Key, Value> {

	private static final boolean RED = true;
	private static final boolean BLACK = false;

	private Node root; // root of the BST

	// BST helper node data type
	private class Node {
		private Key key; // key
		private Value val; // associated data
		private Node left, right; // links to left and right subtrees
		private boolean color; // color of parent link
		private int size; // subtree count

		public Node(Key key, Value val, boolean color, int size) {
			this.key = key;
			this.val = val;
			this.color = color;
			this.size = size;
		}
	}

	/**
	 * Initializes an empty symbol table.
	 */
	public RedBlackBST() {
	}

	/***************************************************************************
	 * Node helper methods.
	 ***************************************************************************/
	// is node x red; false if x is null ?
	private boolean isRed(Node x) {
		if (x == null) {
			return false;
		}
		return x.color == RED;
	}

	// number of node in subtree rooted at x; 0 if x is null
	private int size(Node x) {
		if (x == null) {
			return 0;
		}
		return x.size;
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * 
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return this.size(this.root);
	}

	/**
	 * Is this symbol table empty?
	 * 
	 * @return {@code true} if this symbol table is empty and {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return this.root == null;
	}

	/***************************************************************************
	 * Standard BST search.
	 ***************************************************************************/

	/**
	 * Returns the value associated with the given key.
	 * 
	 * @param key the key
	 * @return the value associated with the given key if the key is in the symbol
	 *         table and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Value get(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to get() is null");
		}
		return this.get(this.root, key);
	}

	// value associated with the given key in subtree rooted at x; null if no such
	// key
	private Value get(Node x, Key key) {
		while (x != null) {
			int cmp = key.compareTo(x.key);
			if (cmp < 0) {
				x = x.left;
			} else if (cmp > 0) {
				x = x.right;
			} else {
				return x.val;
			}
		}
		return null;
	}

	/**
	 * Does this symbol table contain the given key?
	 * 
	 * @param key the key
	 * @return {@code true} if this symbol table contains {@code key} and
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(Key key) {
		return this.get(key) != null;
	}

	/***************************************************************************
	 * Red-black tree insertion.
	 ***************************************************************************/

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the
	 * old value with the new value if the symbol table already contains the
	 * specified key. Deletes the specified key (and its associated value) from this
	 * symbol table if the specified value is {@code null}.
	 *
	 * @param key the key
	 * @param val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, Value val) {
		if (key == null) {
			throw new IllegalArgumentException("first argument to put() is null");
		}
		if (val == null) {
			this.delete(key);
			return;
		}

		this.root = this.put(this.root, key, val);
		this.root.color = BLACK;
		// assert check();
	}

	// insert the key-value pair in the subtree rooted at h
	private Node put(Node h, Key key, Value val) {
		if (h == null) {
			return new Node(key, val, RED, 1);
		}

		int cmp = key.compareTo(h.key);
		if (cmp < 0) {
			h.left = this.put(h.left, key, val);
		} else if (cmp > 0) {
			h.right = this.put(h.right, key, val);
		} else {
			h.val = val;
		}

		// fix-up any right-leaning links
		if (this.isRed(h.right) && !this.isRed(h.left)) {
			h = this.rotateLeft(h);
		}
		if (this.isRed(h.left) && this.isRed(h.left.left)) {
			h = this.rotateRight(h);
		}
		if (this.isRed(h.left) && this.isRed(h.right)) {
			this.flipColors(h);
		}
		h.size = this.size(h.left) + this.size(h.right) + 1;

		return h;
	}

	/***************************************************************************
	 * Red-black tree deletion.
	 ***************************************************************************/

	/**
	 * Removes the smallest key and associated value from the symbol table.
	 * 
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMin() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("BST underflow");
		}

		// if both children of root are black, set root to red
		if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
			this.root.color = RED;
		}

		this.root = this.deleteMin(this.root);
		if (!this.isEmpty()) {
			this.root.color = BLACK;
			// assert check();
		}
	}

	// delete the key-value pair with the minimum key rooted at h
	private Node deleteMin(Node h) {
		if (h.left == null) {
			return null;
		}

		if (!this.isRed(h.left) && !this.isRed(h.left.left)) {
			h = this.moveRedLeft(h);
		}

		h.left = this.deleteMin(h.left);
		return this.balance(h);
	}

	/**
	 * Removes the largest key and associated value from the symbol table.
	 * 
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public void deleteMax() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("BST underflow");
		}

		// if both children of root are black, set root to red
		if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
			this.root.color = RED;
		}

		this.root = this.deleteMax(this.root);
		if (!this.isEmpty()) {
			this.root.color = BLACK;
			// assert check();
		}
	}

	// delete the key-value pair with the maximum key rooted at h
	private Node deleteMax(Node h) {
		if (this.isRed(h.left)) {
			h = this.rotateRight(h);
		}

		if (h.right == null) {
			return null;
		}

		if (!this.isRed(h.right) && !this.isRed(h.right.left)) {
			h = this.moveRedRight(h);
		}

		h.right = this.deleteMax(h.right);

		return this.balance(h);
	}

	/**
	 * Removes the specified key and its associated value from this symbol table (if
	 * the key is in this symbol table).
	 *
	 * @param key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to delete() is null");
		}
		if (!this.contains(key)) {
			return;
		}

		// if both children of root are black, set root to red
		if (!this.isRed(this.root.left) && !this.isRed(this.root.right)) {
			this.root.color = RED;
		}

		this.root = this.delete(this.root, key);
		if (!this.isEmpty()) {
			this.root.color = BLACK;
			// assert check();
		}
	}

	// delete the key-value pair with the given key rooted at h
	private Node delete(Node h, Key key) {
		// assert get(h, key) != null;

		if (key.compareTo(h.key) < 0) {
			if (!this.isRed(h.left) && !this.isRed(h.left.left)) {
				h = this.moveRedLeft(h);
			}
			h.left = this.delete(h.left, key);
		} else {
			if (this.isRed(h.left)) {
				h = this.rotateRight(h);
			}
			if (key.compareTo(h.key) == 0 && h.right == null) {
				return null;
			}
			if (!this.isRed(h.right) && !this.isRed(h.right.left)) {
				h = this.moveRedRight(h);
			}
			if (key.compareTo(h.key) == 0) {
				Node x = this.min(h.right);
				h.key = x.key;
				h.val = x.val;
				// h.val = get(h.right, min(h.right).key);
				// h.key = min(h.right).key;
				h.right = this.deleteMin(h.right);
			} else {
				h.right = this.delete(h.right, key);
			}
		}
		return this.balance(h);
	}

	/***************************************************************************
	 * Red-black tree helper functions.
	 ***************************************************************************/

	// make a left-leaning link lean to the right
	private Node rotateRight(Node h) {
		// assert (h != null) && isRed(h.left);
		Node x = h.left;
		h.left = x.right;
		x.right = h;
		x.color = x.right.color;
		x.right.color = RED;
		x.size = h.size;
		h.size = this.size(h.left) + this.size(h.right) + 1;
		return x;
	}

	// make a right-leaning link lean to the left
	private Node rotateLeft(Node h) {
		// assert (h != null) && isRed(h.right);
		Node x = h.right;
		h.right = x.left;
		x.left = h;
		x.color = x.left.color;
		x.left.color = RED;
		x.size = h.size;
		h.size = this.size(h.left) + this.size(h.right) + 1;
		return x;
	}

	// flip the colors of a node and its two children
	private void flipColors(Node h) {
		// h must have opposite color of its two children
		// assert (h != null) && (h.left != null) && (h.right != null);
		// assert (!isRed(h) && isRed(h.left) && isRed(h.right))
		// || (isRed(h) && !isRed(h.left) && !isRed(h.right));
		h.color = !h.color;
		h.left.color = !h.left.color;
		h.right.color = !h.right.color;
	}

	// Assuming that h is red and both h.left and h.left.left
	// are black, make h.left or one of its children red.
	private Node moveRedLeft(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

		this.flipColors(h);
		if (this.isRed(h.right.left)) {
			h.right = this.rotateRight(h.right);
			h = this.rotateLeft(h);
			this.flipColors(h);
		}
		return h;
	}

	// Assuming that h is red and both h.right and h.right.left
	// are black, make h.right or one of its children red.
	private Node moveRedRight(Node h) {
		// assert (h != null);
		// assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
		this.flipColors(h);
		if (this.isRed(h.left.left)) {
			h = this.rotateRight(h);
			this.flipColors(h);
		}
		return h;
	}

	// restore red-black tree invariant
	private Node balance(Node h) {
		// assert (h != null);

		if (this.isRed(h.right)) {
			h = this.rotateLeft(h);
		}
		if (this.isRed(h.left) && this.isRed(h.left.left)) {
			h = this.rotateRight(h);
		}
		if (this.isRed(h.left) && this.isRed(h.right)) {
			this.flipColors(h);
		}

		h.size = this.size(h.left) + this.size(h.right) + 1;
		return h;
	}

	/***************************************************************************
	 * Utility functions.
	 ***************************************************************************/

	/**
	 * Returns the height of the BST (for debugging).
	 * 
	 * @return the height of the BST (a 1-node tree has height 0)
	 */
	public int height() {
		return this.height(this.root);
	}

	private int height(Node x) {
		if (x == null) {
			return -1;
		}
		return 1 + Math.max(this.height(x.left), this.height(x.right));
	}

	/***************************************************************************
	 * Ordered symbol table methods.
	 ***************************************************************************/

	/**
	 * Returns the smallest key in the symbol table.
	 * 
	 * @return the smallest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key min() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("calls min() with empty symbol table");
		}
		return this.min(this.root).key;
	}

	// the smallest key in subtree rooted at x; null if no such key
	private Node min(Node x) {
		// assert x != null;
		if (x.left == null) {
			return x;
		} else {
			return this.min(x.left);
		}
	}

	/**
	 * Returns the largest key in the symbol table.
	 * 
	 * @return the largest key in the symbol table
	 * @throws NoSuchElementException if the symbol table is empty
	 */
	public Key max() {
		if (this.isEmpty()) {
			throw new NoSuchElementException("calls max() with empty symbol table");
		}
		return this.max(this.root).key;
	}

	// the largest key in the subtree rooted at x; null if no such key
	private Node max(Node x) {
		// assert x != null;
		if (x.right == null) {
			return x;
		} else {
			return this.max(x.right);
		}
	}

	/**
	 * Returns the largest key in the symbol table less than or equal to
	 * {@code key}.
	 * 
	 * @param key the key
	 * @return the largest key in the symbol table less than or equal to {@code key}
	 * @throws NoSuchElementException   if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Key floor(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to floor() is null");
		}
		if (this.isEmpty()) {
			throw new NoSuchElementException("calls floor() with empty symbol table");
		}
		Node x = this.floor(this.root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}

	// the largest key in the subtree rooted at x less than or equal to the given
	// key
	private Node floor(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int cmp = key.compareTo(x.key);
		if (cmp == 0) {
			return x;
		}
		if (cmp < 0) {
			return this.floor(x.left, key);
		}
		Node t = this.floor(x.right, key);
		if (t != null) {
			return t;
		} else {
			return x;
		}
	}

	/**
	 * Returns the smallest key in the symbol table greater than or equal to
	 * {@code key}.
	 * 
	 * @param key the key
	 * @return the smallest key in the symbol table greater than or equal to
	 *         {@code key}
	 * @throws NoSuchElementException   if there is no such key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Key ceiling(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to ceiling() is null");
		}
		if (this.isEmpty()) {
			throw new NoSuchElementException("calls ceiling() with empty symbol table");
		}
		Node x = this.ceiling(this.root, key);
		if (x == null) {
			return null;
		} else {
			return x.key;
		}
	}

	// the smallest key in the subtree rooted at x greater than or equal to the
	// given key
	private Node ceiling(Node x, Key key) {
		if (x == null) {
			return null;
		}
		int cmp = key.compareTo(x.key);
		if (cmp == 0) {
			return x;
		}
		if (cmp > 0) {
			return this.ceiling(x.right, key);
		}
		Node t = this.ceiling(x.left, key);
		if (t != null) {
			return t;
		} else {
			return x;
		}
	}

	/**
	 * Return the key in the symbol table whose rank is {@code k}. This is the
	 * (k+1)st smallest key in the symbol table.
	 *
	 * @param k the order statistic
	 * @return the key in the symbol table of rank {@code k}
	 * @throws IllegalArgumentException unless {@code k} is between 0 and
	 *                                  <em>n</em>–1
	 */
	public Key select(int k) {
		if (k < 0 || k >= this.size()) {
			throw new IllegalArgumentException("argument to select() is invalid: " + k);
		}
		Node x = this.select(this.root, k);
		return x.key;
	}

	// the key of rank k in the subtree rooted at x
	private Node select(Node x, int k) {
		// assert x != null;
		// assert k >= 0 && k < size(x);
		int t = this.size(x.left);
		if (t > k) {
			return this.select(x.left, k);
		} else if (t < k) {
			return this.select(x.right, k - t - 1);
		} else {
			return x;
		}
	}

	/**
	 * Return the number of keys in the symbol table strictly less than {@code key}.
	 * 
	 * @param key the key
	 * @return the number of keys in the symbol table strictly less than {@code key}
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public int rank(Key key) {
		if (key == null) {
			throw new IllegalArgumentException("argument to rank() is null");
		}
		return this.rank(key, this.root);
	}

	// number of keys less than key in the subtree rooted at x
	private int rank(Key key, Node x) {
		if (x == null) {
			return 0;
		}
		int cmp = key.compareTo(x.key);
		if (cmp < 0) {
			return this.rank(key, x.left);
		} else if (cmp > 0) {
			return 1 + this.size(x.left) + this.rank(key, x.right);
		} else {
			return this.size(x.left);
		}
	}

	/***************************************************************************
	 * Range count and range search.
	 ***************************************************************************/

	/**
	 * Returns all keys in the symbol table as an {@code Iterable}. To iterate over
	 * all of the keys in the symbol table named {@code st}, use the foreach
	 * notation: {@code for (Key key : st.keys())}.
	 * 
	 * @return all keys in the symbol table as an {@code Iterable}
	 */
	public Iterable<Key> keys() {
		if (this.isEmpty()) {
			return new Queue<Key>();
		}
		return this.keys(this.min(), this.max());
	}

	/**
	 * Returns all keys in the symbol table in the given range, as an
	 * {@code Iterable}.
	 *
	 * @param lo minimum endpoint
	 * @param hi maximum endpoint
	 * @return all keys in the sybol table between {@code lo} (inclusive) and
	 *         {@code hi} (inclusive) as an {@code Iterable}
	 * @throws IllegalArgumentException if either {@code lo} or {@code hi} is
	 *                                  {@code null}
	 */
	public Iterable<Key> keys(Key lo, Key hi) {
		if (lo == null) {
			throw new IllegalArgumentException("first argument to keys() is null");
		}
		if (hi == null) {
			throw new IllegalArgumentException("second argument to keys() is null");
		}

		Queue<Key> queue = new Queue<Key>();
		// if (isEmpty() || lo.compareTo(hi) > 0) return queue;
		this.keys(this.root, queue, lo, hi);
		return queue;
	}

	// add the keys between lo and hi in the subtree rooted at x
	// to the queue
	private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
		if (x == null) {
			return;
		}
		int cmplo = lo.compareTo(x.key);
		int cmphi = hi.compareTo(x.key);
		if (cmplo < 0) {
			this.keys(x.left, queue, lo, hi);
		}
		if (cmplo <= 0 && cmphi >= 0) {
			queue.enqueue(x.key);
		}
		if (cmphi > 0) {
			this.keys(x.right, queue, lo, hi);
		}
	}

	/**
	 * Returns the number of keys in the symbol table in the given range.
	 *
	 * @param lo minimum endpoint
	 * @param hi maximum endpoint
	 * @return the number of keys in the sybol table between {@code lo} (inclusive)
	 *         and {@code hi} (inclusive)
	 * @throws IllegalArgumentException if either {@code lo} or {@code hi} is
	 *                                  {@code null}
	 */
	public int size(Key lo, Key hi) {
		if (lo == null) {
			throw new IllegalArgumentException("first argument to size() is null");
		}
		if (hi == null) {
			throw new IllegalArgumentException("second argument to size() is null");
		}

		if (lo.compareTo(hi) > 0) {
			return 0;
		}
		if (this.contains(hi)) {
			return this.rank(hi) - this.rank(lo) + 1;
		} else {
			return this.rank(hi) - this.rank(lo);
		}
	}

	/***************************************************************************
	 * Check integrity of red-black tree data structure.
	 ***************************************************************************/
	private boolean check() {
		if (!this.isBST()) {
			StdOut.println("Not in symmetric order");
		}
		if (!this.isSizeConsistent()) {
			StdOut.println("Subtree counts not consistent");
		}
		if (!this.isRankConsistent()) {
			StdOut.println("Ranks not consistent");
		}
		if (!this.is23()) {
			StdOut.println("Not a 2-3 tree");
		}
		if (!this.isBalanced()) {
			StdOut.println("Not balanced");
		}
		return this.isBST() && this.isSizeConsistent() && this.isRankConsistent() && this.is23() && this.isBalanced();
	}

	// does this binary tree satisfy symmetric order?
	// Note: this test also ensures that data structure is a binary tree since order
	// is strict
	private boolean isBST() {
		return this.isBST(this.root, null, null);
	}

	// is the tree rooted at x a BST with all keys strictly between min and max
	// (if min or max is null, treat as empty constraint)
	// Credit: Bob Dondero's elegant solution
	private boolean isBST(Node x, Key min, Key max) {
		if (x == null) {
			return true;
		}
		if (min != null && x.key.compareTo(min) <= 0) {
			return false;
		}
		if (max != null && x.key.compareTo(max) >= 0) {
			return false;
		}
		return this.isBST(x.left, min, x.key) && this.isBST(x.right, x.key, max);
	}

	// are the size fields correct?
	private boolean isSizeConsistent() {
		return this.isSizeConsistent(this.root);
	}

	private boolean isSizeConsistent(Node x) {
		if (x == null) {
			return true;
		}
		if (x.size != this.size(x.left) + this.size(x.right) + 1) {
			return false;
		}
		return this.isSizeConsistent(x.left) && this.isSizeConsistent(x.right);
	}

	// check that ranks are consistent
	private boolean isRankConsistent() {
		for (int i = 0; i < this.size(); i++) {
			if (i != this.rank(this.select(i))) {
				return false;
			}
		}
		for (Key key : this.keys()) {
			if (key.compareTo(this.select(this.rank(key))) != 0) {
				return false;
			}
		}
		return true;
	}

	// Does the tree have no red right links, and at most one (left)
	// red links in a row on any path?
	private boolean is23() {
		return this.is23(this.root);
	}

	private boolean is23(Node x) {
		if (x == null) {
			return true;
		}
		if (this.isRed(x.right)) {
			return false;
		}
		if (x != this.root && this.isRed(x) && this.isRed(x.left)) {
			return false;
		}
		return this.is23(x.left) && this.is23(x.right);
	}

	// do all paths from root to leaf have same number of black edges?
	private boolean isBalanced() {
		int black = 0; // number of black links on path from root to min
		Node x = this.root;
		while (x != null) {
			if (!this.isRed(x)) {
				black++;
			}
			x = x.left;
		}
		return this.isBalanced(this.root, black);
	}

	// does every path from the root to a leaf have the given number of black links?
	private boolean isBalanced(Node x, int black) {
		if (x == null) {
			return black == 0;
		}
		if (!this.isRed(x)) {
			black--;
		}
		return this.isBalanced(x.left, black) && this.isBalanced(x.right, black);
	}

	/**
	 * Unit tests the {@code RedBlackBST} data type.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		RedBlackBST<String, Integer> st = new RedBlackBST<String, Integer>();
		for (int i = 0; !StdIn.isEmpty(); i++) {
			String key = StdIn.readString();
			st.put(key, i);
		}
		for (String s : st.keys()) {
			StdOut.println(s + " " + st.get(s));
		}
		StdOut.println();
	}
}

/******************************************************************************
 * Copyright 2002-2018, Robert Sedgewick and Kevin Wayne.
 *
 * This file is part of algs4.jar, which accompanies the textbook
 *
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 *
 *
 * algs4.jar is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * algs4.jar is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * algs4.jar. If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
