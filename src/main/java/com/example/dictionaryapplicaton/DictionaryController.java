package com.example.dictionaryapplicaton;


import com.example.dictionaryapplicaton.Entity.DictionaryManagement;
import com.example.dictionaryapplicaton.Entity.Word;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DictionaryController implements Initializable {
    public ArrayList<String> wordHistory=new ArrayList<>();
    public ArrayList<String> wordUpdated=new ArrayList<>();
    private static DictionaryManagement dictionaryManagement = new DictionaryManagement();
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private Button removeButton;
    @FXML
    private TextField searchInput;
    @FXML
    private TextField addInput;
    @FXML
    private TextField removeInput;
    @FXML
    private TextField editInput;
    @FXML
    private ListView<String> listViewSearch = new ListView<>();
    @FXML
    private ListView<String> listViewEdit = new ListView<>();
    @FXML
    private Label label = new Label();
    @FXML
    private TextArea definitionSearch = new TextArea();
    @FXML
    private TextArea addDefinition = new TextArea();
    @FXML
    private TextArea removeDefinition = new TextArea();
    @FXML
    private TextArea editDefinition = new TextArea();
    @FXML
    private ListView<String> listViewHistory = new ListView<>();
    @FXML
    private ListView<String> listViewAdd = new ListView<>();
    @FXML
    private ListView<String> listViewRemove = new ListView<>();
    @FXML
    private Label addStatus;
    @FXML
    private Label removeStatus;
    @FXML
    private Label editStatus;


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

    public void lookup() {
        Word word = dictionaryManagement.dictionaryLookup(searchInput.getText());
        if (word != null) {
            definitionSearch.setText(word.getWordExplain());
            for (String word1 : listViewHistory.getItems())
                if (word1.startsWith(word.getWordTarget())) {
                    listViewHistory.getItems().remove(word1);
                    break;
                }
            listViewHistory.getItems().add(0, word.getWordTarget() + "\n" + word.getWordExplain());
            if (listViewHistory.getItems().size() > 100) listViewHistory.getItems().remove(100);
        }
    }


    public void lookup1() {
        Word word = dictionaryManagement.dictionaryLookup(removeInput.getText());
        if (word != null) {
            removeDefinition.setText(word.getWordExplain());
        }
    }

    public void lookup2() {
        Word word = dictionaryManagement.dictionaryLookup(editInput.getText());
        if (word != null) {
            editDefinition.setText(word.getWordExplain());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dictionaryManagement.insertFromFile();
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
                    String statusMsg = "";
                    addStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isBlank()) statusMsg += "Word Target is empty.\n";
                    if (wordExplain == null || wordExplain.isBlank()) statusMsg += "Word Explain is empty.\n";
                    if (dictionaryManagement.isExisted(wordTarget)) statusMsg += "Word is existed\n";
                    if (statusMsg.isBlank()) {
                        dictionaryManagement.addWord(new Word(wordTarget, wordExplain));
                        statusMsg += "Adding successfully!";
                        addStatus.setTextFill(Color.color(0, 1, 0));
                    }
                    addStatus.setText(statusMsg);
                }
            }
        });
        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    String wordTarget = editInput.getText();
                    String wordExplain = editDefinition.getText();
                    String statusMsg = "";
                    editStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isBlank()) statusMsg += "Word Target is empty.\n";
                    if (!dictionaryManagement.isExisted(wordTarget) && wordTarget != null)
                        statusMsg += "Word is not existed\n";
                    if (wordExplain == null || wordExplain.isBlank()) statusMsg += "Word Explain is empty.\n";
                    if (statusMsg.isBlank()) {
                        dictionaryManagement.removeWord(wordTarget);
                        dictionaryManagement.addWord(new Word(wordTarget, wordExplain));
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
                    String statusMsg = "";
                    removeStatus.setTextFill(Color.color(1, 0, 0));
                    if (wordTarget == null || wordTarget.isBlank()) statusMsg += "Word Target is empty.\n";
                    if (!dictionaryManagement.isExisted(wordTarget)) statusMsg += "Word is not existed\n";
                    if (statusMsg.isBlank()) {
                        dictionaryManagement.removeWord(wordTarget);
                        statusMsg += "Removing successfully!";
                        removeStatus.setTextFill(Color.color(0, 1, 0));
                    }
                    removeStatus.setText(statusMsg);
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