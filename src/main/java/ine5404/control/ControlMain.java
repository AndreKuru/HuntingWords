package ine5404.control;

import ine5404.model.ModelMain;
import ine5404.exception.InvalidInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ControlMain {
  private Scanner   scan;
  private ModelMain model;
  private int       correctAnswersMissing;
  private boolean   gameOver;

  public                ControlMain       () throws FileNotFoundException{
    this.model      = new ModelMain(getWords());
  }

  public  void          newGame           (String dimensionS, String wordsAmountS, List<ModelMain.Layout> layouts, boolean reverse) throws InvalidInput{
    int dimension         = getIntFromString(dimensionS);
    int wordsAmount       = getIntFromString(wordsAmountS);
    model.newGame           (dimension, wordsAmount, layouts, reverse);
    gameOver              = false;
    correctAnswersMissing = model.getCorrectAnswersAmount();
  }

  private List<String>  getWords          () throws FileNotFoundException{
    scan                      = new Scanner     (new File("list-of-words.txt"));
    List<String> listOfWords  = new ArrayList<> ();

    while (scan.hasNextLine()){
      listOfWords.add(scan.nextLine());
    }
    return listOfWords;
  }

  private int           getIntFromString  (String word) throws InvalidInput{
    if ((!isStringNull(word)) &&
        (isInt(word)))
      return Integer.parseInt(word);
    else
      throw new InvalidInput();
  }

  private boolean       isStringNull      (String word){
    if (word == null)
      return true;
    else
      return false;
  }

  private boolean       isInt             (String word){
    return (word.matches("[-+]?\\d+"));
  }

//  private ModelMain.Layout[] getLayouts[] ()
  public char[][]       getGrid           (){
    return model.getGrid();
  }

  public String[]       getOriginalWords  (){
    return model.getOriginalWords();
  }

  public void           checkAnswer       (int line, int column, boolean activated){
    if (model.getAnswerGrid()[line][column] == true)
      if (activated)
        correctAnswersMissing--;
      else
        correctAnswersMissing++;
    else
      if (activated)
        correctAnswersMissing++;
      else
        correctAnswersMissing--;
  }

  public boolean        isGameOver        (){
    if (correctAnswersMissing == 0)
      return true;
    else
      return false;
  }
}
