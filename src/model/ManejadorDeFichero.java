package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ManejadorDeFichero {

	public String[] getLanguageData(String language) {
		String csvFile = "src/languages/" + language + ".csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = "/";
		String[] languagedata = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				languagedata = line.split(cvsSplitBy);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return languagedata;
	}
}
