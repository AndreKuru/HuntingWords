package ine5404.model.setWordInGridByLayout;

public class Vertical implements SetWordInGridByLayout{

  @Override
  public boolean setWordInGrid(int line,
                               int column,
                               String word,
                               char[][] grid){
    if (validateCoordinate(line, column, word, grid)){
      for(int i = 0; i < word.length(); i++)
        grid[line+i][column] = word.charAt(i);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateCoordinate(int line,
                                    int column,
                                    String word,
                                    char[][] grid){
    if (line > grid.length - word.length())
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line+i][column] != 0) &&
          (grid[line+i][column] != word.charAt(i)))
        return false;

    return true;
  }
}
