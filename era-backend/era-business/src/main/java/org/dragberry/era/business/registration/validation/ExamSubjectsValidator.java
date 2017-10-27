package org.dragberry.era.business.registration.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.dragberry.era.business.validation.AbstractValidator;
import org.dragberry.era.common.IssueTO;
import org.dragberry.era.common.Issues;
import org.dragberry.era.common.expression.ExpressionParser;
import org.dragberry.era.dao.ExamSubjectDao;
import org.dragberry.era.domain.EducationBase;
import org.dragberry.era.domain.ExamSubject;
import org.dragberry.era.domain.RegisteredSpecialty;
import org.dragberry.era.domain.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamSubjectsValidator extends AbstractValidator<Registration> implements RegistrationValidationHelper {

	@Autowired
	private ExamSubjectDao examSubjectDao;
	
	private ExpressionParser<ExamSubject> parser = new ExpressionParser<>(code -> examSubjectDao.findByCode(code));
	
	private class Errors {
		final String isEmpty = errorCode("is-empty");
		public String markIsEmpty = errorCode("mark-is-empty");
		public String markAlreadyExists = errorCode("mark-already-exists");
		public String extraMarksProvided = errorCode("extra-mark-provided");
	}
	
	private final Errors errors = new Errors();
	
	@Override
	protected boolean shouldValidate(Registration entity) {
		return entity.getEducationBase() == EducationBase.L11
				&& entity.getRegistrationPeriod() != null && entity.getSpecialty() != null;
	}
	
	@Override
	protected List<IssueTO> validateEntity(Registration entity) {
		Map<ExamSubject, Integer> marks = entity.getExamMarks();
		if (MapUtils.isEmpty(marks)) {
			return issues(Issues.warning(errors.isEmpty));
		}
		Optional<RegisteredSpecialty> regSpec = entity.getRegistrationPeriod().getSpecialties().stream().filter(rs -> entity.getSpecialty().equals(rs.getSpecialty())).findFirst();
		if (regSpec.isPresent()) {
			List<IssueTO> issues = new ArrayList<>();
			Map<ExamSubject, Integer> validatedMarks = new HashMap<>(marks);
			List<List<ExamSubject>> requiredSubjects = parser.parse(regSpec.get().getExamSubjectExpression()).getList();
			requiredSubjects.forEach(subjectsGroup -> {
				validateMarkForGroup(subjectsGroup, validatedMarks, issues);
			});
			if (!validatedMarks.isEmpty()) {
				issues.add(Issues.warning(errors.extraMarksProvided));
			}
			return issues;
		}
		return Collections.emptyList();
	}

	private void validateMarkForGroup(List<ExamSubject> subjectsGroup, Map<ExamSubject, Integer> validatedMarks, List<IssueTO> issues) {
		boolean markExists = false;
		ExamSubject subjectWithExistedMark = null;
		Integer mark = null;
		for (ExamSubject subject : subjectsGroup) {
			Integer tempMark = validatedMarks.get(subject);
			if (tempMark != null) {
				if (markExists) {
					issues.add(Issues.error(errors.markAlreadyExists, subject.getTitle(), subjectWithExistedMark.getTitle()));
				} else {
					markExists = true;
					mark = tempMark;
					subjectWithExistedMark = subject;
				}
			}
			validatedMarks.remove(subject);
		}
		if (mark == null) {
			issues.add(Issues.warning(errors.markIsEmpty, (Object) subjectsGroup.get(0).getTitle()));
		}
	}

	@Override
	protected String errorPrefix() {
		return BASE_ERROR_CODE_PREFIX + "exam-subjects.";
	}

	@Override
	protected String fieldPrefix() {
		return StringUtils.EMPTY;
	}

}
