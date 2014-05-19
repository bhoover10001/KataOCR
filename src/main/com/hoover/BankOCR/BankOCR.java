package com.hoover.BankOCR;

import java.util.ArrayList;
import java.util.List;

public class BankOCR {

	private BankOCRDAO bankOCRDAO = new BankOCRDAO();

	public void processAccountNumbers(String inputFile, String outputFile)
			throws Exception {
		List<String> inputString = bankOCRDAO.loadFile(inputFile);
		List<List<char[][]>> parsedString = ParseInput.parseInput(inputString);
		List<String> accountNumbers = new ArrayList<String>();
		for (List<char[][]> parsedRow : parsedString) {
			accountNumbers.add(new AccountNumber(parsedRow).toOutputString());
		}
		bankOCRDAO.writeFile(accountNumbers, outputFile);
	}

	/**
	 * args[0] is the full or relative file name for the input<br />
	 * args[1] is the full or relative file name for the output.
	 * 
	 * The output will be a list of parsed account numbers. <br />
	 * 
	 * If a digit cannot be parsed, the digit will be a ? and the account number
	 * will be appended with "ERR" in the output. If the account number doesn't
	 * pass the checksum, the account number will be appended with an "ILL" in
	 * the output.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out
					.println("There should be two arguments. \n  Argument 1 is the input file name.\nArgument 2 is the output file name");
			return;
		}
		BankOCR bankOCR = new BankOCR();
		bankOCR.processAccountNumbers(args[0], args[1]);
	}

}
