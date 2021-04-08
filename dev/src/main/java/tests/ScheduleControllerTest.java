package tests;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Business.TypeOfEmployee;
import Business.TypeOfShift;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class ScheduleControllerTest {

    private StaffController staffController;
    private ScheduleController scheduleController;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        staffController = new StaffController(TypeOfEmployee.HRManager);
        scheduleController = new ScheduleController(TypeOfEmployee.HRManager, staffController);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void addShift() {
        TypeOfShift type = TypeOfShift.Morning;
        Date date = new Date(1111111111);
        Map<TypeOfEmployee, Integer> constraints= new HashMap<>();
        constraints.put(TypeOfEmployee.HRManager, 1);
        //this.scheduleController.addShift(date,type, constraints);

    }

    @org.junit.jupiter.api.Test
    void removeShift() {
    }

    @org.junit.jupiter.api.Test
    void addEmployeeToShift() {
    }

    @org.junit.jupiter.api.Test
    void removeEmployeeFromShift() {
    }
}