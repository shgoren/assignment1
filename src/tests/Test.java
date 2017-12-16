package tests;

import wavl.WAVLTree;
import java.util.Random;

public class Test {
	
	public static boolean CheckTree(WAVLTree.WAVLNode moshe)
	{
		if(moshe==null || !moshe.isRealNode())
		{
			return true;
		}
		else {
		int rightRank=moshe.getRank()-((WAVLTree.WAVLNode)moshe.getRight()).getRank();
		int leftRank=moshe.getRank()-((WAVLTree.WAVLNode)moshe.getLeft()).getRank();
		if(!((rightRank==2 && leftRank==1) || (rightRank==1 && leftRank==2)||(rightRank==2 && leftRank==2) || (rightRank==1 && leftRank==1)))
		{
			System.out.println("illegal rank diffrence");
			return false;
		}
		else
		{
			if(rightRank==2 && leftRank==2)
			{
				if(!moshe.getRight().isRealNode()&&!moshe.getLeft().isRealNode())
				{
					System.out.println("leaf with 2,2");
					return false;
				}
			}
		}
		return CheckTree((WAVLTree.WAVLNode)moshe.getLeft()) && CheckTree((WAVLTree.WAVLNode)moshe.getRight());
		}
	}

	public static void main(String[] args) {
		WAVLTree tree = new WAVLTree();
		int length = 100000,
			insertCounter = 0,
			deleteCounter = 0,
			deleteMaxCount = 0,
			insertMaxCount =0;	
		int[] insertCounterArr = new int[10],
			  deleteCounterArr = new int[10],
			  insertMaxCountArr = new int[10],
			  deleteMaxCountArr = new int[10];
		double[] insertAverageArr = new double[10],
			     deleteAverageArr = new double[10];
		Random randomGenerator = new Random(2);
		
		for (int i=1; i<=length; i++) {
			int j = i/10000;
			int n = randomGenerator.nextInt(1000000000);
			int tmp = tree.insert(n, "");
			 //if n is already in the tree
			if (tmp==-1) {
				i--;
				continue;
			}
			insertCounter += tmp;
			if (tmp > insertMaxCount) {
				insertMaxCount = tmp;
			}
			if (i%10000 == 0) {	
				insertMaxCountArr[j-1] = insertMaxCount;
				insertCounterArr[j-1] = insertCounter;
				insertAverageArr[j-1] = ((double)insertCounter)/i;
			}
		}
		
		int[] keysArray = tree.keysToArray();
		//delete
		for (int i=1; i<=keysArray.length; i++) {
			if (i == 28271)
				System.out.print("");
			int j = i/10000;
			int tmp = tree.delete(keysArray[i-1]);
			deleteCounter += tmp;
			if (tmp > deleteMaxCount) {
				deleteMaxCount = tmp;
			}
			if (i%10000 == 0) {
				// document state of experiment
				deleteMaxCountArr[j-1] = deleteMaxCount;
				deleteCounterArr[j-1] = deleteCounter;
				deleteAverageArr[j-1] = ((double)deleteCounter)/i;
			}
		}
		
		//print
		for(int i=0; i<10; i++) {
			System.out.println();
			System.out.println("num of nodes = " + (i+1)*10000 );
			System.out.println("insertCounter: " + insertCounterArr[i]);
			System.out.println("deleteCounter: " + deleteCounterArr[i]);
			System.out.println("avgInsert: " + insertAverageArr[i]);
			System.out.println("avgDelete: " + deleteAverageArr[i]);
			System.out.println("insertMaxCount: " + insertMaxCountArr[i]);
			System.out.println("deleteMaxCount: " + deleteMaxCountArr[i]);
			
		}
			
	}
	
}
