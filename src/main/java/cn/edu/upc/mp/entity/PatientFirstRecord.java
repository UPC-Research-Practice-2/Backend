package cn.edu.upc.mp.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "patient_first")
public class PatientFirstRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer keyNumber;
    Integer number;
    String zhusu;
    String current;
    String history;
    String personalHistory;
    String marriageHistory;
    String familyHistory;

    public PatientFirstRecord(Integer number) {
        this.number = number;
    }

    public PatientFirstRecord() {
    }
}
