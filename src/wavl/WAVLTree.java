package wavl;

import java.util.Arrays;

import com.sun.org.apache.xpath.internal.axes.NodeSequence;

/**
 * Ran Armony & Shahaf Goren
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree with distinct integer keys and info
 *
 */

public class WAVLTree {

	public WAVLNode root;

	/// inserts for test
	public void test() {
		root = new WAVLNode(10,"ten");
		root.leftSon = new WAVLNode(5,"five");
		root.leftSon.leftSon = new WAVLNode(1,"one");
		root.leftSon.leftSon.leftSon = new WAVLNode(0,"zero");
		root.leftSon.rightSon = new WAVLNode(8,"eight");
		root.leftSon.rightSon.leftSon = new WAVLNode(7,"seven");
		root.leftSon.rightSon.rightSon = new WAVLNode(9,"nine");
		root.rightSon = new WAVLNode(15,"fifteen");
		root.rightSon.leftSon = new WAVLNode(13,"thirteen");
		root.rightSon.rightSon = new WAVLNode(19,"nineteen");
		root.treeSize = 10;
		

		root.leftSon.dad = root.rightSon.dad = root;
		root.leftSon.leftSon.dad = root.leftSon;
		root.leftSon.leftSon.leftSon.dad = root.leftSon.leftSon;
		root.leftSon.rightSon.leftSon.dad = root.leftSon.rightSon.rightSon.dad = root.leftSon.rightSon;
		root.rightSon.rightSon.dad = root.rightSon.leftSon.dad = root.rightSon;
		
	}
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	
	public WAVLTree() {
		root = new WAVLNode();
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
		return root.searchVal(k);
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
		int ops = 0;
		WAVLNode place = root.searchNode(k),
				 node;
		
		if(place.isRealNode())
			return -1;
		node = new WAVLNode(k,i);
		place.insertInPlace(node);
		ops = reBalance(node);
		return ops;
	}
	
	public int reBalance(WAVLNode node) {
		int ops = 0;
		WAVLNode curr = node.dad;
		while(curr!=null) {
			curr.treeSize++;
			if(curr.needsPromote()) {
				promote(curr);
				ops++;
				curr = curr.getDad();
				continue;
			}
			
			if(curr.needsDemote()) {
				if(curr.getRight().needsDemote()) {
					demote(curr.getRight());
					ops++;
				}
				if(curr.getLeft().needsDemote()) {
					demote(curr.getLeft());
					ops++;
				}
				demote(curr);
				ops++;
				continue;
					
			}
			if(curr.needsRightRotate()) {
				if(curr.needsDoubleRotateRight()) {
					WAVLNode newCurr = doubleRotate(curr,curr.getLeft());
					curr = newCurr.dad;
					ops+=2;
					continue;
				}
				else {
					WAVLNode newCurr = rotate(curr,curr.getLeft());
					curr = newCurr.dad;
					ops++;
					continue;
				}
			}
			if(curr.needsLeftRotate()) {
				if(curr.needsDoubleRotateLeft()) {
					WAVLNode newCurr = doubleRotate(curr,curr.getRight());
					curr = newCurr.dad;
					ops+=2;
					continue;
				}
				else {
					WAVLNode newCurr = rotate(curr,curr.getRight());
					curr = newCurr.dad;
					ops++;
					continue;
				}
			}
			curr = curr.dad;
			
			
		}
		return ops;
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
		int ops = 0;
		WAVLNode place = root.searchNode(k),
				 successor,
				 fatherOfDelted;
		//if not found
		if(!place.isRealNode())
			return -1;
		// found: is middle node?
		if(place.isMiddleNode()) {
			successor = place.successor();
			place.replace(successor);
		}
		fatherOfDelted = place.dad;
		
		if(place.getRank() != 0) // is an unary node
			place.deleteUnary();
		else // a leaf
			place.deleteLeaf();
		// ops = reBalance(fatherOfDelted);
		if(place.getRank() != 0)
			if(place.isLeftSon())
				if(place.leftSon.isRealNode())
					place.dad.setLeftSon(place.leftSon);
				else
					place.dad.setLeftSon(place.rightSon);
			else
				if(place.rightSon.isRealNode())
					place.dad.setRightSon(place.leftSon);
				else
					place.dad.setRightSon(place.rightSon);
		ops = reBalance(place.dad);
		return ops;
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 */
	public String min() {
		return root.minVal(); // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 */
	public String max() {
		return root.maxVal(); // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 */
	public int[] keysToArray() {
		int[] arr = new int[root.getSubtreeSize()];
		WAVLNode curr = root.minNode();
		int i=0;
		while(curr!=null && curr.isRealNode()){
			arr[i]=curr.getKey();
			WAVLNode temp = curr.successor();
			curr = temp;
			i++;
		}
		
		return arr; 
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] infoToArray() {
		String[] arr = new String[root.getSubtreeSize()];
		WAVLNode curr = root.minNode();
		int i=0;
		while(curr!=null && curr.isRealNode()){
			arr[i]=curr.getValue();
			WAVLNode temp = curr.successor();
			curr = temp;
			i++;
		}
		
		return arr; 
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return root.getSubtreeSize(); // to be replaced by student code
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root WAVL node, or null if the tree is empty
	 *
	 * precondition: none postcondition: none
	 */
	public WAVLNode getRoot() {
		return root;
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
		WAVLNode current = root.minNode();
		for (int j=1; j<i; j++)
			current = successor(current);
		return current.getValue();
	}

	public WAVLNode successor(WAVLNode node) {
		return node.successor();
	}
	
	public WAVLNode predeccessor(WAVLNode node) {
		return node.predeccessor();
	}
	// returns the root of the new root of the rotate
	public WAVLNode rotate(WAVLNode father, WAVLNode troubleMakerSon) {
		WAVLNode temp, grandpa = father.getDad();
		if (father.leftSon == troubleMakerSon) {
			// right rotate
			temp = troubleMakerSon.rightSon;
			troubleMakerSon.setRightSon(father);
			father.setLeftSon(temp);
		}
		else {
			// left rotate
			temp = troubleMakerSon.leftSon;
			troubleMakerSon.setLeftSon(father);
			father.setRightSon(temp);
		}
		troubleMakerSon.dad = grandpa;
		if (root == father)
			root = troubleMakerSon;
		else if(grandpa.leftSon == father)
			grandpa.setLeftSon(troubleMakerSon);
		else
			grandpa.setRightSon(troubleMakerSon);
		
		demote(father);
		father.updateSize();
		troubleMakerSon.updateSize();
		
		return troubleMakerSon;
	}	

	// returns the root of the new root of the rotate
	public WAVLNode doubleRotate(WAVLNode father, WAVLNode son) {
		WAVLNode holySpirit;
		if (father.leftSon == son)
			holySpirit = son.rightSon;
		else
			holySpirit = son.leftSon;
		rotate(son, holySpirit);
		rotate(father, holySpirit);	
		
		promote(holySpirit);
		return holySpirit;
	}

	public void promote(WAVLNode node) {
		node.rank++;
	}

	public void demote(WAVLNode node) {
		node.rank--;
	}
	
	public void doubleDemote(WAVLNode node) {
		
		node.rank--;
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
										// sentinel)

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

		public int key, rank, treeSize;
		public String val;
		public WAVLNode rightSon, leftSon, dad;
		public boolean isReal;
		

		// virtual root constructor
		public WAVLNode() {
			rank = -1;
			isReal = false;
			treeSize = 0;
			this.key = -1;
			this.val = "";
			dad = null;
			rightSon = null;
			leftSon = null;
		}

		// virtual leaf constructor
		public WAVLNode(WAVLNode dad) {
			rank = -1;
			isReal = false;
			treeSize = 0;
			this.key = -1;
			this.val = "";
			this.dad = dad;
			rightSon = null;
			leftSon = null;
		}
		
		//real root constructor
		public WAVLNode(int key, String val) {
			rank = 0;
			isReal = true;
			treeSize = 1;
			this.key = key;
			this.val = val;
			dad = null;
			rightSon = new WAVLNode(this);
			leftSon = new WAVLNode(this);
		}

		//real leaf constructor
		public WAVLNode(int key, String val, WAVLNode dad) {
			rank = 0;
			isReal = true;
			treeSize = 1;
			this.key = key;
			this.val = val;
			this.dad = dad;
			rightSon = new WAVLNode(this);
			leftSon = new WAVLNode(this);
		}
		
		public boolean isMiddleNode() {
			return (rightSon.isRealNode() && leftSon.isRealNode());
		}



		public void replace(WAVLNode node) {
			WAVLNode thisFather = this.dad,
					 nodeFather = node.dad,
					 nodeLeftChild = node.leftSon,
					 nodeRightChild = node.rightSon;
			boolean thisIsRoot =false,
					nodeIsRoot = false;
			int temp = 0;
			boolean nodeWasLeftSon;
			
			// replacing two real nodes
			if(this.isRealNode() && node.isRealNode()) {
				//replace fields
				temp = node.getRank();
				node.rank = this.rank;
				this.rank = temp;
				
				temp = node.getSubtreeSize();
				node.treeSize = this.treeSize;
				this.treeSize = temp;

				// replace place children
				node.setLeftSon(this.leftSon);
				node.setRightSon(this.rightSon);
				this.setLeftSon(nodeLeftChild);
				this.setRightSon(nodeRightChild);
				
				//replace fathers
				nodeWasLeftSon = node.isLeftSon();
				if (thisFather == null) {
					root = node;
					node.dad = null;
				}
				else if(this.isLeftSon())
					thisFather.setLeftSon(node);
				else
					thisFather.setRightSon(node);


				if (nodeFather == null) {
					root = this;
					this.dad = null;
				} else if(nodeWasLeftSon)
					nodeFather.setLeftSon(this);
				else
					nodeFather.setRightSon(this);
			}				
		}

		public void deleteUnary() {
			if(this.dad == null) {
				if(leftSon.isRealNode()) {
					root = this.leftSon;
					this.leftSon.dad = null;
					}
				else {
					root = this.rightSon;
					this.rightSon.dad = null;
				}
			}
			else if(this.isLeftSon())
				if(this.leftSon.isRealNode())
					this.dad.setLeftSon(this.leftSon);
				else
					this.dad.setLeftSon(this.rightSon);
			else
				if(this.leftSon.isRealNode())
					this.dad.setRightSon(this.leftSon);
				else
					this.dad.setRightSon(this.rightSon);
		}
		
		public void deleteLeaf() {
			if(this.dad == null)
				root = new WAVLNode();
			else if(this.isLeftSon())
				this.dad.setLeftSon(new WAVLNode());
			else
				this.dad.setRightSon(new WAVLNode());
		}
		
		private void insertInPlace(WAVLNode node) {
			if(this.dad == null) {
				root = node;
				node.dad = null;
			}
			else if(this.isLeftSon())
				this.dad.setLeftSon(node);
			else
				this.dad.setRightSon(node);
		}

		private boolean isLeftSon() {
			return (this.dad.leftSon == this);
		}



		public void setRightSon(WAVLNode node) {
			this.rightSon = node;
			node.dad = this;
		}

		public void setLeftSon(WAVLNode node) {
			this.leftSon = node;
			node.dad = this;
			
		}

		public void updateSize() {
			treeSize = leftSon.treeSize + rightSon.treeSize +1;
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

		public WAVLNode getLeft() {
			return leftSon;
		}

		public WAVLNode getRight() {
			return rightSon;
		}

		// returns null if root
		public WAVLNode getDad() {
			return dad;
		}

		// Returns True if this is a non-virtual WAVL node (i.e not a virtual leaf or a
		// sentinal)
		public boolean isRealNode() {
			return isReal;
		}
		
		public WAVLNode successor() {
			if(rightSon.isRealNode())
				return rightSon.minNode();
			WAVLNode prev = this;
			WAVLNode ans = prev.getDad();
			while(ans!=null && ans.getRight()==prev) {
				prev = ans;
				ans = prev.getDad();
			}
			
			return ans;
			
		}
		
		public WAVLNode predeccessor() {
			if(leftSon.isRealNode())
				return leftSon.minNode();
			WAVLNode prev = this;
			WAVLNode ans = prev.getDad();
			while(ans!=null && ans.getLeft()==prev) {
				prev = ans;
				ans = prev.getDad();
			}
			
			return ans;
		}
		
		public int[] difs() {
			if(!isReal)
				return null;
			int[] difs = new int[2];
			difs[0] = this.getRank() - this.getLeft().getRank();
			difs[1] = this.getRank() - this.getRight().getRank();
			return difs;
		}
		
		public boolean isValidDifs(){
			int[] difs = this.difs();
			int[] opt1 = {1,2};
			int[] opt2 = {1,1};
			int[] opt3 = {2,1};
			if( Arrays.equals(difs,(opt1)) || Arrays.equals(difs,(opt2)) || Arrays.equals(difs,(opt3)) )
			int[] opt4 = {2,2};
			if( Arrays.equals(difs,(opt1)) || Arrays.equals(difs,(opt2)) || Arrays.equals(difs,(opt3)) || Arrays.equals(difs,(opt4)) )
					return true;
			return false;
		}
		
		public boolean needsPromote(){
			int[] difs = this.difs();
			int[] opt1 = {1,0};
			int[] opt2 = {0,1};
			if(difs != null && (Arrays.equals(difs, opt1) || Arrays.equals(difs, opt2)) )
					return true;
			return false;
		}
		
		public boolean needsRotate() {
			return(needsRightRotate()||needsLeftRotate());
		}
		
		public boolean needsRightRotate(){
			int[] difs = this.difs();
			int[] opt1 = {0,2};
			if(difs != null && ( Arrays.equals(difs, opt1) ))
			int[] opt2 = {1,3};
			if(difs != null && (Arrays.equals(difs, opt1)||Arrays.equals(difs, opt2) ))
					return true;
			return false;
			
		}
		
		public boolean needsLeftRotate(){
			int[] difs = this.difs();
			int[] opt2 = {2,0};
			if ( (difs != null) && (Arrays.equals(difs, opt2) ) )
			int[] opt1 = {2,0};
			int[] opt2 = {3,1};
			if(difs != null && (Arrays.equals(difs, opt1)||Arrays.equals(difs, opt2) ))
					return true;
			return false;
			
		}
		
		public boolean needsDoubleRotateRight(){
			if(!needsRightRotate())
				return false;
			int[] problem = {2,1};
			if((Arrays.equals(this.leftSon.difs(),(problem) )))
					return true;
			return false;
			
		}
		
		public boolean needsDoubleRotateLeft(){
			if(!needsLeftRotate())
				return false;
			int[] problem = {1,2};
			if(Arrays.equals(this.rightSon.difs(),(problem)))
					return true;
			return false;
			
		}
		
		public boolean needsDemote(){
			int[] difs = this.difs();
			int[] opt1 = {3,2};
			int[] opt2 = {2,3};
			if(difs != null && (Arrays.equals(difs, opt1) || Arrays.equals(difs, opt2)) )
					return true;
			return false;
		}
		//not in use
		public boolean needsDoubleDemoteRight(){
			if(!this.needsDemote())
				return false;
			int[] problem = {2,2};
			int[] rightSonDifs = this.getRight().difs();
			int[] leftSonDifs = this.getLeft().difs();
			if(Arrays.equals(rightSonDifs, problem) || Arrays.equals(leftSonDifs, problem))
				return true;
			return false;
				
		}
		//not in use
		public boolean needsDoubleDemoteLeft(){
			if(!this.needsDemote())
				return false;
			int[] problem = {2,2};
			int[] rightSonDifs = this.getRight().difs();
			int[] leftSonDifs = this.getLeft().difs();
			if(Arrays.equals(rightSonDifs, problem) || Arrays.equals(leftSonDifs, problem))
				return true;
			return false;
				
		}
		
		
		
		
		//              ****tree methods*****
		
		public int getSubtreeSize() {
			return treeSize;
		}
		
		// return null if not found
		public WAVLNode searchNode(int k) {
			if (!isRealNode() || getKey() == k)
				return this;
			if (k < getKey())
				return leftSon.searchNode(k);
			else
				return rightSon.searchNode(k);
		}
		
		// return null if not found
		public String searchVal(int k) {
			WAVLNode node = searchNode(k);
			if (!node.isRealNode())
				return null;
			return searchNode(k).getValue();
		}
		
		public WAVLNode minNode() {
			if (!this.isRealNode() || !leftSon.isRealNode() )
				return this;
			return leftSon.minNode();
		}
		
		public String minVal() {
			return this.minNode().getValue();
		}		
		public WAVLNode maxNode() {
			if (!this.isRealNode() || !rightSon.isRealNode() )
				return this;
			return rightSon.maxNode();
		}
		
		public String maxVal() {
			return this.maxNode().getValue();
		}
		
		
	}

}
