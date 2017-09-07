package ine5404.model.setWordInGridByLayout;

public interface SetWordInGridByLayout{

  boolean setWordInGrid             (int line, int column, String word, char[][] grid);

  boolean validateCoordinate        (int line, int column, String word, char[][] grid);

}
