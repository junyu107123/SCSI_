package scsi.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name= "sc_code_tab")
@Entity
public class CodeTab {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "seq",nullable=false, unique=true)
	private String seq;
	
	private Integer code_id;
	private String name_zh;
	private String name_desc_zh;
	private String name_ch;
	private String name_desc_ch;
	private String name_en;
	private String name_desc_en;
	private String sort;
	private String small_icon;
}
