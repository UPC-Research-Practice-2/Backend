package cn.edu.upc.mp.dao;

import cn.edu.upc.mp.entity.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends CrudRepository<Record, Integer> {
    // 根据病人编号和病例变化得到一条记录
    public Record getRecordByNumberAndPatientNumber(Integer number, Integer patientNumber);
    // 根据记录单号查找
    public Record getRecordByNumber(Integer number);
    // 返回所有的记录信息
    public List<Record> findAll();
}
