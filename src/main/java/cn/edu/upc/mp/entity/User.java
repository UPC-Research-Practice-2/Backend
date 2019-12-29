package cn.edu.upc.mp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * User
 *
 * @author liumengxiao
 * @date 2019/03/12
 */


@Entity
@Data
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer userID;
    private String password;
    private String email;
    private String phoneNumber;

    public User() {
    }


    public User(String password, String email, String phoneNumber) {
        if (getPasswordHash(password) == null) {
            throw new NullPointerException();
        }
        this.password = getPasswordHash(password);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static String getPasswordHash(String password) {
        try {
            if (password != null) {
                Integer passwordHash = password.hashCode();
                return passwordHash.toString();
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            System.err.println("密码进行HASH运算时候遇到问题，请检查。");
            System.err.println(e.toString());
        }
        return null;
    }

}
