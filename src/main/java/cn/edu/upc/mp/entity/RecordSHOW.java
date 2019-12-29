package cn.edu.upc.mp.entity;

import cn.edu.upc.mp.dao.PatientRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RecordSHOW {
    Integer number;
    String name;
    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createTime;


    public RecordSHOW(Record record, String name) {
        this.number = record.number;
        this.name = name;
        this.createTime = record.createTime;
    }
}
