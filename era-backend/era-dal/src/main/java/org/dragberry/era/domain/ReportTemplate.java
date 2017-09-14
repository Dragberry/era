package org.dragberry.era.domain;

import java.sql.Blob;
import java.text.MessageFormat;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.dragberry.era.domain.converter.ReportTemplateTypeConverter;

@Entity
@Table(name = "REPORT_TEMPLATE")
@TableGenerator(
		name = "REPORT_TEMPLATE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "REPORT_TEMPLATE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class ReportTemplate extends BaseEntity {

	private static final long serialVersionUID = 1996752534212078539L;
	
	private static final String UNKNOWN_VALUE_MSG = "Unknown Report type value: {0}!";
	private static final String NPE_MSG = "Transaction Report type cannot be null!";
	
	public static enum Type {
		DOCX("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"), 
		PDF("pdf", "application/pdf");
		
		public String fileExtension;
		public String mime;
		
		private Type(String fileExtension, String mime) {
			this.fileExtension = fileExtension;
			this.mime = mime;
		}
		
		public static Type valueOfFileExtension(String fileExtension) {
			if (fileExtension == null) {
				throw new NullPointerException(NPE_MSG);
			}
			for (Type type : Type.values()) {
				if (fileExtension.equals(type.fileExtension)) {
					return type;
				}
			}
			throw new IllegalArgumentException(MessageFormat.format(UNKNOWN_VALUE_MSG, fileExtension));
		}
	}
	
	@Id
	@Column(name = "REPORT_TEMPLATE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "REPORT_TEMPLATE_GEN")
	private Long entityKey;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDUCATION_INSTITUTION_KEY", referencedColumnName = "EDUCATION_INSTITUTION_KEY")
	private EducationInstitution institution;
	
	@Column(name = "FILE_EXTENSION")
	@Convert(converter = ReportTemplateTypeConverter.class)
	private Type type;
	
	@Column(name = "TITLE")
	private String title;
	
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "TEMPLATE")
	private Blob template;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public EducationInstitution getInstitution() {
		return institution;
	}

	public void setInstitution(EducationInstitution institution) {
		this.institution = institution;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Blob getTemplate() {
		return template;
	}

	public void setTemplate(Blob template) {
		this.template = template;
	}

}
