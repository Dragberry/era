package org.dragberry.era.common.specialty;

import java.io.Serializable;

public class SpecialtySimpleTO implements Serializable {
	
	private static final long serialVersionUID = -5500253439264001707L;

	private Long id;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
