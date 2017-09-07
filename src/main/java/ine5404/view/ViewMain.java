package ine5404.view;

import ine5404.control.ControlMain;
import ine5404.exception.InvalidInput;
import ine5404.model.ModelMain;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

public class ViewMain{
  JFrame      frame;
  JPanel      panelGrid;
  JPanel      panelWords;
  ControlMain control;

  public          ViewMain     (){
    try{
      control = new ControlMain();
    } catch (FileNotFoundException e){
      JOptionPane.showMessageDialog(null, "Arquivo de palavras não encontrado.",
                                    "Erro!", JOptionPane.ERROR_MESSAGE);
      System.exit(1);
    }
  }

  public  void    newGame      (){
    boolean set = false;
    while (!set){
      set = setNewGame();
    }

    setFrame(control.getGrid(),
             control.getOriginalWords());

  }

  public  void    setFrame     (char[][] grid,
                                String[] originalWords){
    frame = new JFrame("Caça-Palavras");
    frame.setLayout(new BorderLayout());

    setPanelGrid(grid);

    setPanelWords(grid, originalWords);


    frame.add(panelGrid, BorderLayout.NORTH);
    frame.add(panelWords, BorderLayout.SOUTH);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

  }

  private void    setPanelGrid (char[][] grid){
    panelGrid = new JPanel(new GridLayout(grid.length, grid.length));

    for (int i = 0; i < grid.length; i++)
      for (int j = 0; j < grid.length; j++){
        ButtonSquare button = new ButtonSquare(String.valueOf(grid[i][j]), control, i, j);
        panelGrid.add(button);
        button.addActionListener(actionEvent -> {
          button.action();
          if (control.isGameOver()){
            JOptionPane.showMessageDialog(null, "Parabéns, jogo concluído",
                                          "Caça-palavras", JOptionPane.INFORMATION_MESSAGE);
            int input = JOptionPane.showConfirmDialog(null, "Você deseja começar outro jogo?",
                                                      "Caça-palavras", JOptionPane.YES_NO_OPTION);
            if(input == JOptionPane.YES_OPTION){
              frame.dispose();
              newGame();
            }
            else
              System.exit(0);
          }
        });
      }
  }

  private void    setPanelWords(char[][] grid,
                                String[] originalWords){
    panelWords = new JPanel(new GridLayout(originalWords.length/(grid.length/5),grid.length/5));

    for (int i = 0; i < originalWords.length; i++){
      Label label = new Label(originalWords[i]);
      panelWords.add(label);
    }
  }

  private boolean setNewGame   (){
    String dimension    = JOptionPane.showInputDialog(null, "Insira a dimensão: ",
                                                      "Caça-Palavras", JOptionPane.PLAIN_MESSAGE);
    String wordsAmount  = JOptionPane.showInputDialog(null, "Insira a quantidade palavras: ",
                                                      "Caça-palavras", JOptionPane.PLAIN_MESSAGE);
    JCheckBox horizontal    = new JCheckBox("Horizontal");
    JCheckBox vertical      = new JCheckBox("Vertical");
    JCheckBox mainDiagonal  = new JCheckBox("Diagonal principal");
    JCheckBox antidiagonal  = new JCheckBox("Diagonal secundaria");
    JCheckBox reverse_box   = new JCheckBox("Reverse word");

    JPanel panel = new JPanel();
    panel.add(horizontal);
    panel.add(vertical);
    panel.add(mainDiagonal);
    panel.add(antidiagonal);
    panel.add(reverse_box);

    java.util.List<ModelMain.Layout> layouts = new ArrayList<>();
    boolean reverse = false;

    JOptionPane.showMessageDialog(null, panel, "Caça-palavras", JOptionPane.OK_OPTION);
    if (horizontal.isSelected())
      layouts.add(ModelMain.Layout.HORIZONTAL);
    if (vertical.isSelected())
      layouts.add(ModelMain.Layout.VERTICAL);
    if (mainDiagonal.isSelected())
      layouts.add(ModelMain.Layout.MAIN_DIAGONAL);
    if (antidiagonal.isSelected())
      layouts.add(ModelMain.Layout.ANTIDIAGONAL);
    if (reverse_box.isSelected())
      reverse = true;



    try{
      if (layouts.isEmpty())
        throw new InvalidInput();
      control.newGame(dimension, wordsAmount, layouts, reverse);
      return true;
    } catch (InvalidInput invalidInput){
      JOptionPane.showMessageDialog(null, "Valor inserido inválido.",
                                    "Erro!", JOptionPane.ERROR_MESSAGE);
      return false;

    }
  }

}
