/**
 * Copyright 2014 Marin Solutions
 */
package com.captaindebug.errortrack.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.captaindebug.errortrack.Validator;

/**
 * @author Roger
 * 
 */
@Service
public class RegexValidator implements InitializingBean, Validator {

	private static final Logger logger = LoggerFactory.getLogger(RegexValidator.class);

	@Value("${scan.for}")
	private String scanFor;

	private Pattern pattern;

	@Override
	public <T> boolean validate(T line) {

		boolean retVal = false;
		Matcher matcher = pattern.matcher((String) line);
		retVal = matcher.matches();
		if (retVal && logger.isDebugEnabled()) {
			logger.debug("Found error line: {}", line);
		}

		return retVal;
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {

		pattern = Pattern.compile(scanFor);
		logger.info("loaded regex: {}", scanFor);
	}

}
