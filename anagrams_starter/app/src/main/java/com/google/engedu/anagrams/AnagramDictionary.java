package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static  int wordLength = DEFAULT_WORD_LENGTH;
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet= new HashSet<String>();
    private HashMap<String,ArrayList<String>> lettersToWords= new HashMap<String, ArrayList<String>>();
    private HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        //ArrayList<String> wordList = new ArrayList<String>();
        String line;
        String nword;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            nword=AlphabeticalOrder(word);
            int l=word.length();
            ArrayList<String> temp_word = new ArrayList<String>();
            ArrayList<String> temp_word2 = new ArrayList<String>();
            boolean stwKeyExists= sizeToWords.containsKey(l);
            boolean keyExists = lettersToWords.containsKey(nword);
            if(keyExists){
                temp_word=lettersToWords.get(nword);
                temp_word.add(word);
                lettersToWords.put(nword,temp_word);
            }
            else{
                temp_word.add(word);
                lettersToWords.put(nword,temp_word);
            }

            if(stwKeyExists){
                temp_word2=sizeToWords.get(l);
                temp_word2.add(word);
                sizeToWords.put(l,temp_word2);
            }
            else{
                temp_word2.add(word);
                sizeToWords.put(l,temp_word2);
            }

        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            if(word.contains(base)){
                return false;
            }
            else
                return true;
        }
        else
            return false;
    }

   /* public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }*/
    public String AlphabeticalOrder(String word){
        char[] charArray=word.toCharArray();
        Arrays.sort(charArray);
        String newWord =new String(charArray);
        return newWord;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        AlphabeticalOrder(word);
        for(char alphabet = 'a'; alphabet <= 'z';alphabet++) {
            String s=word;
            s = s+alphabet;
            AlphabeticalOrder(s);
            if(lettersToWords.containsKey(s)){
                result.addAll(lettersToWords.get(s));
            }
        }
        // here we can either comment both of the log file and uncomment *result.remove(i) or keep it the same
        for (int i=0;i<result.size();i++){
            Log.d("AD list",result.get(i));
            if(!isGoodWord(result.get(i),word)){
                Log.d("AD removed",result.remove(i));
                //result.remove(i);
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        String word=new String();
        int j;
        ArrayList<String> lengthWords = new ArrayList<String>();
        if(wordLength<=MAX_WORD_LENGTH){
            lengthWords=sizeToWords.get(wordLength);
        }

        int i =random.nextInt(lengthWords.size());

        for(j=i;j<lengthWords.size();j++){
            if(getAnagramsWithOneMoreLetter(lengthWords.get(j)).size()>= MIN_NUM_ANAGRAMS){

                word=lengthWords.get(j);
                break;
            }
        }

        if(j == lengthWords.size()-1 && word==null){
            for (j=0;j<i;j++){
                if(getAnagramsWithOneMoreLetter(lengthWords.get(j)).size()>= MIN_NUM_ANAGRAMS) {
                    word = lengthWords.get(j);
                    break;
                }
            }
        }

        if(wordLength < MAX_WORD_LENGTH){
            wordLength++;
        }
        return word;
    }
}
