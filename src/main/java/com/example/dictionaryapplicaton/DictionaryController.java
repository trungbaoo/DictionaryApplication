package com.example.dictionaryapplicaton;


import com.example.dictionaryapplicaton.Entity.DictionaryManagement;
import com.example.dictionaryapplicaton.Entity.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    private static  DictionaryManagement dictionaryManagement =new DictionaryManagement();
    @FXML
    private TextField searchInput;

    @FXML
    private ListView<String> listView =new ListView<>();
    @FXML
    private Label label=new Label();
    @FXML
    private TextArea textArea=new TextArea();
    public void getSearchInput(){
        System.out.println(searchInput.getText());
        ArrayList<String> list=dictionaryManagement.dictionarySearcher(searchInput.getText());
        listView.getItems().clear();
        ObservableList<String> data= FXCollections.observableArrayList(list);
        listView.getItems().addAll(data);
        listView.refresh();
    }
    public void lookup(){
        Word word=dictionaryManagement.dictionaryLookup(searchInput.getText());
        if(word!=null){
            label.setText(word.getWordTarget());
            textArea.setText(word.getWordExplain());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile();
    }
//    public void lookupListView(){
//        String selectionWord=listView.get
//    }
}