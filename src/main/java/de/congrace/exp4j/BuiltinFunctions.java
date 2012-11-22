package de.congrace.exp4j;

import java.util.Map;

public abstract class BuiltinFunctions {

	public static ABS ABS = new ABS();
	public static ACOS ACOS = new ACOS();
	public static ASIN ASIN = new ASIN();
	public static ATAN ATAN = new ATAN();
	public static CBRT CBRT = new CBRT();
	public static CEIL CEIL = new CEIL();
	public static COS COS = new COS();
	public static COSH COSH = new COSH();
	public static EXP EXP = new EXP();
	public static EXPM1 EXPM1 = new EXPM1();
	public static FLOOR FLOOR = new FLOOR();
	public static LOG LOG = new LOG();
	public static SIN SIN = new SIN();
	public static SINH SINH = new SINH();
	public static SQRT SQRT = new SQRT();
	public static TAN TAN = new TAN();
	public static TANH TANH = new TANH();

	public static class ABS extends CustomFunction {
		public ABS() {
			super("abs", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.abs(args[0]);
		}
	}

	public static class ACOS extends CustomFunction {
		public ACOS() {
			super("acos", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.acos(args[0]);
		}
	};

	public static class ASIN extends CustomFunction {
		public ASIN() {
			super("asin", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.asin(args[0]);
		}
	};

	public static class ATAN extends CustomFunction {
		public ATAN() {
			super("atan", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.atan(args[0]);
		}
	};

	public static class CBRT extends CustomFunction {
		public CBRT() {
			super("cbrt", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.cbrt(args[0]);
		}
	};

	public static class CEIL extends CustomFunction {
		public CEIL() {
			super("ceil", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.ceil(args[0]);
		}
	};

	public static class COS extends CustomFunction {
		public COS() {
			super("cos", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.cos(args[0]);
		}
	};

	public static class COSH extends CustomFunction {
		public COSH() {
			super("cosh", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.cosh(args[0]);
		}
	};

	public static class EXP extends CustomFunction {
		public EXP() {
			super("exp", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.exp(args[0]);
		}
	};

	public static class EXPM1 extends CustomFunction {
		public EXPM1() {
			super("expm1", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.expm1(args[0]);
		}
	};

	public static class FLOOR extends CustomFunction {
		public FLOOR() {
			super("floor", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.floor(args[0]);
		}
	};

	public static class LOG extends CustomFunction {
		public LOG() {
			super("log", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.log(args[0]);
		}
	};

	public static class SIN extends CustomFunction {
		public SIN() {
			super("sin", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.sin(args[0]);
		}
	};

	public static class SINH extends CustomFunction {
		public SINH() {
			super("sinh", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.sinh(args[0]);
		}
	};

	public static class SQRT extends CustomFunction {
		public SQRT() {
			super("sqrt", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.sqrt(args[0]);
		}
	};

	public static class TAN extends CustomFunction {
		public TAN() {
			super("tan", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.tan(args[0]);
		}
	};

	public static class TANH extends CustomFunction {
		public TANH() {
			super("tanh", 1, true);
		}

		@Override
		public double applyFunction(double... args) {
			return Math.tanh(args[0]);
		}
	};

}
