package com.hoover.BankOCR;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestParseInput {

	@Test
	public void testParseInput() {
		List<String> testBlock = Arrays.asList("123456789012345678901234567",
				"234567890123456789012345678", "345678901234567890123456789",
				"", "234567890123456789012345678",
				"345678901234567890123456789", "456789012345678901234567890",
				"");
		List<List<char[][]>> returnRow = ParseInput.parseInput(testBlock);
		assertEquals(2, returnRow.size());
		char[][] expectedChar1 = new char[][] { { '1', '2', '3' },
				{ '2', '3', '4' }, { '3', '4', '5' } };
		assertArrayEquals(expectedChar1, returnRow.get(0).get(0));
		char[][] expectedChar9 = new char[][] { { '6', '7', '8' },
				{ '7', '8', '9' }, { '8', '9', '0' } };
		assertArrayEquals(expectedChar9, returnRow.get(1).get(8));
	}

	@Test
	public void testParseRow() {
		List<String> testBlock = Arrays.asList("123456789012345678901234567",
				"234567890123456789012345678", "345678901234567890123456789");
		List<char[][]> returnRow = ParseInput.parseRow(testBlock);
		assertEquals(9, returnRow.size());
		char[][] expectedChar1 = new char[][] { { '1', '2', '3' },
				{ '2', '3', '4' }, { '3', '4', '5' } };
		assertArrayEquals(expectedChar1, returnRow.get(0));
		char[][] expectedChar9 = new char[][] { { '5', '6', '7' },
				{ '6', '7', '8' }, { '7', '8', '9' } };
		assertArrayEquals(expectedChar9, returnRow.get(8));
	}

	@Test
	public void testParseCharacterBlock() {
		List<String> testBlock = Arrays.asList("123456", "234567", "345678");
		char[][] returnBlock = ParseInput.parseCharacterBlock(testBlock, 0);
		char[][] expectedChar = new char[][] { { '1', '2', '3' },
				{ '2', '3', '4' }, { '3', '4', '5' } };
		assertArrayEquals(expectedChar, returnBlock);
	}

	@Test
	public void testParseCharacterBlock_start_non_zero() {
		List<String> testBlock = Arrays.asList("123456", "234567", "345678");
		char[][] returnBlock = ParseInput.parseCharacterBlock(testBlock, 3);
		char[][] expectedChar = new char[][] { { '4', '5', '6' },
				{ '5', '6', '7' }, { '6', '7', '8' } };
		assertArrayEquals(expectedChar, returnBlock);
	}
}
