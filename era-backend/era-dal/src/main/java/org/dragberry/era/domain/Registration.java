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

import org.dragberry.era.domain.converter.EducationFormConverter;
import org.dragberry.era.domain.converter.FundsSourceConverter;

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

	@Id
	@Column(name = "REGISTRATION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REGISTRATION_GEN")
	private Long entityKey;
	
	@Column(name = "FUNDS_SOURCE")
	@Convert(converter = FundsSourceConverter.class)
	private FundsSource fundsSource;
	
	@Column(name = "EDUCATION_FORM")
	@Convert(converter = EducationFormConverter.class)
	private EducationForm educationForm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "PERSON_KEY")
	private Person enrollee;
	
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
	@JoinColumn(name = "SPECIALTY_KEY", referencedColumnName = "SPECIALTY_KEY")
	private Specialty specialty;
	
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

	public Specialty getSpecialty() {
		return specialty;
	}

	public void setSpecialty(Specialty speciality) {
		this.specialty = speciality;
	}

	public Certificate getCertificate() {
		return certificate;
	}

	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}

	public Person getEnrollee() {
		return enrollee;
	}

	public void setEnrollee(Person enrollee) {
		this.enrollee = enrollee;
	}
	
	public FundsSource getFundsSource() {
		return fundsSource;
	}

	public void setFundsSource(FundsSource fundsSource) {
		this.fundsSource = fundsSource;
	}

	public EducationForm getEducationForm() {
		return educationForm;
	}

	public void setEducationForm(EducationForm educationForm) {
		this.educationForm = educationForm;
	}

	public RegistrationPeriod getRegistrationPeriod() {
		return registrationPeriod;
	}

	public void setRegistrationPeriod(RegistrationPeriod registrationPeriod) {
		this.registrationPeriod = registrationPeriod;
	}
	
	private static final String UNKNOWN_VALUE_MSG = "Unknown {0} value: {1}!";

	private static final String NPE_MSG = "{0} cannot be null!";
	
	public static enum EducationForm {
		FULL_TIME('F'), EXTRAMURAL('E');
		
		public final char value;
		
		private EducationForm(char value) {
			this.value = value;
		}
		
		public static EducationForm valueOf(Character value) {
			if (value == null) {
				throw new NullPointerException(MessageFormat.format(NPE_MSG, EducationForm.class.getName()));
			}
			for (EducationForm form : EducationForm.values()) {
				if (value.equals(form.value)) {
					return form;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, EducationForm.class.getName(), value));
		}
	}
	
	public static enum FundsSource {
		BUDGET('B'), PAYER('P');
		
		public final char value;
		
		private FundsSource(char value) {
			this.value = value;
		}
		
		public static FundsSource valueOf(Character value) {
			if (value == null) {
				throw new NullPointerException(MessageFormat.format(NPE_MSG, FundsSource.class.getName()));
			}
			for (FundsSource src : FundsSource.values()) {
				if (value.equals(src.value)) {
					return src;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, FundsSource.class.getName(), value));
		}
	}
	
}
