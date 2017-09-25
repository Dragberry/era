package org.dragberry.era.domain;

import java.time.LocalDateTime;

import javax.persistence.Basic;
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

import org.dragberry.era.domain.converter.AuditRecordActionConverter;

@Entity
@Table(name = "AUDIT_RECORD")
@TableGenerator(
		name = "AUDIT_RECORD_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "AUDIT_RECORD_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class AuditRecord extends AbstractEntity {

	private static final long serialVersionUID = -2785916532430976673L;

	@Id
	@Column(name = "AUDIT_RECORD_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUDIT_RECORD_GEN")
	private Long entityKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ACCOUNT_KEY", referencedColumnName = "USER_ACCOUNT_KEY")
	private UserAccount userAccount;

	@Column(name = "IP")
	private String ip;
	
	@Column(name = "DATE")
	private LocalDateTime date;
	
	@Column(name = "ENTITY_TYPE")
	private String entityType;
	
	@Column(name = "ACTION")
	@Convert(converter = AuditRecordActionConverter.class)
	private Action action;
	
	@Column(name = "REFERENCED_ENTITY_KEY")
	private Long referencedEntityKey;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "CHANGES", columnDefinition = "TEXT")
	private String changes;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}

	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getChanges() {
		return changes;
	}

	public void setChanges(String changes) {
		this.changes = changes;
	}

	public Long getReferencedEntityKey() {
		return referencedEntityKey;
	}

	public void setReferencedEntityKey(Long referencedEntityKey) {
		this.referencedEntityKey = referencedEntityKey;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	public enum Action implements BaseEnum<Character> {
		SEARCH('S'), VIEW('V'), CREATE('C'), UPDATE('U'), DELETE('D');
		
		public final Character value;
		
		private Action(Character value) {
			this.value = value;
		}
		
		public static Action resolve(Character value) {
			if (value == null) {
				throw BaseEnum.npeException(Action.class);
			}
			for (Action form : Action.values()) {
				if (value.equals(form.value)) {
					return form;
				}
			}
			throw BaseEnum.unknownValueException(Action.class, value);
		}
		
		@Override
		public Character getValue() {
			return value;
		}
	}

}
