package com.example.hotel.controller;



import com.example.hotel.model.customerModel;
import com.example.hotel.model.customerModel.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;

public class customerController {


    @FXML private TextField customerIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField emailField;
    @FXML private TextField addressField;

    private final customerModel model = customerModel.getInstance();

    @FXML
    private void initialize() {
    }

    @FXML
    protected void handleAddCustomerAction() {
        if (validateInput()) {
            Customer customer = new Customer(
                    0,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    phoneNumberField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    "DefaultPaymentMethod"
            );
            try {
                boolean success = model.addCustomer(customer);
                if (success) {
                    showAlertDialog(AlertType.INFORMATION, "Success", "Customer added successfully!");
                    clearForm();
                } else {
                    showAlertDialog(AlertType.ERROR, "Error", "Failed to add customer.");
                }
            } catch (SQLException e) {
                showAlertDialog(AlertType.ERROR, "Database Error", "A database error occurred.");
            }
        }
    }

    @FXML
    protected void handleUpdateCustomerAction() {
        if (validateInput() && validateCustomerId()) {
            Customer customer = new Customer(
                    Integer.parseInt(customerIdField.getText()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    phoneNumberField.getText(),
                    emailField.getText(),
                    addressField.getText(),
                    "DefaultPaymentMethod"
            );
            try {
                boolean success = model.updateCustomer(customer);
                if (success) {
                    showAlertDialog(AlertType.INFORMATION, "Success", "Customer updated successfully!");
                } else {
                    showAlertDialog(AlertType.ERROR, "Error", "Failed to update customer.");
                }
            } catch (SQLException e) {
                showAlertDialog(AlertType.ERROR, "Database Error", "A database error occurred.");
            }
        }
    }

    @FXML
    protected void handleDeleteCustomerAction() {
        if (validateCustomerId()) {
            int customerId = Integer.parseInt(customerIdField.getText());
            try {
                boolean success = model.deleteCustomer(customerId);
                if (success) {
                    showAlertDialog(AlertType.INFORMATION, "Success", "Customer deleted successfully!");
                    clearForm();
                } else {
                    showAlertDialog(AlertType.ERROR, "Error", "Failed to delete customer.");
                }
            } catch (SQLException e) {
                showAlertDialog(AlertType.ERROR, "Database Error", "A database error occurred.");
            }
        }
    }

    @FXML
    protected void handleVerifyReservationAction() {
        int customerId = Integer.parseInt(customerIdField.getText());
        try {
            boolean success = model.verifyReservation(customerId);
            String message = success ? "Reservation verified successfully!" : "No active reservation found.";
            showAlertDialog(AlertType.INFORMATION, "Reservation Verification", message);
        } catch (SQLException e) {
            showAlertDialog(AlertType.ERROR, "Database Error", "Error occurred while verifying reservation.");
        }
    }

    @FXML
    protected void handleAssignRoomAction() {
    }

    @FXML
    protected void handleProcessPaymentAction() {
    }

    @FXML
    protected void handleReturnRoomKeyAction() {
    }

    private boolean validateInput() {
        if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                phoneNumberField.getText().isEmpty() || emailField.getText().isEmpty() ||
                addressField.getText().isEmpty()) {
            showAlertDialog(AlertType.WARNING, "Validation Error", "All fields must be filled out.");
            return false;
        }
        return true;
    }

    private boolean validateCustomerId() {
        try {
            Integer.parseInt(customerIdField.getText());
            return true;
        } catch (NumberFormatException e) {
            showAlertDialog(AlertType.WARNING, "Validation Error", "Invalid customer ID.");
            return false;
        }
    }

    private void clearForm() {
        customerIdField.clear();
        firstNameField.clear();
        lastNameField.clear();
        phoneNumberField.clear();
        emailField.clear();
        addressField.clear();
    }

    private void showAlertDialog(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

