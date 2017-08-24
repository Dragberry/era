package org.dragberry.era.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "USER_ACCOUNT")
@NamedQueries({
	@NamedQuery(
			name = UserAccount.FIND_BY_USERNAME_QUERY,
			query = "select ua from UserAccount ua where ua.username = :username")
})
@TableGenerator(
		name = "USER_ACCOUNT_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "USER_ACCOUNT_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class UserAccount extends AbstractEntity {

	private static final long serialVersionUID = -2128713516838952908L;

	public static final String FIND_BY_USERNAME_QUERY = "Customer.findByCustomerNAme";

	@Id
	@Column(name = "USER_ACCOUNT_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_ACCOUNT_GEN")
	private Long entityKey;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "BIRTHDATE")
	private LocalDate birtdate;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "USER_ACCOUNT_ROLE", 
        joinColumns = @JoinColumn(name = "USER_ACCOUNT_KEY", referencedColumnName = "USER_ACCOUNT_KEY"), 
        inverseJoinColumns = @JoinColumn(name = "ROLE_KEY", referencedColumnName = "ROLE_KEY"))
	private Set<Role> roles = new HashSet<Role>();
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public LocalDate getBirtdate() {
		return birtdate;
	}

	public void setBirtdate(LocalDate birtdate) {
		this.birtdate = birtdate;
	}

}