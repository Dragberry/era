package org.dragberry.era.dao.impl;

import java.util.Optional;

import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.domain.Certificate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateDaoImpl extends AbstractDao<Certificate> implements CertificateDao {

	private static final String CERTIFICATE_KEY = "certificateKey";

	public CertificateDaoImpl() {
		super(Certificate.class);
	}

	@Override
	public Double getAverageMark(Long certificateKey) {
		return Optional.ofNullable(getEntityManager()
				.createNamedQuery(Certificate.GET_AVERAGE_MARK_QUERY, Double.class)
				.setParameter(CERTIFICATE_KEY, certificateKey)
				.getSingleResult()).orElse(0.0);
		
	}

}
