package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 4;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    private HashMap<String, ArrayList<String>> lettersToWord;
    private HashMap<Integer,ArrayList> sizeToWords;
    private int wordLength=DEFAULT_WORD_LENGTH;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet = new HashSet();
        wordList = new ArrayList();
        sizeToWords=new HashMap<>();
        lettersToWord = new HashMap<String, ArrayList<String>>();
        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
            ArrayList<String> t=sizeToWords.get(word.length());
            if(t==null)
            {
                t=new ArrayList<>();
                t.add(word);
                sizeToWords.put(word.length(),t);
            }else {
                t.add(word);
            }
            if (lettersToWord.get(sortString(word)) == null) {
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(word);
                lettersToWord.put(sortString(word), temp);
            } else {
                ArrayList temp = (ArrayList) lettersToWord.get(sortString(word));
                temp.add(word);

            }
        }


    }

    public String sortString(String original) {
        char[] chars = original.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        return sorted;
    }

    public boolean isGoodWord(String word, String base) {

        if (!wordSet.contains(word))
            return false;
        if (word.contains(base))
            return false;

        return true;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 97; i < 123; i++) {
            String tempWord = word + "" + (char) i;
            String tempKey = sortString(tempWord);
            ArrayList<String> list= (ArrayList<String>) lettersToWord.get(tempKey);
            if(list!=null) {
                for (int j = 0; j < list.size(); j++) {
                    String a=list.get(0);
                    if (!a.contains(word))
                        result.add(a);
                }
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> wordList=sizeToWords.get(wordLength);
        Collections.shuffle(wordList);
        for (int i = 0; i < wordList.size(); i++) {
            ArrayList<String> temp= lettersToWord.get(sortString(wordList.get(i)));
            if (temp.size()>=MIN_NUM_ANAGRAMS) {
                return wordList.get(i);
            }
        }
        return "foo";
    }
}
