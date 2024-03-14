package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.Attendance;
import com.mes.motorph.entity.Employee;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.view.ViewFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Time;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class AttendanceController {

    @FXML
    private MFXPaginatedTableView<Attendance> attendanceTableView;
    @FXML
    private  Label breadCrumb;
    private AttendanceService attendanceService = new AttendanceService();
    private AttendanceEmployeeController attendanceEmployeeController = new AttendanceEmployeeController();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Attendance / Log");
        attendanceTableView.autosizeColumnsOnInitialization();
        attendanceTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> attendanceTableView.autosizeColumns());
    }

    private void setupTable() {
        attendanceTableView.getTableColumns().clear();

        MFXTableColumn<Attendance> empIdColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(Attendance::getEmployeeId));
        MFXTableColumn<Attendance> dateColumn = new MFXTableColumn<>("Date", true, Comparator.comparing(Attendance::getDate));
        MFXTableColumn<Attendance> timeInColumn = new MFXTableColumn<>("Time In", true, Comparator.comparing(Attendance::getTimeIn));
        MFXTableColumn<Attendance> timeOutColumn = new MFXTableColumn<>("Time Out", true, Comparator.comparing(Attendance::getTimeOut));
        MFXTableColumn<Attendance> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Attendance::getId));
        MFXTableColumn<Attendance> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Attendance::getId));

        empIdColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getEmployeeId));
        dateColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getDate));
        timeInColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getTimeIn));
        timeOutColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getTimeOut));

        deleteButton.setRowCellFactory(attendance -> new MFXTableRowCell<>(attendances -> "") {
            {
                deleteButton.setAlignment(Pos.CENTER);
                deleteButton.setMinWidth(62);
                deleteButton.setMaxWidth(62);
                deleteButton.setColumnResizable(false);

                MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> onClickDeleteAttendance());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        updateButton.setRowCellFactory(attendance -> new MFXTableRowCell<>(attendances -> "") {
            {
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setMinWidth(62);
                updateButton.setMaxWidth(62);
                updateButton.setColumnResizable(false);

                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> {
                    try {
                        onClickUpdate();
                    } catch (AttendanceException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        attendanceTableView.getTableColumns().addAll(empIdColumn, dateColumn, timeInColumn, timeOutColumn, deleteButton, updateButton);
        attendanceTableView.getFilters().addAll(
                new IntegerFilter<>("Employee ID", Attendance::getEmployeeId)
        );

        try {
            List<Attendance> attendanceList = attendanceService.fetchAttedance();
            attendanceTableView.getItems().clear();
            attendanceTableView.setItems(FXCollections.observableArrayList(attendanceList));
        } catch (AttendanceException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void onClickDeleteAttendance() {
        Attendance selectedRow = attendanceTableView.getSelectionModel().getSelectedValues().get(0);

        if (selectedRow != null) {
            int attendanceId = selectedRow.getId();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete selected attendance record?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == okButton) {
                // User clicked OK, proceed with deletion
                try {
                    attendanceService.deleteAttendance(attendanceId);
                    initialize();
                } catch (AttendanceException ex) {
                    // Handle exception
                    AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
                }
            } else {
                // User clicked Cancel or closed the dialog, do nothing
            }
        } else {
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to delete.");
        }
    }

    @FXML
    private void onClickUpdate() throws AttendanceException {
        Attendance selectedAttendance = attendanceTableView.getSelectionModel().getSelectedValues().get(0);

        if(selectedAttendance != null){//check if selected row is not null
            int attendanceId = selectedAttendance.getId();
            int employeedId = selectedAttendance.getEmployeeId();
            Date date = selectedAttendance.getDate();
            Time timeIn = selectedAttendance.getTimeIn();
            Time timeOut = selectedAttendance.getTimeOut();
            //we get each values from the table
            Attendance attendance = new Attendance(attendanceId, employeedId, date, timeIn, timeOut);
            navigateToAttendanceEmployee(attendance);
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Warning", null, "Please select a row to update");
        }

    }

    private void navigateToAttendanceEmployee(Attendance attendance){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-punch-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 462, 650);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Time Management");
            stage.setResizable(false);
            stage.show();

            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            stage.getIcons().add(icon);

            //timeInOut.setVisible(true);
            //call the setAttendanceDetails to set the fields when updating
            AttendanceEmployeeController attendanceEmployeeController = fxmlLoader.getController();
            attendanceEmployeeController.setAttendanceDetails(attendance);
            } catch (Exception e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onClickAdd(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource("/com/mes/motorph/attendance-punch-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 462, 650);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Time Management");
            stage.setResizable(false);
            stage.show();

            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            stage.getIcons().add(icon);

            AttendanceEmployeeController attendanceEmployeeController = fxmlLoader.getController();
            attendanceEmployeeController.onClickAddToCreate();
        } catch (Exception e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
