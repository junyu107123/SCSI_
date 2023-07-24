package scsi.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import scsi.demo.model.CodeTab;
import scsi.demo.model.Nodes;
@Repository
public interface NodeRepository extends CrudRepository<Nodes,String>{

	//Optional<User> findByUserid(String userid);
    Nodes findBysysid(String sysid);
    
    @Query(value = "select * from sc_nodes where del_mark is null", nativeQuery = true)
    List<Nodes> findAll();
    
    @Query(value = "select * from sc_nodes where owner =?1 and del_mark is null", nativeQuery = true)
    List<Nodes> findAllgr(@Param(value = "gr")String gr);
    
    @Query(value = "select node_name from sc_nodes where del_mark is null", nativeQuery = true)
    List<String> findAllName();
    
    @Query(value = "select * from sc_nodes where del_mark is null group by owner", nativeQuery = true)
    List<Nodes> findAllOwner();
    
    @Query(value = "select * from sc_nodes where del_mark is null group by node_type", nativeQuery = true)
    List<Nodes> findAllNType();
    
    @Query(value = "select * from sc_nodes where del_mark is null group by node_failure", nativeQuery = true)
    List<Nodes> findAllFail();

    @Modifying
    @Transactional
    @Query(value = "update sc_nodes set del_mark = '1' where sysid = ?1" ,nativeQuery = true)
    void delnode(String sysid);
    
    @Query(value = "select * from sc_nodes where sysid = ?1 and del_mark is null",nativeQuery = true)
    List<Nodes> findBySysid(String sysid);
    
    @Modifying
    @Transactional
    @Query(value = "update sc_nodes set node_id=?2 ,node_name=?3,owner=?4,address=?5,lon=?6,lat=?7,node_type=?8,node_country=?9,pos=?10,node_failure=?11 where sysid=?1" ,nativeQuery = true)
    void addnode(@Param(value = "sysid")String sysid,@Param(value = "node_id")String node_id,@Param(value = "node_name")String node_name,@Param(value = "owner")String owner,@Param(value = "address")String address,@Param(value = "lon")String lon,@Param(value = "lat")String lat,@Param(value = "node_type")String node_type,@Param(value = "node_country")String node_country,@Param(value = "pos")String pos,@Param(value = "node_failure")String node_failure);
    
    @Query(value = "select * from sc_nodes where (node_id like %?1% or node_name like %?1% or owner like %?1%)and del_mark is null", nativeQuery = true)
    List<Nodes> findNodeKey(String gk);
    
    @Query(value = "select * from sc_nodes where (node_id like %?1% or node_name like %?1%)and owner =?2 and del_mark is null", nativeQuery = true)
    List<Nodes> findNodeKeygr(String gk,String gr);
    
    @Query(value="select count(*) from sc_nodes where node_id =?1 and del_mark is null",nativeQuery = true)
    Integer existsNode_id(@Param("node_id") String node_id);
    
    @Query(value="select count(*) from sc_nodes where node_name =?1 and del_mark is null",nativeQuery = true)
    Integer existsNode_name(@Param("node_name") String node_name);

    @Query(value="select * from sc_nodes where node_type ='ISLAND' and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findIsland();
    
    @Query(value="select * from sc_nodes where node_type ='ISLAND' and owner =?1 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findIslandgr(String gr);
    
    @Query(value="select * from sc_nodes where node_type ='FOREIGN' and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findForeign();
    
    @Query(value="select * from sc_nodes where node_type ='FOREIGN' and owner =?1 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findForeigngr(String gr);
    
    @Query(value="select * from sc_nodes where node_type ='INTR' and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findIntr();
    
    @Query(value="select * from sc_nodes where node_type ='INTR' and owner =?1 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> findIntrgr(String gr);
    
    @Query(value="select * from sc_nodes where (node_type ='INTL' or node_type ='FOREIGN') and node_name != ?1 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getForeign(String rt);
    
    @Query(value="select * from sc_nodes where (node_type ='INTL' or node_type ='FOREIGN') and node_name != ?1 and owner =?2 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getForeigngr(String rt,String gr);
    
    @Query(value="select * from sc_nodes where (node_type ='INTR' and node_name != ?1) and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getIsland(String rt);
    
    @Query(value="select * from sc_nodes where (node_type ='INTR' and node_name != ?1) and owner =?2 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getIslandgr(String rt,String gr);
    
    @Query(value="select * from sc_nodes where (node_type ='INTL' or node_type='ISLAND') and node_name != ?1 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getIntl(String rt);
    
    @Query(value="select * from sc_nodes where (node_type ='INTL' or node_type='ISLAND') and node_name != ?1 and owner = ?2 and del_mark is null order by sysid",nativeQuery = true)
    List<Nodes> getIntlgr(String rt,String gr);

}