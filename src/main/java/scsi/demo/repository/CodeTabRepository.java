package scsi.demo.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import scsi.demo.model.CodeTab;
import scsi.demo.model.Nodes;

public interface CodeTabRepository extends CrudRepository<CodeTab, String>{
	
	CodeTab findByseq(String seq);
	
	@Query(value="select * from sc_code_tab where code_id ='2' and name_zh != 'TWN' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllCountry();
	
	@Query(value="select * from sc_code_tab where code_id ='2' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllCountryWT();

	@Query(value="select * from sc_code_tab where code_id ='3' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllNType();
	
	@Query(value="select * from sc_code_tab where code_id ='4' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllOwner();
	
	@Query(value="select * from sc_code_tab where code_id ='4' and name_zh =?1 and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllOwnergr(String gr);
	
	@Query(value="select * from sc_code_tab where code_id ='5' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllLType();
	
	@Query(value="select * from sc_code_tab where code_id ='6' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllLFail();
	
	@Query(value="select * from sc_code_tab where code_id ='7' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllNFail();
	
	@Query(value="select * from sc_code_tab where code_id ='8' and del_mark is null order by seq",nativeQuery = true)
    List<CodeTab> findAllProto();
	
	@Modifying
	@Transactional
	@Query(value = "insert into sc_code_tab(code_id,name_zh,name_desc_zh)value(?1,?2,?3)",nativeQuery = true)
	void newedit(@Param(value = "code_id")Integer code_id,@Param(value = "name_zh")String name_zh,@Param(value = "name_desc_zh")String name_desc_zh);
	    
	@Modifying
    @Transactional
    @Query(value = "update sc_code_tab set name_zh=?3,name_desc_zh=?4 where seq=?1 and code_id=?2" ,nativeQuery = true)
    void addedit(@Param(value = "seq")String seq,@Param(value = "code_id")Integer code_id,@Param(value = "name_zh")String name_zh,@Param(value = "name_desc_zh")String name_desc_zh);

	@Modifying
    @Transactional
    @Query(value = "update sc_code_tab set del_mark = '1' where seq = ?1" ,nativeQuery = true)
    void deledit(@Param(value = "seq")String seq);
	
	@Modifying
	@Transactional
	@Query(value = "insert into sc_logs(user_id,totime,action,detail)value(?1,?2,?3,?4)",nativeQuery = true)
	void savelog(@Param(value = "user_id")Integer user_id,@Param(value = "totime")String totime,@Param(value = "action")String action,@Param(value = "detail")String detail);
	  
	
	public default String todaytime2() throws IOException, SQLException
	{
		Calendar rightNow = Calendar.getInstance();
		String nowtime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(rightNow.getTime());
		return nowtime ;
	}
	
	public default String todaytime_hhmm() throws IOException, SQLException
	{
		Calendar rightNow = Calendar.getInstance();
		String nowtime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rightNow.getTime());
		return nowtime ;
	}
}
