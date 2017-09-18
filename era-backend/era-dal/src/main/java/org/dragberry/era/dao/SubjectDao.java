package org.dragberry.era.dao;

import java.util.List;

import org.dragberry.era.domain.Subject;

public interface SubjectDao extends DataAccessObject<Subject, Long> {

	List<Subject> fetchList(List<Long> entityKeys);

}
