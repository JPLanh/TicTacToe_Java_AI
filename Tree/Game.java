
public class Game {
	int[][] field = new int[][]{{0,0,0},{0,0,0},{0,0,0}};
	int victory = 0;

	Game(){
	}

	boolean setMarker(int marker, int xPos, int yPos) {
		if (xPos>=0 && xPos<3 && yPos>=0 && yPos<3) {
			if (field[xPos][yPos] == 0) {
				field[xPos][yPos] = marker;
				victory = winCondition(field);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		String form = "{";
		for (int x = 0; x <= 2; x++) {
			for (int y = 0; y <= 2; y++) {
				form += "[" + field[x][y] + "]"; 
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
