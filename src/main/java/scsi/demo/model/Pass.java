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
@Table(name= "sc_pass")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pass {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false, unique=true)
	private String sysid;
	private String userid;
	private String pass;
	private String cdate;
}
