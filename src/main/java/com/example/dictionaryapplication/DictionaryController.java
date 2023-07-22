package com.example.dictionaryapplication;


import com.example.dictionaryapplication.Entity.DictionaryManagement;
import com.example.dictionaryapplication.Entity.Word;
import com.example.dictionaryapplication.model.Sentences;
import com.example.dictionaryapplication.model.TranslatedOutput;
import com.example.dictionaryapplication.model.UserInput;
import com.example.dictionaryapplication.service.TranslationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Component
@FxmlView("/app.fxml")
public class DictionaryController implements Initializable {
    public static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    @Autowired
    public TranslationService translationService;
    @FXML
    private Button addButton, editButton, removeButton, buttonTranslate;
    @FXML
    private ToggleButton buttonFavorite, buttonLanguage;
    @FXML
    private TextField searchInput, addInput, removeInput, editInput;
    @FXML
    public ListView<String> listViewSearch = new ListView<>(), listViewEdit = new ListView<>(), listViewHistory = new ListView<>(), listViewAdd = new ListView<>(), listViewRemove = new ListView<>(), listViewFavourite = new ListView<>();
    @FXML
    private TextArea definitionSearch = new TextArea(), addDefinition = new TextArea(), removeDefinition = new TextArea(), editDefinition = new TextArea(), inputSentence = new TextArea(), outputSentence = new TextArea();
    @FXML
    private Label addStatus, removeStatus, editStatus, wordLabel;
    @FXML
    private TextField addPhonetic, removePhonetic, editPhonetic, searchPhonetic;


    @FXML
    public void getSearchInput() {
        ArrayList<String> list = dictionaryManagement.dictionarySearcher(searchInput.getText());
        listViewSearch.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewSearch.getItems().addAll(data);
        listViewSearch.refresh();
    }

    @FXML
    public void getRemoveInput() {
        removeStatus.setText("");
        ArrayList<String> list = dictionaryManagement.dictionarySearcher(removeInput.getText());
        listViewRemove.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewRemove.getItems().addAll(data);
        listViewRemove.refresh();
    }

    @FXML
    public void getAddInput() {
        addStatus.setText("");
        ArrayList<String> list = dictionaryManagement.dictionarySearcher(addInput.getText());
        listViewAdd.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewAdd.getItems().addAll(data);
        listViewAdd.refresh();
    }

    @FXML
    public void getEditInput() {
        editStatus.setText("");
        ArrayList<String> list = dictionaryManagement.dictionarySearcher(editInput.getText());
        listViewEdit.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList(list);
        listViewEdit.getItems().addAll(data);
        listViewEdit.refresh();
    }

    public void updateHistoryWords(String word) {
        ArrayList<String> arrayList = dictionaryManagement.getHistoryWords();
        for (String word1 : arrayList)
            if (word1.equalsIgnoreCase(word)) {
                arrayList.remove(word1);
                break;
            }
        arrayList.add(0, word);
        if (arrayList.size() > 100) arrayList.remove(100);
        dictionaryManagement.setHistoryWords(arrayList);
        refreshListViewHistory();
    }

    public void updateFavouriteWords(String word) {
        ArrayList<String> arrayList = dictionaryManagement.getFavouriteWords();
        arrayList.add(0, word);
        dictionaryManagement.setFavouriteWords(arrayList);
        refreshListViewFavourite();
    }

    public void removeFavouriteWords(String word) {
        ArrayList<String> arrayList = dictionaryManagement.getFavouriteWords();
        for (String word1 : arrayList)
            if (word1.equalsIgnoreCase(word)) {
                arrayList.remove(word1);
                break;
            }
        dictionaryManagement.setFavouriteWords(arrayList);
        refreshListViewFavourite();
    }

    public void lookup() {
        Word word = dictionaryManagement.dictionaryLookup(searchInput.getText());
        if (word != null) {
            definitionSearch.setText(word.getWordExplain());
            searchPhonetic.setText(word.getPhonetic());
            updateHistoryWords(word.getWordTarget());
            wordLabel.setText(word.getWordTarget());
            buttonFavorite.setSelected(dictionaryManagement.isFavouriteWords(searchInput.getText()));
        }

    }

    public void lookup1() {
        Word word = dictionaryManagement.dictionaryLookup(removeInput.getText());
        if (word != null) {
            removeDefinition.setText(word.getWordExplain());
            removePhonetic.setText(word.getPhonetic());
        }
    }

    public void lookup2() {
        Word word = dictionaryManagement.dictionaryLookup(editInput.getText());
        if (word != null) {
            editDefinition.setText(word.getWordExplain());
            editPhonetic.setText(word.getPhonetic());
        }
    }

    public void refreshListViewHistory() {
        listViewHistory.getItems().clear();
        for (String s : dictionaryManagement.getHistoryWords()) {
            Word word = dictionaryManagement.dictionaryLookup(s);
            listViewHistory.getItems().add(word.getWordTarget() + "\n" + word.getPhonetic());
        }
    }

    public void refreshListViewFavourite() {
        listViewFavourite.getItems().clear();
        for (String s : dictionaryManagement.getFavouriteWords()) {
            Word word = dictionaryManagement.dictionaryLookup(s);
            listViewFavourite.getItems().add(word.getWordTarget() + "\n" + word.getPhonetic());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.loadDataFromFile();
        refreshListViewHistory();
        refreshListViewFavourite();
        listViewSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String target = listViewSearch.getSelectionModel().getSelectedItem();
                    searchInput.setText(target);
                    lookup();
                }
            }
        });
        listViewRemove.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String target = listViewRemove.getSelectionModel().getSelectedItem();
                    removeInput.setText(target);
                    lookup1();
                }
            }
        });
        listViewEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String target = listViewEdit.getSelectionModel().getSelectedItem();
                    editInput.setText(target);
                    lookup2();
                }
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String wordTarget = addInput.getText();
                    String wordExplain = addDefinition.getText();
                    String wordPhonetic = addPhonetic.getText();
                    String statusMsg = "";
                    addStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isEmpty()) statusMsg += "Word Target is empty.\n";
                    if (wordExplain == null || wordExplain.isEmpty()) statusMsg += "Word Explain is empty.\n";
                    if (wordPhonetic == null || wordPhonetic.isEmpty()) statusMsg += "Word Phonetic is empty.\n";
                    if (dictionaryManagement.isExisted(wordTarget)) statusMsg += "Word is existed\n";
                    if (statusMsg.isEmpty()) {
                        dictionaryManagement.addWord(new Word(wordTarget, wordExplain, wordPhonetic));
                        statusMsg += "Adding successfully!";
                        addStatus.setTextFill(Color.color(0, 1, 0));
                    }
                    addStatus.setText(statusMsg);
                }
            }
        });
        buttonLanguage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (buttonLanguage.isSelected()) {
                        buttonLanguage.setText("Anh - Việt");
                    } else {
                        buttonLanguage.setText("Việt - Anh");
                    }
                }
            }

        });
        buttonFavorite.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (wordLabel.getText().isEmpty()) {
                        buttonFavorite.setSelected(false);
                    } else if (buttonFavorite.isSelected()) {
                        updateFavouriteWords(wordLabel.getText());
                    } else {
                        removeFavouriteWords(wordLabel.getText());
                    }
                }
            }
        });
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String wordTarget = editInput.getText();
                    String wordExplain = editDefinition.getText();
                    String wordPhonetic = editPhonetic.getText();
                    String statusMsg = "";
                    editStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isEmpty()) statusMsg += "Word Target is empty.\n";
                    if (!dictionaryManagement.isExisted(wordTarget) && wordTarget != null)
                        statusMsg += "Word is not existed\n";
                    if (wordExplain == null || wordExplain.isEmpty()) statusMsg += "Word Explain is empty.\n";
                    if (wordPhonetic == null || wordPhonetic.isEmpty()) statusMsg += "Word Phonetic is empty.\n";
                    if (statusMsg.isEmpty()) {
                        dictionaryManagement.removeWord(wordTarget);
                        dictionaryManagement.addWord(new Word(wordTarget, wordExplain, wordPhonetic));
                        statusMsg += "Editing successfully!";
                        editStatus.setTextFill(Color.color(0, 1, 0));
                    }
                    editStatus.setText(statusMsg);
                }
            }
        });

        removeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String wordTarget = removeInput.getText();
                    String wordExplain = removeDefinition.getText();
                    String wordPhonetic = removePhonetic.getText();
                    String statusMsg = "";
                    removeStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isEmpty()) statusMsg += "Word Target is empty.\n";
                    if (!dictionaryManagement.isExisted(wordTarget)) statusMsg += "Word is not existed\n";
                    if (statusMsg.isEmpty()) {
                        dictionaryManagement.removeWord(wordTarget);
                        statusMsg += "Removing successfully!";
                        removeStatus.setTextFill(Color.color(0, 1, 0));
                    }
                    removeStatus.setText(statusMsg);
                }
            }
        });
        buttonTranslate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    TranslatedOutput output;
                    if (buttonLanguage.isSelected())
                        output = translationService.translateInput(new UserInput("en", "vi", inputSentence.getText()));
                    else
                        output = translationService.translateInput(new UserInput("vi", "en", inputSentence.getText()));
                    if (null != output && output.getSentences().size() > 0) {
                        String translate="";
                        for (int line = 0; line < output.getSentences().size(); line++) {
                            Sentences sentence = output.getSentences().get(line);
                            translate=translate+sentence.getTrans();
                        }
                        outputSentence.setText(translate);
                    }
                }
            }
        });
        searchInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookup();
                }
                if (ke.getCode() == KeyCode.DOWN) {
                    listViewSearch.requestFocus();
                    listViewSearch.getSelectionModel().selectFirst();
                }
            }
        });
        removeInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookup1();
                }
                if (ke.getCode() == KeyCode.DOWN) {
                    listViewRemove.requestFocus();
                    listViewRemove.getSelectionModel().selectFirst();
                }
            }
        });
        editInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    lookup2();
                }
                if (ke.getCode() == KeyCode.DOWN) {
                    listViewRemove.requestFocus();
                    listViewRemove.getSelectionModel().selectFirst();
                }
            }
        });
        listViewSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target = listViewSearch.getSelectionModel().getSelectedItem();
                    searchInput.setText(target);
                    lookup();
                }
                if ((ke.getCode() == KeyCode.UP) && (listViewSearch.getSelectionModel().getSelectedIndex() == 0))
                    searchInput.requestFocus();
            }
        });
        listViewRemove.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target = listViewRemove.getSelectionModel().getSelectedItem();
                    removeInput.setText(target);
                    lookup1();
                }
                if ((ke.getCode() == KeyCode.UP) && (listViewRemove.getSelectionModel().getSelectedIndex() == 0))
                    removeInput.requestFocus();
            }
        });
        listViewEdit.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target = listViewEdit.getSelectionModel().getSelectedItem();
                    editInput.setText(target);
                    lookup2();
                }
                if ((ke.getCode() == KeyCode.UP) && (listViewEdit.getSelectionModel().getSelectedIndex() == 0))
                    editInput.requestFocus();
            }
        });
        addInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.DOWN) {
                    listViewAdd.requestFocus();
                    listViewAdd.getSelectionModel().selectFirst();
                }
            }
        });
        editInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.DOWN) {
                    listViewEdit.requestFocus();
                    listViewEdit.getSelectionModel().selectFirst();
                }
            }
        });
        listViewAdd.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target = listViewAdd.getSelectionModel().getSelectedItem();
                    addInput.requestFocus();
                    addInput.setText(target);

                }
                if ((ke.getCode() == KeyCode.UP) && (listViewAdd.getSelectionModel().getSelectedIndex() == 0))
                    addInput.requestFocus();
            }
        });
        listViewEdit.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    String target = listViewEdit.getSelectionModel().getSelectedItem();
                    editInput.requestFocus();
                    editInput.setText(target);
                    lookup2();

                }
                if ((ke.getCode() == KeyCode.UP) && (listViewEdit.getSelectionModel().getSelectedIndex() == 0))
                    editInput.requestFocus();
            }
        });
        listViewAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String target = listViewAdd.getSelectionModel().getSelectedItem();
                    addInput.setText(target);
                }
            }
        });
        listViewEdit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String target = listViewEdit.getSelectionModel().getSelectedItem();
                    editInput.setText(target);
                    lookup2();
                }
            }
        });
    }

}