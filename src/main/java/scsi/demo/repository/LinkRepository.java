package scsi.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scsi.demo.model.Link;
import scsi.demo.model.Nodes;
@Repository
public interface LinkRepository extends CrudRepository<Link,String>{

	//Optional<User> findByUserid(String userid);
    Link findBysysid(String sysid);
    
    @Query(value = "select * from sc_links where del_mark is null", nativeQuery = true)
    List<Link> findAll();
    
    @Query(value = "select * from sc_links where link_owner =?1 and del_mark is null", nativeQuery = true)
    List<Link> findAllgr(String gr);
    
    @Query(value = "select * from sc_links where del_mark is null group by link_owner", nativeQuery = true)
    List<Link> findAllOwner();
    
    @Query(value = "select * from sc_links where del_mark is null group by link_type", nativeQuery = true)
    List<Link> findAllNType();
    
    @Query(value = "select * from sc_nodes where del_mark is null", nativeQuery = true)
    List<Nodes> findAllNodeA();
    
    @Query(value = "select * from sc_links where del_mark is null group by failure", nativeQuery = true)
    List<Link> findAllFail();

    @Modifying
    @Transactional
    @Query(value = "update sc_links set del_mark = '1' where sysid = ?1" ,nativeQuery = true)
    void dellink(String sysid);
    
    @Query(value = "select * from sc_links where sysid = ?1 and del_mark is null",nativeQuery = true)
    List<Link> findBySysid(String sysid);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_links set link_id=?2 ,link_name=?3,link_type=?4,link_owner=?5,link_nodeA=?6,link_nodeB=?7,max_bandwidth=?8,using_bandwidth=?9,rest_bandwidth=?10,failure=?11,failure_bandwidth=?12,w_length=?13,w_latency=?14,w_weight=?15 where sysid=?1" ,nativeQuery = true)
    void addlink(@Param(value = "sysid")String sysid,@Param(value = "link_id")String link_id,@Param(value = "link_name")String link_name,@Param(value = "link_type")String link_type,@Param(value = "link_owner")String link_owner,@Param(value = "link_nodeA")String link_nodeA,@Param(value = "link_nodeB")String link_nodeB,@Param(value = "max_bandwidth")String max_bandwidth,@Param(value = "using_bandwidth")String using_bandwidth,@Param(value = "rest_bandwidth")String rest_bandwidth,@Param(value = "failure")String failure,@Param(value = "failure_bandwidth")String failure_bandwidth,@Param(value = "w_length")Float w_length,@Param(value = "w_latency")Float w_latency,@Param(value = "w_weight")Float w_weight);
    
    @Query(value = "select * from sc_links where (link_id like %?1% or link_name like %?1% or link_owner like %?1%)and del_mark is null", nativeQuery = true)
    List<Link> findLinkKey(String gk);
    
    @Query(value = "select * from sc_links where (link_id like %?1% or link_name like %?1%)and link_owner =?2 and del_mark is null", nativeQuery = true)
    List<Link> findLinkKeygr(String gk,String gr);
    
    @Query(value="select count(*) from sc_links where link_id =?1 and del_mark is null",nativeQuery = true)
    Integer existsLink_id(@Param("link_id") String link_id);
    
    @Query(value="select count(*) from sc_links where link_name =?1 and del_mark is null",nativeQuery = true)
    Integer existsLink_name(@Param("link_name") String link_name);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_links set failure ='0',failure_bandwidth ='0' where sysid >0 and del_mark is null" ,nativeQuery = true)
    void resetL();
}