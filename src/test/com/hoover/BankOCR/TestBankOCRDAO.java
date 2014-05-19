package com.hoover.BankOCR;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestBankOCRDAO {

	@Test
	public void test_loadFile() throws Exception {
		Charset utf8 = StandardCharsets.UTF_8;
		List<String> testData = Arrays.asList("Test1", "Test2");
		Files.write(Paths.get("testInput.txt"), testData, utf8);

		BankOCRDAO target = new BankOCRDAO();

		List<String> result = target.loadFile("testInput.txt");
		assertEquals(2, result.size());
	}

	@Test
	public void test_saveFile() throws Exception {
		List<String> testData = Arrays.asList("Test1", "Test2");

		BankOCRDAO target = new BankOCRDAO();

		target.writeFile(testData, "testInput.txt");
		List<String> result = target.loadFile("testInput.txt");
		assertEquals(2, result.size());
	}
}
