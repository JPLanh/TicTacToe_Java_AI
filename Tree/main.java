import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.*;


public class main {

	public static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		Game table = new Game();
		while (table.victory == 0) {
			System.out.println(table);
			String[] getInput = input.nextLine().split(" ");
			if (getInput.length == 3) {
				if (table.setMarker(Integer.parseInt(getInput[0]), Integer.parseInt(getInput[1]), Integer.parseInt(getInput[2]))) {
					ArrayList<Stack<Node>> AIMove = generateAI(table.field).DFS();
					System.out.println(AIMove);
				}
			}
		}
		System.out.println(table.victory);
	}
	
	public static Graph generateAI(int[][] getField) {
		Graph myGraph = new Graph();

		myGraph.addNode(getField);

		Stack<Node> layers = new Stack<Node>();
		layers.push(myGraph.nodeList.get(0));
		int lastMove = 0;

		Instant start = Instant.now();
		while (!layers.isEmpty()) {
			boolean flag = false;
			Node lastNode = layers.peek();
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (lastNode.matrix[x][y] == 0 && !flag) {
						int[][] tempMatrix = new int[3][];
						for (int i = 0; i < 3; i++) {
							tempMatrix[i] = lastNode.matrix[i].clone();
						}
						tempMatrix[x][y] = (lastMove%2)+1;
						Node tempNode = new Node(tempMatrix);
						if (lastNode.winCon == 0) {
							if (!lastNode.neighbor.contains(tempNode)){						
								Node realNode = myGraph.addNode(tempMatrix);
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
			}
		}
		BufferedWriter os;
		try {
			os = new BufferedWriter(new FileWriter("Test.txt"));
			os.write("Lists\n");
			for (Node x : myGraph.nodeList) {
				String neighbors = x.neighbor.toString();
				os.append(x + ": " + neighbors +"\n");				
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Instant finish = Instant.now();
		long timeElapse = Duration.between(start, finish).toMillis();
		System.out.println("It took: " + timeElapse + " miliseconds.");
		return myGraph;
	}
}
