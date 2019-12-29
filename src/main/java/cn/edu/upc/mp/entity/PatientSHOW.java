package cn.edu.upc.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class PatientSHOW {
    Integer number;
    String name;
    String birthPlace;
    String gender;
    Integer age;
    String nation;
    String isMarried;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    Date inPatientDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    Date recordDate;

    public PatientSHOW(Patient patient) {
        this.number = patient.number;
        this.name = patient.name;
        this.birthPlace = patient.birthPlace;
        this.gender = patient.gender;
        this.age = patient.age;
        this.nation = patient.nation;
        if (patient.isMarried) {
            this.isMarried = "已婚";
        } else {
            this.isMarried = "未婚";
        }
        this.inPatientDate = patient.inPatientDate;
        this.recordDate = patient.recordDate;
    }

}
