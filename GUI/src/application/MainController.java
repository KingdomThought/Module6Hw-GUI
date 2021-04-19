package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.print.DocFlavor.URL;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
public class MainController  {
    @FXML 
   // private TextArea myMessage;
    
   // static TextArea staticTxtArea;
	
//private    TextArea myMessage;
	
	public void WordCount(ActionEvent event) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream inputFile = new FileInputStream ("TheRaven.txt");
		Scanner fileInput = new Scanner(inputFile);
		
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<Integer> count = new ArrayList <Integer>();
		
		while(fileInput.hasNext()) {
		String nextWord = fileInput.next();
		if(words.contains(nextWord)) {
			int index = words.indexOf(nextWord);
			count.set(index, count.get(index)+1);
		}
		else {
			words.add(nextWord);
			count.add(1);
		}
	}

		fileInput.close();
		inputFile.close();
		
		for (int i = 0; i < words.size(); i++) {
	//myMessage.setText("The Word " + words.get(i) + " Occured " + count.get(i) + "Times");
		System.out.println("The Word " + words.get(i) + " Occured " + count.get(i) + "Times");
		
		
		
		
		}


    }}
