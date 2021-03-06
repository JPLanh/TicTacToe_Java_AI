import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


public class main {

	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		Game table = new Game();
		int lastMove = 1;
		while (table.victory == 0) {			
			System.out.println(table);
			String[] getInput = input.nextLine().split(" ");
			if (getInput.length == 2) {
				if (table.setMarker(lastMove, Integer.parseInt(getInput[1]), Integer.parseInt(getInput[0]))) {
					table.increaseMove();
					lastMove = (lastMove%2)+1;
					table.increaseMove();
					if (table.victory == 0) table.AImove(generateFullAI(table.field, lastMove).DFSAlpha(), lastMove);
					lastMove = (lastMove%2)+1;
				}
			}
		}
		System.out.println(table.victory + " is victorious");		
		System.out.println(table);
	}
	

	public static Graph generateFullAI(int[][] getField, int lastMove) {
		Graph myGraph = new Graph();
		int depth = 0;

		myGraph.addNode(getField);

		Stack<Node> layers = new Stack<Node>();

		Instant start = Instant.now();
		layers.push(myGraph.nodeList.get(0));
		while (!layers.isEmpty()) {
			boolean flag = false;
			Node lastNode = layers.peek();
			lastNode.depth = depth;
			depth++;
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (lastNode.matrix[x][y] == 0 && !flag) {
						int[][] tempMatrix = new int[3][];
						for (int i = 0; i < 3; i++) {
							tempMatrix[i] = lastNode.matrix[i].clone();
						}
						tempMatrix[x][y] = lastMove;
						Node tempNode = new Node(tempMatrix);
						if (lastNode.winCon == 0) {
							if (!lastNode.neighbor.contains(tempNode)){
								Node realNode = myGraph.addNode(tempMatrix);
								realNode.depth = depth;
								myGraph.addNeighbor(lastNode, realNode);
								layers.push(realNode);
								lastMove = (lastMove%2)+1;
								flag = true;
							}	
						}
					}
				}
			}
			if (!flag) {
				lastMove = (lastMove%2)+1;
				layers.pop();
				depth--;
			}
		}
		Instant finish = Instant.now();
		long timeElapse = Duration.between(start, finish).toMillis();
		System.out.println("It took: " + timeElapse + " miliseconds to finish.");

		BufferedWriter os;
		try {
			os = new BufferedWriter(new FileWriter("FullTest.txt"));
			os.write("Lists\n");
			for (Node x : myGraph.nodeList) {
				os.append(x + ": " + x.winCon + "\n");
//				String neighbors = x.neighbor.toString();
//				os.append(x + ": " + neighbors +"\n");				
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return myGraph;
	}
}
