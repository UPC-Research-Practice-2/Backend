package cn.edu.upc.mp.controller;

import cn.edu.upc.mp.dao.PFRRepository;
import cn.edu.upc.mp.dao.PatientRepository;
import cn.edu.upc.mp.entity.PatientFirstRecord;
import cn.edu.upc.mp.entity.info.Failure;
import cn.edu.upc.mp.entity.info.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@CrossOrigin
@Controller
@RequestMapping(value = "/pfr")
public class PFRController {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    PFRRepository pfrRepository;

    @RequestMapping(value = "/detail.do", method = RequestMethod.GET)
    @ResponseBody
    public Object getPFR(Integer patientNumber) {
        try {
            PatientFirstRecord pfr = pfrRepository.getPatientFirstRecordByNumber(patientNumber);
            return new Success(pfr);
        } catch (Exception e) {
            return new Failure("根据用户ID获取初诊记录失败。");
        }
    }

    @RequestMapping(value = "/pfr.do", method = RequestMethod.POST)
    @ResponseBody
    public Object setZhusu(Integer patientNumber, String operation, String data) {
        try {
            PatientFirstRecord patientFirstRecord = pfrRepository.getPatientFirstRecordByNumber(patientNumber);
            if ("zhusu".equals(operation)) {
                patientFirstRecord.setZhusu(data);
            } else if ("current".equals(operation)) {
                patientFirstRecord.setCurrent(data);
            } else if ("history".equals(operation)) {
                patientFirstRecord.setHistory(data);
            } else if ("personalHistory".equals(operation)) {
                patientFirstRecord.setPersonalHistory(data);
            } else if ("marriageHistory".equals(operation)) {
                patientFirstRecord.setMarriageHistory(data);
            } else if ("familyHistory".equals(operation)) {
                patientFirstRecord.setFamilyHistory(data);
            } else {
                return new Failure(10, "操作码不正确！");
            }
            pfrRepository.save(patientFirstRecord);
            return new Success("初诊情况修改成功！");
        } catch (Exception e) {
            return new Failure(9, "根据病人编号加载初诊记录失败，请核实！");
        }
    }

}
