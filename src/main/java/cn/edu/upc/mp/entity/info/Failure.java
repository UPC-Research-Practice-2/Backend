package cn.edu.upc.mp.entity.info;

import lombok.Data;

@Data
public class Failure {
    public Integer status;
    public String message;

    public Failure(String message) {
        status = -1;
        this.message = message;
    }

    public Failure(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
