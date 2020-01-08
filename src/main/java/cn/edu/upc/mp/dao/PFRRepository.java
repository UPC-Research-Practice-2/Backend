package cn.edu.upc.mp.dao;

import cn.edu.upc.mp.entity.PatientFirstRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PFRRepository extends CrudRepository<PatientFirstRecord, Integer> {
    //根据病人编号读取初诊记录
    public PatientFirstRecord getPatientFirstRecordByNumber(Integer patientNumber);
}
