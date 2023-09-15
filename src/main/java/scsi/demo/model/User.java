package scsi.demo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name= "sc_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable,UserDetails{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_sysid")
	private Integer usersysid;
	
	@Column(name = "user_id",insertable=true,updatable=true)
	private String userid;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "user_name")
	private String username;
	
	@Column(name = "fail_times")
	private int fail_times;
	
	@Column(name = "lock_flag")
	private String lock_flag;
	
	@Column(name = "lock_st")
	private String lock_st;
	
	@Column(name = "user_gr")
	private String user_gr;
	
	@ManyToMany(cascade = CascadeType.DETACH)
	@JsonIgnore
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_sysid",referencedColumnName = "user_sysid"), inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
	private Set<Role> roles =new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		boolean b=true;
		// TODO Auto-generated method stub
		if(getLock_flag().equals("0")) {
			b=true;
		}else {
			b=false;
		}
		return b;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
