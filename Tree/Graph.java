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
			if (firstNode != null && secondNode != null) firstNode.neighbor.add(secondNode);
		}
	}
	public void addNeighbor(int[][] matrixGet1, int[][] matrixGet2) {
		Node tempNode = new Node(nodeList.size(), matrixGet2);
		if (!nodeList.contains(tempNode)) {
			System.out.println("Inputting Node Does Not Exist");
		} else {
			for (Node x : nodeList) {
				if (x.compareTo(new Node(matrixGet1)) == 1) {
					if (!x.neighbor.contains(tempNode))
						x.neighbor.add(tempNode);
				}
			}
		}
	}

	public ArrayList<Stack<Node>> DFS() {
		Stack<Node> layers = new Stack<Node>();
		Set<Node> visited = new HashSet<Node>();
		ArrayList<Stack<Node>> possible = new ArrayList<Stack<Node>>();
		layers.push(nodeList.get(0));
		int travel = 0, shortest = 10;
		while (!layers.isEmpty()) {
			Node currentNode = layers.peek();
			visited.add(currentNode);
			if (currentNode.neighbor.size() > 0) {
				for (Node n : currentNode.neighbor) {
					if (!visited.contains(n)) {
						layers.push(n);
						travel++;
						break;
					}
					travel--;
					layers.pop();
				}
			}
			else {
				if (shortest > travel) {
					shortest = travel;
					possible = new ArrayList<Stack<Node>>();
					possible.add((Stack<Node>) layers.clone());
				} else if (shortest == travel){
					possible.add((Stack<Node>) layers.clone());
				}
				layers.pop();
				travel--;
			}
		}
		return possible;
	}
}

class Node implements Comparable<Node>{
	int ID = 1;
	int[][] matrix;
	int xSize = 2, ySize = 2;
	List<Node> neighbor = new ArrayList<Node>();
	int winCon = 0;
	
	Node(){
		matrix = new int[xSize][ySize];
	}

	Node( int[][] matrixGet) {
		matrix = matrixGet;
		winCon = winCondition(matrixGet);
	}

	Node(Node matrixGet) {
		matrix = matrixGet.matrix;
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
		String form = "{";
			for (int x = 0; x <= xSize; x++) {
				for (int y = 0; y <= ySize; y++) {
					form += "[" + matrix[x][y] + "]"; 
				}
				form += ",";
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