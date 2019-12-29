package cn.edu.upc.mp.service;

import cn.edu.upc.mp.dao.PatientRepository;
import cn.edu.upc.mp.dao.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class ValidCheck {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    RecordRepository recordRepository;

    public Boolean patientCheck(Integer patientID) {
        try {
            if (patientRepository.getPatientByNumber(patientID) == null) {
                throw new NullPointerException("找不到病人编号");
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public Boolean recordCheck(Integer recordID) {
        try {
            if (recordRepository.getRecordByNumber(recordID) == null) {
                throw new NullPointerException("找不到记录编号");
            }
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
