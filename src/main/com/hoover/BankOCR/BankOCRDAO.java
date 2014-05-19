package com.hoover.BankOCR;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankOCRDAO {

	public List<String> loadFile(String fileName) throws IOException {
		List<String> returnList = new ArrayList<String>();
		try (Scanner scanner = new Scanner(Paths.get(fileName))) {
			while (scanner.hasNextLine()) {
				returnList.add(scanner.nextLine());
			}
		}
		return returnList;
	}

	public void writeFile(List<String> accountNumbers, String fileName)
			throws IOException {
		Charset utf8 = StandardCharsets.UTF_8;
		Files.write(Paths.get(fileName), accountNumbers, utf8);
	}
}
