package wavl;

import java.util.Arrays;


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
	
	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 * O(1)
	 */
	public WAVLTree() {
		root = new WAVLNode();
	}
	
	//@ret true iff tree is empty (root is virtual)
	//O(1)
	public boolean empty() {
		return !root.isRealNode();
	}

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree otherwise,
	 * returns null
	 * O(logn)
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
	 * O(logn)
	 */
	public int insert(int k, String i) {
		int ops = 0;
		WAVLNode place = root.searchNode(k),
				 node;
		
		if(place.isRealNode())
			return -1;
		node = new WAVLNode(k,i);
		place.insertInPlace(node);
		ops = reBalance(node.dad, "insert");
		return ops;
	}
	/**
	 * check if the tree is balanced from the node given all the way up and adjusts the ranks and subtree sizes
	 * @param node the node to start the rebalance from
	 * @param state == "delete" || "increse" 
	 * @return
	 */
	public int reBalance(WAVLNode node, String state) {
		int ops = 0;
		WAVLNode curr = node;
		while(curr!=null) {
			changeSize(curr, state);
			if(curr.needsPromote()) {
				promote(curr);
				ops++;
				curr = curr.getDad();
				continue;
			}
			
			if(curr.needsDemote()) {
				demote(curr);
				ops++;
				curr = curr.getDad();
				continue;
					
			}
			if(curr.needsRightRotate()) {
				if(curr.needsDoubleRotateRight()) {
					WAVLNode newCurr = doubleRotate(curr,curr.getLeft());
					curr = newCurr.dad;
					ops+=2;
					continue;
				}
				if(curr.needsDoubleDemoteRight()) {
					doubleDemoteRight(curr);
					ops+=2;
					curr = curr.getDad();
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
				if(curr.needsDoubleDemoteLeft()) {
					doubleDemoteLeft(curr);
					ops+=2;
					curr = curr.getDad();
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
	 * increase or decrease size according to state
	 * @param node
	 * @param state == "delete" || "increse"
	 * O(1)
	 */
	public void changeSize(WAVLNode node, String state) {
		if(state.equals("delete"))
			node.treeSize--;
		else
			node.treeSize++;
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of rebalancing
	 * operations, or 0 if no rebalancing operations were needed. returns -1 if an
	 * item with key k was not found in the tree.
	 * O(logn)
	 */
	public int delete(int k) {
		int ops = 0;
		WAVLNode place = root.searchNode(k),
				 successor,
				 fatherOfDeleted;
		//if not found
		if(!place.isRealNode())
			return -1;
		// found: is middle node?
		if(place.isMiddleNode()) {
			successor = place.successor();
			place.replace(successor);
			place = successor;
		}
		fatherOfDeleted = place.dad; // hold it before deletion
		if(place.getRank() != 0) // is an unary node
			place.deleteUnary();
		else // a leaf
			place.deleteLeaf();
		ops = reBalance(fatherOfDeleted, "delete");
		return ops;
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null if
	 * the tree is empty
	 * O(logn)
	 */
	public String min() {
		return root.minVal(); // to be replaced by student code
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if the
	 * tree is empty
	 * O(logn)
	 */
	public String max() {
		return root.maxVal(); // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty array
	 * if the tree is empty.
	 * O(n)
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
	 * O(n)
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
	 * O(1)
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
	 * O(1)
	 */
	public IWAVLNode getRoot() {
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
	 * O(n)
	 */
	public String select(int i) {
		WAVLNode current = root.minNode();
		for (int j=1; j<i; j++)
			current = successor(current);
		return current.getValue();
	}
	
	/**
	 * return the value of the largest node by key size in the tree
	 * @return the value of smallest (by key) node so that return.key>this.key || null if this == minimum node of tree
	 * O(logn)
	 */
	public WAVLNode successor(WAVLNode node) {
		return node.successor();
	}
	
	/**
	 * return the value of the smallest node by key size in the tree
	 * @return the value of largest (by key) node so that return.key<this.key || null if this == minimum node of tree
	 * O(logn)
	 */
	public WAVLNode predeccessor(WAVLNode node) {
		return node.predeccessor();
	}
	
	/**
	 * make a rotate between the two nodes, takes care of ranks and subtree sizes
	 * @param father
	 * @param son must be left or right son of father
	 * @return the new root after the rotate
	 * O(1)
	 */
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
		
		if(father.rank > troubleMakerSon.rank)
			promote(troubleMakerSon);
		demote(father);
		// check if father is a leaf with 2,2
		if(father.needsDemote())
			demote(father);
		father.updateSize();
		troubleMakerSon.updateSize();
		
		return troubleMakerSon;
	}	

	/**
	 * make a double rotate between the two nodes, takes care of ranks and subtree sizes
	 * @param father
	 * @param son must be left or right son of father
	 * @return the new root after the rotate
	 * O(1)
	 */
	public WAVLNode doubleRotate(WAVLNode father, WAVLNode son) {
		WAVLNode holySpirit;
		int[] fatherDifs = father.difs();
		
		if (father.leftSon == son)
			holySpirit = son.rightSon;
		else
			holySpirit = son.leftSon;
		rotate(son, holySpirit);
		rotate(father, holySpirit);
		return holySpirit;
	}
	
	/**
	 * promote node to balance the tree
	 * @param node
	 * O(1)
	 */
	public void promote(WAVLNode node) {
		node.rank++;
	}
	
	/**
	 * demote node to balance the tree
	 * @param node
	 * O(1)
	 */
	public void demote(WAVLNode node) {
		node.rank--;
	}
	
	/**
	 * double demote right to balance the tree
	 * @param node
	 * O(1)
	 */
	public void doubleDemoteRight(WAVLNode node) {
		node.getRight().rank++;
		node.rank--;
	}
	
	/**
	 * double demote left to balance the tree
	 * @param node
	 * O(1)
	 */
	public void doubleDemoteLeft(WAVLNode node) {
		node.getLeft().rank++;
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
		

		/**
		 * creates a new virtual leaf node with no ancestor
		 * @param dad
		 */
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

		/**
		 * creates a new virtual leaf node with dad as ancestor
		 * @param dad
		 */
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
		
		/**
		 * creates a new real leaf node with no ancestor
		 * @param key >0
		 * @param val
		 */
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
		
		public boolean isMiddleNode() {
			return (rightSon.isRealNode() && leftSon.isRealNode());
		}
		/**
		 * replace two real nodes in position within the tree
		 * @pre this node and node in param must be real
		 * @param node must be a real node
		 * O(1)
		 */
		public void replace(WAVLNode node) {
			int tempKey = this.key;
			String tempVal = this.val;
			
			this.key = node.key;
			this.val = node.val;
			node.key = tempKey;
			node.val = tempVal;
		}
		/**
		 * deletes this node in the tree when it is an unary node
		 * @pre this node is and unary node
		 * O(1)
		 */
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
		
		/**
		 * deletes this node in the tree when it is an leaf node
		 * @pre this node is a leaf
		 * O(1)
		 */
		public void deleteLeaf() {
			if(this.dad == null)
				root = new WAVLNode();
			else if(this.isLeftSon())
				this.dad.setLeftSon(new WAVLNode());
			else
				this.dad.setRightSon(new WAVLNode());
		}
		
		/**
		 * @pre this is a virtual node
		 * change a place holder virtual node with node
		 * @param node to be replaced
		 * O(1)
		 */
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
		
		/**
		 * check if this node is a left son
		 * O(1)
		 */
		private boolean isLeftSon() {
			if (this.dad == null)
				return false;
			return (this.dad.leftSon == this);
		}


		/**
		 * set mutually the node given as the right son of this (and this as dad of node)
		 * O(1)
		 */
		public void setRightSon(WAVLNode node) {
			this.rightSon = node;
			node.dad = this;
		}
		
		/**
		 * set mutually the node given as the left son of this (and this as dad of node)
		 * O(1)
		 */
		public void setLeftSon(WAVLNode node) {
			this.leftSon = node;
			node.dad = this;
			
		}
		
		/**
		 * calculates the subtree size of this node by adding the size of its sons
		 * O(1)
		 */
		public void updateSize() {
			treeSize = leftSon.treeSize + rightSon.treeSize +1;
		}
		
		/**
		 * @return -1 if virtual node
		 * O(1)
		 */
		public int getKey() {
			return key;
		}

		/**
		 * @return null if virtual node
		 * O(1)
		 */
		public String getValue() {
			return val;
		}

		/**
		 * @return -1 for virtual node, 0 for leaf
		 * O(1)
		 */
		public int getRank() {
			return rank;
		}

		/**
		 * @return null if virtual node
		 * O(1)
		 */
		public WAVLNode getLeft() {
			return leftSon;
		}

		/**
		 * @return null if virtual node
		 * O(1)
		 */
		public WAVLNode getRight() {
			return rightSon;
		}


		/**
		 * @return null if root
		 * O(1)
		 */
		public WAVLNode getDad() {
			return dad;
		}

		/**
		 * @return the largest (by key) node so that return.key<this.key || null if this == minimum node of tree
		 */
		public boolean isRealNode() {
			return isReal;
		}

		/**
		 * @return the smallest (by key) node so that return.key>this.key || null if this == maximum node of tree
		 * O(logn)
		 */
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
		
		/**
		 * return the prev node by key size in the tree
		 * @return the largest (by key) node so that return.key<this.key || null if this == minimum node of tree
		 * O(logn)
		 */
		public WAVLNode predeccessor() {
			if(leftSon.isRealNode())
				return leftSon.maxNode();
			WAVLNode prev = this;
			WAVLNode ans = prev.getDad();
			while(ans!=null && ans.getLeft()==prev) {
				prev = ans;
				ans = prev.getDad();
			}			
			return ans;
		}
		
		/**
		 * calculate the differences in ranks between this node and its sons
		 * @return int[] with difference from left son on int[0] and respectively from right in int[1]
		 * O(1)
		 */
		public int[] difs() {
			if(!isReal)
				return null;
			int[] difs = new int[2];
			difs[0] = this.getRank() - this.getLeft().getRank();
			difs[1] = this.getRank() - this.getRight().getRank();
			return difs;
		}
		
		/**
		 * check if this node has a valid rank according to the rules of WAVL trees
		 * O(1)
		 */
		public boolean isValidDifs(){
			int[] difs = this.difs();
			int[] opt1 = {1,2};
			int[] opt2 = {1,1};
			int[] opt3 = {2,1};
			int[] opt4 = {2,2};
			if( Arrays.equals(difs,(opt1)) || Arrays.equals(difs,(opt2)) || Arrays.equals(difs,(opt3)) || Arrays.equals(difs,(opt4)) )
					return true;
			return false;
		}
		
		
		/**
		 * check if this node needs to promoted to keep the ranks valid by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsPromote(){
			int[] difs = this.difs();
			int[] opt1 = {1,0};
			int[] opt2 = {0,1};
			if(difs != null && (Arrays.equals(difs, opt1) || Arrays.equals(difs, opt2)) )
					return true;
			return false;
		}
		
		/**
		 * check if any rotation is needed to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsRotate() {
			return(needsRightRotate()||needsLeftRotate());
		}
		
		/**
		 * check if rotation to the right needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsRightRotate(){
			int[] difs = this.difs();
			int[] opt1 = {0,2};
			int[] opt2 = {1,3};
			if(difs != null && (Arrays.equals(difs, opt1)||Arrays.equals(difs, opt2) ))
					return true;
			return false;
			
		}
		
		/**
		 * check if rotation to the left needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsLeftRotate(){
			int[] difs = this.difs();
			int[] opt1 = {2,0};
			int[] opt2 = {3,1};
			if(difs != null && (Arrays.equals(difs, opt1)||Arrays.equals(difs, opt2) ))
					return true;
			return false;
			
		}
		
		/**
		 * check if double rotation to the right needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsDoubleRotateRight(){
			if(!needsRightRotate())
				return false;
			int[] problem = {2,1};
			if((Arrays.equals(this.leftSon.difs(),(problem) )))
					return true;
			return false;
			
		}
		
		/**
		 * check if double rotation to the left needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsDoubleRotateLeft(){
			if(!needsLeftRotate())
				return false;
			int[] problem = {1,2};
			if(Arrays.equals(this.rightSon.difs(),(problem)))
					return true;
			return false;
			
		}
		
		/**
		 * check if demote needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsDemote(){
			int[] difs = this.difs();
			int[] opt1 = {3,2};
			int[] opt2 = {2,3};
			int[] opt3 = {2,2};
			if(difs != null && (Arrays.equals(difs, opt1) || Arrays.equals(difs, opt2)) ||  ( Arrays.equals(difs, opt3) && this.isLeaf() ) )
					return true;
			return false;
		}
		
		private boolean isLeaf() {
			return !(rightSon.isRealNode() || leftSon.isRealNode());
		}

		/**
		 * check if double demote to the right needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsDoubleDemoteRight(){
			if(!this.needsRightRotate())
				return false;
			int[] problem = {2,2};
			int[] rightSonDifs = this.getRight().difs();
			if(Arrays.equals(rightSonDifs, problem))
				return true;
			return false;
				
		}
		
		/**
		 * check if double demote to the left needs to be done to balance tree by
		 * checking the difference in ranks between this node and its sons
		 * O(1)
		 */
		public boolean needsDoubleDemoteLeft(){
			if(!this.needsLeftRotate())
				return false;
			int[] problem = {2,2};
			int[] leftSonDifs = this.getLeft().difs();
			if(Arrays.equals(leftSonDifs, problem))
				return true;
			return false;
				
		}
		
		/**
		 * @return size of subtree of this node
		 * O(1)
		 */
		public int getSubtreeSize() {
			return treeSize;
		}

		/**
		 * search for node
		 * @param key to search
		 * @return pointer to node || null if none found
		 * O(logn)
		 */
		public WAVLNode searchNode(int k) {
			if (!isRealNode() || getKey() == k)
				return this;
			if (k < getKey())
				return leftSon.searchNode(k);
			else
				return rightSon.searchNode(k);
		}
		
		/**
		 * search for node with key k and return value
		 * @param key to search
		 * @return value of node || null if none found
		 * O(logn)
		 */
		public String searchVal(int k) {
			WAVLNode node = searchNode(k);
			if (!node.isRealNode())
				return null;
			return searchNode(k).getValue();
		}
		
		/**
		 * @return minimal node in subtree
		 * O(logn)
		 */
		public WAVLNode minNode() {
			if (!this.isRealNode() || !leftSon.isRealNode() )
				return this;
			return leftSon.minNode();
		}
		
		/**
		 * @return value of minimal node in subtree
		 * O(logn)
		 */
		public String minVal() {
			return this.minNode().getValue();
		}
		
		/**
		 *  @return maximal node in subtree
		 * O(logn)
		 */
		public WAVLNode maxNode() {
			if (!this.isRealNode() || !rightSon.isRealNode() )
				return this;
			return rightSon.maxNode();
		}
		
		/**
		 * @return value of maximal node in subtree
		 *  O(logn)
		 */
		public String maxVal() {
			return this.maxNode().getValue();
		}
		
		
	}

}
