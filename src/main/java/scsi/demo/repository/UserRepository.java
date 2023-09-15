package scsi.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scsi.demo.model.User;
@Repository
public interface UserRepository extends CrudRepository<User,String>{

	//Optional<User> findByUserid(String userid);
    User findByUsersysid(Integer sysid);
    
    @Query(value = "select * from sc_users where del_mark is null", nativeQuery = true)
    List<User> findAll();
    
    @Query(value = "select * from sc_users where user_gr =?1 and del_mark is null", nativeQuery = true)
    List<User> findAllgr(String gr);
    
    @Query(value = "select * from sc_users where user_sysid =?1 and del_mark is null", nativeQuery = true)
    User findBySysid(String sysid);
    
    @Query(value = "select * from sc_users where user_id =?1 and del_mark is null", nativeQuery = true)
    User findByUser_id(String userid);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_users set del_mark = '1' where user_sysid = ?1" ,nativeQuery = true)
    void deluser(Integer sysid);
    
    @Query(value="select count(*) from sc_users where user_id =?1 and del_mark is null",nativeQuery = true)
    Integer existsUser_id(@Param("user_id") String user_id);

    @Modifying
    @Transactional
    @Query(value = "update sc_users set user_id=?2 ,user_name=?3,user_gr=?4 where user_sysid=?1" ,nativeQuery = true)
    void adduser(@Param(value = "sysid")String sysid,@Param(value = "user_id")String user_id,@Param(value = "user_name")String user_name,@Param(value = "user_gr")String user_gr);
    
    @Modifying
    @Transactional
    @Query(value = "insert into sc_users(user_id,password,user_name,user_gr)value(?1,?2,?3,?4)",nativeQuery = true)
    void newuser(@Param(value = "user_id")String user_id,@Param(value = "password")String password,@Param(value = "user_name")String user_name,@Param(value = "user_gr")String user_gr);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_users set password=?2 where user_sysid=?1" ,nativeQuery = true)
    void uppass(@Param(value = "sysid")Integer sysid,@Param(value = "password")String password);
    
    @Modifying
    @Transactional
    @Query(value = "insert into sc_logs(sysid,user_id,totime,action,detail)value(?1,?2,?3,?4,?5)",nativeQuery = true)
    void logs(String sysid,String user_id,String totime,String action,String detail);
    
    @Query(value ="select user_gr from sc_users where user_id =?1 and del_mark is null" ,nativeQuery=true)
    String getgr(@Param(value = "user_id")String user_id);
    
    
    @Modifying
    @Transactional
    @Query(value = "update sc_users set fail_times =?2 where user_id =?1 and del_mark is null" ,nativeQuery = true)
    void updateFailedAttempts(String user_id,int failtimes );
    
    @Modifying
    @Transactional
    @Query(value = "update sc_users set lock_st =?2 where user_id =?1 and del_mark is null" ,nativeQuery = true)
    void setlocktime(String user_id,String lock_st);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_users set lock_flag =?2 , lock_st ='' where user_id =?1 and del_mark is null" ,nativeQuery = true)
    void setlockflag(String user_id,int lock);
    
    @Query(value = "SELECT u.fail_times FROM sc_users u WHERE u.user_id = ?1 and del_mark is null", nativeQuery = true)
    Integer getfailt(String user_id);
    
    @Query(value = "SELECT u.lock_flag FROM sc_users u WHERE u.user_id = ?1 and del_mark is null", nativeQuery = true)
    Integer getlflag(String user_id);
    
    @Query(value = "SELECT u.lock_st FROM sc_users u WHERE u.user_id = ?1 and del_mark is null", nativeQuery = true)
    String getlockst(String user_id);
}