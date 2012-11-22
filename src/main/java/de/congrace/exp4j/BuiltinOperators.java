package de.congrace.exp4j;

public abstract class BuiltinOperators {
	/**
	 * Property name for unary precedence choice. You can set System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE,"false")
	 * in order to change evaluation from an expression like "-3^2" from "(-3)^2" to "-(3^2)"
	 */
	public static final String PROPERTY_UNARY_HIGH_PRECEDENCE = "exp4j.unary.precedence.high";

	private static ADD ADD = new ADD();
	private static SUB SUB = new SUB();
	private static DIV DIV = new DIV();
	private static MUL MUL = new MUL();
	private static MOD MOD = new MOD();
	private static POW POW = new POW();
	private static UMIN_LOW_PRECEDENCE UMIN_LOW_PRECEDENCE = new UMIN_LOW_PRECEDENCE();
	private static UMIN_HIGH_PRECEDENCE UMIN_HIGH_PRECEDENCE = new UMIN_HIGH_PRECEDENCE();

	public static CustomOperator getOperator(char symbol){
		switch (symbol){
		case '+':
			return ADD;
		case '-':
			return SUB;
		case '*':
			return MUL;
		case '/':
			return DIV;
		case '%':
			return MOD;
		case '^':
			return POW;
		case '\'':
			if (BuiltinOperators.isUnaryHighPrecedence()){
				return UMIN_HIGH_PRECEDENCE;
			}else{
				return UMIN_LOW_PRECEDENCE;
			}
		default:
			return null;
		}
	}

	
	private static class ADD extends CustomOperator {
		private ADD() {
			super("+");
		}

		@Override
		protected double applyOperation(double[] values) {
			return values[0] + values[1];
		}
	}

	private static class SUB extends CustomOperator {
		private SUB() {
			super("-");
		}

		@Override
		protected double applyOperation(double[] values) {
			return values[0] - values[1];
		}
	}

	private static class DIV extends CustomOperator {
		private DIV() {
			super("/", 3);
		}

		@Override
		protected double applyOperation(double[] values) {
			return values[0] / values[1];
		}
	}

	private static class MUL extends CustomOperator {
		private MUL() {
			super("*", 3);
		}

		@Override
		protected double applyOperation(double[] values) {
			return values[0] * values[1];
		}
	}

	private static class MOD extends CustomOperator {
		private MOD() {
			super("%", true, 3);
		}

		@Override
		protected double applyOperation(double[] values) {
			return values[0] % values[1];
		}
	}

	private static class UMIN_HIGH_PRECEDENCE extends CustomOperator {
		private UMIN_HIGH_PRECEDENCE() {
			super("\'", false, 7, 1);
		}

		@Override
		protected double applyOperation(double[] values) {
			return -values[0];
		}
	}

	private static class UMIN_LOW_PRECEDENCE extends CustomOperator {
		private UMIN_LOW_PRECEDENCE() {
			super("\'", false, 5, 1);
		}

		@Override
		protected double applyOperation(double[] values) {
			return -values[0];
		}
	}

	private static class POW extends CustomOperator {
		private POW() {
			super("^", false, 5, 2);
		}

		@Override
		protected double applyOperation(double[] values) {
			return Math.pow(values[0], values[1]);
		}
	}

	private static boolean isUnaryHighPrecedence() {
		return System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE) == null
				|| !System.getProperty(PROPERTY_UNARY_HIGH_PRECEDENCE).equals("false");

	}

	public static boolean isOperatorCharacter(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '\'' || c == '%' || c == '^'){
			return true;
		}
		return false;
	}

}
