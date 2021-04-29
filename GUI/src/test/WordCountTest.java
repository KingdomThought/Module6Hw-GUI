package test;

import application.MainController;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class WordCountTest {


    private MainController mainController;

    @Before
    public void setup() {
        mainController = new MainController();
    }

    @Test
    public void testReadFileAndGetWordCountFromDB() throws IOException {
        System.out.println("****************************************************************************************************************");
        System.out.println("RUNNING: Full Flow Test(File Read, Database/ Table Create, Load Data in DB & get top 20 words");
        assertEquals("The Word The Occured 57 Times\n" +
                "The Word and Occured 38 Times\n" +
                "The Word I Occured 27 Times\n" +
                "The Word my Occured 24 Times\n" +
                "The Word of Occured 22 Times\n" +
                "The Word that Occured 18 Times\n" +
                "The Word this Occured 16 Times\n" +
                "The Word a Occured 15 Times\n" +
                "The Word chamber Occured 11 Times\n" +
                "The Word on Occured 10 Times\n" +
                "The Word bird Occured 10 Times\n" +
                "The Word is Occured 10 Times\n" +
                "The Word at Occured 8 Times\n" +
                "The Word with Occured 8 Times\n" +
                "The Word From Occured 8 Times\n" +
                "The Word ?Nevermore.? Occured 8 Times\n" +
                "The Word in Occured 7 Times\n" +
                "The Word nothing Occured 7 Times\n" +
                "The Word above Occured 7 Times\n" +
                "The Word soul Occured 7 Times\n", mainController.WordCount(null));
        System.out.println("SUCCESS: Full Flow Test");
        System.out.println("****************************************************************************************************************\n\n");
    }

    @Test
    public void testCreateDatabaseConnection() throws SQLException, ClassNotFoundException {
        System.out.println("****************************************************************************************************************");
        System.out.println("RUNNING: Check Create Database Connection");
        mainController.createConnection();
        assertNotNull("Verify that thing is NOT null", MainController.conn);
        System.out.println("SUCCESS: Check Create Database Connection");
        System.out.println("****************************************************************************************************************\n\n");
    }

    @Test
    public void testCreateTableAndInsertData() throws SQLException, ClassNotFoundException {
        System.out.println("****************************************************************************************************************");
        System.out.println("RUNNING: Test Create Table and Insert Data");
        mainController.createConnection();
        mainController.createDatabaseAndTable();
        mainController.insertData("Test");
        assertEquals("The Word Test Occured 1 Times\n", mainController.getTop20WordsFromDatabase());
        System.out.println("SUCCESS: Test Create Table and Insert Data");
        System.out.println("****************************************************************************************************************\n\n");
    }

    @Test
    public void testCreateTableAndInsertDataFrequency() throws SQLException, ClassNotFoundException {
        System.out.println("****************************************************************************************************************");
        System.out.println("RUNNING: Test Create Table and Insert Data and Word Frequency");
        mainController.createConnection();
        mainController.createDatabaseAndTable();
        mainController.insertData("Test");
        mainController.insertData("Test");
        mainController.insertData("Example");
        assertEquals("The Word Test Occured 2 Times\n" +
                "The Word Example Occured 1 Times\n", mainController.getTop20WordsFromDatabase());
        System.out.println("SUCCESS: Test Create Table and Insert Data and Word Frequency");
        System.out.println("****************************************************************************************************************\n\n");
    }



}


