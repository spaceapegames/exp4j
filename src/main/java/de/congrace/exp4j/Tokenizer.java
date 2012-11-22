package de.congrace.exp4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Tokenizer {

	private final Set<String> variableNames;

	private final Map<String, CustomFunction> functions;

	private final Map<String, CustomOperator> operators;

	Tokenizer(Set<String> variableNames, Map<String, CustomFunction> functions, Map<String, CustomOperator> operators) {
		super();
		this.variableNames = variableNames;
		this.functions = functions;
		this.operators = operators;
	}

	private boolean isDigitOrDecimalSeparator(char c) {
		return Character.isDigit(c) || c == '.';
	}

	private boolean isNotationSeparator(char c) {
		return c == 'e' || c == 'E';
	}

	private boolean isVariable(String name) {
		if (variableNames != null) {
			for (String var : variableNames) {
				if (name.equals(var)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isFunction(String name) {
		return getFunctionByName(name) != null;
	}

	private boolean isOperatorCharacter(char c) {
		if (BuiltinOperators.isOperatorCharacter(c)) {
			return true;
		}
		for (String symbol : operators.keySet()) {
			if (symbol.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}

	List<Token> getTokens(final String expression) throws UnparsableExpressionException, UnknownFunctionException {
		final List<Token> tokens = new ArrayList<Token>();
		final char[] chars = expression.toCharArray();
		int openBraces = 0;
		int openCurly = 0;
		int openSquare = 0;
		// iterate over the chars and fork on different types of input
		Token lastToken = null;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == ' ')
				continue;
			if (Character.isDigit(c)) {
				final StringBuilder valueBuilder = new StringBuilder(1);
				// handle the numbers of the expression
				valueBuilder.append(c);
				int numberLen = 1;
				boolean lastCharNotationSeparator = false;
				while (chars.length > i + numberLen) {
					if (isDigitOrDecimalSeparator(chars[i + numberLen])) {
						valueBuilder.append(chars[i + numberLen]);
					} else if (isNotationSeparator(chars[i + numberLen])) {
						if (lastCharNotationSeparator == true) {
							throw new UnparsableExpressionException("Expression can have only one notation separator");
						}
						valueBuilder.append(chars[i + numberLen]);
						lastCharNotationSeparator = true;
					} else if (lastCharNotationSeparator
							&& (chars[i + numberLen] == '-' || chars[i + numberLen] == '+')) {
						valueBuilder.append(chars[i + numberLen]);
					} else {
						break; // break out of the while loop here, since the number seem finished
					}
					numberLen++;
				}
				i += numberLen - 1;
				lastToken = new NumberToken(valueBuilder.toString());
			} else if (Character.isLetter(c) || c == '_') {
				// can be a variable or function
				final StringBuilder nameBuilder = new StringBuilder();
				nameBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset
						&& (Character.isLetter(chars[i + offset]) || Character.isDigit(chars[i + offset]) || chars[i
								+ offset] == '_')) {
					nameBuilder.append(chars[i + offset++]);
				}
				String name = nameBuilder.toString();
				CustomFunction f;
				if (this.isVariable(name)) {
					// a variable
					i += offset - 1;
					lastToken = new VariableToken(name);
				} else if ((f = this.getFunctionByName(name)) != null) {
					// might be a function
					i += offset - 1;
					lastToken = new FunctionToken(f);
				} else {
					// an unknown symbol was encountered
					throw new UnparsableExpressionException(expression, c, i + 1);
				}
			} else if (c == ',') {
				// a function separator, hopefully
				lastToken = new FunctionSeparatorToken();
			} else if (isOperatorCharacter(c)) {
				// might be an operation
				StringBuilder symbolBuilder = new StringBuilder();
				symbolBuilder.append(c);
				int offset = 1;
				while (chars.length > i + offset && (isOperatorCharacter(chars[i + offset]))
						&& isOperatorStart(symbolBuilder.toString() + chars[i + offset])) {
					symbolBuilder.append(chars[i + offset]);
					offset++;
				}
				String symbol = symbolBuilder.toString();
				CustomOperator op = this.getOperator(symbol);
				if (op != null) {
					i += offset - 1;
					lastToken = new OperatorToken(op);
				} else {
					throw new UnparsableExpressionException(expression, c, i + 1);
				}
			} else if (c == '(') {
				openBraces++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '{') {
				openCurly++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '[') {
				openSquare++;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == ')') {
				openBraces--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == '}') {
				openCurly--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else if (c == ']') {
				openSquare--;
				lastToken = new ParenthesesToken(String.valueOf(c));
			} else {
				// an unknown symbol was encountered
				throw new UnparsableExpressionException(expression, c, i + 1);
			}
			tokens.add(lastToken);
		}
		if (openCurly != 0 || openBraces != 0 | openSquare != 0) {
			StringBuilder errorBuilder = new StringBuilder();
			errorBuilder.append("There are ");
			boolean first = true;
			if (openBraces != 0) {
				errorBuilder.append(Math.abs(openBraces) + " unmatched parantheses ");
				first = false;
			}
			if (openCurly != 0) {
				if (!first) {
					errorBuilder.append(" and ");
				}
				errorBuilder.append(Math.abs(openCurly) + " unmatched curly brackets ");
				first = false;
			}
			if (openSquare != 0) {
				if (!first) {
					errorBuilder.append(" and ");
				}
				errorBuilder.append(Math.abs(openSquare) + " unmatched square brackets ");
				first = false;
			}
			errorBuilder.append("in expression '" + expression + "'");
			throw new UnparsableExpressionException(errorBuilder.toString());
		}
		return tokens;

	}

	private CustomOperator getOperator(String symbol) {
		CustomOperator op = BuiltinOperators.getOperator(symbol.charAt(0));
		if (op == null) {
			op = operators.get(symbol);
		}
		return op;

	}

	private CustomFunction getFunctionByName(String name) {
		if (name.equals("abs")) {
			return BuiltinFunctions.ABS;
		} else if (name.equals("acos")) {
			return BuiltinFunctions.ACOS;
		} else if (name.equals("asin")) {
			return BuiltinFunctions.ASIN;
		} else if (name.equals("atan")) {
			return BuiltinFunctions.ATAN;
		} else if (name.equals("cbrt")) {
			return BuiltinFunctions.CBRT;
		} else if (name.equals("ceil")) {
			return BuiltinFunctions.CEIL;
		} else if (name.equals("cos")) {
			return BuiltinFunctions.COS;
		} else if (name.equals("cosh")) {
			return BuiltinFunctions.COSH;
		} else if (name.equals("exp")) {
			return BuiltinFunctions.EXP;
		} else if (name.equals("expm1")) {
			return BuiltinFunctions.EXPM1;
		} else if (name.equals("floor")) {
			return BuiltinFunctions.FLOOR;
		} else if (name.equals("log")) {
			return BuiltinFunctions.LOG;
		} else if (name.equals("sin")) {
			return BuiltinFunctions.SIN;
		} else if (name.equals("sinh")) {
			return BuiltinFunctions.SINH;
		} else if (name.equals("sqrt")) {
			return BuiltinFunctions.SQRT;
		} else if (name.equals("tan")) {
			return BuiltinFunctions.TAN;
		} else if (name.equals("tanh")) {
			return BuiltinFunctions.TANH;
		} else {
			return functions.get(name);
		}
	}

	private boolean isOperatorStart(String op) {
		if (op.length() == 1 && BuiltinOperators.isOperatorCharacter(op.charAt(0))) {
			return true;
		}
		for (String operatorName : operators.keySet()) {
			if (operatorName.startsWith(op)) {
				return true;
			}
		}
		return false;
	}

}
