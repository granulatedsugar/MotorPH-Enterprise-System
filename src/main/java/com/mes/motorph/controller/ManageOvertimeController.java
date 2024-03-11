package com.mes.motorph.controller;

import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.entity.Overtime;
import com.mes.motorph.exception.OvertimeException;
import com.mes.motorph.services.OvertimeService;
import com.mes.motorph.utils.AlertUtility;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.Comparator;
import java.util.List;

public class ManageOvertimeController {

    private int id;

    @FXML
    private Label breadCrumb;
    @FXML
    private MFXPaginatedTableView<Overtime> overtimeReqTableView;

    OvertimeService overtimeService = new OvertimeService();

    @FXML
    protected void initialize(){
        breadCrumb.setText("Attendance / Manage Overtime");
        setupTable();
    }

    private void setupTable(){
        overtimeReqTableView.getTableColumns().clear();

        MFXTableColumn<Overtime> date = new MFXTableColumn<>("Submitted Date", true, Comparator.comparing(Overtime::getDate));
        MFXTableColumn<Overtime> employeeId = new MFXTableColumn<>("Submitted Date", true, Comparator.comparing(Overtime::getEmployeeId));
        MFXTableColumn<Overtime> status = new MFXTableColumn<>("Submitted Date", true, Comparator.comparing(Overtime::getStatus));
        MFXTableColumn<Overtime> approveBtn = new MFXTableColumn<>("", true, Comparator.comparing(Overtime::getOvertimeId));
        MFXTableColumn<Overtime> rejectBtn = new MFXTableColumn<>("", true, Comparator.comparing(Overtime::getOvertimeId));

        date.setRowCellFactory(overtime -> new MFXTableRowCell<>(Overtime::getDate));
        employeeId.setRowCellFactory(overtime -> new MFXTableRowCell<>(Overtime::getEmployeeId));
        status.setRowCellFactory(overtime -> new MFXTableRowCell<>(Overtime::getStatus));

        approveBtn.setRowCellFactory(overtime -> new MFXTableRowCell<>(ovetimes -> "") {
            {
                approveBtn.setAlignment(Pos.CENTER);
                approveBtn.setMinWidth(70);
                approveBtn.setMaxWidth(70);
                approveBtn.setColumnResizable(false);

                MFXButton button = createButton("✔", "mfx-button-table-update", mouseEvent -> approveOvertime());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        rejectBtn.setRowCellFactory(overtime -> new MFXTableRowCell<>(ovetimes -> "") {
            {
                rejectBtn.setAlignment(Pos.CENTER);
                rejectBtn.setMinWidth(70);
                rejectBtn.setMaxWidth(70);
                rejectBtn.setColumnResizable(false);

                MFXButton button = createButton("❌", "mfx-button-table-delete", event -> rejectOvertime());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        overtimeReqTableView.getTableColumns().addAll(date,employeeId,status,approveBtn,rejectBtn);

        overtimeReqTableView.getFilters().addAll(
                new IntegerFilter<>("Employee Id", Overtime::getEmployeeId)
        );

        try {
            List<Overtime> overtimeList = overtimeService.fetchAllOvertime();
            overtimeReqTableView.getItems().clear();
            overtimeReqTableView.setItems(FXCollections.observableArrayList(overtimeList));
        } catch (OvertimeException e) {
            throw new RuntimeException(e);
        }

    }

    private void approveOvertime(){
        Overtime selectedOvertime = overtimeReqTableView.getSelectionModel().getSelectedValues().get(id);
        if (selectedOvertime != null) {
            int overtimeId = selectedOvertime.getOvertimeId();
            String status = "Approved";
            Overtime overtime = new Overtime(overtimeId, status);
            try {
                overtimeService.updateOvertime(overtime);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Overtime", null, "Overtime Approved");
                initialize();
            } catch (OvertimeException e) {
                throw new RuntimeException(e);
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Overtime", null, "Select a ROW");
        }
    }

    private void rejectOvertime(){
        Overtime selectedOvertime = overtimeReqTableView.getSelectionModel().getSelectedValues().get(id);
        if (selectedOvertime != null) {
            int overtimeId = selectedOvertime.getOvertimeId();
            String status = "Rejected";
            Overtime overtime = new Overtime(overtimeId, status);
            try {
                overtimeService.updateOvertime(overtime);
                AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Overtime", null, "Overtime Rejected");
                initialize();
            } catch (OvertimeException e) {
                throw new RuntimeException(e);
            }
        }else{
            AlertUtility.showAlert(Alert.AlertType.WARNING, "Overtime", null, "Select a ROW");
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

}


