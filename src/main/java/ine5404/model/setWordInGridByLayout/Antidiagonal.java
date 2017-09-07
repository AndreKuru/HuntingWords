package ine5404.model.setWordInGridByLayout;

public class Antidiagonal implements SetWordInGridByLayout{
  @Override
  public boolean setWordInGrid(int line,
                               int column,
                               String word,
                               char[][] grid){
    if (validateCoordinate(line, column, word, grid)){
      for(int i = 0; i < word.length(); i++)
        grid[line+i][column-i] = word.charAt(i);
      return true;
    }
    return false;
  }

  @Override
  public boolean validateCoordinate(int line,
                                    int column,
                                    String word,
                                    char[][] grid){
    if ((line   > grid.length   - word.length()) ||
        (column < word.length() - 1))
      return false;

    for (int i = 0; i < word.length(); i++)
      if ((grid[line+i][column-i] != 0) &&
          (grid[line+i][column-i] != word.charAt(i)))
        return false;

    return true;
  }
}
