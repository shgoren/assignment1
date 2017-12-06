package wavl;

/**
 * Ran Armony & Shahaf Goren
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with distinct integer keys and info
 *
 */

public class WAVLTree {

	private IWAVLNode root;

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	
	public WAVLTree() {
		root = new WAVLVirtualNode();
	}
	
	public WAVLTree(WAVLNode root) {
		this.root = root;
	}
	
	public boolean empty() {
		return !root.isRealNode();
	}

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree otherwise,
	 * returns null
	 */
	public String search(int k) {
		return searchRec(root, k);
	}

	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the WAVL tree. the tree must remain
	 * valid (keep its invariants). returns the number of rebalancing operations, or
	 * 0 if no rebalancing operations were necessary. returns -1 if an item with key
	 * k already exists in the tree.
	 */
	public int insert(int k, String i) {
		return 42; // to be replaced by student code
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of rebalancing
	 * operations, or 0 if no rebalancing operations were needed. returns -1 if an
	 * item with key k was not found in the tree.
	 */
	public int delete(int k) {
		return 42; // to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 */
	public String min() {
		return "42"; // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 */
	public String max() {
		return "42"; // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] infoToArray() {
		String[] arr = new String[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return 42; // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root WAVL node, or null if the tree is empty
	 *
	 * precondition: none postcondition: none
	 */
	public IWAVLNode getRoot() {
		return null;
	}

	/**
	 * public int select(int i)
	 *
	 * Returns the value of the i'th smallest key (return -1 if tree is empty)
	 * Example 1: select(1) returns the value of the node with minimal key Example
	 * 2: select(size()) returns the value of the node with maximal key Example 3:
	 * select(2) returns the value 2nd smallest minimal node, i.e the value of the
	 * node minimal node's successor
	 *
	 * precondition: size() >= i > 0 postcondition: none
	 */
	public String select(int i) {
		return null;
	}

	public WAVLNode successor(WAVLNode node) {
		return null;
	}
	
	public WAVLNode predeccessor(WAVLNode node) {
		return null;
	}
	
	

	/**
	 * public interface IWAVLNode ! Do not delete or modify this - otherwise all
	 * tests will fail !
	 */
	public interface IWAVLNode {
		public int getKey(); // returns node's key (for virtual node return -1)

		public int getRank(); // returns node's rank (for virtual node return -1)

		public String getValue(); // returns node's value [info] (for virtual node return null)

		public IWAVLNode getLeft(); // returns left child (if there is no left child return null)

		public IWAVLNode getRight(); // returns right child (if there is no right child return null)

		public IWAVLNode getDad(); // returns dad (if there is no dad return null)

		public boolean isRealNode(); // Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a
										// sentinal)

		public int getSubtreeSize(); // Returns the number of real nodes in this node's subtree (Should be
										// implemented in O(1))
	}

	/**
	 * public class WAVLNode
	 *
	 * If you wish to implement classes other than WAVLTree (for example WAVLNode),
	 * do it in this file, not in another file. This class can and must be modified.
	 * (It must implement IWAVLNode)
	 */
	public class WAVLNode implements IWAVLNode {

		private int key, rank = 0, subSize = 1;
		private String val;
		private IWAVLNode rightSon, leftSon, dad;

		// root constructor
		public WAVLNode(int key, String val) {
			this.key = key;
			this.val = val;
			rightSon = new WAVLVirtualNode(this);
			leftSon = new WAVLVirtualNode(this);
		}

		// leaf constructor
		public WAVLNode(int key, String val, IWAVLNode dad) {
			this.key = key;
			this.val = val;
			this.dad = dad;
			rightSon = new WAVLVirtualNode(this);
			leftSon = new WAVLVirtualNode(this);
		}

		public int getKey() {
			return key;
		}

		public String getValue() {
			return val;
		}

		public int getRank() {
			return rank;
		}

		public IWAVLNode getLeft() {
			return leftSon;
		}

		public IWAVLNode getRight() {
			return rightSon;
		}

		// returns null if root
		public IWAVLNode getDad() {
			return dad;
		}

		// Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a
		// sentinal)
		public boolean isRealNode() {
			return true;
		}

		public int getSubtreeSize() {
			return subSize;
		}
	}

	// virtual node, can be a root of an empty tree or a virtual child of a leaf
	// contains pointer to dad
	public class WAVLVirtualNode implements IWAVLNode {

		private IWAVLNode dad;

		// root constructor
		public WAVLVirtualNode() {
			dad = null;
		}

		// leaf constructor
		public WAVLVirtualNode(IWAVLNode dad) {
			this.dad = dad;
		}

		public int getKey() {
			return -1;
		}

		public String getValue() {
			return null;
		}

		public int getRank() {
			return -1;
		}

		public IWAVLNode getLeft() {
			return null;
		}

		public IWAVLNode getRight() {
			return null;
		}

		// returns null if root
		public IWAVLNode getDad() {
			return dad;
		}

		// Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a
		// sentinal)
		public boolean isRealNode() {
			return false;
		}

		public int getSubtreeSize() {
			return 0; // to be replaced by student code
		}
	}

}
