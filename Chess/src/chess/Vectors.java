package chess;

public class Vectors {

	public static boolean areEqual(int[] vector1, int[] vector2) {
		if (vectorLengthIsValid(vector1, vector2) && vector1[0] == vector2[0] && vector1[1] == vector2[1]) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int[] addVectors(int[] vector1, int[] vector2) {
		if(vectorLengthIsValid(vector1, vector2)) {
			int[] addedVector = new int[2];
			addedVector[0] = vector1[0] + vector2[0];
			addedVector[1] = vector1[1] + vector2[1];
			return addedVector;
		} else {
			System.err.println("Should not have happened, vectorLength is screwed up, look in Vector class");
			System.exit(2);
			return null;
		}
	}
	
	
	private static boolean vectorLengthIsValid(int[] vector1, int[] vector2) {
		if (vector1.length == 2 || vector2.length == 2) {
			return true;
		}
		else {
			System.err.println("The vector is larger than it should be. Look in the vectors class.");
			System.exit(1);
			return false;
		}
	}
}
