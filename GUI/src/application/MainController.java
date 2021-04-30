package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;




import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.sql.*;

public class MainController implements Initializable {

	
	/**
	 * 
	 */
    @FXML
    private TextArea result;

    static TextArea staticTxtArea;

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "root";

    static public Connection conn = null;

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void createConnection() throws ClassNotFoundException, SQLException {
        //Register JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void createDatabaseAndTable() {
        Statement stmt = null;
        try{

            stmt = conn.createStatement();

            // Drop the database if it already exists
            stmt.executeUpdate("DROP DATABASE IF EXISTS wordoccurrences");

            // Create a database named wordoccurrences
            String sql = "CREATE DATABASE wordoccurrences";
            stmt.executeUpdate(sql);

            // Create a Table named word with 2 columns word and frequency
            String createTableSql = "CREATE TABLE wordoccurrences.word (" +
                    " word varchar(255)," +
                    " frequency int)";
            stmt.executeUpdate(createTableSql);
        }catch(SQLException se){
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
        }
    }

    public void insertData(String word) {
        Statement stmt = null;
        try{
            boolean wordExist = false;
            stmt = conn.createStatement();
            // Check if word already exists in database
            String getWordSql = "SELECT frequency from wordoccurrences.word where word = '"+word+"'";
            ResultSet rs = stmt.executeQuery(getWordSql);
            int frequency = 1;
            // if word exists get the existing frequency
            while(rs.next()){
                wordExist = true;
                frequency  = rs.getInt("frequency");
            }
            // if word already exists increment the frequency
            if(wordExist) {
                frequency++;
                // update records in table for the given word
                stmt.executeUpdate("UPDATE wordoccurrences.word SET frequency = " + frequency + " where word = '"+word+"'");
            } else {
                // word does not exist, insert into the database
                stmt.executeUpdate("INSERT INTO wordoccurrences.word values ('"+word+"', "+frequency+")");
            }
        }catch(SQLException se){
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
        }
    }
    
    
/**
 * Returns the top most frequent words from text input file
 * @return The most 20 frequent word Occurrence from  input file
 */
    public String getTop20WordsFromDatabase(){
        Statement stmt = null;
        StringBuilder stringBuilder = new StringBuilder();
        try{
            stmt = conn.createStatement();
            // Get the top 20 words from db
            String getTop20WordSql = "SELECT word, frequency from wordoccurrences.word order by frequency desc limit 20";
            ResultSet rs = stmt.executeQuery(getTop20WordSql);
            while(rs.next()){
                stringBuilder.append("The Word " + rs.getString("word") + " Occured " + rs.getInt("frequency") + " Times\n");
            }
        }catch(SQLException se){
            se.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
        }
        return stringBuilder.toString();
    }

    public String WordCount(ActionEvent event) throws IOException {

        String wordCountResult = null;
        try {
            // TODO Auto-generated method stub
            FileInputStream inputFile = new FileInputStream("TheRaven.txt");
            Scanner fileInput = new Scanner(inputFile);
            createConnection();
            createDatabaseAndTable();

            while (fileInput.hasNext()) {
                String nextWord = fileInput.next();
                insertData(nextWord);
            }

            fileInput.close();
            inputFile.close();

            wordCountResult = getTop20WordsFromDatabase();

            if(result != null) {
                //Setting the data in Text Area
                result.setText(wordCountResult);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        return wordCountResult;
    }

//	public void initialize (URL url, ResourceBundle rb) {

    //	staticTxtArea = textAreaUI;

//}

    @Override
    public void initialize(java.net.URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        staticTxtArea = result;
    }

    public TextArea getResult() {
        return result;
    }

    public void setResult(TextArea result) {
        this.result = result;
    }
}