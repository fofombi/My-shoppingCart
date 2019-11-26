
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    @FXML
    public TableColumn<Item, String> inventoryItems;
    @FXML
    public TableColumn<Item, Double> inventoryPrice;
    @FXML
    public TableColumn<Item, Integer> inventoryPriority;
    @FXML
    public TableColumn<Item, Integer> inventoryQuantity;
    @FXML
    public TableColumn<Item, String> shoppingItems;
    @FXML
    public TableColumn<Item, Double> shoppingPrice;
    @FXML
    public TableColumn<Item, Integer> shoppingPriority;
    @FXML
    public TableColumn<Item, Integer> shoppingQuantity;
    @FXML
    public Label shoppingTotal;
    @FXML
    public Label inventoryTotal;
    @FXML
    public Label customerNameLabel;
    @FXML
    public Label customerBudgetLabel;
    @FXML
    private TableView<Item> inventoryTable;
    @FXML
    private TableView<Item> shoppingTable;
    @FXML
    private javafx.scene.control.Button quitButton;


    @FXML
    private void closeButtonAction() {
        // get a handle to the stage
        Stage stage = (Stage) quitButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @FXML
    private void addInventoryItem() {
        Item item = addItemDialog();
        inventory.addItem(item);
        redraw();
    }

    // DATA
    private ObservableList<Item> items = FXCollections.observableArrayList(
            new Item("Juice", 23, 5, 5), new Item("Orange", 15, 4, 19), new Item("Bread", 8.5, 7, 15)
    );
    private ObservableList<Item> desiredItems = FXCollections.observableArrayList(
    );

    private double customerBudget;
    private String customerName;

    private ShoppingList inventory;
    private ShoppingList shopping;

    private ObservableList<Item> getInventoryObservableList() {
        return FXCollections.observableArrayList(inventory.getItems());
    }

    private ObservableList<Item> getShoppingObservableList() {
        return FXCollections.observableArrayList(shopping.getItems());
    }

    private void redraw() {
        redrawInventoryTable();
        redrawShoppingTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inventory = new ShoppingList(300);
        shopping = new ShoppingList(300);
        inventory.addItem(
                new Item("Juice", 23, 5, 5));
        inventory.addItem(new Item("Orange", 15, 4, 19));
        inventory.addItem(new Item("Bread", 8.5, 7, 15));

        // Get Customer Details
        Pair<String, String> dialogResults = customerDataCollectionDialog().get();
        customerName = dialogResults.getKey();
        String customerBudgetString = dialogResults.getValue();
        customerBudget = Double.parseDouble(customerBudgetString);
        customerBudgetLabel.setText(customerBudgetString);
        customerNameLabel.setText(customerName);

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        inventoryItems.setCellValueFactory(new PropertyValueFactory<>("Name"));
        inventoryPrice.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        inventoryPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        inventoryQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        //make sure the property value factory should be exactly same as the e.g getStudentId from your model class
        shoppingItems.setCellValueFactory(new PropertyValueFactory<>("Name"));
        shoppingPrice.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        shoppingPriority.setCellValueFactory(new PropertyValueFactory<>("Priority"));
        shoppingQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));


        //add your data to the table here.
//        inventoryTable.setItems(getInventoryObservableList());
//        shoppingTable.setItems(desiredItems);
        redraw();

        // Moving data from InventoryTable to ShoppingTable
//        inventoryTable.setOnMouseClicked(event -> {
//            if (event.getClickCount() == 2) {
//                System.out.println(inventoryTable.getSelectionModel().getSelectedItem());
//                Item selectItem = inventoryTable.getSelectionModel().getSelectedItem();
//                if (selectItem.isAvailable()) {
//                    selectItem.decrementQuantity();
//                    System.out.println("Item has been reduced by one");
//                    inventoryTable.refresh();
//                    Item boughtItem = selectItem.createCopy();
//                    boughtItem.setQuantity(1);
//
//                    double total = inventoryTable.getItems().stream().mapToDouble(Item::getAllItemsCost).sum();
//                    inventoryTotal.setText(Double.toString(total));
//
//                    desiredItems.add(boughtItem);
//                    shoppingTable.refresh();
//                }
//
//
//            }
//        });

    }

    private void redrawInventoryTable() {
        inventoryTable.setItems(getInventoryObservableList());
        double total = inventoryTable.getItems().stream().mapToDouble(Item::getAllItemsCost).sum();
        inventoryTotal.setText(Double.toString(total));
    }

    private void redrawShoppingTable() {
        shoppingTable.setItems(getShoppingObservableList());
        double total = shoppingTable.getItems().stream().mapToDouble(Item::getAllItemsCost).sum();
        shoppingTotal.setText(Double.toString(total));
    }

    /*
     *
     * Returns a Pair<String, String> of <Customer Name, Budget> Budget can be converted to a Double
     */
    private Optional<Pair<String, String>> customerDataCollectionDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Kevin's Shop");
        dialog.setHeaderText("Welcome, Please enter the Customer Name and Budget");
        dialog.initStyle(StageStyle.UTILITY);

        ButtonType welcomeButtonType = new ButtonType("Start", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(welcomeButtonType);

        // Create Grid to hold the textboxes.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField customerName = new TextField();
        customerName.setPromptText("Customer Name");
        TextField budgetField = new TextField();
        budgetField.setPromptText("Budget");

        grid.add(new Label("Customer Name:"), 0, 0);
        grid.add(customerName, 1, 0);
        grid.add(new Label("Budget:"), 0, 1);
        grid.add(budgetField, 1, 1);

        // Enable/Disable login button depending on whether a customerName was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(welcomeButtonType);
        loginButton.setDisable(true);

        // Do some validation
        customerName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || budgetField.textProperty().getValue().trim().isEmpty());
        });
        budgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || customerName.textProperty().getValue().trim().isEmpty());
            if (newValue.isEmpty()) return;
            try {
                Double.parseDouble(newValue);
            } catch (Exception e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> customerName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == welcomeButtonType) {
                return new Pair<>(customerName.getText(), budgetField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(userBudget -> {
            System.out.println("Username=" + userBudget.getKey() + ", Budget=" + userBudget.getValue());
        });

        return result;
    }

    private Item addItemDialog() {
        // Create the custom dialog.
        Dialog<Item> dialog = new Dialog<>();
        dialog.setTitle("Kevin's Shop");
        dialog.setHeaderText("Please enter the item's details");
        dialog.initStyle(StageStyle.UTILITY);

//        ButtonType welcomeButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Create Grid to hold the textboxes.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField itemName = new TextField();
        itemName.setPromptText("Item Name");
        TextField itemPrice = new TextField();
        itemPrice.setPromptText("Item Price");
        TextField itemPriority = new TextField();
        itemPriority.setPromptText("Priority [1-7]");
        TextField itemQuantity = new TextField();
        itemQuantity.setPromptText("Quantity");


        grid.add(new Label("Name:"), 0, 0);
        grid.add(itemName, 1, 0);
        grid.add(new Label("Price:"), 0, 1);
        grid.add(itemPrice, 1, 1);
        grid.add(new Label("Priority:"), 0, 2);
        grid.add(itemPriority, 1, 2);
        grid.add(new Label("Quantity:"), 0, 3);
        grid.add(itemQuantity, 1, 3);

        // Enable/Disable login button depending on whether a customerName was entered.
        Node ok_button = dialog.getDialogPane().lookupButton(ButtonType.OK);
        ok_button.setDisable(true);

        // Validate Input
        itemName.textProperty().addListener((observable, oldValue, newValue) -> {
            ok_button.setDisable(
                    newValue.trim().isEmpty() ||
                            isTextFieldEmpty(itemPrice) ||
                            isTextFieldEmpty(itemPriority) ||
                            isTextFieldEmpty(itemQuantity));
        });
        itemPrice.textProperty().addListener((observable, oldValue, newValue) -> {
            ok_button.setDisable(
                    newValue.trim().isEmpty() ||
                            isTextFieldEmpty(itemName) ||
                            isTextFieldEmpty(itemPriority) ||
                            isTextFieldEmpty(itemQuantity)
            );
            if (newValue.isEmpty()) return;
            try {
                Double.parseDouble(newValue);
            } catch (Exception e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });
        itemPriority.textProperty().addListener((observable, oldValue, newValue) -> {
            ok_button.setDisable(
                    newValue.trim().isEmpty() ||
                            isTextFieldEmpty(itemName) ||
                            isTextFieldEmpty(itemPrice) ||
                            isTextFieldEmpty(itemQuantity)
            );
            if (newValue.isEmpty()) return;
            try {
                int x = Integer.parseInt(newValue);
                if (x >= 8 || x < 1) throw new AssertionError();
            } catch (Throwable e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });
        itemQuantity.textProperty().addListener((observable, oldValue, newValue) -> {
            ok_button.setDisable(
                    newValue.trim().isEmpty() ||
                            isTextFieldEmpty(itemName) ||
                            isTextFieldEmpty(itemPrice) ||
                            isTextFieldEmpty(itemPriority)
            );
            if (newValue.isEmpty()) return;
            try {
                Integer.parseInt(newValue);
            } catch (Exception e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        });

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> itemName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Item(itemName.getText(), Double.parseDouble(itemPrice.getText()), Integer.parseInt(itemPriority.getText()), Integer.parseInt(itemQuantity.getText()));
            }
            return new Item();

        });

        return dialog.showAndWait().get();
    }

    private boolean isTextFieldEmpty(TextField field) {
        return field.textProperty().getValue().trim().isEmpty();
    }

    @FXML
    private void actuallyShop() {
        System.out.println("actually shop clicked");
        int inventory_index = 0;
        while (shopping.totalCost() <= customerBudget && inventory_index < inventory.numItems()) {
            Item inventoryItem = inventory.getItem(inventory_index);
            Item cartItem = new Item(inventoryItem.getName(), inventoryItem.getCost(), inventoryItem.getPriority(),
                    inventoryItem.getQuantity());
            cartItem.setQuantity(0);
            while (inventoryItem.isAvailable()) {
                double currentTotal = shopping.totalCost() + (cartItem.getCost() * cartItem.getQuantity());
                double newTotal = inventoryItem.getCost() + currentTotal;
                if (newTotal > customerBudget) {
                    break;

                } else {
                    inventoryItem.decrementQuantity();
                    cartItem.incrementQuantity();
                }

            }
            if (cartItem.isAvailable()) {
                shopping.addItem(cartItem);
            }
            inventory_index++;
        }
        System.out.println(" Fellow items were purchased");
        System.out.println(shopping.toString());
        redraw();

    }

}
