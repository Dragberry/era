package org.dragberry.era.common;

import java.io.Serializable;

public class AbstractCRUDTO implements Serializable {
	
	private static final long serialVersionUID = -1563097091979854139L;

	private Long id;
	
	private Long customerId;
	
	private Long userAccountId;
	
	private Long version;
	
	private Boolean ignoreWarnings;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Boolean getIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setIgnoreWarnings(Boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}
	
}
