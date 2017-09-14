package org.dragberry.era.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;


@Entity
@Table(name = "CUSTOMER")
@NamedQueries({
	@NamedQuery(
			name = Customer.FIND_BY_USER_ACCOUNT_KEY_QUERY,
			query = "select c from UserAccount ua join ua.customer c where c.entityKey = :userAccountKey")
})
@TableGenerator(
		name = "CUSTOMER_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "CUSTOMER_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Customer extends BaseEntity {
	
	private static final long serialVersionUID = -2951173904850762419L;
	
	public static final String FIND_BY_USER_ACCOUNT_KEY_QUERY = "Customer.FindByUserAccountKey";
	
	@Id
	@Column(name = "CUSTOMER_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CUSTOMER_GEN")
	private Long entityKey;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTITUTION_KEY", referencedColumnName = "EDUCATION_INSTITUTION_KEY")
	private EducationInstitution institution;
	
	@OneToMany(mappedBy = "customer")
	private List<UserAccount> userAccounts;

	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;

	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public EducationInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(EducationInstitution institution) {
		this.institution = institution;
	}

	public List<UserAccount> getUserAccounts() {
		return userAccounts;
	}

	public void setUserAccounts(List<UserAccount> userAccounts) {
		this.userAccounts = userAccounts;
	}
	
	

}
