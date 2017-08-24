package org.dragberry.era.business.reporting;

import java.util.regex.Pattern;

public interface DataProvider {
	
	String getData(String param);
	
	Pattern getPropertyPattern();
}