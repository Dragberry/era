package org.dragberry.era.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("P")
public class Prerogative extends Benefit {

	private static final long serialVersionUID = 2424023309695254630L;

}
