package com.hoover.BankOCR;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class TestBankOCR {

	/**
	 * Tests the whole class of the bank OCR by creating a test file and then
	 * parsing it and checking the output file
	 * 
	 * @throws Exception
	 */
	@Test
	public void test_BankOCR() throws Exception {
		createFile(getAccountNumber490867715(), "testInput.txt");
		BankOCR target = new BankOCR();
		target.processAccountNumbers("testInput.txt", "testOuput.txt");
		List<String> returnList = new ArrayList<String>();
		try (Scanner scanner = new Scanner(Paths.get("testOuput.txt"))) {
			while (scanner.hasNextLine()) {
				returnList.add(scanner.nextLine());
			}
		}
		assertEquals(1, returnList.size());
		assertEquals("490867715", returnList.get(0));
	}

	private void createFile(List<String> testData, String fileName)
			throws IOException {
		Charset utf8 = StandardCharsets.UTF_8;
		Files.write(Paths.get(fileName), testData, utf8);
	}

	private List<String> getAccountNumber490867715() {
		return Arrays.asList("    _  _  _  _  _  _     _ ",
				"|_||_|| ||_||_   |  |  | _ ", "  | _||_||_||_|  |  |  | _|");
	}
}
