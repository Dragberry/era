package org.dragberry.era.domain;

import java.text.MessageFormat;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.RegistrationTypeConverter;

@Entity
@Table(name = "REGISTRATION")
@TableGenerator(
		name = "REGISTRATION_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REGISTRATION_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Registration extends AbstractEntity {

	private static final long serialVersionUID = 8173371976074070183L;

	private static final String UNKNOWN_VALUE_MSG = "Unknown Registration type value: {0}!";
	private static final String NPE_MSG = "Registration type cannot be null!";
	
	public static enum Type {
		BUDGET('B'), PAID('P');
		
		public final char value;
		
		private Type(char value) {
			this.value = value;
		}
		
		public static Type valueOf(Character value) {
			if (value == null) {
				throw new NullPointerException(NPE_MSG);
			}
			for (Type type : Type.values()) {
				if (value.equals(type.value)) {
					return type;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, value));
		}
	}
	
	@Id
	@Column(name = "REGISTRATION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REGISTRATION_GEN")
	private Long entityKey;
	
	@Column(name = "REGISTRATION_TYPE")
	@Convert(converter = RegistrationTypeConverter.class)
	private Type type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "ENROLLEE_KEY")
	private Enrollee enrollee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_PERIOD_KEY", referencedColumnName = "REGISTRATION_PERIOD_KEY")
	private RegistrationPeriod registrationPeriod;
	
	@Column(name = "REGISTRATION_DATE")
	private LocalDate registrationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTERED_BY", referencedColumnName = "USER_ACCOUNT_KEY")
	private UserAccount registeredBy;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDUCATION_INSTITUTION_KEY", referencedColumnName = "EDUCATION_INSTITUTION_KEY")
	private EducationInstitution institution;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALITY_KEY", referencedColumnName = "SPECIALITY_KEY")
	private Speciality speciality;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CERTIFICATE_KEY", referencedColumnName = "CERTIFICATE_KEY")
	private Certificate certificate;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}

	public UserAccount getRegisteredBy() {
		return registeredBy;
	}

	public void setRegisteredBy(UserAccount registeredBy) {
		this.registeredBy = registeredBy;
	}

	public EducationInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(EducationInstitution institution) {
		this.institution = institution;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Enrollee getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Enrollee enrollee) {
		this.enrollee = enrollee;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public RegistrationPeriod getRegistrationPeriod() {
		return registrationPeriod;
	}

	public void setRegistrationPeriod(RegistrationPeriod registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}
	
}
