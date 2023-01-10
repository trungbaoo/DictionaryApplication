package com.example.dictionaryapplicaton.Entity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryManagement {
    public static Dictionary dictionary = new Dictionary();
    public static Scanner scanner = new Scanner(System.in);

    //    public void insertFromFile() {
//        File file = new File("src/main/resources/com/example/dictionaryapplicaton/dictionaries.txt");
//        try {
//            Scanner sc = new Scanner(file);
//            while (sc.hasNext()) {
//                String line = sc.nextLine();
//                String[] s = line.split("\t");
//                dictionary.words.add(new Word(s[0], s[1]));
//                System.out.println(s[0] + " " + s[1]);
//            }
//            System.out.println("Them tu file thanh cong");
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void insertFromFileDict() {
        try {
            String content = readFile("src/main/resources/com/example/dictionaryapplicaton/defaultDictionary.dict", Charset.defaultCharset());
            String[] words = content.split("@");
            for (String word : words) {
                String result[] = word.split("\r?\n", 2);
                if (result.length > 1) {
                    String wordExplain1 = new String();
                    String wordTarget1 = new String();
                    String wordSound1 = new String();
                    if (result[0].contains("/")) {
                        String firstmeaning = result[0].substring(0, result[0].indexOf("/"));
                        String lastSoundMeaning = result[0].substring(result[0].indexOf("/"), result[0].length());
                        wordTarget1 = firstmeaning;
                        wordSound1 = lastSoundMeaning;
                    } else {
                        wordTarget1 = result[0];
                        wordSound1 = "";
                    }
                    wordExplain1 = result[1];
                    dictionary.words.add(new Word(wordTarget1.trim(), wordExplain1.trim(), wordSound1.trim()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Word dictionaryLookup(String wordTarget) {
        for (Word word : dictionary.words) {
            if (word.getWordTarget().equals(wordTarget)) return word;
        }
        return null;
    }

    public ArrayList<String> dictionarySearcher(String prefix) {
        ArrayList list = new ArrayList();
        for (Word word : dictionary.words) {
            if (word.getWordTarget().startsWith(prefix)) {
                list.add(word.getWordTarget());
            }
        }
        return list;
    }

    public void addWord(Word word) {
        dictionary.words.add(word);
    }

    public void removeWord(String wordTarget) {
        for (Word word : dictionary.words) {
            if (word.getWordTarget().equalsIgnoreCase(wordTarget)) {
                dictionary.words.remove(word);
                break;
            }
        }
    }

    public boolean isExisted(String wordTarget) {
        for (Word word : dictionary.words)
            if (word.getWordTarget().equalsIgnoreCase(wordTarget))
                return true;
        return false;
    }

    public void loadHistoryWords() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/com/example/dictionaryapplicaton/history.txt"));
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                Word word = dictionaryLookup(s);
                if (word != null) {
                    dictionary.historyWords.add(word.getWordTarget());
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load history");
        }
    }

    public void exportHistoryWord() {
        try {
            FileWriter fileWriter = new FileWriter(new File("src/main/resources/com/example/dictionaryapplicaton/history.txt"));
            for (String s : getHistoryWords()) {
                fileWriter.write(s + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot export history");
        }
    }
    public void loadFavouriteWords() {
        try {
            Scanner scanner = new Scanner(new File("src/main/resources/com/example/dictionaryapplicaton/favourite.txt"));
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();
                Word word = dictionaryLookup(s);
                if (word != null) {
                    dictionary.favouriteWords.add(word.getWordTarget());
                }
            }
        } catch (Exception e) {
            System.out.println("Cannot load favourite");
        }
    }

    public void exportFavouriteWord() {
        try {
            FileWriter fileWriter = new FileWriter(new File("src/main/resources/com/example/dictionaryapplicaton/favourite.txt"));
            for (String s : getFavouriteWords()) {
                fileWriter.write(s + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Cannot export favourite");
        }
    }
    public ArrayList<String> getHistoryWords() {
        return dictionary.historyWords;
    }
    public ArrayList<String> getFavouriteWords() {
        return dictionary.favouriteWords;
    }
    public boolean isFavouriteWords(String word) {
        return dictionary.favouriteWords.contains(word);
    }
    public void setHistoryWords(ArrayList<String> arrayList) {
        dictionary.historyWords = arrayList;
    }
    public void setFavouriteWords(ArrayList<String> arrayList) {
        dictionary.favouriteWords = arrayList;
    }

    public void loadDataFromFile() {
        insertFromFileDict();
        loadHistoryWords();
        loadFavouriteWords();
    }
}