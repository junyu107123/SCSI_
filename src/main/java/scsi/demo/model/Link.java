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
@Table(name= "sc_links")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Link {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false, unique=true)
	private String sysid;
	
	private String link_id;
	private String link_name;
	private String link_owner;
	private String link_nodeA;
	private String link_nodeB;
	private String max_bandwidth;
	private String using_bandwidth;
	private String rest_bandwidth;
	private String pathfile;
	private String pathdata;
	private String link_type;
	private String link_protocol;
	@Column(columnDefinition = "INT(11) default '0'")
	private String failure;
	private String failure_bandwidth;
	@Column(columnDefinition = "FLOAT default '1'")
	private Float w_length;
	@Column(columnDefinition = "FLOAT default '1'")
	private Float w_latency;
	@Column(columnDefinition = "FLOAT default '1'")
	private Float w_weight;
	private String del_mark;
	
}
