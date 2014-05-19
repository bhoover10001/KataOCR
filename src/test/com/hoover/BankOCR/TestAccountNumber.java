package com.hoover.BankOCR;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TestAccountNumber {

	private AccountNumber target;

	private static final char[][] INVALIDCHAR = new char[][] {
			{ '|', ' ', ' ' }, { ' ', ' ', '|' }, { ' ', ' ', '|' } };

	/**
	 * testing the create object and making sure that there is an output.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAccountNumber() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE);
		target = new AccountNumber(testArray);
		assertEquals("111111111", target.getAccountRepresentation());
	}

	/**
	 * testing the create object and making sure that there is an output.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAccountNumber_input_too_small() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE, Character.ONE,
				Character.ONE, Character.ONE);
		try {
			target = new AccountNumber(testArray);
			fail("expected error was not thrown");
		} catch (Exception e) {
			assertEquals(
					"There must be 9 characters for a valid account number",
					e.getMessage());
		}
	}

	/**
	 * One of the characters did not scan properly. it should be a ?
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetAccountRepresentation_Invalid() throws Exception {
		List<char[][]> testArray = Arrays.asList(INVALIDCHAR, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE);
		target = new AccountNumber(testArray);
		assertEquals("?11111111", target.getAccountRepresentation());
	}

	/**
	 * One of the characters didn't scan properly, so this is an invalid account
	 * number.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIsValid_invalid_accountNumber() throws Exception {
		List<char[][]> testArray = Arrays.asList(INVALIDCHAR, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE);
		target = new AccountNumber(testArray);
		assertFalse(target.getIsValid());
	}

	/**
	 * All of the characters scanned properly
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetIsValid() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE, Character.ONE,
				Character.ONE, Character.ONE, Character.ONE);
		target = new AccountNumber(testArray);
		assertTrue(target.getIsValid());
	}

	/*
	 * Creates an account code that shouldn't have a valid checksum
	 */
	@Test
	public void test_checksum_not_valid() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.SIX, Character.SIX,
				Character.FOUR, Character.THREE, Character.SEVEN,
				Character.ONE, Character.FOUR, Character.NINE, Character.FIVE);
		target = new AccountNumber(testArray);
		assertFalse(target.validCheckSum());
	}

	/*
	 * Checksum should fail for scan with an invalid character
	 */
	@Test
	public void test_checksum_invalid_character() throws Exception {
		List<char[][]> testArray = Arrays.asList(INVALIDCHAR, Character.SIX,
				Character.FOUR, Character.THREE, Character.SEVEN,
				Character.ONE, Character.FOUR, Character.NINE, Character.FIVE);
		target = new AccountNumber(testArray);
		assertFalse(target.validCheckSum());
	}

	/*
	 * Creates an account code that should have a valid checksum
	 */
	@Test
	public void test_checksum_valid() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.THREE,
				Character.FOUR, Character.FIVE, Character.EIGHT,
				Character.EIGHT, Character.TWO, Character.EIGHT, Character.SIX,
				Character.FIVE);
		target = new AccountNumber(testArray);
		assertTrue(target.validCheckSum());
	}

	/*
	 * This is a valid account code, toString should just print the account
	 * number
	 */
	@Test
	public void test_toOutputstring() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.THREE,
				Character.FOUR, Character.FIVE, Character.EIGHT,
				Character.EIGHT, Character.TWO, Character.EIGHT, Character.SIX,
				Character.FIVE);
		target = new AccountNumber(testArray);
		assertEquals("345882865", target.toOutputString());
	}

	/*
	 * This was an invalid scan, so toString should append a "ERR"
	 */
	@Test
	public void test_tostring_invalid_scan() throws Exception {
		List<char[][]> testArray = Arrays.asList(INVALIDCHAR, Character.FOUR,
				Character.FIVE, Character.EIGHT, Character.EIGHT,
				Character.TWO, Character.EIGHT, Character.SIX, Character.FIVE);
		target = new AccountNumber(testArray);
		assertEquals("?45882865 ILL", target.toOutputString());
	}

	/*
	 * This is a valid account code, toString should just print the account
	 * number
	 */
	@Test
	public void test_tostring_checkScan_fail() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.SIX, Character.SIX,
				Character.FOUR, Character.THREE, Character.SEVEN,
				Character.ONE, Character.FOUR, Character.NINE, Character.FIVE);
		target = new AccountNumber(testArray);
		assertEquals("664371485", target.toOutputString());
	}

	/*
	 * Get alternates for an invalid code
	 */
	@Test
	public void test_get_alternates_invalid_code() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.NINE,
				Character.NINE, Character.NINE, Character.NINE, Character.NINE,
				Character.NINE, Character.NINE, Character.NINE, Character.NINE);
		target = new AccountNumber(testArray);
		Set<String> alternateAccountNumbers = target
				.getAlternateAccountNumbers();
		assertEquals(3, alternateAccountNumbers.size());
		assertTrue(alternateAccountNumbers.contains("899999999"));
		assertTrue(alternateAccountNumbers.contains("993999999"));
		assertTrue(alternateAccountNumbers.contains("999959999"));
		assertTrue(target.toOutputString().startsWith("999999999 AMB ['"));
		assertTrue(target.toOutputString().contains("'899999999'"));
		assertTrue(target.toOutputString().contains("'993999999'"));
		assertTrue(target.toOutputString().contains("'999959999'"));
		assertTrue(target.toOutputString().endsWith("']"));
	}

	/*
	 * This is a valid code, there should be no alternates.
	 */
	@Test
	public void test_get_alternates_valid_code() throws Exception {
		List<char[][]> testArray = Arrays.asList(Character.THREE,
				Character.FOUR, Character.FIVE, Character.EIGHT,
				Character.EIGHT, Character.TWO, Character.EIGHT, Character.SIX,
				Character.FIVE);
		target = new AccountNumber(testArray);
		Set<String> alternateAccountNumbers = target
				.getAlternateAccountNumbers();
		assertEquals(0, alternateAccountNumbers.size());
		assertEquals("345882865", target.toOutputString());
	}

	/**
	 * This is an invalid code, but there is only one possible alternate
	 */
	@Test
	public void test_invalidCode_OnePossible_alternate() throws Exception {
		char[][] invalidZero = new char[][] { { ' ', ' ', ' ' },
				{ '|', ' ', '|' }, { '|', '_', '|' } };
		List<char[][]> testArray = Arrays.asList(Character.ZERO, invalidZero,
				Character.ZERO, Character.ZERO, Character.ZERO, Character.ZERO,
				Character.ZERO, Character.FIVE, Character.ONE);
		target = new AccountNumber(testArray);
		Set<String> alternateAccountNumbers = target
				.getAlternateAccountNumbers();
		assertEquals(1, alternateAccountNumbers.size());
		assertEquals("000000051", target.toOutputString());
	}
}
