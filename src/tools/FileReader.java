package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import colections.Worker;

public class FileReader {
	public  ArrayList<Worker>  getObjFromFile(String nameFile) throws FileNotFoundException {

		Scanner sk = new Scanner(new File(nameFile));

		ArrayList<Worker> listWorkers = new ArrayList<Worker>();
		while (sk.hasNextLine()) {

			String json = sk.nextLine();
		
			Gson gson = new Gson();
			Worker obj = gson.fromJson(json, Worker.class);
			
			listWorkers.add(obj);
		}sk.close();
	
		return listWorkers;
	}

}
