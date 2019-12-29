package cn.edu.upc.mp.controller;

import cn.edu.upc.mp.dao.PatientRepository;
import cn.edu.upc.mp.dao.RecordRepository;
import cn.edu.upc.mp.entity.Patient;
import cn.edu.upc.mp.entity.PatientSHOW;
import cn.edu.upc.mp.entity.Record;
import cn.edu.upc.mp.entity.info.Failure;
import cn.edu.upc.mp.entity.info.Success;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping(value = "/patient")
public class PatientController {
    @Autowired
    PatientRepository patientRepository;

    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    @ResponseBody
    public Object getPatientList() {
        try {
            List<Patient> patients = patientRepository.findAll();
            List<PatientSHOW> patientSHOWS = new ArrayList<PatientSHOW>();
            for (int i = 0; i < patients.size(); ++i) {
                patientSHOWS.add(new PatientSHOW(patients.get(i)));
            }
            return new Success(patientSHOWS);
        } catch (Exception e) {
            return new Failure("取得用户列表失败！");
        }
    }

    @RequestMapping(value = "/info.do", method = RequestMethod.GET)
    @ResponseBody
    public Object getPatientID(String name) {
        try {
            List<Patient> patients = patientRepository.getPatientsByName(name);
            List<Integer> patientsID = new ArrayList<Integer>();
            for (int i = 0; i < patients.size(); ++i) {
                patientsID.add(patients.get(i).getNumber());
            }
            return new Success(patientsID);
        } catch (Exception e) {
            return new Failure("根据姓名获取用户ID失败。");
        }
    }

    @RequestMapping(value = "/init.do", method = RequestMethod.POST)
    @ResponseBody
    public Object initialize(String name, String birthPlace, String gender, Integer age, String nation, Boolean isMarried, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date inPatientDate, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date recordDate) {
        try {
            Patient inPatient = new Patient(name, birthPlace, gender, age, nation, isMarried, inPatientDate, recordDate);
            patientRepository.save(inPatient);
            Success success = new Success("病人信息录入成功。");
            return success;
        } catch (Exception e) {
            Failure failure = new Failure("病人信息转化存储出错，请检查！");
            return failure;
        }
    }

    @RequestMapping(value = "/del.do", method = RequestMethod.POST)
    @ResponseBody
    public Object deletePatient(Integer patientNumber) {
        try{
            Patient patient = patientRepository.getPatientByNumber(patientNumber);
            patientRepository.delete(patient);
            return new Success("删除成功！");
        } catch (Exception e){
            return new Failure(7,"删除失败，请重试！");
        }
    }



}
