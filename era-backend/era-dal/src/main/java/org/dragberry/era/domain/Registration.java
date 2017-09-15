package org.dragberry.era.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.EducationBaseConverter;
import org.dragberry.era.domain.converter.EducationFormConverter;
import org.dragberry.era.domain.converter.FundsSourceConverter;
import org.dragberry.era.domain.converter.RegistrationStatusConverter;

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
public class Registration extends BaseEntity {

	private static final long serialVersionUID = 8173371976074070183L;

	@Id
	@Column(name = "REGISTRATION_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REGISTRATION_GEN")
	private Long entityKey;
	
	@Column(name = "REGISTRATION_ID")
	private Long registrationId;
	
	@Column(name = "FUNDS_SOURCE")
	@Convert(converter = FundsSourceConverter.class)
	private FundsSource fundsSource;
	
	@Column(name = "EDUCATION_FORM")
	@Convert(converter = EducationFormConverter.class)
	private EducationForm educationForm;
	
	@Column(name = "EDUCATION_BASE")
	@Convert(converter = EducationBaseConverter.class)
	private EducationBase educationBase;
	
	@Column(name = "NOTE")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ENROLLEE_KEY", referencedColumnName = "PERSON_KEY")
	private Person enrollee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGISTRATION_PERIOD_KEY", referencedColumnName = "REGISTRATION_PERIOD_KEY")
	private RegistrationPeriod registrationPeriod;
	
	@Column(name = "REGISTRATION_DATE")
	private LocalDateTime registrationDate;
	
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
	
	@Column(name = "STATUS")
	@Convert(converter = RegistrationStatusConverter.class)
	private Status status;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "REGISTRATION_BENEFIT", 
        joinColumns = @JoinColumn(name = "REGISTRATION_KEY", referencedColumnName = "REGISTRATION_KEY"), 
        inverseJoinColumns = @JoinColumn(name = "BENEFIT_KEY", referencedColumnName = "BENEFIT_KEY"))
	private List<Benefit> benefits;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
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

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public EducationBase getEducationBase() {
		return educationBase;
	}

	public void setEducationBase(EducationBase educationBase) {
		this.educationBase = educationBase;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<Benefit> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<Benefit> benefits) {
		this.benefits = benefits;
	}

	public static enum Status implements BaseEnum<Character> {
		PROCESSING('P'), AWAITING('W'), ACCEPTED('A'), CANCELED('C');
		
		public final Character value;
		
		private Status(Character value) {
			this.value = value;
		}
		
		public static Status resolve(Character value) {
			if (value == null) {
				throw BaseEnum.npeException(Status.class);
			}
			for (Status src : Status.values()) {
				if (value.equals(src.value)) {
					return src;
				}
			}
			throw BaseEnum.unknownValueException(Status.class, value);
		}
		
		@Override
		public Character getValue() {
			return value;
		}
	}
}
