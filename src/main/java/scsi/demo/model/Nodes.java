package scsi.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name= "sc_nodes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Nodes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false, unique=true)
	private String sysid;
	private String node_id;
	private String node_name;
	private String owner;
	private String address;
	private String lon;
	private String lat;
	private String node_type;
	private String node_country;
	@Column(columnDefinition = "INT(11) default '0'")
	private String pos;
	@Column(columnDefinition = "INT(11) default '0'")
	private String node_failure;
	private String del_mark;
}
