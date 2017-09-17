package org.dragberry.era.common.benefit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BenefitListTO implements Serializable {

	private static final long serialVersionUID = -6224144846285091883L;

	private List<BenefitTO> prerogatives = new ArrayList<>();
	
	private List<BenefitTO> outOfCompetitions = new ArrayList<>();

	public List<BenefitTO> getPrerogatives() {
		return prerogatives;
	}

	public void setPrerogatives(List<BenefitTO> prerogatives) {
		this.prerogatives = prerogatives;
	}

	public List<BenefitTO> getOutOfCompetitions() {
		return outOfCompetitions;
	}

	public void setOutOfCompetitions(List<BenefitTO> outOfCompetitions) {
		this.outOfCompetitions = outOfCompetitions;
	}
	
}
