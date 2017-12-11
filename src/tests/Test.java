package tests;

import wavl.WAVLTree;
import wavl.WAVLTree.WAVLNode;

public class Test {
	
	public static void main(String[] args) {
		int[] insertCounter = new int[10],
			  deleteCounter = new int[10];
		int max = 0,
			currOps = 0;
		WAVLTree tree2 = new WAVLTree();
		for(int i=1; i<=10; i++) {
			for(int j=1; j<=i*10000;j++) {
				currOps = tree2.insert(j,""+j);
				insertCounter[i-1] += currOps;
				if (currOps>max)
					max = currOps;
			}
			for(int j=1; j<=i*10000;j++) {
				currOps = tree2.delete(j);
				deleteCounter[i-1] += currOps;
				if (currOps>max)
					max = currOps;
			}
		}
		System.out.println("max number of rebalance operations:" + max);
		System.out.println("total operations of rebalances for inserts in experiments:");
		for(int i=0; i<10; i++)
			System.out.println("     experiment number: "+(i+1)+":"+insertCounter[i]+
					"\t\n          average is: "+insertCounter[i]/((i+1)*10000.0));
		System.out.println("total operations of rebalances for deletions in experiments:");
		for(int i=0; i<10; i++)
			System.out.println("     experiment number: "+(i+1)+":"+deleteCounter[i]+
					"\t\n          average is: "+(deleteCounter[i]/((i+1)*10000.0)));
		
		

	}
	
}
