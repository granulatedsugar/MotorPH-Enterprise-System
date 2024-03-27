package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.Attendance;
import com.mes.motorph.exception.AttendanceException;
import com.mes.motorph.services.AttendanceService;
import com.mes.motorph.utils.AlertUtility;
import com.mes.motorph.view.ViewFactory;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class AttendanceController {

    // Test TableView Pagination
    @FXML
    private TableView<Attendance> pageTableView;
    @FXML
    private TableColumn<Attendance, Integer> empIdColumn;
    @FXML
    private TableColumn<Attendance, Date> dateColumn;
    @FXML
    private TableColumn<Attendance, Time> timeInColumn;
    @FXML
    private TableColumn<Attendance, Time> timeOutColumn;
    @FXML
    private TableColumn<Attendance, Void> updateColumn;
    @FXML
    private TableColumn<Attendance, Void> deleteColumn;
    @FXML
    private Pagination attPagination;
    @FXML
    private List<Attendance> attendancesArrayList;
    private int itemsPerPage;
    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private FilteredList<Attendance> filteredAttendances;

    @FXML
    private MFXPaginatedTableView<Attendance> attendanceTableView;
    @FXML
    private  Label breadCrumb;
    private AttendanceService attendanceService = new AttendanceService();
    private AttendanceEmployeeController attendanceEmployeeController = new AttendanceEmployeeController();
    private boolean isFiltering = false;

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Attendance / Log");
//        attendanceTableView.autosizeColumnsOnInitialization();
//        attendanceTableView.currentPageProperty().addListener((observable, oldValue, newValue) -> attendanceTableView.autosizeColumns());
    }

    private void setupTable() {
        try {
            this.attendancesArrayList = attendanceService.fetchAttedance();
        } catch (AttendanceException e) {
            throw new RuntimeException(e);
        }

        // Initialize filteredAttendances
        filteredAttendances = new FilteredList<>(FXCollections.observableArrayList(attendancesArrayList));

        this.itemsPerPage = 25;
        attPagination.setPageCount(calculatePageCount(filteredAttendances.size(), itemsPerPage));
        attPagination.setPageFactory(this::createPage);

        // Define table columns
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        empIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
        timeOutColumn.setCellValueFactory(new PropertyValueFactory<>("timeOut"));
        updateColumn.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button();

            {
                // Load your image
                Image image = new Image(getClass().getResourceAsStream("/images/pen.png"));

                // Create an ImageView with the image
                ImageView imageView = new ImageView(image);

                // Set the size of the ImageView (adjust as needed)
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);

                // Set the ImageView as the graphic of the button
                updateButton.setGraphic(imageView);
                updateButton.setStyle("-fx-background-color: transparent");
                setAlignment(Pos.CENTER);

                updateButton.setOnAction(event -> {
                    Attendance attendanceRowData = getTableView().getItems().get(getIndex());

                    System.out.println("Update button clicked for: " + attendanceRowData);
                    navigateToAttendanceEmployee(attendanceRowData);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button();

            {
                // Load your image
                Image image = new Image(getClass().getResourceAsStream("/images/trash-bin.png"));

                // Create an ImageView with the image
                ImageView imageView = new ImageView(image);

                // Set the size of the ImageView (adjust as needed)
                imageView.setFitWidth(20);
                imageView.setFitHeight(20);

                // Set the ImageView as the graphic of the button
                deleteButton.setGraphic(imageView);
                deleteButton.setStyle("-fx-background-color: transparent");
                setAlignment(Pos.CENTER);

                deleteButton.setOnAction(event -> {
                    Attendance attendanceRowData = getTableView().getItems().get(getIndex());

                    System.out.println("Update button clicked for: " + attendanceRowData);

                    // Call the onClickDeleteAttendance method with the selected Attendance object
                    onClickDeleteAttendance(attendanceRowData);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        dateColumn.setCellFactory(column -> new TableCell<Attendance, Date>() {
            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item.toString()); // Customize the date formatting as needed
                }
            }
        });

        // Add listener to the DatePicker
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                isFiltering = false;
            } else {
                isFiltering = true;
                String selectedDateString = newValue.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                filteredAttendances.setPredicate(attendance ->
                        attendance.getDate().toString().equals(selectedDateString)
                );
            }
            attPagination.setPageCount(calculatePageCount(filteredAttendances.size(), itemsPerPage));
            attPagination.setPageFactory(this::createPage);
        });
    }

    // Calculate page
    private int calculatePageCount(int totalItems, int itemsPerPage) {
        return (int) Math.ceil((double) totalItems / itemsPerPage);
    }

    private Node createPage(Integer pageIndex) {
        int fromIndex = pageIndex * itemsPerPage;
        int toIndex = Math.min(fromIndex + itemsPerPage, filteredAttendances.size());

        ObservableList<Attendance> items = isFiltering ? filteredAttendances : FXCollections.observableArrayList(attendancesArrayList);
        pageTableView.setItems(FXCollections.observableArrayList(items.subList(fromIndex, toIndex)));

        return pageTableView;
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    protected void onClickDeleteAttendance(Attendance attendance) {
//        Attendance selectedRow = attendanceTableView.getSelectionModel().getSelectedValues().get(0);

        if (attendance != null) {
            int attendanceId = attendance.getId();

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
//            Attendance attendance = new Attendance(attendanceId, employeedId, date, timeIn, timeOut);
//            navigateToAttendanceEmployee(attendance);
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


//    NOTE: MATERIALFX
//    private void setupTable() {
//        attendanceTableView.getTableColumns().clear();
//
//        MFXTableColumn<Attendance> empIdColumn = new MFXTableColumn<>("Employee ID", true, Comparator.comparing(Attendance::getEmployeeId));
//        MFXTableColumn<Attendance> dateColumn = new MFXTableColumn<>("Date", true, Comparator.comparing(Attendance::getDate));
//        MFXTableColumn<Attendance> timeInColumn = new MFXTableColumn<>("Time In", true, Comparator.comparing(Attendance::getTimeIn));
//        MFXTableColumn<Attendance> timeOutColumn = new MFXTableColumn<>("Time Out", true, Comparator.comparing(Attendance::getTimeOut));
//        MFXTableColumn<Attendance> deleteButton = new MFXTableColumn<>("", true, Comparator.comparing(Attendance::getId));
//        MFXTableColumn<Attendance> updateButton = new MFXTableColumn<>("", true, Comparator.comparing(Attendance::getId));
//
//        empIdColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getEmployeeId));
//        dateColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getDate));
//        timeInColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getTimeIn));
//        timeOutColumn.setRowCellFactory(attendance -> new MFXTableRowCell<>(Attendance::getTimeOut));
//
//        deleteButton.setRowCellFactory(attendance -> new MFXTableRowCell<>(attendances -> "") {
//            {
//                deleteButton.setAlignment(Pos.CENTER);
//                deleteButton.setMinWidth(62);
//                deleteButton.setMaxWidth(62);
//                deleteButton.setColumnResizable(false);
//
//                MFXButton button = createButton("⛔", "mfx-button-table-delete", event -> onClickDeleteAttendance());
//                setGraphic(button);
//
//                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
//                    System.out.println(newValue);
//                    if (newValue) {
//                        setMouseTransparent(false);
//                    }
//                });
//            }
//        });
//
//        updateButton.setRowCellFactory(attendance -> new MFXTableRowCell<>(attendances -> "") {
//            {
//                updateButton.setAlignment(Pos.CENTER);
//                updateButton.setMinWidth(62);
//                updateButton.setMaxWidth(62);
//                updateButton.setColumnResizable(false);
//
//                MFXButton button = createButton("🖊", "mfx-button-table-update", event -> {
//                    try {
//                        onClickUpdate();
//                    } catch (AttendanceException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                });
//                setGraphic(button);
//
//                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
//                    System.out.println(newValue);
//                    if (newValue) {
//                        setMouseTransparent(false);
//                    }
//                });
//            }
//        });
//
//        attendanceTableView.getTableColumns().addAll(empIdColumn, dateColumn, timeInColumn, timeOutColumn, deleteButton, updateButton);
//        attendanceTableView.getFilters().addAll(
//                new IntegerFilter<>("Employee ID", Attendance::getEmployeeId)
//        );
//
//        try {
//            List<Attendance> attendanceList = attendanceService.fetchAttedance();
//            attendanceTableView.getItems().clear();
//            attendanceTableView.setItems(FXCollections.observableArrayList(attendanceList));
//        } catch (AttendanceException e) {
//            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
//        }
//    }
