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

		/*	
		System.out.println(tree2.min());
		System.out.println(tree2.max());
		System.out.println(tree2.search(10));
		System.out.println(Arrays.toString(tree2.keysToArray()));
		System.out.println(tree2.root.leftSon.rightSon.getKey());

		System.out.println(tree2.select(8));
		// check rotate
		System.out.println("rotate");
		tree2.rotate(tree2.getRoot(), tree2.getRoot().leftSon);
		System.out.println(tree2.root.getKey());
		System.out.println(tree2.root.leftSon.getKey());
		System.out.println(tree2.root.rightSon.getKey());
		System.out.println(tree2.root.rightSon.leftSon.getKey());

		tree2.rotate(tree2.getRoot(), tree2.getRoot().rightSon);
		System.out.println(tree2.root.getKey());
		System.out.println(tree2.root.rightSon.getKey());
		System.out.println(tree2.root.leftSon.getKey());
		System.out.println(tree2.root.leftSon.rightSon.getKey());
		
		*/

		System.out.println("double trouble");
		tree2.doubleRotate(tree2.getRoot(), tree2.getRoot().leftSon);
		System.out.println(tree2.root.getKey());
		System.out.println(tree2.root.leftSon.getKey());
		System.out.println(tree2.root.leftSon.rightSon.getKey());
		System.out.println(tree2.root.rightSon.getKey());
		System.out.println(tree2.root.rightSon.leftSon.getKey());
		System.out.println(tree2.root.rightSon.rightSon.getKey());
		System.out.println(tree2.root.leftSon.leftSon.getKey());
		
		
		tree2.insert(4, "fdf");
		
		System.out.println(tree2.search(4));
		
		

	}
	
}
