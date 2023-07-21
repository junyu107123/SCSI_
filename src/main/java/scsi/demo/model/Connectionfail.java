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
@Table(name= "sc_connectionfail")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Connectionfail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sysid",nullable=false, unique=true)
	private String sysid;
	
	private String connectionid;
	private String failid;
	private String faildatetime;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failvoice;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failinternet;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing;
	private String faildesc;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_circuit_line;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_circuit_bw;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_ethernet_line;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_ethrtnet_bw;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_vpn_line;
	@Column(columnDefinition = "varchar(10) default 'none'")
	private String failleasing_vpn_bw;
}
