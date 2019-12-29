package cn.edu.upc.mp.dao;

import cn.edu.upc.mp.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    /**
     * 根据用户ID返回用户实体
     *
     * @param userId 用户ID
     * @return 用户实体
     */
    User getUserByUserID(Integer userId);

    /**
     * 返回所有用户
     *
     * @return 所有用户
     */
    @Override
    List<User> findAll();

    /**
     * 根据用户的提交的手机号返回用户实体
     *
     * @param phoneNumber 用户手机号
     * @return 用户实体
     */
    User getUserByPhoneNumber(String phoneNumber);


    /**
     * 通过电子邮箱返回用户实体
     *
     * @param email 电子邮箱
     * @return 用户实体
     */
    User getUserByEmail(String email);

//    /**
//     * 分页展示用户，每页显示10个用户
//     * 提交页数，返回这一页对应的10个用户
//     *
//     * @param recondNumber 起始记录数
//     * @return 这一页对应的用户
//     */
//    @Modifying
//    @Query(value = "select * from user order by userid asc limit ?1,10;", nativeQuery = true)
//    List<User> findUsersByPage(Integer recondNumber);

    /**
     * 统计总的用户数
     *
     * @return 用户总数
     */
    @Transactional
    @Query(value = "select count(*) from user", nativeQuery = true)
    Integer countUsers();


}
