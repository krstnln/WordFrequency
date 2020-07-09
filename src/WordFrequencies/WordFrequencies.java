package WordFrequencies;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * count top 20 most frequent words in a text file
 * @author Kristen
 *
 */


public class WordFrequencies extends Application {

	/**
	 * implements UI and takes in file location
	 */
    @Override
    public void start(Stage stage) throws IOException {

    	/**
    	 * label above input text box
    	 */
        Label inputLabel = new Label("Please enter the location of the file:");
        /**
         * text field to take file location
         */
        TextField inputText = new TextField();
        /**
         * button to signal entered file name
         */
        Button enter = new Button("Enter");

        /**
         * gridPane used for UI
         */
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(550, 75);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.add(inputLabel, 0, 1);
        gridPane.add(inputText, 1, 1);
        gridPane.add(enter, 2, 1);

        enter.setStyle("-fx-background-color: lightgreen; -fx-textfill: white;");
        inputLabel.setStyle("-fx-font: normal bold 15px 'serif' ");
        gridPane.setStyle("-fx-background-color: beige;");

        /**
         * scene used for UI
         */
        Scene scene = new Scene(gridPane);
        stage.setTitle("Word Frequency Analyzer");
        stage.setScene(scene);
        stage.show();

        enter.setOnAction(enterac ->
        {
        	String inputFile = inputText.getText();
        	print(calculate(openFile(inputFile)));
        });

    }


	/**
	 * deletes all non letter characters to prevent word count interference
	 *
	 * @param Current string taken from input file to be formatted
	 * @return string after being formatted
	 */

    public String format(String Current) {
     Current = Current.toLowerCase();

     Current = Current.replaceAll(" , ", "");
     Current = Current.replaceAll("\\,", "");
     Current = Current.replaceAll(",?", "");
     Current = Current.replaceAll("!", "");
     Current = Current.replaceAll("\\?", "");
     Current = Current.replaceAll("—", "");
     Current = Current.replaceAll(";", "");
     Current = Current.replaceAll("\\.", "");

     return Current;
    }


    /**
     * opens file and sorts words into an array list
     * to count frequency of each
     *
     * @param inputFile string containing name of file to be opened
     * @return arrayList of counted words
     */
    public ArrayList<Map.Entry<String, Integer>> openFile(String inputFile){

    	/**
    	 * string is turned into a file variable
    	 */
    	File fileName = new File(inputFile);

    	/**
    	 * fileReader to parse through file
    	 */
        FileReader read = null;

        try {
            read = new FileReader(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(WordFrequencies.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader buff = new BufferedReader(read);

        /**
         * hashmap to compute frequency of each word
         */
        Map<String, Integer> frequency = new HashMap<>();

        /**
         * arrayList to hold each word and its frequency
         */
        ArrayList<Map.Entry<String, Integer>> arrayList;
        arrayList = new ArrayList<>();

        /**
         * string to split text file into lines
         */
        String Poem;

        /**
         * string array to split line into individual words
         */
        String[] Word;

        try {
            while((Poem = buff.readLine()) != null){
                Word = Poem.split(" ");
                for (String Current : Word) {
                    Current = format(Current);
                    frequency.compute(Current, (k,v) -> (v == null) ? v = 1 : v + 1);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(WordFrequencies.class.getName()).log(Level.SEVERE, null, ex);
        }

        for(Map.Entry<String, Integer> e: frequency.entrySet())
            arrayList.add(e);

        return arrayList;

    }


    /**
     * sorts all words by frequency
     *
     * @param arrayList unsorted list of words and frequencies
     * @return list sorted by frequency
     */
    public ArrayList<Map.Entry<String, Integer>> calculate(ArrayList<Map.Entry<String, Integer>> arrayList){

    	/**
    	 * comparator to compare two frequencies
    	 * used to sort most frequent to least frequent
    	 */
        Comparator<Map.Entry<String, Integer>> comp;
        comp = (Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> {
            Integer int1 = e1.getValue();
            Integer int2 = e2.getValue();
            return int2.compareTo(int1);
        };

        Collections.sort(arrayList, comp);

        return arrayList;
    }


    /**
     * prints the 20 most frequent words found
     * in the arraylist and print to screen
     *
     * @param arrayList sorted arraylist
     */
    public static void print (ArrayList<Map.Entry<String, Integer>> arrayList){

    	/**
    	 * linked hashmap to parse through the
    	 * most frequent words in the arraylist
    	 */
    	LinkedHashMap<String, Integer> sortedFreq;
        sortedFreq = new LinkedHashMap<String, Integer>();

        /**
         * gridpane used in UI
         */
    	GridPane gridPane2 = new GridPane();

    	/**
         * scene used in UI
         */
        Scene secondScene = new Scene(gridPane2, 350, 350);

        /**
         * new window to show results
         */
        Stage newWindow = new Stage();

        newWindow.setTitle("Top 20 Most Frequent Words");
        newWindow.setScene(secondScene);

        newWindow.show();
        gridPane2.setMinSize(300, 300);
        gridPane2.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        gridPane2.setVgap(10);
        gridPane2.setHgap(75);
        gridPane2.setAlignment(Pos.TOP_CENTER);

        gridPane2.setStyle("-fx-background-color: beige;");

        /**
         * counting variables
         */
        int i = 0;
        int j = 0;
        int k = 2;

        for(Map.Entry<String, Integer> e: arrayList) {
            sortedFreq.put(e.getKey(), e.getValue());
            Label word = new Label(e.getKey() + ", " + e.getValue() +"\n");
            gridPane2.add(word, j, k);
            word.setStyle("-fx-font: normal bold 15px 'serif' ");

            if (i == 9) {
                j = 1;
                k = 2;
                i++;
            }
            else if (i == 19) {
                break;
                    }
            else {
                k++;
                i++;
            }
        }
    }
}







