package tests;

import wavl.WAVLTree;
import wavl.WAVLTree.WAVLNode;

public class Test {
	
	public static void main(String[] args) {
		int[] insertCounter = new int[10],
			  deleteCounter = new int[10];
		int maxInsert = 0,
			maxDelete = 0,
			currOps = 0;
		WAVLTree tree2 = new WAVLTree();
<<<<<<< HEAD
		//WAVLNode node1 = tree1.new WAVLNode();
		//WAVLNode node2 = tree2.new WAVLNode(2,"two");
		//tree2.test();

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
/*
		System.out.println("double trouble");
		tree2.doubleRotate(tree2.getRoot(), tree2.getRoot().leftSon);
		System.out.println(tree2.root.getKey());
		System.out.println(tree2.root.leftSon.getKey());
		System.out.println(tree2.root.leftSon.rightSon.getKey());
		System.out.println(tree2.root.rightSon.getKey());
		System.out.println(tree2.root.rightSon.leftSon.getKey());
		System.out.println(tree2.root.rightSon.rightSon.getKey());
		System.out.println(tree2.root.leftSon.leftSon.getKey());
	*/	

		tree2.insert(1, "1");
		TreePrinter.printNode(tree2.root);
		tree2.insert(2, "2");
		TreePrinter.printNode(tree2.root);
		tree2.insert(3, "3");
		TreePrinter.printNode(tree2.root);
		tree2.insert(4, "4");
		TreePrinter.printNode(tree2.root);
		tree2.insert(5, "5");
		TreePrinter.printNode(tree2.root);
		tree2.insert(6, "6");
		TreePrinter.printNode(tree2.root);
		tree2.insert(7, "7");
		System.out.println("testing size");
		System.out.println(tree2.root.getSizeRec());
		System.out.println("finished testing size");

		TreePrinter.printNode(tree2.root);

		TreePrinter.printNode(tree2.root);
		tree2.delete(7);

		TreePrinter.printNode(tree2.root);
		tree2.delete(2);

		TreePrinter.printNode(tree2.root);
		tree2.delete(6);		
		TreePrinter.printNode(tree2.root);
=======
		for(int i=1; i<=10; i++) {
			for(int j=1; j<=i*10000;j++) {
				currOps = tree2.insert(j,""+j);
				insertCounter[i-1] += currOps;
				if (currOps>maxInsert)
					maxInsert = currOps;
			}
			for(int j=1; j<=i*10000;j++) {
				currOps = tree2.delete(j);
				deleteCounter[i-1] += currOps;
				if (currOps>maxDelete)
					maxDelete = currOps;
			}
		}
		System.out.println("max number of insert rebalance operations:" + maxInsert);
		System.out.println("total operations of rebalances for inserts in experiments:");
		for(int i=0; i<10; i++)
			System.out.println("     experiment number: "+(i+1)+":"+insertCounter[i]+
					"\t\n          average is: "+insertCounter[i]/((i+1)*10000.0));
>>>>>>> fe1f917ea21f198f22e4d5e245b25f3f422fca2f
		
		System.out.println("\n");
		
		System.out.println("max number of insert rebalance operations:" + maxDelete);
		System.out.println("total operations of rebalances for deletions in experiments:");
		for(int i=0; i<10; i++)
			System.out.println("     experiment number: "+(i+1)+":"+deleteCounter[i]+
					"\t\n          average is: "+(deleteCounter[i]/((i+1)*10000.0)));
		
		

	}
	
}
