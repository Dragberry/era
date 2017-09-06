package org.dragberry.era.common.useraccount;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserAccountDeleteTO implements Serializable{
	
	private static final long serialVersionUID = -8578635700576719888L;

	private Long id;
	
	private Long customerId;
	
	private String username;
	
	@JsonIgnore
	private Long userAccountId;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

}
