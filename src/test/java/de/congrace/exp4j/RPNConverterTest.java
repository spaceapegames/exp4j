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

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class RPNConverterTest {

	static Map<String, CustomFunction> customFunctions = new HashMap<String, CustomFunction>();

	static Map<String, CustomOperator> operations = new HashMap<String, CustomOperator>();

	static Map<String, Number> variables = new LinkedHashMap<String, Number>();

	@BeforeClass
	public static void setup() throws Exception {
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
		customFunctions.put("log", log);
		customFunctions.put("sin", sine);

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
		CustomOperator div = new CustomOperator("/", 2) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() / values[1].getRealPart());
			}
		};
		CustomOperator mul = new CustomOperator("*", 2) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(values[0].getRealPart() / values[1].getRealPart());
			}
		};
		CustomOperator umin = new CustomOperator("\'", false, 4) {
			@Override
			protected Number applyOperation(Number[] values) {
				return new Number(- values[0].getRealPart());
			}
		};
		operations.put("+", add);
		operations.put("-", sub);
		operations.put("*", mul);
		operations.put("/", div);
		operations.put("\'", umin);
	}

	@Test
	public void testInfixTranslation1() throws Exception {
		String expr = "2 + 2";
		String expected = "2.0 2.0 +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation10() throws Exception {
		String expr = "log(1) / -sin(2)";
		String expected = "1.0 log 2.0 sin ' /";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation11() throws Exception {
		String expr = "24/log(1)-2";
		String expected = "24.0 1.0 log / 2.0 -";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation2() throws Exception {
		String expr = "1 + 2 * 4";
		String expected = "1.0 2.0 4.0 * +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation3() throws Exception {
		String expr = "3 - 4 * 5";
		String expected = "3.0 4.0 5.0 * -";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation4() throws Exception {
		String expr = "(1+2) * 4";
		String expected = "1.0 2.0 + 4.0 *";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation5() throws Exception {
		String expr = "(1+2) * (3-4) * 4";
		String expected = "1.0 2.0 + 3.0 4.0 - * 4.0 *";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation6() throws Exception {
		String expr = "1.23 + 3.14";
		String expected = "1.23 3.14 +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation7() throws Exception {
		String expr = "1.23 + 3.14 * 7";
		String expected = "1.23 3.14 7.0 * +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation8() throws Exception {
		String expr = "log(1) + sin(2)";
		String expected = "1.0 log 2.0 sin +";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}

	@Test
	public void testInfixTranslation9() throws Exception {
		String expr = "log(1) / sin(2)";
		String expected = "1.0 log 2.0 sin /";
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t" + actual);
		}
		assertEquals(actual, expected);
	}
	@Test
	public void testInfixTranslation12() throws Exception {
		String expr = "-min(4,0)+10";
		String expected = "4.0 0.0 min ' 10.0 +"; // ' is a placeholder for unary minus
		CustomFunction minFunction=new CustomFunction("min") {
			@Override
			public Number applyFunction(Number... args) {
				double tmp=Double.POSITIVE_INFINITY;
				for (int i=0;i<args.length;i++){
					if (args[i].getRealPart() < tmp) tmp=args[i].getRealPart();
				}
				return new Number(tmp);
			}
		};
		customFunctions.put("min", minFunction);
		String actual = RPNConverter.toRPNExpression(expr, variables, customFunctions, operations).expression;
		if (!actual.equals(expected)) {
			System.err.println("expected:\t" + expected);
			System.err.println("actual:\t\t" + actual);
		}
		assertEquals(actual, expected);
	}
}
