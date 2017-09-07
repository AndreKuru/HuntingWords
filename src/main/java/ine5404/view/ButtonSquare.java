package ine5404.view;

import ine5404.control.ControlMain;

import javax.swing.*;
import java.awt.*;

public class ButtonSquare extends JButton{
  private final ControlMain control;
  private final int         line;
  private final int         column;
  private       boolean     activated;

  public ButtonSquare(String var1, ControlMain control, int line, int column){
    super(var1);
    this.control      = control;
    this.line         = line;
    this.column       = column;
    activated         = false;
    setBackground     (Color.LIGHT_GRAY);

    setSize(10, 10);
  }

  public void action(){
    activated = !activated;
    if (activated)
      setBackground(Color.CYAN);
    else
      setBackground(Color.LIGHT_GRAY);
    control.checkAnswer(line, column, activated);
  }

}
