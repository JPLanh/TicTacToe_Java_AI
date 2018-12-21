import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graph {
	ArrayList<Node> nodeList = new ArrayList<Node>();

	public Node addNode(int[][] matrixGet) {
		Node tempNode = new Node(nodeList.size(), matrixGet);
		if (!nodeList.contains(tempNode)) {
			nodeList.add(tempNode);
			return tempNode;
		} else {	
			for (Node x : nodeList) {
				if (x.equals(tempNode)) return x;
			}
		}
		return null;
	}

	public void addNeighbor(Node node1, Node node2) {
		Node firstNode = null, secondNode = null;
		for (Node x : nodeList) {
			if (x.equals(node1)) {
				firstNode = x;
			}
			if (x.equals(node2)) {
				secondNode = x;				
			}
			if (firstNode != null && secondNode != null) 
				if (!firstNode.neighbor.contains(secondNode)) {
					firstNode.neighbor.add(secondNode);
				}
		}
	}
	public void addNeighbor(int[][] matrixGet1, int[][] matrixGet2) {
		Node tempNode = new Node(nodeList.size(), matrixGet2);
		if (!nodeList.contains(tempNode)) {
			System.out.println("Inputting Node Does Not Exist");
		} else {
			for (Node x : nodeList) {
				if (x.compareTo(new Node(matrixGet1)) == 1) {
					if (!x.neighbor.contains(tempNode)) {
						x.neighbor.add(tempNode);
						break;
					}
				}
			}
		}
	}

	public Node DFSAlpha(){
		Stack<Node> toTravel = new Stack<Node>();
		Stack<Node> layer = new Stack<Node>();
		ArrayList<Stack<Node>> possability = new ArrayList<Stack<Node>>();
		layer.push(nodeList.get(0));
		for (Node n : nodeList.get(0).neighbor) {
			toTravel.push(n);
		}
		while (!toTravel.isEmpty()) {
			Node currentNode = toTravel.peek();
			if (layer.peek().neighbor.contains(currentNode)) {
				toTravel.pop();
				//If the parent contains the child nodes
				if (currentNode.neighbor.size() > 0) {
					layer.add(currentNode);
					//If the node is not a leaf
					for (Node n : currentNode.neighbor) {
						toTravel.push(n);
					}
				} else {
					//If the node is a leaf
					if (currentNode.depth%2==0) layer.peek().depth = Math.max(layer.peek().winCon, currentNode.winCon);
					else layer.peek().depth = Math.min(layer.peek().winCon, currentNode.winCon);
					layer.add(currentNode); 
					possability.add((Stack<Node>) layer.clone());
					layer.pop();
					if (currentNode.winCon == 2) layer.peek().weight++;
					else if (currentNode.winCon == 1) layer.peek().weight--;
				}
			} else {
				layer.pop();
			}
		}		
		return bestChoice(DFShelper(possability));
	}

	public HashMap<Node, Integer> DFShelper(ArrayList<Stack<Node>> getList) {
		HashMap<Node, Integer> count = new HashMap<Node, Integer>();
		for (Stack<Node> x : getList) {
			if (count.containsKey(x.elementAt(1))) count.put(x.elementAt(1), count.get(x.elementAt(1))+1);
			else count.put(x.elementAt(1), 1);
		}
		BufferedWriter os;
		try {
			os = new BufferedWriter(new FileWriter("Test.txt"));
			String writer = count.toString();
			os.write(writer);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Node bestChoice(HashMap<Node, Integer> getMap) {
		//Ugly algorithm
		int highest = 0;
		Node selectedNode = null;
		for (Node n : getMap.keySet()) {
			System.out.println(n + " : " + n.weight);
			if (n.winCon == 1) return n;
			else if (n.winCon == 2) return n;
			if (getMap.get(n) > highest) {
				selectedNode = n;
				highest = getMap.get(n);
			}
		}
		return selectedNode;
	}
	
}

class Node implements Comparable<Node>{
	int ID = 1;
	int[][] matrix;
	int depth;
	int xSize = 2, ySize = 2;
	List<Node> neighbor = new ArrayList<Node>();
	int winCon = 0;
	int weight = 0;

	Node(){
		matrix = new int[xSize][ySize];
	}

	Node( int[][] matrixGet) {
		matrix = matrixGet;
		winCon = winCondition(matrixGet);
	}

	Node(Node matrixGet) {
		matrix = matrixGet.matrix;
		winCon = winCondition(matrixGet.matrix);
	}

	Node(int getID, int[][] matrixGet) {
		ID = getID;
		matrix = matrixGet;
		winCon = winCondition(matrixGet);
	}

	@Override
	public int compareTo(Node n0) {
		if (Arrays.deepEquals(this.matrix, n0.matrix)) return 1;
		else return 0;
	}


	@Override
	public int hashCode() {
		return 29 + ID;
	}

	@Override
	public boolean equals(Object o) {
		if (Arrays.deepEquals(this.matrix, ((Node)o).matrix)) return true;
		return false;
	}

	@Override
	public String toString() {
		String form = "\n{";
		for (int x = 0; x <= xSize; x++) {
			for (int y = 0; y <= ySize; y++) {
				form += "[" + matrix[x][y] + "]"; 
			}
			if (x!= xSize) form += ",";
		}
		form += "}";
		return form;
	}

	public static int winCondition(int[][] getMatrix) {
		for (int j = 1; j < 3; j++) {
			for (int i = 0; i < 3; i++){
				if (getMatrix[0][i] == j && getMatrix[1][i] == j && getMatrix[2][i] == j) return j;
				if (getMatrix[i][0] == j && getMatrix[i][1] == j && getMatrix[i][2] == j) return j;
			}
			if (getMatrix[0][0] == j && getMatrix[1][1] == j && getMatrix[2][2] == j) return j;
			if (getMatrix[0][2] == j && getMatrix[1][1] == j && getMatrix[2][0] == j) return j;
		}
		return 0;
	}
}