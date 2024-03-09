package com.mes.motorph.controller;

import com.mes.motorph.Main;
import com.mes.motorph.entity.Employee;
import com.mes.motorph.entity.LeaveRequest;
import com.mes.motorph.entity.UserRole;
import com.mes.motorph.exception.EmployeeException;
import com.mes.motorph.exception.LeaveRequestException;
import com.mes.motorph.services.EmployeeService;
import com.mes.motorph.services.LeaveRequestService;
import com.mes.motorph.utils.AlertUtility;
import eu.hansolo.tilesfx.addons.Switch;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ManageLeaveReqController {
    private int id;

    private int employeeId;

    @FXML
    private Label breadCrumb;
    @FXML
    private Label employeeIdCrumb;

    @FXML
    private MFXPaginatedTableView<LeaveRequest> leaveRequestTableView;

    LeaveRequestService leaveRequestService = new LeaveRequestService();
    EmployeeService employeeService = new EmployeeService();

    @FXML
    protected void initialize() {
        setupTable();
        breadCrumb.setText("Attendance/Leave Request");
    }
    private void setupTable(){
        leaveRequestTableView.getTableColumns().clear();

        MFXTableColumn<LeaveRequest> employeeName = new MFXTableColumn<>("Employee Name", true, Comparator.comparing(LeaveRequest::getName));
        MFXTableColumn<LeaveRequest> supervisor = new MFXTableColumn<>("Direct Supervisor", true, Comparator.comparing(LeaveRequest::getSupervisor));
        MFXTableColumn<LeaveRequest> regDate = new MFXTableColumn<>("Submitted Date", true, Comparator.comparing(LeaveRequest::getRegDate));
        MFXTableColumn<LeaveRequest> startDate = new MFXTableColumn<>("Start Date", true, Comparator.comparing(LeaveRequest::getStartDate));
        MFXTableColumn<LeaveRequest> endDate = new MFXTableColumn<>("End Date", true, Comparator.comparing(LeaveRequest::getEndDate));
        MFXTableColumn<LeaveRequest> leaveType = new MFXTableColumn<>("Leave Type", true, Comparator.comparing(LeaveRequest::getLeaveType));
        MFXTableColumn<LeaveRequest> apprDate = new MFXTableColumn<>("Status Date", true, Comparator.comparing(LeaveRequest::getApproveDate));
        MFXTableColumn<LeaveRequest> status = new MFXTableColumn<>("Status", true, Comparator.comparing(LeaveRequest::getStatus));
        MFXTableColumn<LeaveRequest> deptDesc = new MFXTableColumn<>("Dept Desc", true, Comparator.comparing(LeaveRequest::getDeptDesc));
        MFXTableColumn<LeaveRequest> leaveReqId = new MFXTableColumn<>("", true, Comparator.comparing(LeaveRequest::getLeaveReqId));
        MFXTableColumn<LeaveRequest> approveBtn = new MFXTableColumn<>("", true, Comparator.comparing(LeaveRequest::getLeaveReqId));
        MFXTableColumn<LeaveRequest> rejectBtn = new MFXTableColumn<>("", true, Comparator.comparing(LeaveRequest::getLeaveReqId));

        employeeName.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getName));
        supervisor.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getSupervisor));
        regDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getRegDate));
        startDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getStartDate));
        endDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getEndDate));
        leaveType.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getLeaveType));
        status.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getStatus));
        apprDate.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(LeaveRequest::getApproveDate));

        approveBtn.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(leaveRequests -> "") {
            {
                approveBtn.setAlignment(Pos.CENTER);
                approveBtn.setMinWidth(70);
                approveBtn.setMaxWidth(70);
                approveBtn.setColumnResizable(false);

                MFXButton button = createButton("✔", "mfx-button-table-update", mouseEvent -> approveLeave());
                setGraphic(button);

                mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println(newValue);
                    if (newValue) {
                        setMouseTransparent(false);
                    }
                });
            }
        });

        rejectBtn.setRowCellFactory(leaveRequest -> new MFXTableRowCell<>(leaveRequests -> "") {
                    {
                        rejectBtn.setAlignment(Pos.CENTER);
                        rejectBtn.setMinWidth(70);
                        rejectBtn.setMaxWidth(70);
                        rejectBtn.setColumnResizable(false);

                        MFXButton button = createButton("❌", "mfx-button-table-delete", event -> rejectLeave());
                        setGraphic(button);

                        mouseTransparentProperty().addListener((observable, oldValue, newValue) -> {
                            System.out.println(newValue);
                            if (newValue) {
                                setMouseTransparent(false);
                            }
                        });
                    }
                });

        leaveRequestTableView.getTableColumns().addAll(employeeName,supervisor,regDate,startDate,endDate,leaveType, apprDate,status, rejectBtn,approveBtn);

        leaveRequestTableView.getFilters().addAll(
                new StringFilter<>("Employee Name", LeaveRequest::getName)
        );

        try {
            List<LeaveRequest> leaveRequests = leaveRequestService.fetchAllLeaveRequest();
            leaveRequestTableView.getItems().clear();
            leaveRequestTableView.setItems(FXCollections.observableArrayList(leaveRequests));
        } catch (LeaveRequestException e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private MFXButton createButton(String text, String styleClass, EventHandler<? super MouseEvent> eventHandler) {
        MFXButton button = new MFXButton(text);
        button.getStyleClass().add(styleClass);
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        return button;
    }

    @FXML
    protected void onClickFileLeave(){
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/mes/motorph/create-leave-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("File Leave");
            stage.setResizable(false);
            stage.show();
            // Load the application icon
            Image icon = new Image(Main.class.getResourceAsStream("/images/app-icon.png"));
            stage.getIcons().add(icon);
            Employee employee = employeeService.fetchEmployeeDetails(employeeId);
            CreateLeaveController fileLeaveController = fxmlLoader.getController();
            fileLeaveController.setData(employee);

        } catch (Exception e) {
            AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Information", null, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setEmployeeId(Employee employee) {
        this.employeeId = employee.getId();
    }

    protected void rejectLeave() {
        LeaveRequest selectedLeaveRequestRow = leaveRequestTableView.getSelectionModel().getSelectedValues().get(id);
        if (selectedLeaveRequestRow != null) {
            int leaveReqId = selectedLeaveRequestRow.getLeaveReqId();
            String status = "Rejected";
            Date approveDate = Date.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            LeaveRequest rejectleaveRequest = new LeaveRequest(status, approveDate, leaveReqId);
            try {
                leaveRequestService.updateLeaveRequest(rejectleaveRequest);
                initialize();
            } catch (LeaveRequestException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("SELECT A ROW!");
        }
    }

    protected void approveLeave() {
        LeaveRequest selectedLeaveRequestRow = leaveRequestTableView.getSelectionModel().getSelectedValues().get(id);
        if (selectedLeaveRequestRow != null) {
            int empId = selectedLeaveRequestRow.getEmployeeId();
            int leaveReqId = selectedLeaveRequestRow.getLeaveReqId();
            Date startDate = selectedLeaveRequestRow.getStartDate();
            Date endDate = selectedLeaveRequestRow.getEndDate();
            String leaveType = selectedLeaveRequestRow.getLeaveType();
            Date approveDate = Date.valueOf(LocalDate.now());
            String status = "Approved";
            LeaveRequest approveLeaveReq = new LeaveRequest(status, approveDate, leaveReqId);
            try {
                Employee employee = employeeService.fetchEmployeeDetails(empId);
                switch (leaveType){
                    case "Sick Leave":
                        double sickHours = employee.getSickHours();
                        long timeBet = (endDate.getTime() - startDate.getTime());
                        double convertDays = ((TimeUnit.DAYS.convert(timeBet, TimeUnit.MILLISECONDS)+1)*8); //*8 to hours
                        System.out.println("Convert days: " + convertDays + " Sick Hours: " + sickHours);
                        double remainingSickHours = sickHours-convertDays;
                        System.out.println(remainingSickHours);
                        Employee updEmployeeSick = new Employee(empId, remainingSickHours);
                        employeeService.updateSickHours(updEmployeeSick);
                        leaveRequestService.updateLeaveRequest(approveLeaveReq);
                        AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request Status", null, "Leave Request Approved");
                        initialize();
                        break;

                    case "Vacation Leave":
                        double vacaHours = employee.getVacationHours();
                        timeBet = (endDate.getTime() - startDate.getTime());
                        convertDays = ((TimeUnit.DAYS.convert(timeBet, TimeUnit.MILLISECONDS)+1)*8); //*8 to hours
                        System.out.println("Convert days: " + convertDays + " Vaca Hours: " + vacaHours);
                        double remainingVacaHours = vacaHours-convertDays;
                        System.out.println(remainingVacaHours);
                        Employee updEmployeeVaca = new Employee(empId, remainingVacaHours);
                        employeeService.updateVacaHours(updEmployeeVaca);
                        leaveRequestService.updateLeaveRequest(approveLeaveReq);
                        AlertUtility.showAlert(Alert.AlertType.INFORMATION, "Leave Request Status", null, "Leave Request Approved");
                        break;
                }
            } catch (EmployeeException | LeaveRequestException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
