package tests;

import Business.Controllers.StaffController;
import Business.TypeOfEmployee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StaffControllerTest {

    @BeforeEach
    void setUp() {
        StaffController staffController = new StaffController(TypeOfEmployee.HRManager);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addEmployee() {
    }

    @Test
    void removeEmployee() {
    }

    @Test
    void editFirstName() {
    }

    @Test
    void editLastName() {
    }

    @Test
    void editID() {
    }

    @Test
    void editBankAccountNumber() {
    }

    @Test
    void editSalary() {
    }

    @Test
    void editEmpConditions() {
    }

    @Test
    void addSkill() {
    }

    @Test
    void removeSkill() {
    }

    @Test
    void addAvailableShift() {
    }

    @Test
    void removeAvailableShift() {
    }
}