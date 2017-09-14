package org.dragberry.era.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.RegistrationPeriodStatusConverter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "REGISTRATION_PERIOD")
@NamedQueries({
	@NamedQuery(
			name = RegistrationPeriod.FIND_ACTIVE_PERIOD_FOR_CUSTOMER,
			query = "select distinct rp from RegistrationPeriod rp left join fetch rp.specialties, Customer c where c.institution = rp.educationInstitution and c.entityKey = :customerKey and rp.status = :status"),
	@NamedQuery(
			name = RegistrationPeriod.FIND_PERIODS_FOR_CUSTOMER,
			query = "select distinct rp from RegistrationPeriod rp left join fetch rp.specialties, Customer c where c.institution = rp.educationInstitution and c.entityKey = :customerKey"),
	@NamedQuery(
			name = RegistrationPeriod.FIND_SPECIALTIES_FOR_PERIOD,
			query = "select distinct rs from Customer c, RegistrationPeriod rp, RegisteredSpecialty rs join fetch rs.specialty where c.institution = rp.educationInstitution and rp = rs.registrationPeriod and c.entityKey = :customerKey and rp.entityKey = :periodKey")

})
@TableGenerator(
		name = "REG_PERIOD_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REG_PERIOD_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class RegistrationPeriod extends BaseEntity {
	
	private static final long serialVersionUID = 3047306263898450821L;

	public final static String FIND_ACTIVE_PERIOD_FOR_CUSTOMER = "RegistrationPeriod.FindActivePeriodForCustomer";
	public static final String FIND_PERIODS_FOR_CUSTOMER = "RegistrationPeriod.FindPeriodsForCustomer";
	public final static String FIND_SPECIALTIES_FOR_PERIOD = "RegistrationPeriod.FindSpecialitiesForPeriod";
	
	@Id
	@Column(name = "REGISTRATION_PERIOD_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REG_PERIOD_GEN")
	private Long entityKey;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "FROM_DATE")
	private LocalDate from;
	
	@Column(name = "TO_DATE")
	private LocalDate to;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDUCATION_INSTITUTION_KEY", referencedColumnName = "EDUCATION_INSTITUTION_KEY")
	private EducationInstitution educationInstitution;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "registrationPeriod")
	@Cascade(CascadeType.SAVE_UPDATE)
	private List<RegisteredSpecialty> specialties;
	
	@Column(name = "STATUS")
	@Convert(converter = RegistrationPeriodStatusConverter.class)
	private Status status;

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

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public EducationInstitution getEducationInstitution() {
		return educationInstitution;
	}

	public void setEducationInstitution(EducationInstitution educationInstitution) {
		this.educationInstitution = educationInstitution;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<RegisteredSpecialty> getSpecialties() {
		return specialties;
	}

	public void setSpecialties(List<RegisteredSpecialty> specialties) {
		this.specialties = specialties;
	}

	public static enum Status implements BaseEnum<Character> {
		NEW('N'), OPENED('O'), CLOSED('C');
		
		public final Character value;
		
		private Status(Character value) {
			this.value = value;
		}
		
		public static Status valueOf(Character value) {
			if (value == null) {
				throw BaseEnum.npeException(Status.class);
			}
			for (Status status : Status.values()) {
				if (value.equals(status.value)) {
					return status;
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
