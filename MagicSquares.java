/* This program creates Magic Squares for any odd number. It also runs verification for each magic square.
Please see README for more information.
Author: Ahmed Raza */

import java.io.*;
import java.util.*;
import java.math.*;

public class MagicSquares {
	public static void main(String[] args) {
		System.out.println("\nThis program prints Magic Squares, which counts from 1 to the number squares there are. In a Magic Square, ALL rows, " + 
			"columns, and diagonals add up to one number: the Magic Constant, which is different based on the size of the square. There is also " +
			"a verification part of this program, which verifies every row, column, and diagonal. You can turn off displaying the verification " +
			"by simply writing typing OFF (and press Enter). Please enter an odd number to generate a magic square of that size e.g. 3\n");

		int squareSizeAndVerif[] = new int[2];
		squareSizeAndVerif[1] = 1; // at index 1, contains whether the user wants to display verification information
		getSquareSize(squareSizeAndVerif);
		int squareSize = squareSizeAndVerif[0]; // square size is at index[0]
		int verif = squareSizeAndVerif[1]; // user preference on verification at index[1]
		int[][] mSquare = new int[squareSize][squareSize];
		double counter = Math.pow(squareSize, 2);
		int tmpRow = 0;
		int tmpCol = (squareSize/2);
		int row = 0;
		int col = (squareSize/2); // middle of top column
		mSquare[row][col] = 1; // sets top, middle column to 1

		createMagicSquare(squareSize, mSquare, counter, tmpRow, tmpCol, row, col, squareSizeAndVerif); // calls method to create the magic square and print it out	
	}

	public static int[] getSquareSize(int[] squareSizeAndVerif) {
		Scanner keyboard = new Scanner(System.in);
		boolean cont = true;
		while(cont) {
			System.out.print("Size of Magic Square: ");
			if(keyboard.hasNextInt()) {
				squareSizeAndVerif[0] = keyboard.nextInt();
				if(squareSizeAndVerif[0] % 2 == 0) 
					System.out.println("Please enter an odd number.");
				else
					cont = false;
			}
			else if(!keyboard.hasNextInt()) {
				String userInput = keyboard.nextLine();
				if(userInput.equals("OFF")) {
					System.out.println("Displaying verification turned off.");
					squareSizeAndVerif[1] = 0;
				}
			}	
		}
	return squareSizeAndVerif;
	}

	public static void createMagicSquare(int squareSize, int[][] mSquare, double counter, int tmpRow, int tmpCol, int row, int col, int[] squareSizeAndVerif) {
		for(int i = 2; i <= counter; i++) {
			if(tmpRow - 1 < 0) {
				tmpRow += squareSize - 1;
			}
			else {
				tmpRow--;
			}
			if(tmpCol + 1 >= squareSize) {
				tmpCol -= squareSize - 1;
			}
			else {
				tmpCol++;
			}
			if(mSquare[tmpRow][tmpCol] != 0) { // if the space is filled, go down
				row += 1;
				if(row <= squareSize - 1) { // not out of bounds if you go down
					mSquare[row][col] = i;
					tmpRow = row;
					tmpCol = col;
				} else if(row > squareSize - 1) {
					row = 0;
					mSquare[row][col] = i;
					tmpRow = row;
					tmpCol = col;
				}
			} else {
				mSquare[tmpRow][tmpCol] = i;
				row = tmpRow;
				col = tmpCol;
			}
		}

		// prints out magic square
		System.out.println();
		for(int j = 0; j < squareSize; j++) {
			for(int k = 0; k < squareSize; k++) {
				System.out.print(mSquare[j][k] + "\t");
			}
			System.out.println("\n");
		}
			verify(squareSize, mSquare, counter, tmpRow, tmpCol, row, col, squareSizeAndVerif); // calls the verify method to verify all rows, columns, and diagonals (user
																								// can turn off)
	}

	public static void verify(int squareSize, int[][] mSquare, double counter, int tmpRow, int tmpCol, int row, int col, int[] squareSizeAndVerif) {	
		List<Integer> rowConstant = new LinkedList<Integer>();
		int rowC = 0; // constant for row
		boolean rowCheck = true;
		for(int j = 0; j < squareSize; j++) { // this checks all the rows to ensure the magic constant
			for(int k = 0; k < squareSize; k++) {
				rowC += mSquare[j][k];
			}
			rowConstant.add(rowC);
			rowC = 0;
		}
		System.out.println("Magic Constant: " + rowConstant.get(0) + "\n");

		if(squareSizeAndVerif[1] == 1) { // if the user turned OFF verification, it won't print the remaining information out

			// checks to ensure that all of the rows equal the same magic constant
			for(int i = 0; i < rowConstant.size() - 1; i++) {
				int tmp1 = rowConstant.get(i);
				int tmp2 = rowConstant.get(i+1);
				if(tmp1 != tmp2) 
					rowCheck = false;
				else 
					System.out.println("Row " + (i + 1) + ": VERIFIED"); // verifies each individual row
			}
			System.out.println("Row " + (rowConstant.size()) + ": VERIFIED"); // because the program checks rows with each other, always one less, this adds
																			  // the last one manually
			if(rowCheck) 
				System.out.println("ALL ROWS VERIFIED\n"); // verifies the entire row check

			// calculates the constant for the columns
			List<Integer> colConstant = new LinkedList<Integer>();
			int colC = 0; // constant for column
			boolean colCheck = true;
			for(int j = 0; j < squareSize; j++) { 
				for(int k = 0; k < squareSize; k++) {
					colC += mSquare[k][j];
				}
				colConstant.add(colC);
				colC = 0;
			}

			// checks to ensure that all of the columns equal the same magic constant
			for(int i = 0; i < colConstant.size() - 1; i++) {
				int tmp1 = colConstant.get(i);
				int tmp2 = colConstant.get(i+1);
				if(tmp1 != tmp2) 
					colCheck = false;
				else 
					System.out.println("Column " + (i + 1) + ": VERIFIED"); // verifies each individual column
			}
			System.out.println("Column " + (colConstant.size()) + ": VERIFIED"); // because the program checks columns with each other, always one less, this 
																			  // adds the last one manually
			if(colCheck) 
				System.out.println("ALL COLUMNS VERIFIED\n"); // verifies the entire column check


			// calculates the constant for the diagnonals
			List<Integer> diagConstant = new LinkedList<Integer>();
			int diagC = 0; // constant for diagonal
			boolean diagCheck = true;

			// diagonal starting from top left
			for(int i = 0; i < squareSize; i++) 
				diagC += mSquare[i][i];
			diagConstant.add(diagC);
			diagC = 0;

			// diagonal starting from top right
			int c1 = squareSize - 1;
			for(int r = 0; r < squareSize; r++) {
					diagC += mSquare[r][c1];
					c1--;
			}
			diagConstant.add(diagC);
			diagC = 0;

			// diagonal starting from bottom left
			int c = 0;
			for(int r = squareSize - 1; r >= 0; r--) {
					diagC += mSquare[r][c];
					c++;
			}
			diagConstant.add(diagC);
			diagC = 0;

			// diagonal starting from bottom right
			for(int i = squareSize - 1; i >= 0; i--) 
				diagC += mSquare[i][i];
			diagConstant.add(diagC);
			diagC = 0;

			// checks to ensure all diagonals are equal
			for(int i = 0; i < 3; i++) { // always 4 diagonals
				int tmp1 = diagConstant.get(i);
				int tmp2 = diagConstant.get(i + 1);
				if(tmp1 != tmp2)
					diagCheck = false;
				else
					System.out.println("Diagonal " + (i + 1) + ": VERIFIED");
			} 
			System.out.println("Diagonal 4: VERIFIED");

			if(diagCheck) {
				System.out.println("ALL DIAGONALS VERIFIED\n");
			}
		}
	}
}	