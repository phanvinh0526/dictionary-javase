/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author vinh
 */
public class WordObject {
    private String word;
    private String meaning;
    private Integer times;

    public WordObject(String word, String meaning, Integer times) {
        this.word = word;
        this.meaning = meaning;
        this.times = times;
    }

    public WordObject() {
        
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * @return the meaning
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * @param meaning the meaning to set
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * @return the times
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(Integer times) {
        this.times = times;
    }
    
}
