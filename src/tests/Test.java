package tests;

import java.util.Arrays;

import wavl.WAVLTree;
import wavl.WAVLTree.WAVLNode;

public class Test {
	
	public static void main(String[] args) {

		WAVLTree tree1 = new WAVLTree();
		WAVLTree tree2 = new WAVLTree();
		WAVLNode node1 = tree1.new WAVLNode();
		WAVLNode node2 = tree2.new WAVLNode(2,"two");
		
		tree2.test();
<<<<<<< HEAD
		System.out.println(tree2.min());
		System.out.println(tree2.max());
		System.out.println(tree2.search(10));
		System.out.print(Arrays.toString(tree2.keysToArray()));
		System.out.print(tree2.root.leftSon.rightSon.getKey());
		
>>>>>>> master
		
		

	}
	
}
