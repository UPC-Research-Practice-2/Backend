package cn.edu.upc.mp.dao;

import cn.edu.upc.mp.entity.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    // 通过病人的编号来得到病人Entity
    Patient getPatientByNumber(Integer number);

    // 通过病人的名字来得到病人的Entities
    List<Patient> getPatientsByName(String name);

    // 取得所有的病人信息
    List<Patient> findAll();

}
