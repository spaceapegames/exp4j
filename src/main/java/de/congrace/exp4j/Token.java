package de.congrace.exp4j;

import java.util.Stack;

abstract class Token {
	private final String originalValue;

	/**
	 * construct a new {@link Token}
	 * 
	 * @param originalValue
	 *            the value of the {@link Token}
	 */
	Token(String originalValue) {
		super();
		this.originalValue = originalValue;
	}

	/**
	 * get the value (String representation) of the token
	 * 
	 * @return the value
	 */
	String getValue() {
		return originalValue;
	}

	abstract void mutateStackForInfixTranslation(Stack<Token> operatorStack, StringBuilder output);
}