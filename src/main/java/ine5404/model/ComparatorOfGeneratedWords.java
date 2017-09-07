package ine5404.model;

import java.util.Comparator;

public class ComparatorOfGeneratedWords implements Comparator<GeneratedWord>{

  @Override
  public int compare(GeneratedWord generatedWord1, GeneratedWord generatedWord2){
      return generatedWord2.getWord().length() - generatedWord1.getWord().length();
  }
}
