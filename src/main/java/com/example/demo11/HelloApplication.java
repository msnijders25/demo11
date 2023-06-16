package com.example.demo11;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

import java.io.*;
import java.util.*;

public class HelloApplication extends Application {
    private VBox chats;
    private VBox chatContainer;
    private TextArea chatTextArea;
    private TextArea inputText;
    private Stage primaryStage;
    private VBox loginBox;
    private TextField usernameField;
    private HashMap<String, String> chatData;

    private ChatFilms filmsChat = new ChatFilms();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        chatData = new HashMap<>();
        createLoginScene();
        primaryStage.setTitle("Chat42");
        primaryStage.show();
    }
    private Map<String, List<String>> chatDataMap = new HashMap<>();
    private void createLoginScene() {
        loginBox = new VBox();
        loginBox.setSpacing(10);
        loginBox.setPadding(new Insets(20));

        Label titleLabel = new Label("Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            // Perform login validation here

            if (isValidLogin(username, password)) {
                // Login successful, show the main chat window
                showMainChatScene(primaryStage);
            } else {
                // Login failed, display an error message
                displayErrorMessage("Invalid username or password");
            }
        });

        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(event -> {
            createSignupScene();
        });

        loginBox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, signupButton);

        Scene loginScene = new Scene(loginBox, 400, 300);
        primaryStage.setScene(loginScene);

    }



    private boolean displayErrorMessage(String message) {
        // Show the error message to the user (e.g., using a dialog or label)
        return false;
    }

    private boolean isValidLogin(String username, String password) {
        return username.equals(username) && password.equals(password);

    }

    private void createSignupScene() {
        VBox signupBox = new VBox();
        signupBox.setSpacing(10);
        signupBox.setPadding(new Insets(20));

        Label titleLabel = new Label("Sign Up");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button signupButton = new Button("Sign Up");
        signupButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (isValidSignup(username, password)) {
                // Signup successful, display a success message
                displaySuccessMessage("Account created successfully");

                // Automatically fill the login fields with the newly created account
                usernameField.setText(username);
                passwordField.setText(password);

                // Close the signup window
                ((Stage) signupButton.getScene().getWindow()).close();

                // Show the login window
                createLoginScene();
            } else {
                // Signup failed, display an error message
                displayErrorMessage("Invalid username or password");
            }
        });

        signupBox.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, signupButton);

        Scene signupScene = new Scene(signupBox, 400, 300);
        Stage signupStage = new Stage();
        signupStage.setTitle("Sign Up");
        signupStage.setScene(signupScene);
        signupStage.show();
    }

    private void displaySuccessMessage(String message) {
        // Show the success message to the user (e.g., using a dialog or label)
    }

    private boolean isValidSignup(String username, String password) {
        // Perform signup validation (e.g., check against a database)
        return false;
    }

    private void showMainChatScene(Stage primaryStage) {


        BorderPane root = new BorderPane();

        chats = new VBox();
        chats.setPrefWidth(200);
        chats.setSpacing(10);

        // Wrap the chats VBox in a ScrollPane
        ScrollPane chatsScrollPane = new ScrollPane(chats);
        chatsScrollPane.setFitToWidth(true);

        chatContainer = new VBox();
        chatContainer.setSpacing(10);

        HBox inputContainer = new HBox();
        inputContainer.setSpacing(5);

        chatTextArea = new TextArea();
        chatTextArea.setPrefSize(600, 530);

        inputText = new TextArea();
        inputText.setPrefSize(600, 50);
        inputText.setPromptText("Type a message...");

        // Update the event handler for the Enter key in inputText
        inputText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER && !event.isShiftDown()) {
                    event.consume(); // Prevents a new line from being added to the text area
                    String message = inputText.getText().trim(); // Get the entered message
                    if (!message.isEmpty()) {
                        // Process the entered message (e.g., send it, display it in the chat, etc.)
                        QueryResolutionForm ontvang = new QueryResolutionForm(message);
                        chatTextArea.appendText("You: " + message + "\n");
                        inputText.clear(); // Clear the input area after sending the message////
                        filmsChat.resolve(ontvang);

                        chatTextArea.appendText("chat42: " + filmsChat.gimmeResultsI() + "\n");


                        // Save the message in the currently selected chat
                        Node selectedChat = chats.getChildren().stream()
                                .filter(node -> node instanceof HBox)
                                .filter(node -> ((HBox) node).getChildren().get(0) instanceof TextArea)
                                .filter(node -> ((TextArea) ((HBox) node).getChildren().get(0)).getStyle().equals("-fx-control-inner-background: #333333;"))
                                .findFirst().orElse(null);

                        if (selectedChat != null) {
                            TextArea chatNameTextArea = (TextArea) ((HBox) selectedChat).getChildren().get(0);
                            String chatName = chatNameTextArea.getText();
                            addChatData(chatName, "You: " + message, "chat42: " + filmsChat.gimmeResultsI() );
                            saveChat(); // Save the chat after adding the message
                        }
                    }
                }
            }
        });



        inputContainer.getChildren().addAll(inputText);
        chatContainer.getChildren().addAll(chatTextArea, inputContainer);

        VBox leftPane = new VBox();
        chats.setPrefHeight(535);

        ComboBox<String> optionsComboBox = new ComboBox<>();
        optionsComboBox.setPrefWidth(120);
        optionsComboBox.setItems(getOptionsList());
        optionsComboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedOption = optionsComboBox.getValue();
                if (selectedOption.equals("Log Out")) {
                    saveChatData();
                    System.exit(1);
                } else if (selectedOption.equals("Clear Conversations")) {
                    clearConversations();
                } else if (selectedOption.equals("Settings")) {
                    // Open settings window or perform settings-related actions
                }
                optionsComboBox.getSelectionModel().clearSelection();
            }

            private void saveChatData() {
            }
        });

        HBox logoutContainer = new HBox(optionsComboBox);
        logoutContainer.setStyle("-fx-alignment: bottom-center; -fx-padding: 0 30 0 0;");

        leftPane.getChildren().addAll(chatsScrollPane, logoutContainer);

        SplitPane splitPane = new SplitPane(leftPane, chatContainer);
        splitPane.setDividerPositions(0.2); // Set the initial divider position

        root.setCenter(splitPane);

        Button newChatButton = new Button("New Chat");
        newChatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createNewChat();
            }
        });

        HBox buttonContainer = new HBox(newChatButton);
        buttonContainer.setSpacing(5);

        root.setTop(buttonContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Chat42");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createNewChat() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("History", "History", "Geography", "Math");
        dialog.setTitle("New Chat");
        dialog.setHeaderText(null);
        dialog.setContentText("Select the type of question:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::addChatItem);
    }

    private void saveChat() {
        for (Node chatItem : chats.getChildren()) {
            if (chatItem instanceof HBox) {
                TextArea chatNameTextArea = (TextArea) ((HBox) chatItem).getChildren().get(0);
                String chatName = chatNameTextArea.getText();
                List<String> chatData = getChatData(chatName);

                if (chatData != null) {
                    String fileName = chatName.toLowerCase() + ".txt"; // Create a file name based on the chat name
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                        for (String message : chatData) {
                            writer.write(message);
                            writer.newLine();
                        }
                        System.out.println("Chat '" + chatName + "' saved successfully.");
                    } catch (IOException e) {
                        System.out.println("Error saving chat '" + chatName + "': " + e.getMessage());
                    }
                } else {
                    System.out.println("Chat '" + chatName + "' not found.");
                }
            }
        }
    }





    private void clearConversations() {
        for (String chatName : chatDataMap.keySet()) {
            clearChat(chatName);
        }
        chats.getChildren().clear(); // Clear the chat items in the UI
        chatTextArea.clear(); // Clear the chat text area
    }

    private void clearChat(String chatName) {
        chatDataMap.put(chatName, new ArrayList<>());
    }


    private List<String> getChatData(String chatName) {
        return chatDataMap.get(chatName);
    }
    private void addChatData(String chatName, String message, String aI) {
        List<String> chatData = chatDataMap.getOrDefault(chatName, new ArrayList<>());
        chatData.add(message);
        chatData.add(aI);
        chatDataMap.put(chatName, chatData);
    }
    private List<String> loadChatDataFromFile(String chatName) {
        File file = new File(chatName + ".txt");
        if (!file.exists()) {
            return null; // File does not exist, return null
        }

        List<String> chatData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                chatData.add(line);
            }
            System.out.println("Chat '" + chatName + "' loaded successfully from file.");
            return chatData;
        } catch (IOException e) {
            System.out.println("Error loading chat '" + chatName + "' from file: " + e.getMessage());
            return null; // Error occurred while loading, return null
        }
    }



    private void addChatItem(String chatName) {
        TextArea item = new TextArea(chatName);
        item.setPrefHeight(20);
        item.setStyle("-fx-control-inner-background: #333333;");
        item.setEditable(false); // Set the chat name TextArea uneditable

        // Mini button for editing chat name
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> openEditWindow(item));

        HBox chatItemContainer = new HBox(item, editButton);
        chatItemContainer.setSpacing(5);

        item.setOnMouseClicked(event -> {
            chatTextArea.clear();
            chatTextArea.appendText("Clicked on: " + item.getText() + "\n");
            List<String> chatData = getChatData(item.getText());
            if (chatData != null) {
                for (String message : chatData) {
                    chatTextArea.appendText(message + "\n");
                }
            }
        });

        // Load chat data from file based on chat name
        String fileName = chatName.toLowerCase() + ".txt";
        List<String> chatData = loadChatDataFromFile(fileName);
        if (chatData != null) {
            chatDataMap.put(chatName, chatData);
            for (String message : chatData) {
                chatTextArea.appendText(message + "\n");
            }
        }

        chats.getChildren().add(chatItemContainer);
    }


    private void openEditWindow(TextArea chatNameTextArea) {
        Stage editStage = new Stage();
        BorderPane editRoot = new BorderPane();
        TextArea editTextArea = new TextArea(chatNameTextArea.getText());
        Button saveButton = new Button("Save");

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String updatedName = editTextArea.getText();
                chatNameTextArea.setText(updatedName);
                editStage.close();
            }
        });

        editRoot.setCenter(editTextArea);
        editRoot.setBottom(saveButton);
        Scene editScene = new Scene(editRoot, 300, 200);
        editStage.setScene(editScene);
        editStage.setTitle("Edit Chat Name");
        editStage.show();
    }

    private ObservableList<String> getOptionsList() {
        ObservableList<String> optionsList = FXCollections.observableArrayList();
        optionsList.addAll("Clear Conversations", "Settings", "Log Out");
        return optionsList;
    }


    @Override
    public void stop() {
        saveChat();
    }

}