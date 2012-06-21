package de.congrace.exp4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This is Builder implementation for the exp4j API used to create a Calculable instance for the user
 * 
 * @author frank asseg
 * 
 */
public class ExpressionBuilder {
	private final Map<String, Number> variables = new LinkedHashMap<String, Number>();

	private final Map<String, CustomFunction> customFunctions;

	private final Map<String, CustomOperator> builtInOperators;

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
		customFunctions = getBuiltinFunctions();
		builtInOperators = getBuiltinOperators();
		validOperatorSymbols = getValidOperators();
	}

	private List<Character> getValidOperators() {
		return Arrays.asList('!', '#', '§', '$', '&', ';', ':', '~', '<', '>', '|', '=');
	}

	private static boolean isReal(Number... numbers){
		for (Number n:numbers){
			if (!n.isReal()){
				return false;
			}
		}
		return true;
	}
	
	private Map<String, CustomOperator> getBuiltinOperators() {
		CustomOperator add = new CustomOperator("+") {
			@Override
			protected Number applyOperation(Number[] values) {
				if (isReal(values)){
					return new Number(values[0].getRealPart() + values[1].getRealPart());
				}else{
					return new Number(values[0].getRealPart() + values[1].getRealPart(),values[0].getImaginaryPart() + values[1].getImaginaryPart());
				}
			}
		};
		CustomOperator sub = new CustomOperator("-") {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() - values[1].getRealPart());
			}
		};
		CustomOperator div = new CustomOperator("/", 3) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() / values[1].getRealPart());
			}
		};
		CustomOperator mul = new CustomOperator("*", 3) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() * values[1].getRealPart());
			}
		};
		CustomOperator mod = new CustomOperator("%", false, 3) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() % values[1].getRealPart());
			}
		};
		CustomOperator umin = new CustomOperator("\'", false, 7, 1) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(-values[0].getRealPart());
			}
		};
		CustomOperator pow = new CustomOperator("^", false, 5, 2) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(Math.pow(values[0].getRealPart(), values[1].getRealPart()));
			}
		};
		Map<String, CustomOperator> operations = new HashMap<String, CustomOperator>();
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);
		operations.put("\'", umin);
		operations.put("^", pow);
		operations.put("%", mod);
		return operations;
	}

	private Map<String, CustomFunction> getBuiltinFunctions() {
		try {
			CustomFunction abs = new CustomFunction("abs") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.abs(args[0].getRealPart()));
				}
			};
			CustomFunction acos = new CustomFunction("acos") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.acos(args[0].getRealPart()));
				}
			};
			CustomFunction asin = new CustomFunction("asin") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.asin(args[0].getRealPart()));
				}
			};
			CustomFunction atan = new CustomFunction("atan") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.atan(args[0].getRealPart()));
				}
			};
			CustomFunction cbrt = new CustomFunction("cbrt") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.cbrt(args[0].getRealPart()));
				}
			};
			CustomFunction ceil = new CustomFunction("ceil") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.ceil(args[0].getRealPart()));
				}
			};
			CustomFunction cos = new CustomFunction("cos") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.cos(args[0].getRealPart()));
				}
			};
			CustomFunction cosh = new CustomFunction("cosh") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.cosh(args[0].getRealPart()));
				}
			};
			CustomFunction exp = new CustomFunction("exp") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.exp(args[0].getRealPart()));
				}
			};
			CustomFunction expm1 = new CustomFunction("expm1") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.expm1(args[0].getRealPart()));
				}
			};
			CustomFunction floor = new CustomFunction("floor") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.floor(args[0].getRealPart()));
				}
			};
			CustomFunction log = new CustomFunction("log") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.log(args[0].getRealPart()));
				}
			};
			CustomFunction sine = new CustomFunction("sin") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.sin(args[0].getRealPart()));
				}
			};
			CustomFunction sinh = new CustomFunction("sinh") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.sinh(args[0].getRealPart()));
				}
			};
			CustomFunction sqrt = new CustomFunction("sqrt") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.sqrt(args[0].getRealPart()));
				}
			};
			CustomFunction tan = new CustomFunction("tan") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.tan(args[0].getRealPart()));
				}
			};
			CustomFunction tanh = new CustomFunction("tanh") {
				@Override
				public Number applyFunction(Number... args) {
					return new Number(Math.tanh(args[0].getRealPart()));
				}
			};
			Map<String, CustomFunction> customFunctions = new HashMap<String, CustomFunction>();
			customFunctions.put("abs", abs);
			customFunctions.put("acos", acos);
			customFunctions.put("asin", asin);
			customFunctions.put("atan", atan);
			customFunctions.put("cbrt", cbrt);
			customFunctions.put("ceil", ceil);
			customFunctions.put("cos", cos);
			customFunctions.put("cosh", cosh);
			customFunctions.put("exp", exp);
			customFunctions.put("expm1", expm1);
			customFunctions.put("floor", floor);
			customFunctions.put("log", log);
			customFunctions.put("sin", sine);
			customFunctions.put("sinh", sinh);
			customFunctions.put("sqrt", sqrt);
			customFunctions.put("tan", tan);
			customFunctions.put("tanh", tanh);
			return customFunctions;
		} catch (InvalidCustomFunctionException e) {
			// this should not happen...
			throw new RuntimeException(e);
		}
	}

	/**
	 * build a new {@link Calculable} from the expression using the supplied variables
	 * 
	 * @return the {@link Calculable} which can be used to evaluate the expression
	 * @throws UnknownFunctionException
	 *             when an unrecognized function name is used in the expression
	 * @throws UnparsableExpressionException
	 *             if the expression could not be parsed
	 */
	public Calculable build() throws UnknownFunctionException, UnparsableExpressionException {
		for (CustomOperator op : customOperators.values()) {
			for (int i = 0; i < op.symbol.length(); i++) {
				if (!validOperatorSymbols.contains(op.symbol.charAt(i))) {
					throw new UnparsableExpressionException("" + op.symbol
							+ " is not a valid symbol for an operator please choose from: !,#,§,$,&,;,:,~,<,>,|,=");
				}
			}
		}
		for (String varName : variables.keySet()) {
			if (customFunctions.containsKey(varName)) {
				throw new UnparsableExpressionException("Variable '" + varName
						+ "' cannot have the same name as a function");
			}
			if (varName.equals("i")){
				throw new UnparsableExpressionException("'i' can not be used as a variable name");
			}
		}
		builtInOperators.putAll(customOperators);
		return RPNConverter.toRPNExpression(expression, variables, customFunctions, builtInOperators);
	}

	/**
	 * add a custom function instance for the evaluator to recognize
	 * 
	 * @param function
	 *            the {@link CustomFunction} to add
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withCustomFunction(CustomFunction function) {
		customFunctions.put(function.name, function);
		return this;
	}

	public ExpressionBuilder withCustomFunctions(Collection<CustomFunction> functions) {
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
	public ExpressionBuilder withVariable(String variableName, Number value) {
		variables.put(variableName, value);
		return this;
	}

	/**
	 * set the variables names used in the expression without setting their values
	 * 
	 * @param variableNames
	 *            vararg {@link String} of the variable names used in the expression
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
	public ExpressionBuilder withVariables(Map<String, Number> variableMap) {
		for (Entry<String, Number> v : variableMap.entrySet()) {
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
	 * set a {@link Collection} of {@link CustomOperator} to use in the expression
	 * 
	 * @param operations
	 *            the {@link Collection} of {@link CustomOperator} to use
	 * @return the {@link ExpressionBuilder} instance
	 */
	public ExpressionBuilder withOperations(Collection<CustomOperator> operations) {
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
