package org.dragberry.era.domain;

import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import org.dragberry.era.domain.converter.DocumentTypeConverter;

@Embeddable
public class Document implements Serializable {

	private static final long serialVersionUID = -6105124089308181567L;

	private static final String UNKNOWN_VALUE_MSG = "Unknown document type value: {0}!";
	private static final String NPE_MSG = "Transaction  document type cannot be null!";
	
	public static enum Type {
		PASSPORT('P');
		
		public final char value;
		
		private Type(char value) {
			this.value = value;
		}
		
		public static Type valueOf(Character value) {
			if (value == null) {
				throw new NullPointerException(NPE_MSG);
			}
			for (Type status : Type.values()) {
				if (value.equals(status.value)) {
					return status;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, value));
		}
	}
	
	@Column(name = "DOCUMENT_TYPE")
	@Convert(converter = DocumentTypeConverter.class)
	private Type type;
	
	@Column(name = "ID")
	private String id;
	
	@Column(name = "DOCUMENT_ID")
	private String documentId;

	@Column(name = "DOCUMENT_ISSUE_DATE")
	private LocalDate issueDate;
	
	@Column(name = "DOCUMENT_ISSUED_BY")
	private String issuedBy;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(LocalDate issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssuedBy() {
		return issuedBy;
	}

	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}
	
}
