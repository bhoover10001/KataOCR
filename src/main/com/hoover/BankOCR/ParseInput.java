package com.hoover.BankOCR;

import java.util.ArrayList;
import java.util.List;

public class ParseInput {

	/**
	 * Takes all the input rows and returns them as a list of rows of 3x3
	 * character arrays.
	 * 
	 * @param inputString
	 * @return
	 */
	public static List<List<char[][]>> parseInput(List<String> inputString) {
		List<List<char[][]>> returnInput = new ArrayList<List<char[][]>>();
		for (int i = 0; i < inputString.size(); i += 4) {
			returnInput.add(parseRow(inputString.subList(i, i + 3)));
		}
		return returnInput;
	}

	/**
	 * Takes a list of 3 lines and parses it into a list of 3x3 array of char.
	 * 
	 * @param inputString
	 * @return
	 */
	protected static List<char[][]> parseRow(List<String> inputString) {
		List<char[][]> returnRow = new ArrayList<char[][]>();
		for (int i = 0; i < 27; i += 3) {
			returnRow.add(parseCharacterBlock(inputString, i));
		}
		return returnRow;
	}

	/**
	 * Takes a list of 3 lines and a position and returns the 3x3 array starting
	 * at that position
	 * 
	 * @param inputString
	 * @param pos
	 * @return
	 */
	protected static char[][] parseCharacterBlock(List<String> inputString,
			int pos) {
		char[][] characterBlock = new char[3][3];
		int endPosition = pos + 3;
		characterBlock[0] = inputString.get(0).substring(pos, endPosition)
				.toCharArray();
		characterBlock[1] = inputString.get(1).substring(pos, endPosition)
				.toCharArray();
		characterBlock[2] = inputString.get(2).substring(pos, endPosition)
				.toCharArray();
		return characterBlock;
	}
}
