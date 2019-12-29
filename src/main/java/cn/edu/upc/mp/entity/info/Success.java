package cn.edu.upc.mp.entity.info;

import lombok.Data;

@Data
public class Success {
    public Integer status;
    public Object data;

    public Success(Object data) {
        status = 0;
        this.data = data;
    }

    public Success() {
        status = 0;
        data = null;
    }
}
