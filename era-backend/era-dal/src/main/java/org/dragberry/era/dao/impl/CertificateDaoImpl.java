package org.dragberry.era.dao.impl;

import org.dragberry.era.dao.CertificateDao;
import org.dragberry.era.domain.Certificate;
import org.springframework.stereotype.Repository;

@Repository
public class CertificateDaoImpl extends AbstractDao<Certificate> implements CertificateDao {

	public CertificateDaoImpl() {
		super(Certificate.class);
	}

}
