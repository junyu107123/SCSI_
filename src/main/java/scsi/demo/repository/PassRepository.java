package scsi.demo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scsi.demo.model.Pass;

@Repository
public interface PassRepository extends CrudRepository<Pass,String>{
	
	@Modifying
    @Transactional
    @Query(value = "insert into sc_pass(userid,pass,cdate)value(?1,?2,?3)",nativeQuery = true)
    void savepass(String userid,String pass,String cdate);

	@Query(value="select count(*) from (select * from sc_pass order by sysid desc limit 3)as a where pass = ?1 order by sysid desc",nativeQuery = true)
    Integer check3pass(String pass);

}
