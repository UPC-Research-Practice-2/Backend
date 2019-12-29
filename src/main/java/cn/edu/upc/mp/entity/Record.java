package cn.edu.upc.mp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer number;
    Integer patientNumber;
    // 创建时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    Date createTime;
    // 病人当前阶段自述
    String description;
    // 同上阶段的比较
    String comparison;
    // 恢复情况
    String recoverySituation;

    private Record() {

    }

    public Record(Integer patientNumber, Date createTime, String description, String comparison, String recoverySituation) {
        this.patientNumber = patientNumber;
        this.createTime = createTime;
        this.description = description;
        this.comparison = comparison;
        this.recoverySituation = recoverySituation;
    }
}
