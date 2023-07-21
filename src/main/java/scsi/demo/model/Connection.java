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
@Table(name= "sc_connection")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sysid",nullable=false, unique=true)
	private String sysid;
	
	private String connection_id;
	private String connection_A;
	private String connection_B;
	private String del_mark;
	@Column(columnDefinition = "varchar(45) default '0'")
	private String leasing_circuit_bw;
	private String notes;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String level;
	@Column(columnDefinition = "varchar(10) default '#0acf24'")
	private String linecolor;
	private String InterVoice;
	private String Internet;
	private String InterLeasing;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String Leasing_circuit_line;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String Leasing_ethernet_line;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String Leasing_ethernet_bw;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String Leasing_vpn_line;
	@Column(columnDefinition = "varchar(10) default '0'")
	private String Leasing_vpn_bw;
}
