package com.hoover.BankOCR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Character {

	/**
	 * The char array 3x3 representation of each character
	 */
	public final static char[][] ZERO = new char[][] { { ' ', '_', ' ' },
			{ '|', ' ', '|' }, { '|', '_', '|' } };
	public final static char[][] ONE = new char[][] { { ' ', ' ', ' ' },
			{ ' ', ' ', '|' }, { ' ', ' ', '|' } };
	public final static char[][] TWO = new char[][] { { ' ', '_', ' ' },
			{ ' ', '_', '|' }, { '|', '_', ' ' } };
	public final static char[][] THREE = new char[][] { { ' ', '_', ' ' },
			{ ' ', '_', '|' }, { ' ', '_', '|' } };
	public final static char[][] FOUR = new char[][] { { ' ', ' ', ' ' },
			{ '|', '_', '|' }, { ' ', ' ', '|' } };
	public final static char[][] FIVE = new char[][] { { ' ', '_', ' ' },
			{ '|', '_', ' ' }, { ' ', '_', '|' } };
	public final static char[][] SIX = new char[][] { { ' ', '_', ' ' },
			{ '|', '_', ' ' }, { '|', '_', '|' } };
	public final static char[][] SEVEN = new char[][] { { ' ', '_', ' ' },
			{ ' ', ' ', '|' }, { ' ', ' ', '|' } };
	public final static char[][] EIGHT = new char[][] { { ' ', '_', ' ' },
			{ '|', '_', '|' }, { '|', '_', '|' } };
	public final static char[][] NINE = new char[][] { { ' ', '_', ' ' },
			{ '|', '_', '|' }, { ' ', '_', '|' } };

	/**
	 * A map from the integer value to the char array representation.
	 */
	private final static Map<Integer, char[][]> valueMap = getValueMap();

	// This can be null, if there is no possible return value
	private Integer value;

	private char[][] originalArray = new char[9][9];

	// how many characters are different. This is stored so that eventually we
	// might sort on how far off the characters are.
	private Integer difference = 0;

	// How many incorrect characters are we going to allow?
	private int numberOfIncorrectCharacters = 1;

	// If there there are alternatives for this character, keep them here.
	private List<Character> alternatives = new ArrayList<Character>();

	/**
	 * This is a private constructor for use during the alternating characters.
	 * Since we already know the integer value, set it and then set the original
	 * array to be the corrected array.
	 * 
	 * @param value
	 * @param difference
	 */
	private Character(int value, int difference) {
		this.value = value;
		this.difference = difference;
		this.originalArray = valueMap.get(value);
	}

	/*
	 * creates a character from the original 3x3 array
	 */
	public Character(char[][] character) {
		originalArray = character;
		for (Entry<Integer, char[][]> valueSet : valueMap.entrySet()) {
			int characterDifference = deepMatchDifference(character,
					valueSet.getValue());
			if (characterDifference == 0) {
				value = valueSet.getKey();
				difference = 0;
			} else if (characterDifference <= numberOfIncorrectCharacters) {
				alternatives.add(new Character(valueSet.getKey(),
						characterDifference));
			}
		}
	}

	private static Map<Integer, char[][]> getValueMap() {
		Map<Integer, char[][]> valueMap = new HashMap<Integer, char[][]>();
		valueMap.put(0, ZERO);
		valueMap.put(1, ONE);
		valueMap.put(2, TWO);
		valueMap.put(3, THREE);
		valueMap.put(4, FOUR);
		valueMap.put(5, FIVE);
		valueMap.put(6, SIX);
		valueMap.put(7, SEVEN);
		valueMap.put(8, EIGHT);
		valueMap.put(9, NINE);
		return valueMap;
	}

	/**
	 * Calculates how far off the match is from the passed in character. Assumes
	 * that character and match are both 3x3 arrays
	 * 
	 * @param character
	 * @param match
	 * @return
	 */
	public int deepMatchDifference(final char[][] character,
			final char[][] match) {
		int difference = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (character[i][j] != match[i][j]) {
					difference++;
				}
			}
		}
		return difference;
	}

	/**
	 * Returns a list of the alternatives that are off by less than
	 * numberOfIncorrectCharacters
	 * 
	 * @return
	 */
	public List<Character> getAlternatives() {
		return this.alternatives;
	}

	public int getDifference() {
		return this.difference;
	}

	public char[][] getOriginalArray() {
		return originalArray;
	}

	/**
	 * returns the value or null if there is no possible value
	 * 
	 * @return
	 */
	public Integer getValue() {
		return value;
	}

}
