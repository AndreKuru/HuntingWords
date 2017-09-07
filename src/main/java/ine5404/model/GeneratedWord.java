package ine5404.model;

public class GeneratedWord{
  private String word;
  private boolean reversed;

  public GeneratedWord (String word){
    this.word = word;
    reversed  = false;
  }

  public String getWord(){
    return word;
  }

  public String getOriginalWord(){
    if (!reversed)
      return word;
    if (reversed);
      return new StringBuilder(word).reverse().toString();
  }

  public void reverseWord(){
    word = new StringBuilder(word).reverse().toString();
    reversed = !reversed;
  }
}
