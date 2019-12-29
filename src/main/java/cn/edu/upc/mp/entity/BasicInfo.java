package cn.edu.upc.mp.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "basicinfo")
public class BasicInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer number;

    Integer patientNumber;
    // 自述
    String description;
    // 既往史
    String history;
    // 个人情况
    String personalSituation;
    // 家庭情况
    String familySituation;
    // 家族情况
    String populationSituation;
}
