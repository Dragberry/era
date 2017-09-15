package org.dragberry.era.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("O")
public class OutOfCompetition extends Benefit {

	private static final long serialVersionUID = -2901535499431671559L;

}
