package com.hoover.BankOCR;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestCharacter {

	private Character target;

	@Test
	public void test_zero() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ '|', ' ', '|' }, { '|', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 0, target.getValue());
	}

	@Test
	public void test_one() {
		char[][] testChar = new char[][] { { ' ', ' ', ' ' },
				{ ' ', ' ', '|' }, { ' ', ' ', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 1, target.getValue());
	}

	@Test
	public void test_two() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ ' ', '_', '|' }, { '|', '_', ' ' } };
		target = new Character(testChar);
		assertEquals((Integer) 2, target.getValue());
	}

	@Test
	public void test_three() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ ' ', '_', '|' }, { ' ', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 3, target.getValue());
	}

	@Test
	public void test_four() {
		char[][] testChar = new char[][] { { ' ', ' ', ' ' },
				{ '|', '_', '|' }, { ' ', ' ', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 4, target.getValue());
	}

	@Test
	public void test_five() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ '|', '_', ' ' }, { ' ', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 5, target.getValue());
	}

	@Test
	public void test_six() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ '|', '_', ' ' }, { '|', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 6, target.getValue());
	}

	@Test
	public void test_seven() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ ' ', ' ', '|' }, { ' ', ' ', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 7, target.getValue());
	}

	@Test
	public void test_eight() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ '|', '_', '|' }, { '|', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 8, target.getValue());
	}

	@Test
	public void test_nine() {
		char[][] testChar = new char[][] { { ' ', '_', ' ' },
				{ '|', '_', '|' }, { ' ', '_', '|' } };
		target = new Character(testChar);
		assertEquals((Integer) 9, target.getValue());
	}

	/**
	 * GIVEN: this is a nine, but the scan wasn't perfect and one character was
	 * off. This should return a 4 or a 9 because both are off by one character
	 * from this scan.
	 * 
	 */
	@Test
	public void test_nine_off_one() {
		char[][] testChar = new char[][] { { ' ', ' ', ' ' },
				{ '|', '_', '|' }, { ' ', '_', '|' } };
		target = new Character(testChar);
		assertNull(target.getValue());
		assertEquals(2, target.getAlternatives().size());
		assertEquals((Integer) 4, target.getAlternatives().get(0).getValue());
		assertEquals((Integer) 9, target.getAlternatives().get(1).getValue());
	}

	/**
	 * GIVEN: this is a nine, but the scan wasn't perfect and there were two
	 * characters off, so there should be no matching characters.
	 */
	@Test
	public void test_nine_two_characters() {
		char[][] testChar = new char[][] { { ' ', ' ', ' ' },
				{ '|', '_', '|' }, { ' ', '_', ' ' } };
		target = new Character(testChar);
		assertNull(target.getValue());
		assertEquals(0, target.getAlternatives().size());
	}
}
