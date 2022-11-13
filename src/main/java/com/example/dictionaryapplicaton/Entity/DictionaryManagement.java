package com.example.dictionaryapplicaton.Entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    public static Dictionary dictionary = new Dictionary();
    public static Scanner scanner = new Scanner(System.in);

    public  void insertFromFile() {
        File file = new File("src/main/java/com/example/dictionaryapplicaton/Entity/dictionaries.txt");
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] s = line.split("\t");
                dictionary.words.add(new Word(s[0], s[1]));
                System.out.println(s[0]+" "+s[1]);
            }
            System.out.println("Them tu file thanh cong");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Word dictionaryLookup(String wordTarget) {
        for (Word word : dictionary.words) {
            if (word.getWordTarget().equals(wordTarget)) return word;
        }
        return null;
    }
    public ArrayList<String> dictionarySearcher(String prefix){
        ArrayList list=new ArrayList();
        for (Word word: dictionary.words){
            if(word.getWordTarget().startsWith(prefix)){
                list.add(word.getWordTarget());
                System.out.println(word.getWordTarget());
            }
        }return list;
    }
}
