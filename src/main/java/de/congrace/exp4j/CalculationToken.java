package de.congrace.exp4j;

import java.util.Map;
import java.util.Stack;

abstract class CalculationToken extends Token {

	CalculationToken(String originalValue) {
		super(originalValue);
	}

	abstract void mutateStackForCalculation(Stack<Number> stack, Map<String, Number> variableValues);

}