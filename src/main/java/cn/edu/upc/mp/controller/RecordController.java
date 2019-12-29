package cn.edu.upc.mp.controller;

import cn.edu.upc.mp.dao.PatientRepository;
import cn.edu.upc.mp.dao.RecordRepository;
import cn.edu.upc.mp.entity.Record;
import cn.edu.upc.mp.entity.RecordSHOW;
import cn.edu.upc.mp.entity.info.Failure;
import cn.edu.upc.mp.entity.info.Success;
import cn.edu.upc.mp.service.ValidCheck;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/record")
public class RecordController {
    @Autowired
    RecordRepository recordRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ValidCheck validCheck;

    @RequestMapping(value = "/newRecord.do", method = RequestMethod.POST)
    @ResponseBody
    public Object newRecord(Integer patientNumber, String description, String comparison, String recoverySituation) {
        try {
            Date currentTime = new Date();
            Record record = new Record(patientNumber, currentTime, description, comparison, recoverySituation);
            recordRepository.save(record);
            Success success = new Success("诊疗记录录入成功。");
            return success;
        } catch (Exception e) {
            Failure failure = new Failure("病人诊疗记录保存出错，请检查！");
            return failure;
        }
    }

    @RequestMapping(value = "/list.do", method = RequestMethod.GET)
    @ResponseBody
    public Object listRecords() {
        try {
            List<Record> records = recordRepository.findAll();
            List<RecordSHOW> recordSHOWS = new ArrayList<>();
            //Test
            for (int i = 0; i < records.size(); ++i) {
                Integer patientNumber = records.get(i).getPatientNumber();
                String name = patientRepository.getPatientByNumber(patientNumber).getName();
                recordSHOWS.add(new RecordSHOW(records.get(i), name));
            }
            return new Success(recordSHOWS);
        } catch (Exception e) {
            return new Failure("查询病情信息出错，请稍后重试！");
        }
    }

    @RequestMapping(value = "/recordDetail.do", method = RequestMethod.GET)
    @ResponseBody
    public Object getDetail(String parameter, Integer number) {
        if (validCheck.recordCheck(number)) {
            if (!parameter.equals("description") && !parameter.equals("comparison") && !parameter.equals("recoverySituation")) {
                return new Failure("传入参数异常，无法获悉所需信息类型！");
            } else {
                try {
                    Record record = recordRepository.getRecordByNumber(number);
                    if (parameter.equals("description")) {
                        return new Success(record.getDescription());
                    } else if (parameter.equals("comparison")) {
                        return new Success(record.getComparison());
                    } else {
                        return new Success(record.getRecoverySituation());
                    }
                } catch (Exception e) {
                    return new Failure("获取信息异常，请重新核对输入参数！");
                }
            }
        } else {
            return new Failure("病人编号或记录单号不存在，请重新核实！");
        }
    }

    @RequestMapping(value = "/record.do", method = RequestMethod.POST)
    @ResponseBody
    public Object modifyRecord(String parameter, Integer number, String data) {
        if (validCheck.recordCheck(number)) {
            if (!parameter.equals("description") && !parameter.equals("comparison") && !parameter.equals("recoverySituation")) {
                return new Failure("传入参数异常，无法获悉所需信息类型！");
            } else {
                try {
                    Record record = recordRepository.getRecordByNumber(number);
                    if (parameter.equals("description")) {
                        record.setDescription(data);
                        recordRepository.save(record);
                        return new Success("病情描述修改成功！");
                    } else if (parameter.equals("comparison")) {
                        record.setComparison(data);
                        recordRepository.save(record);
                        return new Success("病情对比修改成功！");
                    } else {
                        record.setRecoverySituation(data);
                        recordRepository.save(record);
                        return new Success("恢复情况修改成功！");
                    }
                } catch (Exception e) {
                    return new Failure("获取信息异常，请重新核对输入参数！");
                }
            }
        } else {
            return new Failure("病人编号或记录单号不存在，请重新核实！");
        }
    }
}
