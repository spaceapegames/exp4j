package de.congrace.exp4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is Builder implementation for the exp4j API used to create a Calculable
 * instance for the user
 * 
 * @author frank asseg
 * 
 */
public class ExpressionBuilder {

	
	private final Map<String, Double> variables = new LinkedHashMap<String, Double>();

	private final Map<String, CustomFunction> functions = new HashMap<String, CustomFunction>();

	private Map<String, CustomOperator> customOperators = new HashMap<String, CustomOperator>();

	private final List<Character> validOperatorSymbols;


	private String expression;

	/**
	 * Create a new ExpressionBuilder
	 * 
	 * @param expression
	 *            the expression to evaluate
	 */
	public ExpressionBuilder(String expression) {
		this.expression = expression;
		validOperatorSymbols = getValidOperators();
	}

	private List<Character> getValidOperators() {
		return Arrays.asList('!', '#', '§', '$', '&', ';', ':', '~', '<', '>',
				'|', '=');
	}

	/**
	 * build a new {@link Calculable} from the expression using the supplied
	 * variables
	 * 
	 * @return the {@link Calculable} which can be used to evaluate the
	 *         expression
	 * @throws UnknownFunctionException
	 *             when an unrecognized function name is used in the expression
	 * @throws UnparsableExpressionException
	 *             if the expression could not be parsed
	 */
	public Calculable build() throws UnknownFunctionException,
			UnparsableExpressionException {
		for (CustomOperator op : customOperators.values()) {
			for (int i = 0; i < op.symbol.length(); i++) {
				if (!validOperatorSymbols.contains(op.symbol.charAt(i))) {
					throw new UnparsableExpressionException(
							""
									+ op.symbol
									+ " is not a valid symbol for an operator please choose from: !,#,§,$,&,;,:,~,<,>,|,=");
				}
			}
		}
		for (String varName : variables.keySet()) {
			checkVariableName(varName);
			if (functions.containsKey(varName)) {
				throw new UnparsableExpressionException("Variable '" + varName
						+ "' cannot have the same name as a function");
			}
		}
		return RPNConverter.toRPNExpression(expression, variables,
				functions, customOperators);
	}

	private void checkVariableName(String varName)
			throws UnparsableExpressionException {
		char[] name = varName.toCharArray();
		for (int i = 0; i < name.length; i++) {
			if (i == 0){
				if (!Character.isLetter(name[i]) && name[i] != '_'){
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i); 
				}
			}else{
				if (!Character.isLetter(name[i]) && !Character.isDigit(name[i]) && name[i] != '_'){
					throw new UnparsableExpressionException(varName + " is not a valid variable name: character '" + name[i] + " at " + i); 
				}
			}
		}
	}

	/**
	 * add a custom function instance for the evaluator to recognize
	 * 
	 * @param function
	 *            the {@link CustomFunction} to add
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withCustomFunction(CustomFunction function) {
		functions.put(function.name, function);
		return this;
	}

	public ExpressionBuilder withCustomFunctions(
			Collection<CustomFunction> functions) {
		for (CustomFunction f : functions) {
			withCustomFunction(f);
		}
		return this;
	}

	/**
	 * set the value for a variable
	 * 
	 * @param variableName
	 *            the variable name e.g. "x"
	 * @param value
	 *            the value e.g. 2.32d
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariable(String variableName, double value) {
		variables.put(variableName, value);
		return this;
	}

	/**
	 * set the variables names used in the expression without setting their
	 * values
	 * 
	 * @param variableNames
	 *            vararg {@link String} of the variable names used in the
	 *            expression
	 * @return the ExpressionBuilder instance
	 */
	public ExpressionBuilder withVariableNames(String... variableNames) {
		for (String variable : variableNames) {
			variables.put(variable, null);
		}
		return this;
	}

	/**
	 * set the values for variables
	 * 
	 * @param variableMap
	 *            a map of variable names to variable values
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withVariables(Map<String, Double> variableMap) {
		for (Entry<String, Double> v : variableMap.entrySet()) {
			variables.put(v.getKey(), v.getValue());
		}
		return this;
	}

	/**
	 * set a {@link CustomOperator} to be used in the expression
	 * 
	 * @param operation
	 *            the {@link CustomOperator} to be used
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperation(CustomOperator operation) {
		customOperators.put(operation.symbol, operation);
		return this;
	}

	/**
	 * set a {@link Collection} of {@link CustomOperator} to use in the
	 * expression
	 * 
	 * @param operations
	 *            the {@link Collection} of {@link CustomOperator} to use
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperations(
			Collection<CustomOperator> operations) {
		for (CustomOperator op : operations) {
			withOperation(op);
		}
		return this;
	}

	/**
	 * set the mathematical expression for parsing
	 * 
	 * @param expression
	 *            a mathematical expression
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withExpression(String expression) {
		this.expression = expression;
		return this;
	}
}
