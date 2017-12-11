package tests;

import wavl.WAVLTree;
import wavl.WAVLTree.WAVLNode;

public class Test {
	
	public static void main(String[] args) {

		WAVLNode node;
		WAVLTree tree2 = new WAVLTree();
		for(int j=1; j<40; j++) {
			for (int i = 1; i<=j; i++) {
				tree2.insert(i, ""+i);
			}
			for (int i = 8; i<=15; i++) {
				if(i==10 && j==16)
					TreePrinter.printNode(tree2.root);
				tree2.delete(i);
				for(int k = 1; k<=j; k++) {
					node = tree2.root.searchNode(k);
					if(node.isRealNode()) {
						if(!(node.treeSize == (node.leftSon.treeSize + node.rightSon.treeSize +1)))
							System.out.println("size error in tree of size: "+j+" in node: "+node.getKey()+" i = "+i);
					}
				}
				
			}
			/*for (int i = 1; i<=8; i++) {
				tree2.delete(i);
			}
			for (int i = 1; i<=j; i++) {
				tree2.insert(i, ""+i);
			}

			node = tree2.root.searchNode(i);
			for (int i = 1; i<=40; i++) {
				if(node.isRealNode()) {
					if(!(node.treeSize == (node.leftSon.treeSize + node.rightSon.treeSize +1)))
						System.out.println("size error in tree of size: "+j+" in node: "+node.getKey());
					if(!node.isValidDifs())
						System.out.println("rank error in tree of size: "+j+" in node: "+node.getKey());
				}
			}*/
		}
		
		
		

	}
	
}
