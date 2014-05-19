package com.hoover.BankOCR;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AccountNumber {

	private static final int NUMCHARINACCOUNT = 9;
	private Character[] accountNumber = new Character[NUMCHARINACCOUNT];
	private Set<String> alternateAccountNumbers;

	/**
	 * Initialize the account number, with a list of 9 - 3x3 arrays parses them
	 * into an array of characters.
	 * 
	 * @param inputString
	 * @throws Exception
	 */
	public AccountNumber(List<char[][]> inputString) throws Exception {
		if (inputString.size() != NUMCHARINACCOUNT) {
			throw new Exception("There must be " + NUMCHARINACCOUNT
					+ " characters for a valid account number");
		}
		int i = 0;
		for (char[][] character : inputString) {
			accountNumber[i++] = new Character(character);
		}
	}

	/**
	 * returns a string with the account representation. If a character match
	 * couldn't be found, returns a ? in that position.
	 **/
	public String getAccountRepresentation() {
		StringBuilder sb = new StringBuilder();
		for (Character c : accountNumber) {
			if (c.getValue() == null) {
				sb.append("?");
			} else {
				sb.append(c.getValue().toString());
			}
		}
		return sb.toString();
	}

	/**
	 * The output string for the account code. This has quite a bit of business
	 * logic, so it's more than just a simple toString.
	 * 
	 * If the
	 * 
	 * @return
	 */
	public String toOutputString() {
		String returnString = getAccountRepresentation();
		if (getIsValid() && validCheckSum()) {
			return returnString;
		}
		// The original code wasn't valid, but there is only one possible
		// alternate.
		if (getAlternateAccountNumbers().size() == 1) {
			return (String) getAlternateAccountNumbers().toArray()[0];
		}
		if (!getAlternateAccountNumbers().isEmpty()) {
			returnString += " AMB [";
			for (String account : getAlternateAccountNumbers()) {
				returnString += "'" + account + "'";
			}
			returnString += "]";
			return returnString;
		}
		if (getIsValid()) {
			if (!validCheckSum()) {
				returnString += " ERR";
			}
		} else {
			returnString += " ILL";
		}
		return returnString;
	}

	/**
	 * returns a set with alternate account numbers. Alternate account numbers
	 * are numbers that have a valid checksum where the account number was off
	 * by a single scanned character.
	 * 
	 * @return
	 */
	public Set<String> getAlternateAccountNumbers() {
		if (alternateAccountNumbers == null) {
			alternateAccountNumbers = new HashSet<String>();
			findAlternates();
		}
		return alternateAccountNumbers;
	}

	/**
	 * Looks for all alternatives for when the account number is off by one
	 * single scanned character.
	 */
	public void findAlternates() {
		Integer[] baseAccountNumber = accountTransformation(accountNumber);
		for (int i = 0; i < 9; i++) {
			List<Integer> alternates = alternateRepresentations(accountNumber[i]);
			for (int altInt : alternates) {
				Integer[] newAccountNumber = Arrays
						.copyOf(baseAccountNumber, 9);
				newAccountNumber[i] = altInt;
				if (validCheckSum(newAccountNumber)) {
					addAlternateToList(newAccountNumber);
				}
			}
		}
	}

	private void addAlternateToList(Integer[] accountNumber) {
		StringBuilder sb = new StringBuilder();
		for (Integer i : accountNumber) {
			sb.append(i.toString());
		}
		alternateAccountNumbers.add(sb.toString());
	}

	/**
	 * returns true if a specific character has alternate representations
	 */

	private List<Integer> alternateRepresentations(Character c) {
		List<Integer> alternateRepresentations = new ArrayList<Integer>();
		if (!c.getAlternatives().isEmpty()) {
			for (Character altC : c.getAlternatives()) {
				alternateRepresentations.add(altC.getValue());
			}
		}
		return alternateRepresentations;
	}

	/**
	 * Checks to see if the account is only composed of valid digits, ones that
	 * can be converted.
	 * 
	 * @return
	 */
	public boolean getIsValid() {
		for (Character c : accountNumber) {
			if (c.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines if the checksum of the current account number is valid or not.
	 * 
	 * @return
	 */

	public boolean validCheckSum() {
		return validCheckSum(accountTransformation(accountNumber));
	}

	/**
	 * Calculates the checksum of an array of Integers. If one of the integers
	 * is a null, then returns a false.
	 * 
	 * @param accountNumber
	 * @return
	 */
	private boolean validCheckSum(Integer[] accountNumber) {
		int checksum = 0;
		for (int i = 8; i >= 0; i--) {
			if (accountNumber[i] == null) {
				return false;
			}
			checksum += accountNumber[i] * (9 - i);
		}
		return checksum % 11 == 0;
	}

	/**
	 * Transforms an array of Character into an Array of Integer
	 * 
	 * @param accountNumber
	 * @return
	 */
	private Integer[] accountTransformation(Character[] accountNumber) {
		Integer[] account = new Integer[9];
		for (int i = 0; i < 9; i++) {
			account[i] = accountNumber[i].getValue();
		}
		return account;
	}

}
