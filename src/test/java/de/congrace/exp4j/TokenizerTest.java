/*
   Copyright 2011 frank asseg

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */
package de.congrace.exp4j;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class TokenizerTest {
	static Map<String, CustomOperator> operations = new HashMap<String, CustomOperator>();

	static Map<String, CustomFunction> functions = new HashMap<String, CustomFunction>();

	@BeforeClass
	public static void setup() throws Exception {
		CustomOperator add = new CustomOperator("+") {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() + values[1].getRealPart());
			}
		};
		CustomOperator sub = new CustomOperator("-") {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() - values[1].getRealPart());
			}
		};
		CustomOperator div = new CustomOperator("/", 1) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() / values[1].getRealPart());
			}
		};
		CustomOperator mul = new CustomOperator("*", 1) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() / values[1].getRealPart());
			}
		};
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);

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
		functions.put("abs", abs);
		functions.put("acos", acos);
		functions.put("asin", asin);
		functions.put("atan", atan);
		functions.put("cbrt", cbrt);
		functions.put("ceil", ceil);
		functions.put("cos", cos);
		functions.put("cosh", cosh);
		functions.put("exp", exp);
		functions.put("expm1", expm1);
		functions.put("floor", floor);
		functions.put("log", log);
		functions.put("sin", sine);
		functions.put("sinh", sinh);
		functions.put("sqrt", sqrt);
		functions.put("tan", tan);
		functions.put("tanh", tanh);
	}

	@Test
	public void testInfixTokenize1() throws Exception {
		String expr = "2+45 -   12";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("+", operations.get('+')),
				new NumberToken("45"), new OperatorToken("-", operations.get('-')), new NumberToken("12") };
		Tokenizer tokenizer = new Tokenizer(new HashSet<String>(), functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testInfixTokenize2() throws Exception {
		String expr = "2*4412+23/12";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4412"), new OperatorToken("+", operations.get('+')), new NumberToken("23"),
				new OperatorToken("/", operations.get('/')), new NumberToken("12") };
		Tokenizer tokenizer = new Tokenizer(new HashSet<String>(), functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testInfixTokenize3() throws Exception {
		String expr = "2*4.412+2.3/12";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4.412"), new OperatorToken("+", operations.get('+')), new NumberToken("2.3"),
				new OperatorToken("/", operations.get('/')), new NumberToken("12") };
		Tokenizer tokenizer = new Tokenizer(new HashSet<String>(), functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testInfixTokenize4() throws Exception {
		String expr = "2*4.4+(2.3/12)*4+(20-2)";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4.4"), new OperatorToken("+", operations.get('+')), new ParenthesisToken("("),
				new NumberToken("2.3"), new OperatorToken("/", operations.get('/')), new NumberToken("12"),
				new ParenthesisToken(")"), new OperatorToken("*", operations.get('*')), new NumberToken("4"),
				new OperatorToken("+", operations.get('+')), new ParenthesisToken("("), new NumberToken("20"),
				new OperatorToken("-", operations.get('-')), new NumberToken("2"), new ParenthesisToken(")") };
		Tokenizer tokenizer = new Tokenizer(new HashSet<String>(), functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testInfixTokenize5() throws Exception {
		String expr = "2*4.4+(2.3/12) - cos(x) *4+(20-2)";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4.4"), new OperatorToken("+", operations.get('+')), new ParenthesisToken("("),
				new NumberToken("2.3"), new OperatorToken("/", operations.get('/')), new NumberToken("12"),
				new ParenthesisToken(")"), new OperatorToken("-", operations.get('-')),
				new FunctionToken("cos", functions.get("cos")), new ParenthesisToken("("), new VariableToken("x"),
				new ParenthesisToken(")"), new OperatorToken("*", operations.get('*')), new NumberToken("4"),
				new OperatorToken("+", operations.get('+')), new ParenthesisToken("("), new NumberToken("20"),
				new OperatorToken("-", operations.get('-')), new NumberToken("2"), new ParenthesisToken(")") };
		Set<String> variableNames = new HashSet<String>();
		variableNames.add("x");
		Tokenizer tokenizer = new Tokenizer(variableNames, functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test(expected = UnparsableExpressionException.class)
	public void testInfixTokenize6() throws Exception {
		String expr = "2*4.4+(2.3/12)*y - cos(x) *4+(20-2)";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4.4"), new OperatorToken("+", operations.get('+')), new ParenthesisToken("("),
				new NumberToken("2.3"), new OperatorToken("/", operations.get('/')), new NumberToken("12"),
				new ParenthesisToken(")"), new OperatorToken("-", operations.get('-')),
				new FunctionToken("cos", functions.get("cos")), new ParenthesisToken("("), new VariableToken("x"),
				new ParenthesisToken(")"), new OperatorToken("*", operations.get('*')), new NumberToken("4"),
				new OperatorToken("+", operations.get('+')), new ParenthesisToken("("), new NumberToken("20"),
				new OperatorToken("-", operations.get('-')), new NumberToken("2"), new ParenthesisToken(")") };
		Set<String> variableNames = new HashSet<String>();
		variableNames.add("x");
		Tokenizer tokenizer = new Tokenizer(variableNames, functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

	@Test
	public void testInfixTokenize7() throws Exception {
		String expr = "2*4.4+(2.3/12) - cos(x) *4+(20-2)";
		Token[] expected = new Token[] { new NumberToken("2"), new OperatorToken("*", operations.get('*')),
				new NumberToken("4.4"), new OperatorToken("+", operations.get('+')), new ParenthesisToken("("),
				new NumberToken("2.3"), new OperatorToken("/", operations.get('/')), new NumberToken("12"),
				new ParenthesisToken(")"), new OperatorToken("-", operations.get('-')),
				new FunctionToken("cos", functions.get("cos")), new ParenthesisToken("("), new VariableToken("x"),
				new ParenthesisToken(")"), new OperatorToken("*", operations.get('*')), new NumberToken("4"),
				new OperatorToken("+", operations.get('+')), new ParenthesisToken("("), new NumberToken("20"),
				new OperatorToken("-", operations.get('-')), new NumberToken("2"), new ParenthesisToken(")") };
		Set<String> variableNames = new HashSet<String>();
		variableNames.add("x");
		Tokenizer tokenizer = new Tokenizer(variableNames, functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	public void testInfixTokenizeComplex1() throws Exception {
		String expr = "2+3i";
		Token[] expected = new Token[] { new NumberToken("2","3")};
		Tokenizer tokenizer = new Tokenizer(new HashSet<String>(), functions, operations);
		Token[] actual = tokenizer.getTokens(expr).toArray(new Token[0]);
		assertArrayEquals(expected, actual);
	}

}
