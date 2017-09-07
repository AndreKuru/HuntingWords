package ine5404.model;

import ine5404.exception.InvalidInput;
import ine5404.model.setWordInGridByLayout.*;

import java.util.*;

public class ModelMain {
  public enum Layout {
    HORIZONTAL,
    VERTICAL,
    MAIN_DIAGONAL,
    ANTIDIAGONAL
  }

  private char[][]            grid;
  private boolean[][]         answerGrid;
  private List<GeneratedWord> selectedWords;
  private String[]            allWords;
  private Random              random;
  private List<Layout>        layouts;
  private boolean             reverse;

  public              ModelMain                 (List<String> listOfWords){
    setAllWords (listOfWords);
    random      = new Random();
    contByWordSize();
  }

  public  void        newGame                   (int dimension, int wordsAmount, List<Layout> layouts, boolean reverse) throws InvalidInput{
    selectedWords   = new ArrayList<>();
    this.layouts    = layouts;
    this.reverse    = reverse;
    setGrid         (dimension);
    answerGrid      = new boolean[grid.length][grid.length];
    getRandomWords  (wordsAmount);
    setSortByLength (selectedWords);
    setWordsInGrid  ();
  }

  private void        setAllWords               (List<String> listOfWords){
    allWords = listOfWords.toArray(new String[0]);
  }

  private void        setGrid                   (int dimension) throws InvalidInput{
    if (dimension >= minimumDimension())
      grid = new char[dimension][dimension];
    else
      throw new InvalidInput();
  }

  private void        getRandomWords            (int wordsAmount){
    selectedWords.clear();
    for(int c = 0; c < wordsAmount; c++){
      String word = allWords[random.nextInt(allWords.length)];
      selectedWords.add(new GeneratedWord(word));
    }
  }

  private void        setSortByLength           (List<GeneratedWord> selectedWords){
    Collections.sort(selectedWords, new ComparatorOfGeneratedWords());
    //                     (s1, s2) -> s2.length() - s1.length());
  }

  private void        setWordsInGrid            () throws InvalidInput{
    setSelectedWordsInGrid();
    for (int i = 0; i < grid.length; i++)
      for (int j = 0; j < grid.length; j++)
        if(grid[i][j] != 0)
          answerGrid[i][j] = true;
        else
          grid[i][j] = getRandomLetter();
  }

  private void        setSelectedWordsInGrid    () throws InvalidInput{
    boolean set;
    int cont = 0;

    for (GeneratedWord gWord : selectedWords) {
      reverseOfGeneratedWord(gWord);
      cont = 0;
      do{
        SetWordInGridByLayout setByLayout;
        setByLayout = setWordInGridByLayout(getRandomLayout());
        set = setByLayout.setWordInGrid(random.nextInt(grid.length),
                          random.nextInt(grid.length),
                          gWord.getWord(),
                          grid);
        cont++;
        if (cont > 50)
          throw new InvalidInput();
      }while (!set);
    }

  }

  public char         getRandomLetter           (){
    List<Character> letters = new ArrayList<>();
    for (GeneratedWord gWord : selectedWords)
      for(Character letter : gWord.getWord().toCharArray())
        if (!letters.contains(letter))
          letters.add(letter);

    return letters.get(random.nextInt(letters.size()));
  }

  private Layout      getRandomLayout           (){
    return layouts.get(random.nextInt(layouts.size()));
  }

  private void        reverseOfGeneratedWord    (GeneratedWord generatedWord){
    if (reverse && random.nextBoolean())
      generatedWord.reverseWord();
  }

  public  char[][]    getGrid                   (){
    return grid;
  }

  public boolean[][]  getAnswerGrid             (){
    return answerGrid;
  }

  public int          getCorrectAnswersAmount   (){
    int count = 0;

    for (int i = 0; i < answerGrid.length; i++)
      for (int j = 0; j < answerGrid.length; j++)
        if (answerGrid[i][j])
          count++;

    return count;
  }

  public String[]     getOriginalWords          (){
    List<String> originalWords = new ArrayList<>();

    for (GeneratedWord gWord : selectedWords)
      originalWords.add(gWord.getOriginalWord());

    return originalWords.toArray(new String[originalWords.size()]);
  }

  //validateCoordinates begin
  private boolean validateCoordinateHorizontal   (int line, int column, String word){
    if (column > grid.length - word.length())
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line][column+i] != 0) &&
          (grid[line][column+i] != word.charAt(i)))
        return false;

    return true;
  }

  private boolean validateCoordinateVertical     (int line, int column, String word){
    if (line > grid.length - word.length())
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line+i][column] != 0) &&
          (grid[line+i][column] != word.charAt(i)))
        return false;

    return true;
  }

  private boolean validateCoordinateMainDiagonal (int line, int column, String word){
    if ((line   > grid.length - word.length()) ||
        (column > grid.length - word.length()))
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line+i][column+i] != 0) &&
          (grid[line+i][column+i] != word.charAt(i)))
        return false;

    return true;
  }

  private boolean validateCoordinateAntidiagonal (int line, int column, String word){
    if ((line   > grid.length   - word.length()) ||
        (column < word.length() - 1))
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line+i][column-i] != 0) &&
          (grid[line+i][column-i] != word.charAt(i)))
        return false;

    return true;
  }
  //validateCoordinates end

  //setWordInGrid begin
  private SetWordInGridByLayout setWordInGridByLayout           (Layout layout){
    switch(layout){
    case HORIZONTAL:
      return new Horizontal();
    case VERTICAL:
      return new Vertical();
    case MAIN_DIAGONAL:
      return new MainDiagonal();
    case ANTIDIAGONAL:
      return new Antidiagonal();
    }
    return null;
  }

  private boolean setWordToHorizontal             (int line, int column, String word){
    if (validateCoordinateHorizontal(line, column, word)){
      for (int i = 0; i < word.length(); i++)
        grid[line][column + i] = word.charAt(i);
      return true;
    }
    return false;
  }

  private boolean setWordToVertical               (int line, int column, String word){
    if (validateCoordinateVertical(line, column, word)){
      for(int i = 0; i < word.length(); i++)
        grid[line+i][column] = word.charAt(i);
      return true;
    }
    return false;
  }

  private boolean setWordToMainDiagonal           (int line, int column, String word){
    if (validateCoordinateMainDiagonal(line, column, word)){
      for(int i = 0; i < word.length(); i++)
        grid[line+i][column+i] = word.charAt(i);
      return true;
    }
    return false;
  }

  private boolean setWordToAntidiagonal           (int line, int column, String word){
    if (validateCoordinateAntidiagonal(line, column, word)){
      for(int i = 0; i < word.length(); i++)
        grid[line+i][column-i] = word.charAt(i);
      return true;
    }
    return false;
  }
  //setLayoutOfWordInGrid End

  public  void    printSelectedWords(){
    for (GeneratedWord generatedWord : selectedWords)
      System.out.println(generatedWord.getOriginalWord());
  }

  private boolean isGameOver        (boolean[][] activatedGrid){
    if (activatedGrid == answerGrid)
      return true;
    else
      return false;
  }

  private int     minimumDimension  (){
    int bigger = 0;
    for (int i = 0; i < allWords.length; i++)
      if (allWords[i].length() > bigger)
        bigger = allWords[i].length();
    return bigger;
  }

  private void     contByWordSize  (){
    int cont = 0;
    for (int i = 0; i < allWords.length; i++)
      if (allWords[i].length() == 2)
        cont = allWords[i].length();
    System.out.println(cont);
  }

  public void     printGrid         (){
    for(int i = 0; i < grid.length; i++){
      for(int j = 0; j < grid[i].length; j++){
        if (grid[i][j] != 0){
          System.out.print(grid[i][j]);
        } else
          System.out.print(".");
      }
      System.out.print("\n");
    }
  }

}
