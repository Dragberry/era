package org.dragberry.era.domain;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.SpecialtyStatusConverter;

@Entity
@Table(name = "SPECIALTY")
@TableGenerator(
		name = "SPECIALTY_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "SPECIALTY_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Specialty extends AbstractEntity {

	private static final long serialVersionUID = 6491824262933007676L;
	
	private static final String UNKNOWN_VALUE_MSG = "Unknown Specialty status value: {0}!";
	private static final String NPE_MSG = "Specialty status cannot be null!";
	
	public static enum Status {
		ACTIVE('A'), INACTIVE('I');
		
		public final char value;
		
		private Status(char value) {
			this.value = value;
		}
		
		public static Status valueOf(Character value) {
			if (value == null) {
				throw new NullPointerException(NPE_MSG);
			}
			for (Status status : Status.values()) {
				if (value.equals(status.value)) {
					return status;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, value));
		}
	}
	
	@Id
	@Column(name = "SPECIALTY_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SPECIALTY_GEN")
	private Long entityKey;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "STATUS")
	@Convert(converter = SpecialtyStatusConverter.class)
	private Status STATUS;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
