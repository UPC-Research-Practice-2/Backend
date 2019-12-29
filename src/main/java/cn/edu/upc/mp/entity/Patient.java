package cn.edu.upc.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer number;
    String name;
    String birthPlace;
    String gender;
    Integer age;
    String nation;
    Boolean isMarried;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    Date inPatientDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")
    Date recordDate;

    private Patient() {

    }

    public Patient(String name, String birthPlace, String gender, Integer age, String nation, Boolean isMarried, Date inPatientDate, Date recordDate) {
        this.name = name;
        this.birthPlace = birthPlace;
        this.gender = gender;
        this.age = age;
        this.nation = nation;
        this.isMarried = isMarried;
        this.inPatientDate = inPatientDate;
        this.recordDate = recordDate;
    }
}
