package Business.Objects;

import Business.Misc.Pair;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;
import Data.DTO.DriverDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Driver extends Employee implements persistentObject<DriverDTO> {
    private int License;

    public Driver(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills, int license) throws Exception {
        super(firstName, lastName, id, bankAccountNumber, salary, empConditions, startWorkingDate, skills);
        setLicense(license);
    }

    public Driver(DriverDTO dto) throws Exception {
        super(dto);
        setLicense(dto.License);
    }

    public void setLicense(int license) {
        License = license;
    }

    public int getLicense() {
        return License;
    }

    @Override
    public String toString() {
        return super.toString()+
                ", License=" + License +
                '}';
    }

    @Override
    public DriverDTO toDTO() {
        ArrayList<String> stringSkills = new ArrayList<>();
        ArrayList<Pair<Date, String>> stringShifts = new ArrayList<>();
        for (TypeOfEmployee type : getSkills())
            stringSkills.add(type.toString());
        for (Pair<Date, TypeOfShift> pair : getAvailableShifts()) {
            stringShifts.add(new Pair<Date, String>(pair.first, pair.second.toString()));
        }
        return new DriverDTO(getFirstName(), getLastName(), getId(), getBankAccountNumber(), getSalary(), getEmpConditions(), getStartWorkingDate(), getLicense(), stringSkills, stringShifts);
    }
}
