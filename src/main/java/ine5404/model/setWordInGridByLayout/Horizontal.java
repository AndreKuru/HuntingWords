package ine5404.model.setWordInGridByLayout;

public class Horizontal implements SetWordInGridByLayout{

  @Override
  public boolean setWordInGrid      (int line,
                                     int column,
                                     String word,
                                     char[][] grid){
    if (validateCoordinate(line, column, word, grid)){
      for (int i = 0; i < word.length(); i++)
        grid[line][column + i] = word.charAt(i);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateCoordinate          (int line,
                                              int column,
                                              String word,
                                              char[][] grid){
    if (column > grid.length - word.length())
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line][column+i] != 0) &&
          (grid[line][column+i] != word.charAt(i)))
        return false;

    return true;
  }
}
